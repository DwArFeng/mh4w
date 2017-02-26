package com.dwarfeng.jier.mh4w.core.view.obv;

/**
 * 主界面观察器适配器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class MainFrameAdapter implements MainFrameObverser {

	@Override
	public void fireWindowClosing() {}
	@Override
	public void fireSelectAttendanceFile() {}
	@Override
	public void fireSelectWorkticketFile() {}
	@Override
	public void fireCountReset() {}
	@Override
	public void fireHideDetail() {}
	@Override
	public void fireShowDetail() {}
	@Override
	public void fireCount() {}
	@Override
	public void fireShowAttrFrame() {}
	@Override
	public void fireShowDateTypeFrame() {}
	
}
