package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 出勤数据。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface AttendanceData {
	
	/**
	 * 获得出勤数据中的工号（主键）。
	 * @return 出勤数据中的工号（主键）。
	 */
	public default String getWorkNumber(){
		return getStaff().getWorkNumber();
	}
	
	/**
	 * 获得数据中的员工信息。
	 * @return 数据中的员工信息。
	 */
	public Staff getStaff();
	
	/**
	 * 获取数据中的统计日期。
	 * @return 数据中的统计日期。
	 */
	public CountDate getCountDate();
	
	/**
	 * 获取数据中的班次信息。
	 * @return 数据中的班次信息。
	 */
	public Shift getShift();
	
	/**
	 * 获取数据中的出勤记录。
	 * @return 数据中的出勤记录。
	 */
	public TimeSection getAttendanceRecord();

}
