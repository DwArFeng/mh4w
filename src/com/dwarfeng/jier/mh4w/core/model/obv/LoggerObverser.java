package com.dwarfeng.jier.mh4w.core.model.obv;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo;

/**
 * 记录器观察器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface LoggerObverser extends Obverser{
	
	/**
	 * 通知模型中的记录器上下文发生了改变。
	 * @param oldOne 旧的记录器上下文。
	 * @param newOne  新的记录器上下文。
	 */
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne);
	
	/**
	 * 通知模型中的记录器信息被添加。
	 * @param loggerInfo 被添加的记录器信息。
	 */
	public void fireLoggerInfoAdded(LoggerInfo loggerInfo);
	
	/**
	 * 通知模型中的记录器信息被移除。
	 * @param loggerInfo 被移除的记录器信息。
	 */
	public void fireLoggerInfoRemoved(LoggerInfo loggerInfo);
	
	/**
	 * 通知模型中的记录器信息被清除。
	 */
	public void fireLoggerInfoCleared();

	/**
	 * 通知模型更新。
	 */
	public void fireUpdated();
}
