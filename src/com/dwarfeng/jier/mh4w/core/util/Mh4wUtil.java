package com.dwarfeng.jier.mh4w.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.dwarfeng.jier.mh4w.core.control.Mh4w;
import com.dwarfeng.jier.mh4w.core.model.struct.Logger;

/**
 * 关于工时统计软件的工具类。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class Mh4wUtil {
	
	/**
	 * 获取一个新的阻挡字典指示的输入流。
	 * @return 新的阻挡字典指示的输入流。
	 */
	public final static InputStream newBlockDictionary(){
		return Mh4w.class.getResourceAsStream("/com/dwarfeng/jier/mh4w/resource/block_dictionary.xml");
	}
	
	/**
	 * 获取默认的记录器上下文。
	 * @return 默认的记录器上下文。
	 */
	public final static LoggerContext newDefaultLoggerContext(){
		try {
			ConfigurationSource cs = new ConfigurationSource(Mh4w.class.getResourceAsStream("/com/dwarfeng/jier/mh4w/resource/defaultres/logger/setting.xml"));
			return Configurator.initialize(null, cs);
		} catch (IOException e) {
			e.printStackTrace();
			return (LoggerContext) LogManager.getContext();
		}
	}
	
	/**
	 * 获取默认记录器接口。
	 * <p> 该记录器不进行任何操作。
	 * @return 新的初始化记录器接口。
	 */
	public final static Logger newDefaultLogger(){
		return new InitialLogger();
	}
	
	/**
	 * 向事件队列中添加一个指定的可运行对象。
	 * @param runnable 指定的可运行对象。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public final static void invokeInEventQueue(Runnable runnable){
		Objects.requireNonNull(runnable, "入口参数 runnable 不能为 null。");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeLater(runnable);
		}
	}
	
	/**
	 * 向实践队列中添加一个指定的可运行对象。
	 * <p> 在指定的可运行对象运行结束之前，当前线程将处于阻塞状态。
	 * @param runnable 指定的可运行对象。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 * @throws InvocationTargetException <code>runnable</code>	运行时抛出异常。
	 * @throws InterruptedException 如果等待事件指派线程执完成执行 <code>runnable.run()</code>时被中断 
	 */
	public final static void invokeAndWaitInEventQueue(Runnable runnable) throws InvocationTargetException, InterruptedException{
		Objects.requireNonNull(runnable, "入口参数 runnable 不能为 null。");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeAndWait(runnable);
		}
	}
	
	
	

	/**
	 * 默认记录器接口。
	 * <p> 该记录器不进行任何操作。
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InitialLogger implements Logger{

		@Override
		public void trace(String message) {}
		@Override
		public void debug(String message) {}
		@Override
		public void info(String message) {}
		@Override
		public void warn(String message) {}
		@Override
		public void warn(String message, Throwable t) {}
		@Override
		public void error(String message, Throwable t) {}
		@Override
		public void fatal(String message, Throwable t) {}
		
	}

	//禁止外部实例化
	private Mh4wUtil(){}

}
