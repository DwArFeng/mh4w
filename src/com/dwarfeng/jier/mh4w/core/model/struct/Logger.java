package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 工具平台用记录器。
 * <p> 该命名是为了与 log4j 中的 logger 加以区分。
 * <p> 用来处理与记录有关的方法。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface Logger {

	/**
	 * 调用记录站点的trace方法。
	 * @param message 指定的信息。
	 */
	public void trace(String message);
	
	/**
	 * 调用记录站点的debug方法。
	 * @param message 指定的信息。
	 */
	public void debug(String message);
	
	/**
	 * 调用记录站点的info方法。
	 * @param message 指定的信息。
	 */
	public void info(String message);
	
	/**
	 * 调用记录站点的warn方法。
	 * @param message 指定的信息。
	 */
	public void warn(String message);
	
	/**
	 * 调用记录站点的warn方法。
	 * @param message 指定的信息。
	 * @param t 指定的可抛出对象，一般是线程或异常的跟踪堆栈。
	 */
	public void warn(String message, Throwable t);
	
	/**
	 * 调用记录站点的error方法。
	 * @param message 指定的信息。
	 * @param t 指定的可抛出对象，一般是线程或异常的跟踪堆栈。
	 */
	public void error(String message, Throwable t);
	
	/**
	 * 调用记录站点的fatal方法。
	 * @param message 指定的信息。
	 * @param t 指定的可抛出对象，一般是线程或异常的跟踪堆栈。
	 */
	public void fatal(String message, Throwable t);
}
