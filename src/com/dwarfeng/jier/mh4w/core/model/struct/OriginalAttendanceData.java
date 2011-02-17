package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 原始出勤数据。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface OriginalAttendanceData extends OriginalDataWithStaff {

	/**
	 * 获取记录的日期。
	 * @return 记录的日期。
	 */
	public String getDate();
	
	/**
	 * 获取记录的班次。
	 * @return 记录的班次。
	 */
	public String getShift();
	
	/**
	 * 获取出勤记录。
	 * @return 出勤记录。
	 */
	public String getAttendanceRecord();
	
}
