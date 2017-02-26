package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * 日期类型界面适配器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class DateTypeFrameAdapter implements DateTypeFrameObverser {

	@Override
	public void fireHideDateTypeFrame() {}
	@Override
	public void fireSubmitDateTypeEntry(CountDate key, DateType value) {}
	@Override
	public void fireRemoveDateTypeEntry(CountDate key) {}
	@Override
	public void fireClearDateTypeEntry() {}
	@Override
	public void fireSaveDateTypeEntry() {}
	@Override
	public void fireLoadDateTypeEntry() {}
	
}
