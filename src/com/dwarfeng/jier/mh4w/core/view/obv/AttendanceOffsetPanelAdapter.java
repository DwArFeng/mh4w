package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset;

/**
 * ³öÇÚ²¹³¥Ãæ°å¹Û²ìÆ÷ÊÊÅäÆ÷¡£
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AttendanceOffsetPanelAdapter implements AttendanceOffsetPanelObverser {

	@Override
	public void fireSubmitAttendanceOffset(UnsafeAttendanceOffset unsafeAttendanceOffset) {}
	@Override
	public void fireClearAttendanceOffset() {}
	@Override
	public void fireSaveAttendanceOffset() {}
	@Override
	public void fireLoadAttendanceOffset() {}
	@Override
	public void fireRemoveAttendanceOffset(int index) {}
	
}
