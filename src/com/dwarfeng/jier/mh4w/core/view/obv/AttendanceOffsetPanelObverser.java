package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset;

/**
 * 考勤补偿面板观察器。
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface AttendanceOffsetPanelObverser extends Obverser{

	/**
	 * 通知提交考勤补偿。
	 * @param unsafeAttendanceOffset 不安全出勤补偿。
	 */
	public void fireSubmitAttendanceOffset(UnsafeAttendanceOffset unsafeAttendanceOffset);

	/**
	 * 通知清除考勤补偿。
	 */
	public void fireClearAttendanceOffset();

	/**
	 * 通知保存考勤补偿。
	 */
	public void fireSaveAttendanceOffset();

	/**
	 * 通知读取考勤补偿。
	 */
	public void fireLoadAttendanceOffset();

	/**
	 * 通知移除考勤补偿。
	 * @param index 需要移除的序号。
	 */
	public void fireRemoveAttendanceOffset(int index);

}
