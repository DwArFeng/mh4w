package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset;

/**
 * 详细面板观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DetailFrameObverser extends Obverser{

	/**
	 * 通知隐藏详细界面。
	 */
	public void fireHideDetailFrame();

	/**
	 * 通知导出统计结果
	 */
	public void fireExportCountResult();

	/**
	 * 通知提交考勤补偿。
	 * @param unsafeAttendanceOffset 不安全考勤补偿。
	 */
	public void fireSubmitAttendanceOffset(UnsafeAttendanceOffset unsafeAttendanceOffset);

	/**
	 * 通知清除考勤补偿。
	 */
	public void fireClearAttendanceOffset();

}
