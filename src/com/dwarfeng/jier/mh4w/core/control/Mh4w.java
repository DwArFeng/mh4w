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
 * 工时统计软件。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class Mh4w {
	
	/**
	 * 启动程序的方法。 
	 * @param args 入口参数。
	 */
	public static void main(String[] args) {
		//CT.trace(VERSION);
		
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException ignore) {
			//界面中的所有元素均支持这一外观，因此不可能出现异常。
		}
		
		Mh4w mh4w = null;
		try {
			mh4w = new Mh4w();
			mh4w.start();
		} catch (ProcessException e) {
			if(Objects.nonNull(mh4w)){
				mh4w.manager.getLoggerModel().getLogger().error("程序未能正确启动", e);
				System.exit(12450);
			}else{
				System.exit(12451);
			}
		}
	}
	
	/**程序的版本*/
	public final static Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 1)
			.buildDate("20170228")
			.buildVersion('A')
			.type(VersionType.BETA)
			.build();
	
	/**程序的实例列表，用于持有引用*/
	private static final Set<Mh4w> INSTANCES  = Collections.synchronizedSet(new HashSet<>());
	/**工具平台的进程工厂*/
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("mh4w");
	
	/**程序的过程提供器*/
	private final FlowProvider flowProvider = new FlowProvider();
	/**程序管理器*/
	private final Manager manager;
	/**程序的状态*/
	private final AtomicReference<RuntimeState> state;
	
	
	public Mh4w() {
		this.manager = new Manager();
		this.state = new AtomicReference<RuntimeState>(RuntimeState.NOT_START);
		
		//为自己保留引用。
		INSTANCES.add(this);
	}
	
	/**
	 * 启动程序。
	 * @throws ProcessException 过程异常。
	 * @throws IllegalStateException 程序已经开始。
	 */
	public void start() throws ProcessException{
		//开启初始化过程
		final Flow initializeFlow = flowProvider.newInitializeFlow();
		manager.getBackgroundModel().submit(initializeFlow);
		while(! initializeFlow.isDone()){
			try {
				initializeFlow.waitFinished();
			} catch (InterruptedException ignore) {
				//中断也要按照基本法
			}
		}
		if(initializeFlow.getThrowable() != null){
			throw new ProcessException("初始化过程失败", initializeFlow.getThrowable());
		}
	}
	
	/**
	 * 返回程序的状态。
	 * @return 程序的状态。
	 */
	public RuntimeState getState() {
		return state.get();
	}

	private void setState(RuntimeState state){
		this.state.set(state);
	}
	
	/**
	 * 尝试关闭本程序。
	 * <p> 调用该方法后，会尝试关闭程序。如果程序满足关闭的条件，则关闭，否则，会询问用户。
	 * 就像是用户点击关闭按钮那样。
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
		 * 新实例。
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
				//未初始化之前，多语言模型使用的是固化在程序中的数据，不可能出现异常。
				e.printStackTrace();
			}
		}

		/**
		 * 释放资源。
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
		 * 获取一个新的程序初始化时使用的过程。
		 * @return 新的程序初始化时使用的后台过程。
		 */
		public Flow newInitializeFlow() {
			return new InitializeFlow();
		}
		
		/**
		 * 获取一个新的程序关闭流。
		 * @return 新的程序关闭流。
		 */
		public Flow newClosingFlow() {
			return new WindowClosingFlow();
		}

		/**
		 * 获取一个新的选择出勤文件流。
		 * @return 新的出勤文件流。
		 */
		public Flow newSelectAttendanceFileFlow() {
			return new SelectAttendanceFileFlow();
		}

		/**
		 * 获取一个新的选择工票文件流。
		 * @return 新的选择工票文件流。
		 */
		public Flow newSelectWorkticketFileFlow() {
			return new SelectWorkticketFileFlow();
		}

		/**
		 * 获取一个新的统计复位流。
		 * @return 新的统计复位流。
		 */
		public Flow newCountResetFlow() {
			return new CountResetFlow();
		}

		/**
		 * 获取一个新的显示详细面板流。
		 * @return 新的显示详细面板流。
		 */
		public Flow newShowDetailFlow() {
			return new ShowDetailFlow();
		}

		/**
		 * 获取一个新的隐藏详细面板流。
		 * @return 新的隐藏详细面板流。
		 */
		public Flow newHideDetailFlow() {
			return new HideDetailFlow();
		}

		/**
		 * 获取一个新的统计流。
		 * @return 新的统计流。
		 */
		public Flow newCountFlow() {
			return new CountFlow();
		}

		/**
		 * 获取一个新的显示属性面板流。
		 * @return 新的显示属性面板流。
		 */
		public Flow newShowAttrFrameFlow() {
			return new ShowAttrFrameFlow();
		}
		
		/**
		 * 获取一个新的关闭属性面板流。
		 * @return 新的关闭属性面板流。
		 */
		public Flow newHideAttrFrameFlow() {
			return new HideAttrFrameFlow();
		}

		/**
		 * 获取一个新的重新读取属性流。
		 * @return 新的重新读取属性流。
		 */
		public Flow newReloadAttrFlow() {
			return new ReloadAttrFlow();
		}

		/**
		 * 获取一个新的隐藏失败界面的流。
		 * @return 新的隐藏失败界面的流。
		 */
		public Flow newHideFailFrameFlow() {
			return new HideFailFrameFlow();
		}

		/**
		 * 获取一个新的隐藏详细界面的流。
		 * @return 新的隐藏详细界面的流。
		 */
		public Flow newHideDetailFrameFlow() {
			return new HideDetailFrameFlow();
		}

		/**
		 * 获取一个新的显示日期类型界面的流。
		 * @return 新的显示日期类型界面的流。
		 */
		public Flow newShowDateTypeFrameFlow() {
			return new ShowDateTypeFrameFlow();
		}

		/**
		 * 获取一个新的隐藏日期类型界面的流。
		 * @return 新的隐藏日期类型界面的流。
		 */
		public Flow newHideDateTypeFrameFlow() {
			return new HideDateTypeFrameFlow();
		}

		/**
		 * 获取一个新的提交日期类型入口的流。
		 * @param key 入口的键。
		 * @param value 入口的值。
		 * @return 新的提交日期类型入口的流。
		 */
		public Flow newSubmitDateTypeEntryFlow(CountDate key, DateType value) {
			return new SubmitDateTypeEntryFlow(key, value);
		}

		/**
		 * 获取一个新的移除日期类型入口的流。
		 * @param key 指定的键。
		 * @return 新的移除日期类型入口的流。
		 */
		public Flow newRemoveDateTypeEntryFlow(CountDate key) {
			return new RemoveDateTypeEntryFlow(key);
		}

		/**
		 * 获取一个新的清除日期类型的流。
		 * @return 新的清除日期类型的流。
		 */
		public Flow newClearDateTypeEntryFlow() {
			return new ClearDateTypeEntryFlow();
		}

		/**
		 * 获取一个新的保存日期类型的流。
		 * @return 新的保存日期类型的流。
		 */
		public Flow newSaveDateTypeEntryFlow() {
			return new SaveDateTypeEntryFlow();
		}

		/**
		 * 获取一个新的读取日期类型的流。
		 * @return 新的读取日期类型的流。
		 */
		public Flow newLoadDateTypeEntryFlow() {
			return new LoadDateTypeEntryFlow();
		}

		/**
		 * 获取一个新的导出统计结果的流。
		 * @return 新的导出统计结果的流。
		 */
		public Flow newExportCountResultFlow() {
			return new ExportCountResultFlow();
		}

		/**
		 * 内部抽象过程。
		 * <p> 定义常用的内部用方法。
		 * @author DwArFeng
		 * @since 0.0.0-alpha
		 */
		private abstract class AbstractInnerFlow extends AbstractFlow{
			
			private final String blockKey;
			
			/**
			 * 新实例。
			 * @param blockKey 阻挡键, 不能为 <code>null</code>。
			 * @throws NullPointerException 入口参数为 <code>null</code>。
			 */
			public AbstractInnerFlow(BlockKey blockKey) {
				this(blockKey, 0, 0, false, false);
			}
			
			/**
			 * 新实例。
			 * @param blockKey 阻挡键，不能为 <code>null</code>。
			 * @param progress 当前进度。
			 * @param totleProgress 总进度。
			 * @param determinateFlag 是否为进度已知的流程。
			 * @param cancelableFlag 是否能够被取消。
			 * @throws NullPointerException 入口参数为 <code>null</code>。
			 */
			public AbstractInnerFlow(BlockKey blockKey, int progress, int totleProgress, boolean determinateFlag, boolean cancelableFlag ){
				super(progress, totleProgress, determinateFlag, cancelableFlag);
				Objects.requireNonNull(blockKey, "入口参数 blockKey 不能为 null。");
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
			 * 处理方法实现方法。
			 * <p> 该方法是主要的处理方法。
			 * <p> 该方法不允许抛出任何异常。
			 */
			protected abstract void processImpl();
			
			/**
			 * 返回指定的记录器键所对应的字符串。
			 * @param loggerStringKey 指定的记录器键。
			 * @return 记录器键对应的字符串。
			 */
			protected String getLabel(LoggerStringKey loggerStringKey){
				return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
			}
		
			/**
			 * 返回指定记录器键的 format 字符串。
			 * @param loggerStringKey 指定的记录器键。
			 * @param args 指定的 format 参数。
			 * @return 指定的记录器键的 format 字符串。
			 */
			protected String formatLabel(LoggerStringKey loggerStringKey, Object... args){
				return String.format(manager.getLoggerMutilangModel().getMutilang().getString(
						loggerStringKey.getName()),args);
			}
			
			/**
			 * 向记录器中输入一条INFO类信息。
			 * @param loggerStringKey 指定的记录器键。
			 */
			protected void info(LoggerStringKey loggerStringKey){
				manager.getLoggerModel().getLogger().info(getLabel(loggerStringKey));
			}
		
			/**
			 * 向记录器中format一条INFO类信息。
			 * @param loggerStringKey 指定的记录器键。
			 * @param args format参数。
			 */
			protected void formatInfo(LoggerStringKey loggerStringKey, Object... args){
				manager.getLoggerModel().getLogger().info(formatLabel(loggerStringKey, args));	
			}
		
			/**
			 * 向记录器中输入一条WARN类信息。
			 * @param loggerStringKey 指定的记录器键。
			 * @param throwable 指定的可抛出对象。
			 */
			protected void warn(LoggerStringKey loggerStringKey, Throwable throwable){
				manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey), throwable);
			}
		
			/**
			 * 获取指定键对应的资源。
			 * @param resourceKey 指定的键。
			 * @return 指定的键对应的资源。
			 */
			protected Resource getResource(ResourceKey resourceKey){
				return manager.getResourceModel().get(resourceKey.getName());
			}
			
			/**
			 * 设置信息为指定的信息。
			 * @param loggerStringKey 指定的记录器键。
			 */
			protected void message(LoggerStringKey loggerStringKey){
				setMessage(getLabel(loggerStringKey));
			}
			
//			/**
//			 * 格式化设置信息。
//			 * @param loggerStringKey 指定的记录器键。
//			 * @param args format 参数。
//			 */
//			protected void formatMessage(LoggerStringKey loggerStringKey, Object... args){
//				setMessage(formatLabel(loggerStringKey, args));	
//			}
			
		}

		/**
		 * 可能会改变程序的状态的流。
		 * <p> 封装了常用的状态监测的方法。
		 * @author DwArFeng
		 * @since 0.0.0-alpha
		 */
		private abstract class AbstractMayChangeStateFlow extends AbstractInnerFlow{

			/**
			 * 新实例。
			 * @param blockKey 阻挡键，不能为 <code>null</code>。
			 * @throws NullPointerException 入口参数为 <code>null</code>。
			 */
			public AbstractMayChangeStateFlow(BlockKey blockKey) {
				super(blockKey);
			}
			
			/**
			 * 新实例。
			 * @param blockKey 阻挡键，不能为 <code>null</code>。
			 * @param progress 当前进度。
			 * @param totleProgress 总进度。
			 * @param determinateFlag 是否为进度已知的流程。
			 * @param cancelableFlag 是否能够被取消。
			 * @throws NullPointerException 入口参数为 <code>null</code>。
			 */
			public AbstractMayChangeStateFlow(BlockKey blockKey, int progress, int totleProgress,
					boolean determinateFlag, boolean cancelableFlag) {
				super(blockKey, progress, totleProgress, determinateFlag, cancelableFlag);
			}

			/**
			 * 检查程序是否可以进行统计了。
			 * @return 程序是否可以进行统计了。
			 */
			protected boolean isReadyForCount(){
				if(Objects.isNull(manager.getFileSelectModel().getAttendanceFile())) return false;
				if(Objects.isNull(manager.getFileSelectModel().getWorkticketFile())) return false;
				
				return true;
			}

			/**
			 * 可能会导致程序的统计结果过期。
			 * <p> 该方法会检查程序的统计状态，如果状态不是还未统计，则令统计结果过期。
			 * @return 该操作是否造成了程序的统计结果过期。
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
						throw new IllegalStateException("程序已经启动或已经结束");
					}
					
					//更新模型，此时的多语言模型和记录器模型被更新为默认值。
					try{
						manager.getLoggerModel().update();
						manager.getLabelMutilangModel().update();
						manager.getLoggerMutilangModel().update();
						manager.getBlockModel().update();
					}catch (ProcessException ignore) {
						//此时均为默认值，不可能抛出异常。
					}
					
					//加载程序的资源模型
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
					
					//加载程序中的记录器模型。
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
					
					//加载记录器多语言配置。
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
					
					//加载程序的核心配置。
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
					
					//加载阻挡模型字典
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
					
					//加载标签多语言配置。
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
					
					//加载班次信息。
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
					
					//加载工作信息
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
					
					//生成视图，并使其可见。
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
					
					//设置成功消息
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_13);
					
					//决定根目录
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

					//决定文件筛选器
					FileFilter[] fileFilters = new FileFilter[]{
							new FileNameExtensionFilter(getLabel(LoggerStringKey.Mh4w_FlowProvider_15), "xls")
					};
					
					//不接受所有文件筛选器
					boolean acceptAllFileFilter = false;
					
					//不接受文件多选
					boolean mutiSelectionEnabled = false;
					
					//只允许选择文件
					int fileSelectionMode = JFileChooser.FILES_ONLY;
					
					//获得文件
					File[] files = manager.getGuiController().askFile4Open(directory, fileFilters, acceptAllFileFilter, 
							mutiSelectionEnabled, fileSelectionMode, manager.getCoreConfigModel().getLabelMutilangLocale());
					
					//将获取的文件设置在模型中并输出记录
					if(files.length == 0){
						info(LoggerStringKey.Mh4w_FlowProvider_17);
					}else{
						manager.getFileSelectModel().setAttendanceFile(files[0]);
						formatInfo(LoggerStringKey.Mh4w_FlowProvider_18, files[0].getAbsolutePath());
					}
					
					//更新状态模型-是否能够进行统计了
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
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_13);
					
					//决定根目录
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

					//决定文件筛选器
					FileFilter[] fileFilters = new FileFilter[]{
							new FileNameExtensionFilter(getLabel(LoggerStringKey.Mh4w_FlowProvider_15), "xls")
					};
					
					//不接受所有文件筛选器
					boolean acceptAllFileFilter = false;
					
					//不接受文件多选
					boolean mutiSelectionEnabled = false;
					
					//只允许选择文件
					int fileSelectionMode = JFileChooser.FILES_ONLY;
					
					//获得文件
					File[] files = manager.getGuiController().askFile4Open(directory, fileFilters, acceptAllFileFilter, 
							mutiSelectionEnabled, fileSelectionMode, manager.getCoreConfigModel().getLabelMutilangLocale());
					
					//将获取的文件设置在模型中并输出记录
					if(files.length == 0){
						info(LoggerStringKey.Mh4w_FlowProvider_17);
					}else{
						manager.getFileSelectModel().setWorkticketFile(files[0]);
						formatInfo(LoggerStringKey.Mh4w_FlowProvider_18, files[0].getAbsolutePath());
					}
					
					//更新状态模型-是否能够进行统计了
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_41);
					
					/*
					 * -------------------------- 统计算法 -------------------------
					 * start：
					 * 清除错误模型
					 * 清除原始考勤数据模型
					 * 清除原始工票数据模型
					 * 
					 * 读取原始考勤数据
					 * 读取原始工票数据
					 * 
					 * 转换原始数据，并将发生的问题记录在错误模型中
					 * if 错误模型.size() > 0 then goto err_1
					 * 
					 * 检测人员一致性，并将发生的问题记录在错误模型中
					 * 检测人员匹配性，并将发生的问题记录在错误模型中
					 * if 错误模型.size() > 0 then goto err_2
					 * 
					 * 统计数据，并把统计结果放在统计结果模型中
					 * 
					 * 设置状态模型为 统计_等待导出，显示详细信息面板
					 * 
					 * exit
					 * 
					 *  err_1:
					 *  设置状态模型为 统计_错误，并显示错误面板。
					 *  exit
					 *  
					 *  err_2:
					 *  设置状态模型为 统计_错误，并显示错误面板。
					 *  exit
					 */
					
					//清除残留数据
					info(LoggerStringKey.Mh4w_FlowProvider_47);
					message(LoggerStringKey.Mh4w_FlowProvider_47);
					manager.getOriginalAttendanceDataModel().clear();
					manager.getOriginalWorkticketDataModel().clear();
					manager.getAttendanceDataModel().clear();
					manager.getWorkticketDataModel().clear();
					manager.getCountResultModel().clear();
					manager.getFailModel().clear();
					manager.getStateModel().setCountResultOutdated(false);
					
					//读取原始考勤数据
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
					//读取原始工票数据
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
					
					//转换原始数据，并将发生的问题记录在错误模型中
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
					//if 错误模型.size() > 0 then goto err_1
					if(manager.getFailModel().size() > 0){
						message(LoggerStringKey.Mh4w_FlowProvider_53);
						manager.getStateModel().setCountState(CountState.STARTED_ERROR);
						return;
					}
					
					//检测人员一致性，并将发生的问题记录在错误模型中
					info(LoggerStringKey.Mh4w_FlowProvider_55);
					for(DataFromXls dataFromXls : CountUtil.personConsistentCheck(manager.getAttendanceDataModel(),
							manager.getWorkticketDataModel())){
						manager.getFailModel().add(new DefaultFail(dataFromXls, FailType.PERSON_UNCONSISTENT));
					}
					//检测人员匹配性，并将发生的问题记录在错误模型中
					info(LoggerStringKey.Mh4w_FlowProvider_78);
					for(DataFromXls dataFromXls : CountUtil.personMatchCheck(manager.getAttendanceDataModel(),
							manager.getWorkticketDataModel())){
						manager.getFailModel().add(new DefaultFail(dataFromXls, FailType.PERSON_UNMATCH));
					}
					//if 错误模型.size() > 0 then goto err_2
					if(manager.getFailModel().size() > 0){
						message(LoggerStringKey.Mh4w_FlowProvider_53);
						manager.getStateModel().setCountState(CountState.STARTED_ERROR);
						return;
					}
					
					//统计数据，并把统计结果放在统计结果模型中
					info(LoggerStringKey.Mh4w_FlowProvider_79);
					for(CountResult countResult : CountUtil.countData(manager.getAttendanceDataModel(),
							manager.getWorkticketDataModel())){
						manager.getCountResultModel().add(countResult);
					}
					
					//更新状态
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_38);
					
					//加载程序的核心配置。
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
					
					//加载班次信息。
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
					
					//加载工作信息
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
					
					//可能会引起统计结果的过时
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
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
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_71);
					
					//保存日期类型信息。
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
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_74);
					
					//读取日期类型信息。
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
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.Mh4w_FlowProvider_80);
					
					//决定根目录
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

					//决定文件筛选器
					FileFilter[] fileFilters = new FileFilter[]{
							new FileNameExtensionFilter(getLabel(LoggerStringKey.Mh4w_FlowProvider_15), "xls")
					};
					
					//不接受所有文件筛选器
					boolean acceptAllFileFilter = false;
					
					//获得文件
					File file = manager.getGuiController().askFile4Save(directory, fileFilters, acceptAllFileFilter, "xls",
							manager.getCoreConfigModel().getLabelMutilangLocale());
					
					//将获取的文件设置在模型中并输出记录
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
			//停止后台模型和工具运行时模型。
			info(LoggerStringKey.Mh4w_Exitor_2);
			manager.getBackgroundModel().shutdown();
			manager.getFinishedFlowTaker().shutdown();
			
			//等待50毫秒，此时后台模型和工具运行时模型中的执行器应该会自然终结。
			try {
				Thread.sleep(50);
			} catch (InterruptedException ignore) {
				//中断也要按照基本法。
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
					//中断也要按照基本法。
				}
				
				if(! manager.getBackgroundModel().getExecutorService().isTerminated()){
					warn(LoggerStringKey.Mh4w_Exitor_3);
				}
			}
			
			//释放界面
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
				//中断也要按照基本法。
			}
			
			//释放模型
			info(LoggerStringKey.Mh4w_Exitor_5);
			manager.dispose();
			
			//解除对象的引用
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
