package com.dwarfeng.jier.mh4w.core.view.obv;

/**
 * ������۲�����������
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
	public void fireHideDetailFrame() {}
	@Override
	public void fireShowDetailFrame() {}
	@Override
	public void fireCount() {}
	
}
