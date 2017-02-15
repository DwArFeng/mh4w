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
 * 工时统计软件。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class Mh4w {
	
	
	/**
	 * 调试用的启动方法。
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws ProcessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		new Mh4w().start();
	}
	
	/**程序的版本*/
	public final static Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	
	/**程序的实例列表，用于持有引用*/
	private static final Set<Mh4w> INSTANCES  = Collections.synchronizedSet(new HashSet<>());
	/**工具平台的进程工厂*/
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("mh4w");
	
	/**程序的过程提供器*/
	private final FlowProvider flowProvider = new FlowProvider();
	/**程序被中止时的钩子*/
	private final Map<String, Thread> shutdownHooks = Collections.synchronizedMap(new HashMap<>());

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
				//未初始化之前，多语言模型使用的是固化在程序中的数据，不可能出现异常。
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
		 * 获取一个新的程序初始化时使用的过程。
		 * @return 新的程序初始化时使用的后台过程。
		 */
		public Flow newInitializeFlow() {
			return new InitializeFlow();
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
			 * @param initMessage 初始的信息，不能为 <code>null</code>。
			 * @throws NullPointerException 入口参数为 <code>null</code>。
			 */
			public AbstractInnerFlow(BlockKey blockKey, String initMessage) {
				this(blockKey, initMessage, 0, 0, false, false);
			}
			
			/**
			 * 新实例。
			 * @param blockKey 阻挡键，不能为 <code>null</code>。
			 * @param initMessage 初始的信息，不能为 <code>null</code>。
			 * @param progress 当前进度。
			 * @param totleProgress 总进度。
			 * @param determinateFlag 是否为进度已知的流程。
			 * @param cancelableFlag 是否能够被取消。
			 */
			public AbstractInnerFlow(BlockKey blockKey, String initMessage, int progress, int totleProgress, boolean determinateFlag, boolean cancelableFlag ){
				super(progress, totleProgress, determinateFlag, cancelableFlag);
				Objects.requireNonNull(blockKey, "入口参数 blockKey 不能为 null。");
				Objects.requireNonNull(initMessage, "入口参数 initMessage 不能为 null。");
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
			 * 子处理方法。
			 * <p> 该方法是主要的处理方法。
			 * <p> 该方法不允许抛出任何异常。
			 */
			protected abstract void subProcess();
			
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
			
			/**
			 * 格式化设置信息。
			 * @param loggerStringKey 指定的记录器键。
			 * @param args format 参数。
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
					
					//设置成功消息
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
