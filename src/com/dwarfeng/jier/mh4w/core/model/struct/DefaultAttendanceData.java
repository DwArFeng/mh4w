package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认出勤数据。
 * <p> 出勤数据的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultAttendanceData implements AttendanceData{
	
	private final Staff staff;
	private final CountDate countDate;
	private final Shift shift;
	private final TimeSection attendanceRecord;
	
	/**
	 * 新实例。
	 * @param staff 员工。
	 * @param countDate 统计日期。
	 * @param shift 班次。
	 * @param attendanceRecord 出勤记录。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultAttendanceData(Staff staff, CountDate countDate, Shift shift, TimeSection attendanceRecord) {
		Objects.requireNonNull(staff, "入口参数 staff 不能为 null。");
		Objects.requireNonNull(countDate, "入口参数 countDate 不能为 null。");
		Objects.requireNonNull(shift, "入口参数 shift 不能为 null。");
		Objects.requireNonNull(attendanceRecord, "入口参数 attendanceRecord 不能为 null。");

		this.staff = staff;
		this.countDate = countDate;
		this.shift = shift;
		this.attendanceRecord = attendanceRecord;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData#getStaff()
	 */
	@Override
	public Staff getStaff() {
		return staff;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData#getCountDate()
	 */
	@Override
	public CountDate getCountDate() {
		return countDate;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData#getShift()
	 */
	@Override
	public Shift getShift() {
		return shift;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData#getAttendanceRecord()
	 */
	@Override
	public TimeSection getAttendanceRecord() {
		return attendanceRecord;
	}
	
	

}
