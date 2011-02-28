package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;

/**
 * 出勤数据。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface AttendanceData extends DataWithPerson, DataFromXls{
	
	/**
	 * 获得出勤数据中的工号（主键）。
	 * @return 出勤数据中的工号（主键）。
	 */
	public default String getWorkNumber(){
		return getPerson().getWorkNumber();
	}
	
	/**
	 * 获取数据中的统计日期。
	 * @return 数据中的统计日期。
	 */
	public CountDate getCountDate();
	
	/**
	 * 获取统计日期的日期类型。
	 * @return 统计日期的日期类型。
	 */
	public DateType getDateType();
	
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
	
	/**
	 * 获取等效的工作时间。
	 * @return 等效的工作时间。
	 */
	public double getEquivalentWorkTime();
	
	/**
	 * 获取原始工作时间。
	 * @return 原始工作时间。
	 */
	public double getOriginalWorkTime();

}
