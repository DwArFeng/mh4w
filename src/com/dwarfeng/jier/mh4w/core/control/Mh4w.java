package com.dwarfeng.jier.mh4w.core.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
import com.dwarfeng.jier.mh4w.core.model.cm.BackgroundModel;
import com.dwarfeng.jier.mh4w.core.model.cm.BlockModel;
import com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultBackgroundModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultBlockModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultCoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultLoggerModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultMutilangModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultResourceModel;
import com.dwarfeng.jier.mh4w.core.model.cm.LoggerModel;
import com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ResourceModel;
import com.dwarfeng.jier.mh4w.core.model.eum.BlockKey;
import com.dwarfeng.jier.mh4w.core.model.eum.CoreConfig;
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






	private final class Manager {

		//model
		private ResourceModel resourceModel = new DefaultResourceModel();
		private LoggerModel loggerModel = new DefaultLoggerModel();
		private CoreConfigModel coreConfigModel = new DefaultCoreConfigModel();
		private BackgroundModel backgroundModel = new DefaultBackgroundModel();
		private BlockModel blockModel = new DefaultBlockModel();
		private MutilangModel loggerMutilangModel = new DefaultMutilangModel();
		private MutilangModel labelMutilangModel = new DefaultMutilangModel();
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
		 * @return the finishedFlowTaker
		 */
		public FinishedFlowTaker getFinishedFlowTaker() {
			return finishedFlowTaker;
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
					subProcess();
				}finally {
					manager.getBlockModel().getBlock().unblock(blockKey);
				}
			}
			
			/**
			 * �Ӵ�������
			 * <p> �÷�������Ҫ�Ĵ�������
			 * <p> �÷����������׳��κ��쳣��
			 */
			protected abstract void subProcess();
			
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

		private final class InitializeFlow extends AbstractInnerFlow{
			
			public InitializeFlow() {
				super(BlockKey.INITIALIZE, manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.Mh4w_FlowProvider_3.getName()));
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.control.Mh4w.FlowProvider.AbstractInnerFlow#subProcess()
			 */
			@Override
			protected void subProcess() {
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
					
					//���óɹ���Ϣ
					message(LoggerStringKey.Mh4w_FlowProvider_1);
					setState(RuntimeState.RUNNING);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_2);
				}
			}
			
		}
	
	}

}
