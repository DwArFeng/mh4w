package com.dwarfeng.jier.mh4w.core.control;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.dutil.develop.cfg.ConfigAdapter;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigObverser;
import com.dwarfeng.dutil.develop.cfg.io.PropConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.PropConfigSaver;
import com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel;
import com.dwarfeng.jier.mh4w.core.model.cm.BlockModel;
import com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultBackgroundModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultBlockModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultCoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultFileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultLoggerModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultMutilangModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultResourceModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultStateModel;
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.LoggerModel;
import com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ResourceModel;
import com.dwarfeng.jier.mh4w.core.model.cm.StateModel;
import com.dwarfeng.jier.mh4w.core.model.eum.BlockKey;
import com.dwarfeng.jier.mh4w.core.model.eum.CoreConfig;
import com.dwarfeng.jier.mh4w.core.model.eum.CountState;
import com.dwarfeng.jier.mh4w.core.model.eum.LoggerStringKey;
import com.dwarfeng.jier.mh4w.core.model.eum.ResourceKey;
import com.dwarfeng.jier.mh4w.core.model.io.XmlBlockLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlLoggerLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlMutilangLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlResourceLoader;
import com.dwarfeng.jier.mh4w.core.model.obv.LoggerAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.LoggerObverser;
import com.dwarfeng.jier.mh4w.core.model.obv.MutilangAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.MutilangObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.AbstractFlow;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultFinishedFlowTaker;
import com.dwarfeng.jier.mh4w.core.model.struct.FinishedFlowTaker;
import com.dwarfeng.jier.mh4w.core.model.struct.Flow;
import com.dwarfeng.jier.mh4w.core.model.struct.ProcessException;
import com.dwarfeng.jier.mh4w.core.model.struct.Resource;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController;
import com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController;
import com.dwarfeng.jier.mh4w.core.view.gui.DetailFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.MainFrame;
import com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameObverser;
import com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser;

/**
 * ��ʱͳ�������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class Mh4w {
	
	
	/**
	 * �����õ�����������
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws ProcessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		new Mh4w().start();
	}
	
	/**����İ汾*/
	public final static Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	
	/**�����ʵ���б����ڳ�������*/
	private static final Set<Mh4w> INSTANCES  = Collections.synchronizedSet(new HashSet<>());
	/**����ƽ̨�Ľ��̹���*/
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("mh4w");
	
	/**����Ĺ����ṩ��*/
	private final FlowProvider flowProvider = new FlowProvider();
	/**������ֹʱ�Ĺ���*/
	private final Map<String, Thread> shutdownHooks = Collections.synchronizedMap(new HashMap<>());

	/**���������*/
	private final Manager manager;
	/**�����״̬*/
	private final AtomicReference<RuntimeState> state;
	
	
	public Mh4w() {
		this.manager = new Manager();
		this.state = new AtomicReference<RuntimeState>(RuntimeState.NOT_START);
		
		//Ϊ�Լ��������á�
		INSTANCES.add(this);
	}
	
	/**
	 * ��������
	 * @throws ProcessException �����쳣��
	 * @throws IllegalStateException �����Ѿ���ʼ��
	 */
	public void start() throws ProcessException{
		//������ʼ������
		final Flow initializeFlow = flowProvider.newInitializeFlow();
		manager.getBackgroundModel().submit(initializeFlow);
		while(! initializeFlow.isDone()){
			try {
				initializeFlow.waitFinished();
			} catch (InterruptedException ignore) {
				//�ж�ҲҪ���ջ�����
			}
		}
		if(initializeFlow.getThrowable() != null){
			throw new ProcessException("��ʼ������ʧ��", initializeFlow.getThrowable());
		}
	}
	
	/**
	 * ���س����״̬��
	 * @return �����״̬��
	 */
	public RuntimeState getState() {
		return state.get();
	}

	private void setState(RuntimeState state){
		this.state.set(state);
	}
	
	/**
	 * ���Թرձ�����
	 * <p> ���ø÷����󣬻᳢�Թرճ��������������رյ���������رգ����򣬻�ѯ���û���
	 * �������û�����رհ�ť������
	 */
	public void tryExit(){
		manager.getBackgroundModel().submit(flowProvider.newClosingFlow());
	}

	private void exit(){
		THREAD_FACTORY.newThread(new Exitor()).start();
	}

	private final class Manager {

		//model
		private ResourceModel resourceModel = new DefaultResourceModel();
		private LoggerModel loggerModel = new DefaultLoggerModel();
		private CoreConfigModel coreConfigModel = new DefaultCoreConfigModel();
		private BackgroundModel backgroundModel = new DefaultBackgroundModel();
		private BlockModel blockModel = new DefaultBlockModel();
		private MutilangModel loggerMutilangModel = new DefaultMutilangModel();
		private MutilangModel labelMutilangModel = new DefaultMutilangModel();
		private FileSelectModel fileSelectModel = new DefaultFileSelectModel();
		private StateModel stateModel = new DefaultStateModel();
		//structs
		private FinishedFlowTaker finishedFlowTaker = new DefaultFinishedFlowTaker(backgroundModel);
		//obvs
		private LoggerObverser loggerObverser = new LoggerAdapter() {
			@Override
			public void fireUpdated() {
				finishedFlowTaker.setLogger(loggerModel.getLogger());
			}
		};
		private MutilangObverser loggerMutilangObverser = new MutilangAdapter() {
			@Override
			public void fireUpdated() {
				finishedFlowTaker.setMutilang(loggerMutilangModel.getMutilang());
			}
		};
		private ConfigObverser coreConfigObverser = new ConfigAdapter() {
			@Override
			public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue,String validValue) {
				if(configKey.equals(CoreConfig.MUTILANG_LOGGER.getConfigKey())){
					try {
						loggerMutilangModel.setCurrentLocale(coreConfigModel.getLoggerMutilangLocale());
						loggerMutilangModel.update();
					} catch (ProcessException e) {
						loggerModel.getLogger().warn(loggerMutilangModel.getMutilang().getString(LoggerStringKey.Update_LoggerMutilang_1.getName()), e);
					}
				}
			}
		};
		//GuiControllers
		private GuiController guiController = new AbstractGuiController() {

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#newMainFrameImpl()
			 */
			@Override
			protected MainFrame newMainFrameImpl() {
				MainFrame mainFrame = new MainFrame(
						labelMutilangModel.getMutilang(),
						fileSelectModel,
						stateModel
				);
				mainFrame.addObverser(mainFrameObverser);
				return mainFrame;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#disposeMainFrameImpl()
			 */
			@Override
			protected boolean disposeMainFrameImpl() {
				mainFrame.removeObverser(mainFrameObverser);
				mainFrame.dispose();
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#newDetailFrameImpl()
			 */
			@Override
			protected DetailFrame newDetailFrameImpl() {
				DetailFrame detailFrame = new DetailFrame();
				detailFrame.addObverser(detailFrameObverser);
				return detailFrame;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#disposeDetailFrameImpl()
			 */
			@Override
			protected boolean disposeDetailFrameImpl() {
				detailFrame.removeObverser(detailFrameObverser);
				detailFrame.dispose();
				return true;
			}
		};
		//GUI obversers
		private final MainFrameObverser mainFrameObverser = new MainFrameAdapter() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser#fireWindowClosing()
			 */
			@Override
			public void fireWindowClosing() {
				manager.getBackgroundModel().submit(flowProvider.newClosingFlow());
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser#fireSelectAttendanceFile()
			 */
			@Override
			public void fireSelectAttendanceFile() {
				manager.getBackgroundModel().submit(flowProvider.newSelectAttendanceFileFlow());
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser#fireSelectWorkticketFile()
			 */
			@Override
			public void fireSelectWorkticketFile() {
				manager.getBackgroundModel().submit(flowProvider.newSelectWorkticketFileFlow());
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser#fireCountReset()
			 */
			@Override
			public void fireCountReset() {
				manager.getBackgroundModel().submit(flowProvider.newCountResetFlow());
			}

			/* 
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter#fireHideDetailFrame()
			 */
			@Override
			public void fireHideDetailFrame() {
				manager.getBackgroundModel().submit(flowProvider.newHideDetailFrameFlow());
			}

			/* 
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter#fireShowDetailFrame()
			 */
			@Override
			public void fireShowDetailFrame() {
				manager.getBackgroundModel().submit(flowProvider.newShowDetailFrameFlow());
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter#fireCount()
			 */
			@Override
			public void fireCount() {
				manager.getBackgroundModel().submit(flowProvider.newCountFlow());
			};
			
		};
		private final DetailFrameObverser detailFrameObverser = new DetailFrameAdapter() {
			
			
			
		};
		
		
		public Manager() {
			coreConfigModel.addAll(Arrays.asList(CoreConfig.values()));
			loggerMutilangModel.setDefaultMutilangInfo(Constants.getDefaultLoggerMutilangInfo());
			loggerMutilangModel.setDefaultValue(Constants.getDefaultMissingString());
			loggerMutilangModel.setSupportedKeys(Constants.getLoggerMutilangSupportedKeys());
			labelMutilangModel.setDefaultMutilangInfo(Constants.getDefaultLabelMutilangInfo());
			labelMutilangModel.setDefaultValue(Constants.getDefaultMissingString());
			labelMutilangModel.setSupportedKeys(Constants.getLabelMutilangSupportedKeys());
			
			loggerModel.addObverser(loggerObverser);
			loggerMutilangModel.addObverser(loggerMutilangObverser);
			coreConfigModel.addObverser(coreConfigObverser);
			
			try {
				loggerMutilangModel.update();
				labelMutilangModel.update();
			} catch (ProcessException e) {
				//δ��ʼ��֮ǰ��������ģ��ʹ�õ��ǹ̻��ڳ����е����ݣ������ܳ����쳣��
				e.printStackTrace();
			}
		}

		/**
		 * �ͷ���Դ��
		 */
		public void dispose() {
			loggerModel.removeObverser(loggerObverser);
			loggerMutilangModel.removeObverser(loggerMutilangObverser);
			coreConfigModel.removeObverser(coreConfigObverser);
		}

		/**
		 * @return the resourceModel
		 */
		public ResourceModel getResourceModel() {
			return resourceModel;
		}

		/**
		 * @return the loggerModel
		 */
		public LoggerModel getLoggerModel() {
			return loggerModel;
		}

		/**
		 * @return the coreConfigModel
		 */
		public CoreConfigModel getCoreConfigModel() {
			return coreConfigModel;
		}

		/**
		 * @return the backgroundModel
		 */
		public BackgroundModel getBackgroundModel() {
			return backgroundModel;
		}

		/**
		 * @return the blockModel
		 */
		public BlockModel getBlockModel(){
			return blockModel;
		}

		/**
		 * @return the loggerMutilangModel
		 */
		public MutilangModel getLoggerMutilangModel() {
			return loggerMutilangModel;
		}

		/**
		 * @return the labelMutilangModel
		 */
		public MutilangModel getLabelMutilangModel() {
			return labelMutilangModel;
		}

		/**
		 * @return the fileSelectModel
		 */
		public FileSelectModel getFileSelectModel() {
			return fileSelectModel;
		}

		/**
		 * @return the stateModel
		 */
		public StateModel getStateModel() {
			return stateModel;
		}

		/**
		 * @return the finishedFlowTaker
		 */
		public FinishedFlowTaker getFinishedFlowTaker() {
			return finishedFlowTaker;
		}

		/**
		 * @return the guiController
		 */
		public GuiController getGuiController() {
			return guiController;
		}
		
	}
	
	
	
	
	
	
	private final class FlowProvider {

		/**
		 * ��ȡһ���µĳ����ʼ��ʱʹ�õĹ��̡�
		 * @return �µĳ����ʼ��ʱʹ�õĺ�̨���̡�
		 */
		public Flow newInitializeFlow() {
			return new InitializeFlow();
		}
		
		/**
		 * ��ȡһ���µĳ���ر�����
		 * @return �µĳ���ر�����
		 */
		public Flow newClosingFlow() {
			return new WindowClosingFlow();
		}

		/**
		 * ��ȡһ���µ�ѡ������ļ�����
		 * @return �µĳ����ļ�����
		 */
		public Flow newSelectAttendanceFileFlow() {
			return new SelectAttendanceFileFlow();
		}

		/**
		 * ��ȡһ���µ�ѡ��Ʊ�ļ�����
		 * @return �µ�ѡ��Ʊ�ļ�����
		 */
		public Flow newSelectWorkticketFileFlow() {
			return new SelectWorkticketFileFlow();
		}

		/**
		 * ��ȡһ���µ�ͳ�Ƹ�λ����
		 * @return �µ�ͳ�Ƹ�λ����
		 */
		public Flow newCountResetFlow() {
			return new CountResetFlow();
		}

		/**
		 * ��ȡһ���µ���ʾ��ϸ�������
		 * @return �µ���ʾ��ϸ�������
		 */
		public Flow newShowDetailFrameFlow() {
			return new ShowDetailFrameFlow();
		}

		/**
		 * ��ȡһ���µ�������ϸ�������
		 * @return �µ�������ϸ�������
		 */
		public Flow newHideDetailFrameFlow() {
			return new HideDetailFrameFlow();
		}

		/**
		 * ��ȡһ���µ�ͳ������
		 * @return �µ�ͳ������
		 */
		public Flow newCountFlow() {
			return new CountFlow();
		}

		/**
		 * �ڲ�������̡�
		 * <p> ���峣�õ��ڲ��÷�����
		 * @author DwArFeng
		 * @since 0.0.0-alpha
		 */
		private abstract class AbstractInnerFlow extends AbstractFlow{
			
			private final String blockKey;
			
			/**
			 * ��ʵ����
			 * @param blockKey �赲��, ����Ϊ <code>null</code>��
			 * @param initMessage ��ʼ����Ϣ������Ϊ <code>null</code>��
			 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
			 */
			public AbstractInnerFlow(BlockKey blockKey, String initMessage) {
				this(blockKey, initMessage, 0, 0, false, false);
			}
			
			/**
			 * ��ʵ����
			 * @param blockKey �赲��������Ϊ <code>null</code>��
			 * @param initMessage ��ʼ����Ϣ������Ϊ <code>null</code>��
			 * @param progress ��ǰ���ȡ�
			 * @param totleProgress �ܽ��ȡ�
			 * @param determinateFlag �Ƿ�Ϊ������֪�����̡�
			 * @param cancelableFlag �Ƿ��ܹ���ȡ����
			 */
			public AbstractInnerFlow(BlockKey blockKey, String initMessage, int progress, int totleProgress, boolean determinateFlag, boolean cancelableFlag ){
				super(progress, totleProgress, determinateFlag, cancelableFlag);
				Objects.requireNonNull(blockKey, "��ڲ��� blockKey ����Ϊ null��");
				Objects.requireNonNull(initMessage, "��ڲ��� initMessage ����Ϊ null��");
				this.blockKey = blockKey.getName();
				setMessage(initMessage);
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.model.struct.AbstractFlow#process()
			 */
			@Override
			protected void process() {
				manager.getBlockModel().getBlock().block(blockKey);
				try{
					processImpl();
				}finally {
					manager.getBlockModel().getBlock().unblock(blockKey);
				}
			}
			
			/**
			 * ������ʵ�ַ�����
			 * <p> �÷�������Ҫ�Ĵ�������
			 * <p> �÷����������׳��κ��쳣��
			 */
			protected abstract void processImpl();
			
			/**
			 * ����ָ���ļ�¼��������Ӧ���ַ�����
			 * @param loggerStringKey ָ���ļ�¼������
			 * @return ��¼������Ӧ���ַ�����
			 */
			protected String getLabel(LoggerStringKey loggerStringKey){
				return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
			}
		
			/**
			 * ����ָ����¼������ format �ַ�����
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param args ָ���� format ������
			 * @return ָ���ļ�¼������ format �ַ�����
			 */
			protected String formatLabel(LoggerStringKey loggerStringKey, Object... args){
				return String.format(manager.getLoggerMutilangModel().getMutilang().getString(
						loggerStringKey.getName()),args);
			}
			
			/**
			 * ���¼��������һ��INFO����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 */
			protected void info(LoggerStringKey loggerStringKey){
				manager.getLoggerModel().getLogger().info(getLabel(loggerStringKey));
			}
		
			/**
			 * ���¼����formatһ��INFO����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param args format������
			 */
			protected void formatInfo(LoggerStringKey loggerStringKey, Object... args){
				manager.getLoggerModel().getLogger().info(formatLabel(loggerStringKey, args));	
			}
		
			/**
			 * ���¼��������һ��WARN����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param throwable ָ���Ŀ��׳�����
			 */
			protected void warn(LoggerStringKey loggerStringKey, Throwable throwable){
				manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey), throwable);
			}
		
			/**
			 * ��ȡָ������Ӧ����Դ��
			 * @param resourceKey ָ���ļ���
			 * @return ָ���ļ���Ӧ����Դ��
			 */
			protected Resource getResource(ResourceKey resourceKey){
				return manager.getResourceModel().get(resourceKey.getName());
			}
			
			/**
			 * ������ϢΪָ������Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 */
			protected void message(LoggerStringKey loggerStringKey){
				setMessage(getLabel(loggerStringKey));
			}
			
			/**
			 * ��ʽ��������Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param args format ������
			 */
			protected void formatMessage(LoggerStringKey loggerStringKey, Object... args){
				setMessage(formatLabel(loggerStringKey, args));	
			}
			
		}

		/**
		 * ���ܻ�ı�����״̬������
		 * <p> ��װ�˳��õ�״̬���ķ�����
		 * @author DwArFeng
		 * @since 0.0.0-alpha
		 */
		private abstract class AbstractMayChangeStateFlow extends AbstractInnerFlow{

			public AbstractMayChangeStateFlow(BlockKey blockKey, String initMessage) {
				super(blockKey, initMessage);
			}

			/**
			 * �������Ƿ���Խ���ͳ���ˡ�
			 * @return �����Ƿ���Խ���ͳ���ˡ�
			 */
			protected boolean isReadyForCount(){
				if(Objects.isNull(manager.getFileSelectModel().getAttendanceFile())) return false;
				if(Objects.isNull(manager.getFileSelectModel().getWorkticketFile())) return false;
				
				return true;
			}

			/**
			 * ���ܻᵼ�³����ͳ�ƽ�����ڡ�
			 * <p> �÷�����������ͳ��״̬�����״̬���ǻ�δͳ�ƣ�����ͳ�ƽ�����ڡ�
			 * @return �ò����Ƿ�����˳����ͳ�ƽ�����ڡ�
			 */
			protected boolean mayCountResultOutdated(){
				if(manager.getStateModel().getCountState().equals(CountState.NOT_START)){
					return false;
				}else{
					manager.getStateModel().setCountResultOutdated(true);
					return true;
				}
			}
			
		}
		
		private final class InitializeFlow extends AbstractInnerFlow{
			
			public InitializeFlow() {
				super(BlockKey.INITIALIZE, manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_3.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.control.Mh4w.FlowProvider.AbstractInnerFlow#subProcess()
			 */
			@Override
			protected void processImpl() {
				try{
					if(getState() != RuntimeState.NOT_START){
						throw new IllegalStateException("�����Ѿ��������Ѿ�����");
					}
					
					//����ģ�ͣ���ʱ�Ķ�����ģ�ͺͼ�¼��ģ�ͱ�����ΪĬ��ֵ��
					try{
						manager.getLoggerModel().update();
						manager.getLabelMutilangModel().update();
						manager.getLoggerMutilangModel().update();
						manager.getBlockModel().update();
					}catch (ProcessException ignore) {
						//��ʱ��ΪĬ��ֵ���������׳��쳣��
					}
					
					//���س������Դģ��
					info(LoggerStringKey.Mh4w_FlowProvider_3);
					message(LoggerStringKey.Mh4w_FlowProvider_3);
					XmlResourceLoader resourceLoader = null;
					try{
						resourceLoader = new XmlResourceLoader(Mh4w.class.getResourceAsStream("/com/dwarfeng/jier/mh4w/resource/paths.xml"));
						resourceLoader.load(manager.getResourceModel());
					}finally{
						if(Objects.nonNull(resourceLoader)){
							resourceLoader.close();
						}
					}
					
					//���س����еļ�¼��ģ�͡�
					info(LoggerStringKey.Mh4w_FlowProvider_5);
					message(LoggerStringKey.Mh4w_FlowProvider_5);
					if(manager.getLoggerModel().getLoggerContext() != null){
						manager.getLoggerModel().getLoggerContext().stop();
					}
					XmlLoggerLoader loggerLoader = null;
					try{
						loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
						loggerLoader.load(manager.getLoggerModel());
					}catch (IOException e) {
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.LOGGER_SETTING).reset();
						loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
						loggerLoader.load(manager.getLoggerModel());
					}finally {
						if(Objects.nonNull(loggerLoader)){
							loggerLoader.close();
						}
					}
					try{
						manager.getLoggerModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_Logger_1, e);
					}
					
					//���ؼ�¼�����������á�
					info(LoggerStringKey.Mh4w_FlowProvider_7);
					message(LoggerStringKey.Mh4w_FlowProvider_7);
					XmlMutilangLoader loggerMutilangLoader = null;
					try{
						loggerMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LOGGER_SETTING).openInputStream());
						loggerMutilangLoader.load(manager.getLoggerMutilangModel());
					}catch (IOException e) {
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.MUTILANG_LOGGER_SETTING).reset();
						loggerMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LOGGER_SETTING).openInputStream());
						loggerMutilangLoader.load(manager.getLoggerMutilangModel());
					}finally {
						if(Objects.nonNull(loggerMutilangLoader)){
							loggerMutilangLoader.close();
						}
					}
					try{
						manager.getLoggerMutilangModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_LoggerMutilang_1, e);
					}
					
					//���س���ĺ������á�
					info(LoggerStringKey.Mh4w_FlowProvider_6);
					message(LoggerStringKey.Mh4w_FlowProvider_6);
					PropConfigLoader coreConfigLoader = null;
					try{
						coreConfigLoader = new PropConfigLoader(getResource(ResourceKey.CONFIGURATION_CORE).openInputStream());
						coreConfigLoader.load(manager.getCoreConfigModel());
					}catch (IOException e) {
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.CONFIGURATION_CORE).reset();
						coreConfigLoader = new PropConfigLoader(getResource(ResourceKey.CONFIGURATION_CORE).openInputStream());
						coreConfigLoader.load(manager.getCoreConfigModel());
					}finally {
						if(Objects.nonNull(coreConfigLoader)){
							coreConfigLoader.close();
						}
					}
					
					//�����赲ģ���ֵ�
					info(LoggerStringKey.Mh4w_FlowProvider_10);
					message(LoggerStringKey.Mh4w_FlowProvider_10);
					XmlBlockLoader blockLoader = null;
					try{
						blockLoader = new XmlBlockLoader(Mh4wUtil.newBlockDictionary());
						blockLoader.load(manager.getBlockModel());
					}finally {
						if(Objects.nonNull(blockLoader)){
							blockLoader.close();
						}
					}
					
					//���ر�ǩ���������á�
					info(LoggerStringKey.Mh4w_FlowProvider_9);
					message(LoggerStringKey.Mh4w_FlowProvider_9);
					XmlMutilangLoader labelMutilangLoader = null;
					try{
						labelMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LABEL_SETTING).openInputStream());
						labelMutilangLoader.load(manager.getLabelMutilangModel());
					}catch(IOException e){
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.MUTILANG_LABEL_SETTING).reset();
						labelMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LABEL_SETTING).openInputStream());
						labelMutilangLoader.load(manager.getLabelMutilangModel());
					}finally{
						if(Objects.nonNull(labelMutilangLoader)){
							labelMutilangLoader.close();
						}
					}
					try{
						manager.getLabelMutilangModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_LabelMutilang_1, e);
					}
					
					//������ͼ����ʹ��ɼ���
					info(LoggerStringKey.Mh4w_FlowProvider_8);
					message(LoggerStringKey.Mh4w_FlowProvider_8);
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().newMainFrame();
							manager.getGuiController().setMainFrameVisible(true);
							manager.getGuiController().newDetailFrame();
						}
					});
					
					//���óɹ���Ϣ
					message(LoggerStringKey.Mh4w_FlowProvider_1);
					setState(RuntimeState.RUNNING);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_2);
				}
			}
			
		}

		private final class WindowClosingFlow extends AbstractInnerFlow{
		
			public WindowClosingFlow() {
				super(BlockKey.CLOSING,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_11.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.Mh4w.FlowProvider.AbstractInnerFlow#processImpl()
			 */
			@Override
			protected void processImpl() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_11);
					exit();
					
				}catch (Exception e) {
					message(LoggerStringKey.Mh4w_FlowProvider_12);
				}
			}
			
		}

		private final class SelectAttendanceFileFlow extends AbstractMayChangeStateFlow{
		
			public SelectAttendanceFileFlow() {
				super(BlockKey.SELECT_ATTENDANCE_FILE,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_13.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.Mh4w.FlowProvider.AbstractInnerFlow#processImpl()
			 */
			@Override
			protected void processImpl() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					//������Ŀ¼
					File directory = new File(System.getProperty("user.dir"));
					manager.getFileSelectModel().getLock().writeLock().lock();
					try{
						if(Objects.nonNull(manager.getFileSelectModel().getWorkticketFile())){
							directory = manager.getFileSelectModel().getWorkticketFile().getParentFile();
						}else if (Objects.nonNull(manager.getFileSelectModel().getAttendanceFile())) {
							directory = manager.getFileSelectModel().getAttendanceFile().getParentFile();
						}
					}finally {
						manager.getFileSelectModel().getLock().writeLock().unlock();
					}

					//�����ļ�ɸѡ��
					FileFilter[] fileFilters = new FileFilter[]{
							new FileNameExtensionFilter(getLabel(LoggerStringKey.Mh4w_FlowProvider_15), "xls")
					};
					
					//�����������ļ�ɸѡ��
					boolean acceptAllFileFilter = false;
					
					//�������ļ���ѡ
					boolean mutiSelectionEnabled = false;
					
					//ֻ����ѡ���ļ�
					int fileSelectionMode = JFileChooser.FILES_ONLY;
					
					//����ļ�
					File[] files = manager.getGuiController().askFile(directory, fileFilters, acceptAllFileFilter, mutiSelectionEnabled, fileSelectionMode);
					
					//����ȡ���ļ�������ģ���в������¼
					if(files.length == 0){
						info(LoggerStringKey.Mh4w_FlowProvider_17);
					}else{
						manager.getFileSelectModel().setAttendanceFile(files[0]);
						formatInfo(LoggerStringKey.Mh4w_FlowProvider_18, files[0].getAbsolutePath());
					}
					
					//����״̬ģ��-�Ƿ��ܹ�����ͳ����
					manager.getStateModel().setReadyForCount(isReadyForCount());
					
					message(LoggerStringKey.Mh4w_FlowProvider_16);
					
				}catch (Exception e) {
					message(LoggerStringKey.Mh4w_FlowProvider_14);
				}finally {
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().attendanceClickUnlock();
						}
					});
				}
			}
			
		}

		private final class SelectWorkticketFileFlow extends AbstractMayChangeStateFlow{
		
			public SelectWorkticketFileFlow() {
				super(BlockKey.SELECT_WORKTICKET_FILE,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_19.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.Mh4w.FlowProvider.AbstractInnerFlow#processImpl()
			 */
			@Override
			protected void processImpl() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					//������Ŀ¼
					File directory = new File(System.getProperty("user.dir"));
					manager.getFileSelectModel().getLock().writeLock().lock();
					try{
						if (Objects.nonNull(manager.getFileSelectModel().getAttendanceFile())) {
							directory = manager.getFileSelectModel().getAttendanceFile().getParentFile();
						}else if(Objects.nonNull(manager.getFileSelectModel().getWorkticketFile())){
							directory = manager.getFileSelectModel().getWorkticketFile().getParentFile();
						}
					}finally {
						manager.getFileSelectModel().getLock().writeLock().unlock();
					}

					//�����ļ�ɸѡ��
					FileFilter[] fileFilters = new FileFilter[]{
							new FileNameExtensionFilter(getLabel(LoggerStringKey.Mh4w_FlowProvider_15), "xls")
					};
					
					//�����������ļ�ɸѡ��
					boolean acceptAllFileFilter = false;
					
					//�������ļ���ѡ
					boolean mutiSelectionEnabled = false;
					
					//ֻ����ѡ���ļ�
					int fileSelectionMode = JFileChooser.FILES_ONLY;
					
					//����ļ�
					File[] files = manager.getGuiController().askFile(directory, fileFilters, acceptAllFileFilter, mutiSelectionEnabled, fileSelectionMode);
					
					//����ȡ���ļ�������ģ���в������¼
					if(files.length == 0){
						info(LoggerStringKey.Mh4w_FlowProvider_17);
					}else{
						manager.getFileSelectModel().setWorkticketFile(files[0]);
						formatInfo(LoggerStringKey.Mh4w_FlowProvider_18, files[0].getAbsolutePath());
					}
					
					//����״̬ģ��-�Ƿ��ܹ�����ͳ����
					manager.getStateModel().setReadyForCount(isReadyForCount());
					
					message(LoggerStringKey.Mh4w_FlowProvider_20);
					
				}catch (Exception e) {
					message(LoggerStringKey.Mh4w_FlowProvider_21);
				}finally {
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().workticketClickUnlock();
						}
					});
				}
			}
			
		}

		private final class CountResetFlow extends AbstractInnerFlow{
		
			public CountResetFlow() {
				super(BlockKey.RESET_COUNT,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_22.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.Mh4w.FlowProvider.AbstractInnerFlow#processImpl()
			 */
			@Override
			protected void processImpl() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					manager.getFileSelectModel().setAttendanceFile(null);
					manager.getFileSelectModel().setWorkticketFile(null);
					manager.getStateModel().setCountResultOutdated(false);
					manager.getStateModel().setCountState(CountState.NOT_START);
					manager.getStateModel().setReadyForCount(false);
					
					message(LoggerStringKey.Mh4w_FlowProvider_23);
					
				}catch (Exception e) {
					message(LoggerStringKey.Mh4w_FlowProvider_24);
				}finally {
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().workticketClickUnlock();
						}
					});
				}
			}
			
		}

		private final class ShowDetailFrameFlow extends AbstractInnerFlow{
		
			public ShowDetailFrameFlow() {
				super(BlockKey.SHOW_DETAIL_FRAME,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_25.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.Mh4w.FlowProvider.AbstractInnerFlow#processImpl()
			 */
			@Override
			protected void processImpl() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_25);
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.guiController.setDetailFrameVisible(true);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_29);
					
				}catch (Exception e) {
					message(LoggerStringKey.Mh4w_FlowProvider_26);
				}
			}
			
		}

		private final class HideDetailFrameFlow extends AbstractInnerFlow{
		
			public HideDetailFrameFlow() {
				super(BlockKey.HIDE_DETAIL_FRAME,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_27.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.Mh4w.FlowProvider.AbstractInnerFlow#processImpl()
			 */
			@Override
			protected void processImpl() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_27);
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.guiController.setDetailFrameVisible(false);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_30);
					
				}catch (Exception e) {
					message(LoggerStringKey.Mh4w_FlowProvider_28);
				}
			}
			
		}

		private final class CountFlow extends AbstractMayChangeStateFlow{
		
			public CountFlow() {
				super(BlockKey.SELECT_ATTENDANCE_FILE,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_13.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.Mh4w.FlowProvider.AbstractInnerFlow#processImpl()
			 */
			@Override
			protected void processImpl() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					
					
					message(LoggerStringKey.Mh4w_FlowProvider_16);
					
				}catch (Exception e) {
					message(LoggerStringKey.Mh4w_FlowProvider_14);
				}finally {
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().attendanceClickUnlock();
						}
					});
				}
			}
			
		}
	
	}






	private final class Exitor implements Runnable{
	
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {	
			//ֹͣ��̨ģ�ͺ͹�������ʱģ�͡�
			info(LoggerStringKey.Mh4w_Exitor_2);
			manager.getBackgroundModel().shutdown();
			manager.getFinishedFlowTaker().shutdown();
			
			//�ȴ�50���룬��ʱ��̨ģ�ͺ͹�������ʱģ���е�ִ����Ӧ�û���Ȼ�սᡣ
			try {
				Thread.sleep(50);
			} catch (InterruptedException ignore) {
				//�ж�ҲҪ���ջ�������
			}
			
			boolean waitFlag = false;
			if(! manager.getBackgroundModel().getExecutorService().isTerminated()){
				warn(LoggerStringKey.Mh4w_Exitor_1);
				waitFlag = true;
			}
			
			if(waitFlag){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignore) {
					//�ж�ҲҪ���ջ�������
				}
				
				if(! manager.getBackgroundModel().getExecutorService().isTerminated()){
					warn(LoggerStringKey.Mh4w_Exitor_3);
				}
			}

			//�����������
			info(LoggerStringKey.Mh4w_Exitor_7);
			PropConfigSaver coreConfigSaver = null;
			try{
				try{
					coreConfigSaver = new PropConfigSaver(getResource(ResourceKey.CONFIGURATION_CORE).openOutputStream());
					coreConfigSaver.save(manager.getCoreConfigModel());
				}catch (IOException e) {
					warn(LoggerStringKey.Mh4w_Exitor_6, e);
					getResource(ResourceKey.CONFIGURATION_CORE).reset();
					coreConfigSaver = new PropConfigSaver(getResource(ResourceKey.CONFIGURATION_CORE).openOutputStream());
					coreConfigSaver.save(manager.getCoreConfigModel());
				}finally{
					if(Objects.nonNull(coreConfigSaver)){
						coreConfigSaver.close();
					}
				}
				
			}catch (Exception e) {
				warn(LoggerStringKey.Mh4w_Exitor_8, e);
			}
			
			//�ͷŽ���
			info(LoggerStringKey.Mh4w_Exitor_4);
			try {
				Mh4wUtil.invokeAndWaitInEventQueue(new Runnable() {
					@Override
					public void run() {
						manager.getGuiController().disposeMainFrame();
					}
				});
			} catch (InvocationTargetException ignore) {
			} catch (InterruptedException ignore) {
				//�ж�ҲҪ���ջ�������
			}
			
			//�ͷ�ģ��
			info(LoggerStringKey.Mh4w_Exitor_5);
			manager.dispose();
			
			//������������
			INSTANCES.remove(Mh4w.this);
	
		}
		
	
		private void info(LoggerStringKey loggerStringKey){
			manager.getLoggerModel().getLogger().info(getLabel(loggerStringKey));
		}
	
		private void warn(LoggerStringKey loggerStringKey){
			manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey));
		}
		
		private void warn(LoggerStringKey loggerStringKey, Throwable e){
			manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey), e);
		}
	
		private String getLabel(LoggerStringKey loggerStringKey){
			return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
		}
	
		/**
		 * ��ȡָ������Ӧ����Դ��
		 * @param resourceKey ָ���ļ���
		 * @return ָ���ļ���Ӧ����Դ��
		 */
		private Resource getResource(ResourceKey resourceKey){
			return manager.getResourceModel().get(resourceKey.getName());
		}
		
	}

}
