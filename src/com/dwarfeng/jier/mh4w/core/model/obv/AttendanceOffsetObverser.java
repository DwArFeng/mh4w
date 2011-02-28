package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 考勤补偿观察器。
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface AttendanceOffsetObverser extends Obverser{

	/**
	 * 通知指定的日期被添加进模型。
	 * @param key 指定的键。
	 * @param value 指定的值。
	 */
	public void fireWorkNumberPut(String key, Double value);
	
	/**
	 * 通知指定的数据从模型中移除。
	 * @param key 指定的键。
	 */
	public void fireWorkNumberRemoved(String key);
	
	/**
	 * 通知模型中指定的键被改变。
	 * @param key 指定的键。
	 * @param oldValue 键对应的旧值。
	 * @param newValue 键对应的新值。
	 */
	public void fireWorkNumberChanged(String key, Double oldValue, Double newValue);
	
	/**
	 * 通知指定的数据被清除。
	 */
	public void fireDateCleared();
	
}
