package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Set;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.LoggerObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Logger;
import com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo;
import com.dwarfeng.jier.mh4w.core.model.struct.Updateable;

/**
 * 有关记录器的配置模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface LoggerModel extends Set<LoggerInfo>, ObverserSet<LoggerObverser>, ExternalReadWriteThreadSafe, Updateable{
	
	/**
	 * 获取记录器上下文。
	 * @return 记录器上下文。
	 */
	public LoggerContext getLoggerContext();
	
	/**
	 * 设置记录器上下文为指定上下文。
	 * @param loggerContext 记录器上下文。
	 * @return 该操作是否对模型造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setLoggerContext(LoggerContext loggerContext);
	
	/**
	 * 获取模型中的记录器。
	 * @return 模型中的记录器。
	 */
	public Logger getLogger();
		
}
