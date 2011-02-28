package com.dwarfeng.jier.mh4w.core.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

import com.dwarfeng.dutil.basic.io.CT;
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
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DateTypeModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultBackgroundModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultBlockModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultCoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultDataListModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultDateTypeModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultFileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultJobModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultLoggerModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultMutilangModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultResourceModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultShiftModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DefaultStateModel;
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.cm.LoggerModel;
import com.dwarfeng.jier.mh4w.core.model.cm.MutilangModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ResourceModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel;
import com.dwarfeng.jier.mh4w.core.model.cm.StateModel;
import com.dwarfeng.jier.mh4w.core.model.eum.BlockKey;
import com.dwarfeng.jier.mh4w.core.model.eum.CoreConfig;
import com.dwarfeng.jier.mh4w.core.model.eum.CountState;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.eum.FailType;
import com.dwarfeng.jier.mh4w.core.model.eum.LoggerStringKey;
import com.dwarfeng.jier.mh4w.core.model.eum.ResourceKey;
import com.dwarfeng.jier.mh4w.core.model.io.XlsCountResultsSaver;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalAttendanceDataLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalWorkticketDataLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlBlockLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlDateTypeLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlDateTypeSaver;
import com.dwarfeng.jier.mh4w.core.model.io.XmlJobLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlLoggerLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlMutilangLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlResourceLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XmlShiftLoader;
import com.dwarfeng.jier.mh4w.core.model.obv.LoggerAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.LoggerObverser;
import com.dwarfeng.jier.mh4w.core.model.obv.MutilangAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.MutilangObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.AbstractFlow;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;
import com.dwarfeng.jier.mh4w.core.model.struct.CountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultFail;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultFinishedFlowTaker;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultJob;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultShift;
import com.dwarfeng.jier.mh4w.core.model.struct.Fail;
import com.dwarfeng.jier.mh4w.core.model.struct.FinishedFlowTaker;
import com.dwarfeng.jier.mh4w.core.model.struct.Flow;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.ProcessException;
import com.dwarfeng.jier.mh4w.core.model.struct.Resource;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;
import com.dwarfeng.jier.mh4w.core.model.struct.TimeSection;
import com.dwarfeng.jier.mh4w.core.model.struct.TransException;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeDateTypeEntry;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeJob;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift;
import com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.CountUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController;
import com.dwarfeng.jier.mh4w.core.view.ctrl.GuiController;
import com.dwarfeng.jier.mh4w.core.view.gui.AttrFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.DateTypeFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.DetailFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.FailFrame;
import com.dwarfeng.jier.mh4w.core.view.gui.MainFrame;
import com.dwarfeng.jier.mh4w.core.view.obv.AttrFrameAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.AttrFrameObverser;
import com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameObverser;
import com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameObverser;
import com.dwarfeng.jier.mh4w.core.view.obv.FailFrameAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.FailFrameObverser;
import com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser;

/**
 * ��ʱͳ�������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class Mh4w {
	
	/**
	 * ��������ķ����� 
	 * @param args ��ڲ�����
	 */
	public static void main(String[] args) {
		//CT.trace(VERSION);
		
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException ignore) {
			//�����е�����Ԫ�ؾ�֧����һ��ۣ���˲����ܳ����쳣��
		}
		
		Mh4w mh4w = null;
		try {
			mh4w = new Mh4w();
			mh4w.start();
		} catch (ProcessException e) {
			if(Objects.nonNull(mh4w)){
				mh4w.manager.getLoggerModel().getLogger().error("����δ����ȷ����", e);
				System.exit(12450);
			}else{
				System.exit(12451);
			}
		}
	}
	
	/**����İ汾*/
	public final static Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 1)
			.buildDate("20170228")
			.buildVersion('A')
			.type(VersionType.BETA)
			.build();
	
	/**�����ʵ���б����ڳ�������*/
	private static final Set<Mh4w> INSTANCES  = Collections.synchronizedSet(new HashSet<>());
	/**����ƽ̨�Ľ��̹���*/
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("mh4w");
	
	/**����Ĺ����ṩ��*/
	private final FlowProvider flowProvider = new FlowProvider();
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
		private ShiftModel shiftModel = new DefaultShiftModel();
		private DataListModel<OriginalAttendanceData> originalAttendanceDataModel = new DefaultDataListModel<>();
		private DataListModel<OriginalWorkticketData> originalWorkticketDataModel = new DefaultDataListModel<>();
		private JobModel jobModel = new DefaultJobModel();
		private DataListModel<Fail> failModel = new DefaultDataListModel<>();
		private DataListModel<AttendanceData> attendanceDataModel = new DefaultDataListModel<>();
		private DataListModel<WorkticketData> workticketDataModel = new DefaultDataListModel<>();
		private DateTypeModel dateTypeModel = new DefaultDateTypeModel();
		private DataListModel<CountResult> countResultModel = new DefaultDataListModel<>();
		//structs
		private FinishedFlowTaker finishedFlowTaker = new DefaultFinishedFlowTaker(backgroundModel, 
				loggerModel.getLogger(), loggerMutilangModel.getMutilang());
		//obvs
		private LoggerObverser loggerObverser = new LoggerAdapter() {
			@Override
			public void fireUpdated() {
				finishedFlowTaker.updateLogger();
			}
		};
		private MutilangObverser loggerMutilangObverser = new MutilangAdapter() {
			@Override
			public void fireUpdated() {
				finishedFlowTaker.updateLogger();
			}
		};
		private MutilangObverser labelMutilangObverser = new MutilangAdapter() {
			@Override
			public void fireUpdated() {
				Mh4wUtil.invokeInEventQueue(new Runnable() {
					@Override
					public void run() {
						guiController.updateMainFrameMutilang();
						guiController.updateDetailFrameMutilang();
						guiController.updateAttrFrameMutilang();
						guiController.updateFailFrameMutilang();
						guiController.updateDateTypeFrameMutilang();
					}
				});
			};
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
				if(configKey.equals(CoreConfig.MUTILANG_LABEL.getConfigKey())){
					try {
						labelMutilangModel.setCurrentLocale(coreConfigModel.getLabelMutilangLocale());
						labelMutilangModel.update();
					} catch (ProcessException e) {
						loggerModel.getLogger().warn(loggerMutilangModel.getMutilang().getString(LoggerStringKey.Update_LabelMutilang_1.getName()), e);
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
						VERSION,
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
				DetailFrame detailFrame = new DetailFrame(
						labelMutilangModel.getMutilang(),
						stateModel,
						originalAttendanceDataModel,
						originalWorkticketDataModel,
						attendanceDataModel,
						workticketDataModel,
						countResultModel
				);
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

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#newAttrFrameImpl()
			 */
			@Override
			protected AttrFrame newAttrFrameImpl() {
				if(Objects.isNull(mainFrame)) return null;
				
				AttrFrame attrFrame = new AttrFrame(
						labelMutilangModel.getMutilang(),
						coreConfigModel,
						shiftModel,
						jobModel
				);
				attrFrame.addObverser(attrFrameObverser);
				return attrFrame;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#disposeAttrFrameImpl()
			 */
			@Override
			protected boolean disposeAttrFrameImpl() {
				attrFrame.removeObverser(attrFrameObverser);
				attrFrame.dispose();
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#newFailFrameImpl()
			 */
			@Override
			protected FailFrame newFailFrameImpl() {
				FailFrame failFrame = new FailFrame(
						labelMutilangModel.getMutilang(),
						failModel
				);
				failFrame.addObverser(failFrameObverser);
				return failFrame;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#disposeFailFrameImpl()
			 */
			@Override
			protected boolean disposeFailFrameImpl() {
				failFrame.removeObverser(failFrameObverser);
				failFrame.dispose();
				return true;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#newDateTypeFrameImpl()
			 */
			@Override
			protected DateTypeFrame newDateTypeFrameImpl() {
				DateTypeFrame dateTypeFrame = new DateTypeFrame(
						labelMutilangModel.getMutilang(),
						dateTypeModel
				);
				dateTypeFrame.addObverser(dateTypeFrameObverser);
				return dateTypeFrame;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.ctrl.AbstractGuiController#disposeDateTypeFrameImpl()
			 */
			@Override
			protected boolean disposeDateTypeFrameImpl() {
				dateTypeFrame.removeObverser(dateTypeFrameObverser);
				dateTypeFrame.dispose();
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
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter#fireHideDetail()
			 */
			@Override
			public void fireHideDetail() {
				manager.getBackgroundModel().submit(flowProvider.newHideDetailFlow());
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter#fireShowDetail()
			 */
			@Override
			public void fireShowDetail() {
				manager.getBackgroundModel().submit(flowProvider.newShowDetailFlow());
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter#fireCount()
			 */
			@Override
			public void fireCount() {
				manager.getBackgroundModel().submit(flowProvider.newCountFlow());
			};
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter#fireShowAttributes()
			 */
			@Override
			public void fireShowAttrFrame() {
				manager.getBackgroundModel().submit(flowProvider.newShowAttrFrameFlow());
			};
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.MainFrameAdapter#fireShowDateTypeFrame()
			 */
			@Override
			public void fireShowDateTypeFrame() {
				manager.getBackgroundModel().submit(flowProvider.newShowDateTypeFrameFlow());
			};
			
		};
		private final DetailFrameObverser detailFrameObverser = new DetailFrameAdapter() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameAdapter#fireHideDetailFrame()
			 */
			@Override
			public void fireHideDetailFrame() {
				manager.getBackgroundModel().submit(flowProvider.newHideDetailFrameFlow());
			};
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameAdapter#fireExportCountResult()
			 */
			@Override
			public void fireExportCountResult() {
				manager.getBackgroundModel().submit(flowProvider.newExportCountResultFlow());
			};
			
		};
		private final AttrFrameObverser attrFrameObverser = new AttrFrameAdapter() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.AttrFrameAdapter#fireAttrFrameClosing()
			 */
			@Override
			public void fireHideAttrFrame() {
				manager.getBackgroundModel().submit(flowProvider.newHideAttrFrameFlow());
			};
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.AttrFrameAdapter#fireReloadAttr()
			 */
			@Override
			public void fireReloadAttr() {
				manager.getBackgroundModel().submit(flowProvider.newReloadAttrFlow());
			};
			
		};
		private final FailFrameObverser failFrameObverser = new FailFrameAdapter() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.FailFrameAdapter#fireHideFailFrame()
			 */
			@Override
			public void fireHideFailFrame() {
				manager.getBackgroundModel().submit(flowProvider.newHideFailFrameFlow());
			};
			
		};
		private final DateTypeFrameObverser dateTypeFrameObverser = new DateTypeFrameAdapter() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameAdapter#fireHideDateTypeFrame()
			 */
			@Override
			public void fireHideDateTypeFrame() {
				manager.getBackgroundModel().submit(flowProvider.newHideDateTypeFrameFlow());
			};
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameAdapter#fireDateTypeEntrySubmitted(com.dwarfeng.jier.mh4w.core.model.struct.CountDate, com.dwarfeng.jier.mh4w.core.model.eum.DateType)
			 */
			@Override
			public void fireSubmitDateTypeEntry(CountDate key, DateType value) {
				manager.getBackgroundModel().submit(flowProvider.newSubmitDateTypeEntryFlow(key, value));
			};
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameAdapter#fireRemoveDateTypeEntry(com.dwarfeng.jier.mh4w.core.model.struct.CountDate)
			 */
			@Override
			public void fireRemoveDateTypeEntry(CountDate key) {
				manager.getBackgroundModel().submit(flowProvider.newRemoveDateTypeEntryFlow(key));
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameAdapter#fireClearDateTypeEntry()
			 */
			@Override
			public void fireClearDateTypeEntry() {
				manager.getBackgroundModel().submit(flowProvider.newClearDateTypeEntryFlow());
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameAdapter#fireSaveDateTypeEntry()
			 */
			@Override
			public void fireSaveDateTypeEntry() {
				manager.getBackgroundModel().submit(flowProvider.newSaveDateTypeEntryFlow());
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameAdapter#fireLoadDateTypeEntry()
			 */
			@Override
			public void fireLoadDateTypeEntry() {
				manager.getBackgroundModel().submit(flowProvider.newLoadDateTypeEntryFlow());
			};
			
		};
		
		/**
		 * ��ʵ����
		 */
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
			labelMutilangModel.addObverser(labelMutilangObverser);
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
			labelMutilangModel.removeObverser(labelMutilangObverser);
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
		 * @return the shiftModel
		 */
		public ShiftModel getShiftModel() {
			return shiftModel;
		}

		/**
		 * @return the originalAttendanceDataModel
		 */
		public DataListModel<OriginalAttendanceData> getOriginalAttendanceDataModel() {
			return originalAttendanceDataModel;
		}

		/**
		 * @return the originalWorkticketDataModel
		 */
		public DataListModel<OriginalWorkticketData> getOriginalWorkticketDataModel() {
			return originalWorkticketDataModel;
		}

		/**
		 * @return the jobModel
		 */
		public JobModel getJobModel() {
			return jobModel;
		}

		/**
		 * @return the failsModel
		 */
		public DataListModel<Fail> getFailModel() {
			return failModel;
		}

		/**
		 * @return the attendanceDataModel
		 */
		public DataListModel<AttendanceData> getAttendanceDataModel() {
			return attendanceDataModel;
		}

		/**
		 * @return the workticketDataModel
		 */
		public DataListModel<WorkticketData> getWorkticketDataModel() {
			return workticketDataModel;
		}

		/**
		 * @return the dateTypeModel
		 */
		public DateTypeModel getDateTypeModel() {
			return dateTypeModel;
		}

		/**
		 * @return the countResultModel
		 */
		public DataListModel<CountResult> getCountResultModel() {
			return countResultModel;
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
		public Flow newShowDetailFlow() {
			return new ShowDetailFlow();
		}

		/**
		 * ��ȡһ���µ�������ϸ�������
		 * @return �µ�������ϸ�������
		 */
		public Flow newHideDetailFlow() {
			return new HideDetailFlow();
		}

		/**
		 * ��ȡһ���µ�ͳ������
		 * @return �µ�ͳ������
		 */
		public Flow newCountFlow() {
			return new CountFlow();
		}

		/**
		 * ��ȡһ���µ���ʾ�����������
		 * @return �µ���ʾ�����������
		 */
		public Flow newShowAttrFrameFlow() {
			return new ShowAttrFrameFlow();
		}
		
		/**
		 * ��ȡһ���µĹر������������
		 * @return �µĹر������������
		 */
		public Flow newHideAttrFrameFlow() {
			return new HideAttrFrameFlow();
		}

		/**
		 * ��ȡһ���µ����¶�ȡ��������
		 * @return �µ����¶�ȡ��������
		 */
		public Flow newReloadAttrFlow() {
			return new ReloadAttrFlow();
		}

		/**
		 * ��ȡһ���µ�����ʧ�ܽ��������
		 * @return �µ�����ʧ�ܽ��������
		 */
		public Flow newHideFailFrameFlow() {
			return new HideFailFrameFlow();
		}

		/**
		 * ��ȡһ���µ�������ϸ���������
		 * @return �µ�������ϸ���������
		 */
		public Flow newHideDetailFrameFlow() {
			return new HideDetailFrameFlow();
		}

		/**
		 * ��ȡһ���µ���ʾ�������ͽ��������
		 * @return �µ���ʾ�������ͽ��������
		 */
		public Flow newShowDateTypeFrameFlow() {
			return new ShowDateTypeFrameFlow();
		}

		/**
		 * ��ȡһ���µ������������ͽ��������
		 * @return �µ������������ͽ��������
		 */
		public Flow newHideDateTypeFrameFlow() {
			return new HideDateTypeFrameFlow();
		}

		/**
		 * ��ȡһ���µ��ύ����������ڵ�����
		 * @param key ��ڵļ���
		 * @param value ��ڵ�ֵ��
		 * @return �µ��ύ����������ڵ�����
		 */
		public Flow newSubmitDateTypeEntryFlow(CountDate key, DateType value) {
			return new SubmitDateTypeEntryFlow(key, value);
		}

		/**
		 * ��ȡһ���µ��Ƴ�����������ڵ�����
		 * @param key ָ���ļ���
		 * @return �µ��Ƴ�����������ڵ�����
		 */
		public Flow newRemoveDateTypeEntryFlow(CountDate key) {
			return new RemoveDateTypeEntryFlow(key);
		}

		/**
		 * ��ȡһ���µ�����������͵�����
		 * @return �µ�����������͵�����
		 */
		public Flow newClearDateTypeEntryFlow() {
			return new ClearDateTypeEntryFlow();
		}

		/**
		 * ��ȡһ���µı����������͵�����
		 * @return �µı����������͵�����
		 */
		public Flow newSaveDateTypeEntryFlow() {
			return new SaveDateTypeEntryFlow();
		}

		/**
		 * ��ȡһ���µĶ�ȡ�������͵�����
		 * @return �µĶ�ȡ�������͵�����
		 */
		public Flow newLoadDateTypeEntryFlow() {
			return new LoadDateTypeEntryFlow();
		}

		/**
		 * ��ȡһ���µĵ���ͳ�ƽ��������
		 * @return �µĵ���ͳ�ƽ��������
		 */
		public Flow newExportCountResultFlow() {
			return new ExportCountResultFlow();
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
			 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
			 */
			public AbstractInnerFlow(BlockKey blockKey) {
				this(blockKey, 0, 0, false, false);
			}
			
			/**
			 * ��ʵ����
			 * @param blockKey �赲��������Ϊ <code>null</code>��
			 * @param progress ��ǰ���ȡ�
			 * @param totleProgress �ܽ��ȡ�
			 * @param determinateFlag �Ƿ�Ϊ������֪�����̡�
			 * @param cancelableFlag �Ƿ��ܹ���ȡ����
			 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
			 */
			public AbstractInnerFlow(BlockKey blockKey, int progress, int totleProgress, boolean determinateFlag, boolean cancelableFlag ){
				super(progress, totleProgress, determinateFlag, cancelableFlag);
				Objects.requireNonNull(blockKey, "��ڲ��� blockKey ����Ϊ null��");
				this.blockKey = blockKey.getName();
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
			
//			/**
//			 * ��ʽ��������Ϣ��
//			 * @param loggerStringKey ָ���ļ�¼������
//			 * @param args format ������
//			 */
//			protected void formatMessage(LoggerStringKey loggerStringKey, Object... args){
//				setMessage(formatLabel(loggerStringKey, args));	
//			}
			
		}

		/**
		 * ���ܻ�ı�����״̬������
		 * <p> ��װ�˳��õ�״̬���ķ�����
		 * @author DwArFeng
		 * @since 0.0.0-alpha
		 */
		private abstract class AbstractMayChangeStateFlow extends AbstractInnerFlow{

			/**
			 * ��ʵ����
			 * @param blockKey �赲��������Ϊ <code>null</code>��
			 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
			 */
			public AbstractMayChangeStateFlow(BlockKey blockKey) {
				super(blockKey);
			}
			
			/**
			 * ��ʵ����
			 * @param blockKey �赲��������Ϊ <code>null</code>��
			 * @param progress ��ǰ���ȡ�
			 * @param totleProgress �ܽ��ȡ�
			 * @param determinateFlag �Ƿ�Ϊ������֪�����̡�
			 * @param cancelableFlag �Ƿ��ܹ���ȡ����
			 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
			 */
			public AbstractMayChangeStateFlow(BlockKey blockKey, int progress, int totleProgress,
					boolean determinateFlag, boolean cancelableFlag) {
				super(blockKey, progress, totleProgress, determinateFlag, cancelableFlag);
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
					CT.trace(true);
					return true;
				}
			}
			
		}
		
		private final class InitializeFlow extends AbstractInnerFlow{
			
			public InitializeFlow() {
				super(BlockKey.INITIALIZE);
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
					
					//���ذ����Ϣ��
					info(LoggerStringKey.Mh4w_FlowProvider_31);
					message(LoggerStringKey.Mh4w_FlowProvider_31);
					Set<UnsafeShift> unsafeShifts = new LinkedHashSet<>();
					XmlShiftLoader shiftLoader = null;
					try{
						shiftLoader = new XmlShiftLoader(getResource(ResourceKey.DEFINE_SHIFTS).openInputStream());
						shiftLoader.load(unsafeShifts);
					}catch(IOException e){
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.DEFINE_SHIFTS).reset();
						shiftLoader = new XmlShiftLoader(getResource(ResourceKey.DEFINE_SHIFTS).openInputStream());
						shiftLoader.load(unsafeShifts);
					}finally{
						if(Objects.nonNull(shiftLoader)){
							shiftLoader.close();
						}
					}
					
					for(UnsafeShift unsafeShift : unsafeShifts){
						try{
							String name = unsafeShift.getName();
							TimeSection[] shiftSections = unsafeShift.getShiftSections();
							TimeSection[] restSections = unsafeShift.getRestSections();
							TimeSection[] extraShiftSections = unsafeShift.getExtraShiftSections();
							
							Shift shift = new DefaultShift(name, shiftSections, restSections, extraShiftSections);
							manager.getShiftModel().add(shift);
						}catch (ProcessException e) {
							warn(LoggerStringKey.Mh4w_FlowProvider_44, e);
						}
					}
					
					//���ع�����Ϣ
					info(LoggerStringKey.Mh4w_FlowProvider_43);
					message(LoggerStringKey.Mh4w_FlowProvider_43);
					Set<UnsafeJob> unsafeJobs = new LinkedHashSet<>();
					XmlJobLoader jobLoader = null;
					try{
						jobLoader = new XmlJobLoader(getResource(ResourceKey.DEFINE_JOBS).openInputStream());
						jobLoader.load(unsafeJobs);
					}catch(IOException e){
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.DEFINE_JOBS).reset();
						jobLoader = new XmlJobLoader(getResource(ResourceKey.DEFINE_JOBS).openInputStream());
						jobLoader.load(unsafeJobs);
					}finally{
						if(Objects.nonNull(jobLoader)){
							jobLoader.close();
						}
					}
					
					for(UnsafeJob unsafeJob : unsafeJobs){
						try{
							String name = unsafeJob.getName();
							double valuePerHour = unsafeJob.getValuePerHour();
							int originalColumn = unsafeJob.getOriginalColumn();
							
							Job job = new DefaultJob(name, valuePerHour, originalColumn);
							manager.getJobModel().add(job);
						}catch (ProcessException e) {
							warn(LoggerStringKey.Mh4w_FlowProvider_45, e);
						}
					}
					
					//������ͼ����ʹ��ɼ���
					info(LoggerStringKey.Mh4w_FlowProvider_8);
					message(LoggerStringKey.Mh4w_FlowProvider_8);
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().newMainFrame();
							manager.getGuiController().newDetailFrame();
							manager.getGuiController().newAttrFrame();
							manager.getGuiController().newFailFrame();
							manager.getGuiController().newDateTypeFrame();
							
							manager.getGuiController().setMainFrameVisible(true);
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
				super(BlockKey.CLOSING);
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
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_12);
				}
			}
			
		}

		private final class SelectAttendanceFileFlow extends AbstractMayChangeStateFlow{
		
			public SelectAttendanceFileFlow() {
				super(BlockKey.SELECT_ATTENDANCE_FILE);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_13);
					
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
					File[] files = manager.getGuiController().askFile4Open(directory, fileFilters, acceptAllFileFilter, 
							mutiSelectionEnabled, fileSelectionMode, manager.getCoreConfigModel().getLabelMutilangLocale());
					
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
					setThrowable(e);
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
				super(BlockKey.SELECT_WORKTICKET_FILE);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_13);
					
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
					File[] files = manager.getGuiController().askFile4Open(directory, fileFilters, acceptAllFileFilter, 
							mutiSelectionEnabled, fileSelectionMode, manager.getCoreConfigModel().getLabelMutilangLocale());
					
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
					setThrowable(e);
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
				super(BlockKey.RESET_COUNT);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_22);
					
					manager.getFileSelectModel().setAttendanceFile(null);
					manager.getFileSelectModel().setWorkticketFile(null);
					
					manager.getOriginalAttendanceDataModel().clear();
					manager.getOriginalWorkticketDataModel().clear();
					manager.getAttendanceDataModel().clear();
					manager.getWorkticketDataModel().clear();
					manager.getCountResultModel().clear();
					
					manager.getFailModel().clear();
					
					manager.getStateModel().setCountResultOutdated(false);
					manager.getStateModel().setCountState(CountState.NOT_START);
					manager.getStateModel().setReadyForCount(false);
					
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().setDetailFrameVisible(false);
							manager.getGuiController().setFailFrameVisible(false);
							manager.getGuiController().setDetailButtonSelect(false, true);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_23);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_24);
				}
			}
			
		}

		private final class ShowDetailFlow extends AbstractInnerFlow{
		
			public ShowDetailFlow() {
				super(BlockKey.SHOW_DETAIL);
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
							manager.getGuiController().setDetailFrameVisible(true);
							if(manager.getFailModel().size() > 0) 
								manager.getGuiController().setFailFrameVisible(true);
							manager.getGuiController().setDetailButtonSelect(true, true);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_29);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_26);
				}
			}
			
		}

		private final class HideDetailFlow extends AbstractInnerFlow{
		
			public HideDetailFlow() {
				super(BlockKey.HIDE_DETAIL);
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
							manager.getGuiController().setDetailFrameVisible(false);
							manager.getGuiController().setFailFrameVisible(false);
							manager.getGuiController().setDetailButtonSelect(false, true);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_30);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_28);
				}
			}
			
		}

		private final class CountFlow extends AbstractMayChangeStateFlow{
		
			public CountFlow() {
				super(BlockKey.COUNT);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_41);
					
					/*
					 * -------------------------- ͳ���㷨 -------------------------
					 * start��
					 * �������ģ��
					 * ���ԭʼ��������ģ��
					 * ���ԭʼ��Ʊ����ģ��
					 * 
					 * ��ȡԭʼ��������
					 * ��ȡԭʼ��Ʊ����
					 * 
					 * ת��ԭʼ���ݣ����������������¼�ڴ���ģ����
					 * if ����ģ��.size() > 0 then goto err_1
					 * 
					 * �����Աһ���ԣ����������������¼�ڴ���ģ����
					 * �����Աƥ���ԣ����������������¼�ڴ���ģ����
					 * if ����ģ��.size() > 0 then goto err_2
					 * 
					 * ͳ�����ݣ�����ͳ�ƽ������ͳ�ƽ��ģ����
					 * 
					 * ����״̬ģ��Ϊ ͳ��_�ȴ���������ʾ��ϸ��Ϣ���
					 * 
					 * exit
					 * 
					 *  err_1:
					 *  ����״̬ģ��Ϊ ͳ��_���󣬲���ʾ������塣
					 *  exit
					 *  
					 *  err_2:
					 *  ����״̬ģ��Ϊ ͳ��_���󣬲���ʾ������塣
					 *  exit
					 */
					
					//�����������
					info(LoggerStringKey.Mh4w_FlowProvider_47);
					message(LoggerStringKey.Mh4w_FlowProvider_47);
					manager.getOriginalAttendanceDataModel().clear();
					manager.getOriginalWorkticketDataModel().clear();
					manager.getAttendanceDataModel().clear();
					manager.getWorkticketDataModel().clear();
					manager.getCountResultModel().clear();
					manager.getFailModel().clear();
					manager.getStateModel().setCountResultOutdated(false);
					
					//��ȡԭʼ��������
					info(LoggerStringKey.Mh4w_FlowProvider_42);
					message(LoggerStringKey.Mh4w_FlowProvider_42);
					XlsOriginalAttendanceDataLoader originalAttendanceDataLoader = null;
					try{
						originalAttendanceDataLoader = CountUtil.newXlsOriginalAttendanceDataLoader(
								manager.getFileSelectModel(), manager.getCoreConfigModel());
						originalAttendanceDataLoader.load(manager.getOriginalAttendanceDataModel());
					}finally {
						if(Objects.nonNull(originalAttendanceDataLoader)){
							originalAttendanceDataLoader.close();
						}
					}
					//��ȡԭʼ��Ʊ����
					info(LoggerStringKey.Mh4w_FlowProvider_46);
					message(LoggerStringKey.Mh4w_FlowProvider_46);
					XlsOriginalWorkticketDataLoader originalWorkticketDataLoader = null;
					try{
						originalWorkticketDataLoader = CountUtil.newXlsOriginalWorkticketDataLoader(
								manager.getFileSelectModel(), manager.getCoreConfigModel(), manager.getJobModel());
						originalWorkticketDataLoader.load(manager.getOriginalWorkticketDataModel());
					}finally {
						if(Objects.nonNull(originalWorkticketDataLoader)){
							originalWorkticketDataLoader.close();
						}
					}
					
					//ת��ԭʼ���ݣ����������������¼�ڴ���ģ����
					info(LoggerStringKey.Mh4w_FlowProvider_52);
					message(LoggerStringKey.Mh4w_FlowProvider_52);
					manager.getOriginalAttendanceDataModel().getLock().readLock().lock();
					try{
						for(OriginalAttendanceData rawData : manager.getOriginalAttendanceDataModel()){
							try{
								AttendanceData attendanceData = CountUtil.transAttendanceData(rawData, manager.getShiftModel(),
										manager.getCoreConfigModel(), manager.getDateTypeModel());
								manager.getAttendanceDataModel().add(attendanceData);
							}catch (TransException e) {
								manager.getFailModel().add(new DefaultFail(rawData, FailType.DATA_STRUCT_FAIL));
								warn(LoggerStringKey.Mh4w_FlowProvider_54, e);
							}
						}
					}finally {
						manager.getOriginalAttendanceDataModel().getLock().readLock().unlock();
					}
					manager.getOriginalWorkticketDataModel().getLock().readLock().lock();
					try{
						for(OriginalWorkticketData rawData : manager.getOriginalWorkticketDataModel()){
							try{
								WorkticketData workticketData = CountUtil.transWorkticketData(rawData);
								manager.getWorkticketDataModel().add(workticketData);
							}catch (TransException e) {
								manager.getFailModel().add(new DefaultFail(rawData, FailType.DATA_STRUCT_FAIL));
								warn(LoggerStringKey.Mh4w_FlowProvider_54, e);
							}
						}
					}finally {
						manager.getOriginalWorkticketDataModel().getLock().readLock().unlock();
					}
					//if ����ģ��.size() > 0 then goto err_1
					if(manager.getFailModel().size() > 0){
						message(LoggerStringKey.Mh4w_FlowProvider_53);
						manager.getStateModel().setCountState(CountState.STARTED_ERROR);
						return;
					}
					
					//�����Աһ���ԣ����������������¼�ڴ���ģ����
					info(LoggerStringKey.Mh4w_FlowProvider_55);
					for(DataFromXls dataFromXls : CountUtil.personConsistentCheck(manager.getAttendanceDataModel(),
							manager.getWorkticketDataModel())){
						manager.getFailModel().add(new DefaultFail(dataFromXls, FailType.PERSON_UNCONSISTENT));
					}
					//�����Աƥ���ԣ����������������¼�ڴ���ģ����
					info(LoggerStringKey.Mh4w_FlowProvider_78);
					for(DataFromXls dataFromXls : CountUtil.personMatchCheck(manager.getAttendanceDataModel(),
							manager.getWorkticketDataModel())){
						manager.getFailModel().add(new DefaultFail(dataFromXls, FailType.PERSON_UNMATCH));
					}
					//if ����ģ��.size() > 0 then goto err_2
					if(manager.getFailModel().size() > 0){
						message(LoggerStringKey.Mh4w_FlowProvider_53);
						manager.getStateModel().setCountState(CountState.STARTED_ERROR);
						return;
					}
					
					//ͳ�����ݣ�����ͳ�ƽ������ͳ�ƽ��ģ����
					info(LoggerStringKey.Mh4w_FlowProvider_79);
					for(CountResult countResult : CountUtil.countData(manager.getAttendanceDataModel(),
							manager.getWorkticketDataModel())){
						manager.getCountResultModel().add(countResult);
					}
					
					//����״̬
					manager.getStateModel().setCountState(CountState.STARTED_WAITING);
					
					message(LoggerStringKey.Mh4w_FlowProvider_83);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_84);
				}finally {
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().setDetailFrameVisible(true);
							if(manager.getFailModel().size() > 0){
								manager.getGuiController().setFailFrameVisible(true);
							}else{
								manager.getGuiController().setFailFrameVisible(false);
							}
							manager.getGuiController().setDetailButtonSelect(true, true);
							manager.getGuiController().knockCountFinished();
						}
					});
				}
			}
			
		}

		private final class ShowAttrFrameFlow extends AbstractInnerFlow{
		
			public ShowAttrFrameFlow() {
				super(BlockKey.SHOW_ATTR_FRAME);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_32);
					
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().setAttrFrameVisible(true);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_33);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_34);
				}
			}
			
		}

		private final class HideAttrFrameFlow extends AbstractInnerFlow{
		
			public HideAttrFrameFlow() {
				super(BlockKey.HIDE_ATTR_FRAME);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_35);
					
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().setAttrFrameVisible(false);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_36);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_37);
				}
			}
			
		}

		private final class ReloadAttrFlow extends AbstractMayChangeStateFlow{
		
			public ReloadAttrFlow() {
				super(BlockKey.RELOAD_ATTR);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_38);
					
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
					
					//���ذ����Ϣ��
					info(LoggerStringKey.Mh4w_FlowProvider_31);
					message(LoggerStringKey.Mh4w_FlowProvider_31);
					manager.getShiftModel().clear();
					Set<UnsafeShift> unsafeShifts = new LinkedHashSet<>();
					XmlShiftLoader shiftLoader = null;
					try{
						shiftLoader = new XmlShiftLoader(getResource(ResourceKey.DEFINE_SHIFTS).openInputStream());
						shiftLoader.load(unsafeShifts);
					}catch(IOException e){
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.DEFINE_SHIFTS).reset();
						shiftLoader = new XmlShiftLoader(getResource(ResourceKey.DEFINE_SHIFTS).openInputStream());
						shiftLoader.load(unsafeShifts);
					}finally{
						if(Objects.nonNull(shiftLoader)){
							shiftLoader.close();
						}
					}
					
					for(UnsafeShift unsafeShift : unsafeShifts){
						try{
							String name = unsafeShift.getName();
							TimeSection[] shiftSections = unsafeShift.getShiftSections();
							TimeSection[] restSections = unsafeShift.getRestSections();
							TimeSection[] extraShiftSections = unsafeShift.getExtraShiftSections();
							
							Shift shift = new DefaultShift(name, shiftSections, restSections, extraShiftSections);
							manager.getShiftModel().add(shift);
						}catch (ProcessException e) {
							warn(LoggerStringKey.Mh4w_FlowProvider_44, e);
						}
					}
					
					//���ع�����Ϣ
					info(LoggerStringKey.Mh4w_FlowProvider_43);
					message(LoggerStringKey.Mh4w_FlowProvider_43);
					manager.getJobModel().clear();
					Set<UnsafeJob> unsafeJobs = new LinkedHashSet<>();
					XmlJobLoader jobLoader = null;
					try{
						jobLoader = new XmlJobLoader(getResource(ResourceKey.DEFINE_JOBS).openInputStream());
						jobLoader.load(unsafeJobs);
					}catch(IOException e){
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.DEFINE_JOBS).reset();
						jobLoader = new XmlJobLoader(getResource(ResourceKey.DEFINE_JOBS).openInputStream());
						jobLoader.load(unsafeJobs);
					}finally{
						if(Objects.nonNull(jobLoader)){
							jobLoader.close();
						}
					}
					
					for(UnsafeJob unsafeJob : unsafeJobs){
						try{
							String name = unsafeJob.getName();
							double valuePerHour = unsafeJob.getValuePerHour();
							int originalColumn = unsafeJob.getOriginalColumn();
							
							Job job = new DefaultJob(name, valuePerHour, originalColumn);
							manager.getJobModel().add(job);
						}catch (ProcessException e) {
							warn(LoggerStringKey.Mh4w_FlowProvider_45, e);
						}
					}
					
					//���ܻ�����ͳ�ƽ���Ĺ�ʱ
					mayCountResultOutdated();
					
					message(LoggerStringKey.Mh4w_FlowProvider_39);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_40);
				}
			}
			
		}

		private final class HideFailFrameFlow extends AbstractInnerFlow{
		
			public HideFailFrameFlow() {
				super(BlockKey.HIDE_FAIL_FRAME);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_49);
					
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().setFailFrameVisible(false);
							if(! manager.getGuiController().getDetailFrameVisible())
								manager.getGuiController().setDetailButtonSelect(false, true);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_50);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_51);
				}
			}
			
		}

		private final class HideDetailFrameFlow extends AbstractInnerFlow{
		
			public HideDetailFrameFlow() {
				super(BlockKey.HIDE_DETAIL);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_56);
					
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().setDetailFrameVisible(false);
							if(! manager.getGuiController().getFailFrameVisible())
								manager.getGuiController().setDetailButtonSelect(false, true);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_57);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_58);
				}
			}
			
		}

		private final class ShowDateTypeFrameFlow extends AbstractInnerFlow{
		
			public ShowDateTypeFrameFlow() {
				super(BlockKey.SHOW_DATE_TYPE_FRAME);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_59);
					
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().setDateTypeFrameVisible(true);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_60);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_61);
				}
			}
			
		}

		private final class HideDateTypeFrameFlow extends AbstractInnerFlow{
		
			public HideDateTypeFrameFlow() {
				super(BlockKey.HIDE_DATE_TYPE_FRAME);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_62);
					
					Mh4wUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getGuiController().setDateTypeFrameVisible(false);
						}
					});
					
					message(LoggerStringKey.Mh4w_FlowProvider_63);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_64);
				}
			}
			
		}

		private final class SubmitDateTypeEntryFlow extends AbstractMayChangeStateFlow{
			
			private final CountDate key;
			private final DateType value;
		
			public SubmitDateTypeEntryFlow(CountDate key, DateType value) {
				super(BlockKey.SUBMIT_DATE_TYPE_ENTRY);
				this.key = key;
				this.value = value;
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_65);
					
					manager.getDateTypeModel().put(key, value);
					
					mayCountResultOutdated();
					
					message(LoggerStringKey.Mh4w_FlowProvider_66);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_67);
				}
			}
			
		}

		private final class RemoveDateTypeEntryFlow extends AbstractMayChangeStateFlow{
			
			private final CountDate key;
		
			public RemoveDateTypeEntryFlow(CountDate key) {
				super(BlockKey.RESET_COUNT);
				this.key = key;
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_68);
					
					manager.getDateTypeModel().remove(key);
					
					mayCountResultOutdated();
					
					message(LoggerStringKey.Mh4w_FlowProvider_69);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_70);
				}
			}
			
		}

		private final class ClearDateTypeEntryFlow extends AbstractMayChangeStateFlow{
			
			public ClearDateTypeEntryFlow() {
				super(BlockKey.CLEAR_DATE_TYPE_ENTRY);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_68);
					
					manager.getDateTypeModel().clear();
					
					mayCountResultOutdated();
					
					message(LoggerStringKey.Mh4w_FlowProvider_69);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_70);
				}
			}
			
		}

		private final class SaveDateTypeEntryFlow extends AbstractInnerFlow{
			
			public SaveDateTypeEntryFlow() {
				super(BlockKey.SAVE_DATE_TYPE_ENTRY);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_71);
					
					//��������������Ϣ��
					XmlDateTypeSaver dateTypeSaver = null;
					try{
						dateTypeSaver = new XmlDateTypeSaver(getResource(ResourceKey.STORAGE_DATE_TYPE).openOutputStream());
						dateTypeSaver.save(manager.getDateTypeModel());
					}catch(IOException e){
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.STORAGE_DATE_TYPE).reset();
						dateTypeSaver = new XmlDateTypeSaver(getResource(ResourceKey.STORAGE_DATE_TYPE).openOutputStream());
						dateTypeSaver.save(manager.getDateTypeModel());
					}finally{
						if(Objects.nonNull(dateTypeSaver)){
							dateTypeSaver.close();
						}
					}
					
					message(LoggerStringKey.Mh4w_FlowProvider_72);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_73);
				}
			}
			
		}

		private final class LoadDateTypeEntryFlow extends AbstractMayChangeStateFlow{
			
			public LoadDateTypeEntryFlow() {
				super(BlockKey.LOAD_DATE_TYPE_ENTRY);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_74);
					
					//��ȡ����������Ϣ��
					manager.getDateTypeModel().clear();
					Set<UnsafeDateTypeEntry> unsafeDateTypeEntries = new LinkedHashSet<>();
					XmlDateTypeLoader dateTypeLoader = null;
					try{
						dateTypeLoader = new XmlDateTypeLoader(getResource(ResourceKey.STORAGE_DATE_TYPE).openInputStream());
						dateTypeLoader.load(unsafeDateTypeEntries);
					}catch(IOException e){
						warn(LoggerStringKey.Mh4w_FlowProvider_4, e);
						getResource(ResourceKey.STORAGE_DATE_TYPE).reset();
						dateTypeLoader = new XmlDateTypeLoader(getResource(ResourceKey.STORAGE_DATE_TYPE).openInputStream());
						dateTypeLoader.load(unsafeDateTypeEntries);
					}finally{
						if(Objects.nonNull(dateTypeLoader)){
							dateTypeLoader.close();
						}
					}
					
					for(UnsafeDateTypeEntry entry : unsafeDateTypeEntries){
						try{
							CountDate key = entry.getCountDate();
							DateType value = entry.getDateType();
							
							manager.getDateTypeModel().put(key, value);
						}catch (ProcessException e) {
							warn(LoggerStringKey.Mh4w_FlowProvider_77, e);
						}
					}
					
					mayCountResultOutdated();
					
					message(LoggerStringKey.Mh4w_FlowProvider_75);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_76);
				}
			}
			
		}

		private final class ExportCountResultFlow extends AbstractInnerFlow{
			
			public ExportCountResultFlow() {
				super(BlockKey.SAVE_DATE_TYPE_ENTRY);
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
					
					info(LoggerStringKey.Mh4w_FlowProvider_80);
					
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
					
					//����ļ�
					File file = manager.getGuiController().askFile4Save(directory, fileFilters, acceptAllFileFilter, "xls",
							manager.getCoreConfigModel().getLabelMutilangLocale());
					
					//����ȡ���ļ�������ģ���в������¼
					if(Objects.isNull(file)){
						info(LoggerStringKey.Mh4w_FlowProvider_17);
						return;
					}else{
						formatInfo(LoggerStringKey.Mh4w_FlowProvider_18, file.getAbsolutePath());
					}
					
					XlsCountResultsSaver countResultsSaver = null;
					try{
						countResultsSaver = new XlsCountResultsSaver(new FileOutputStream(file), 
								manager.getLabelMutilangModel().getMutilang());
						countResultsSaver.save(new XlsCountResultsSaver.CountResults(manager.getAttendanceDataModel(),
								manager.getWorkticketDataModel(), manager.getCountResultModel(), manager.getJobModel()));
					}finally {
						if(Objects.nonNull(countResultsSaver)){
							countResultsSaver.close();
						}
					}
					
					manager.getStateModel().setCountState(CountState.STARTED_EXPORTED);
					
					message(LoggerStringKey.Mh4w_FlowProvider_81);
					
				}catch (Exception e) {
					setThrowable(e);
					message(LoggerStringKey.Mh4w_FlowProvider_82);
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
			
			//�ͷŽ���
			info(LoggerStringKey.Mh4w_Exitor_4);
			try {
				Mh4wUtil.invokeAndWaitInEventQueue(new Runnable() {
					@Override
					public void run() {
						manager.getGuiController().setMainFrameVisible(false);
						manager.getGuiController().setAttrFrameVisible(false);
						manager.getGuiController().setDetailFrameVisible(false);
						manager.getGuiController().setFailFrameVisible(false);
						manager.getGuiController().setDateTypeFrameVisible(false);
						
						manager.getGuiController().disposeMainFrame();
						manager.getGuiController().disposeAttrFrame();
						manager.getGuiController().disposeDetialFrame();
						manager.getGuiController().disposeFailFrame();
						manager.getGuiController().disposeDateTypeFrame();
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
		
//		private void warn(LoggerStringKey loggerStringKey, Throwable e){
//			manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey), e);
//		}
	
		private String getLabel(LoggerStringKey loggerStringKey){
			return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
		}
	
//		private Resource getResource(ResourceKey resourceKey){
//			return manager.getResourceModel().get(resourceKey.getName());
//		}
		
	}

}
