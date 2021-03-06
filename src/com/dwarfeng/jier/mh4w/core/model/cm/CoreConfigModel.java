package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Locale;

/**
 * 核心配置模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface CoreConfigModel extends SyncConfigModel {
	
	/**
	 * 获取考勤表的起始列。
	 * @return 考勤表的起始列。
	 */
	public int getAttendanceStartRow();
	
	/**
	 * 获取考勤表中部门所在的列。
	 * @return 考勤表中部门所在的列。
	 */
	public int getAttendanceDepartmentColumn();
	
	/**
	 * 获取考勤表中工号所在的列。
	 * @return 考勤表中工号所在的列。
	 */
	public int getAttendanceWorkNumberColumn();
	
	/**
	 * 获取考勤表中姓名所在的列。
	 * @return 考勤表中姓名所在的列。
	 */
	public int getAttendacneNameColumn();
	
	/**
	 * 获取考勤表中日期所在的列。
	 * @return 考勤表中日期所在的列。
	 */
	public int getAttendanceDateColumn();
	
	/**
	 * 获取考勤表中班次所在的列。
	 * @return 考勤表中班次所在的列。
	 */
	public int getAttendanceShiftColumn();
	
	/**
	 * 获取考勤表中记录所在的列。
	 * @return 考勤表中班次所在的列
	 */
	public int getAttendanceRecordColumn();
	
	/**
	 * 获取出勤表中工作超过一天的关键字。
	 * @return 出勤表中工作超过一天的关键字。
	 */
	public String getAttendanceOverdayKeyword();
	
	/**
	 * 获取工票表的起始列。
	 * @return 工票表的起始列。
	 */
	public int getWorkticketStartRow();
	
	/**
	 * 获取工票表中部门所在的列。
	 * @return 工票表中部门所在的列。
	 */
	public int getWorkticketDepartmentColumn();
	
	/**
	 * 获取工票表中工号所在的列。
	 * @return 工票表中工号所在的列。
	 */
	public int getWorkticketWorkNumberColumn();
	
	/**
	 * 获取工票表中姓名所在的列。
	 * @return 工票表中姓名所在的列。
	 */
	public int getWorkticketNameColumn();
	
	/**
	 * 获取统计时所用的正常上班的工时系数。
	 * @return 统计所用的正常上班的工时系数。
	 */
	public double getShiftCoefficientCount();
	
	/**
	 * 获取统计时所用的拖班的工时系数。
	 * @return 统计所用的拖班的工时系数。
	 */
	public double getExtraCoefficientCount();
	
	/**
	 * 获取统计时所用的周末上班的工时系数。
	 * @return 统计所用的周末上班的工时系数。
	 */
	public double getWeekendCoefficientCount();
	
	/**
	 * 获取统计时所用的节假日上班的工时系数。
	 * @return 统计所用的节假日上班的工时系数。
	 */
	public double getHolidayCoefficientCount();
	
	/**
	 * 获取记录器多语言接口的当前语言。
	 * @return 记录器多语言接口当前的语言。
	 */
	public Locale getLoggerMutilangLocale();
	
	/**
	 * 获取标签多语言接口的当前语言。
	 * @return 标签多语言接口的当前语言。
	 */
	public Locale getLabelMutilangLocale();
	
}
