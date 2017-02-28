package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * �������͹۲�����������
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
