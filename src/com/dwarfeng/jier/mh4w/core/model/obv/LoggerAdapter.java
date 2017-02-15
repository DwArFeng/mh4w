package com.dwarfeng.jier.mh4w.core.model.obv;

import org.apache.logging.log4j.core.LoggerContext;

import com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo;

/**
 * 记录器模型观察器适配器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class LoggerAdapter implements LoggerObverser{

	@Override
	public void fireLoggerContextChanged(LoggerContext oldOne, LoggerContext newOne) {}
	@Override
	public void fireLoggerInfoAdded(LoggerInfo loggerInfo) {}
	@Override
	public void fireLoggerInfoRemoved(LoggerInfo loggerInfo) {}
	@Override
	public void fireLoggerInfoCleared() {}
	@Override
	public void fireUpdated() {}

}
