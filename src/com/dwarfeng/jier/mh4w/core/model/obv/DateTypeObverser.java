package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * 日期类型观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DateTypeObverser extends Obverser{

	/**
	 * 通知指定的日期被添加进模型。
	 * @param key 指定的键。
	 * @param value 指定的值。
	 */
	public void fireDatePut(CountDate key, DateType value);
	
	/**
	 * 通知指定的数据从模型中移除。
	 * @param key 指定的键。
	 */
	public void fireDateRemoved(CountDate key);
	
	/**
	 * 通知模型中指定的键被改变。
	 * @param key 指定的键。
	 * @param oldValue 键对应的旧值。
	 * @param newValue 键对应的新值。
	 */
	public void fireDateChanged(CountDate key, DateType oldValue, DateType newValue);
	
	/**
	 * 通知指定的数据被清除。
	 */
	public void fireDateCleared();
	
}
