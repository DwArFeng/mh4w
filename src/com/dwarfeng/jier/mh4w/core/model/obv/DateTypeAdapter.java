package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * 日期类型观察器适配器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public abstract class DateTypeAdapter implements DateTypeObverser {

	@Override
	public void fireDatePut(CountDate key, DateType value) {}
	@Override
	public void fireDateRemoved(CountDate key) {}
	@Override
	public void fireDateChanged(CountDate key, DateType oldValue, DateType newValue) {}
	@Override
	public void fireDateCleared() {}
	
}
