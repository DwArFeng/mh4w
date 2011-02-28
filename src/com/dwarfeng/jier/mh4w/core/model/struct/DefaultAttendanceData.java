package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;

/**
 * 默认出勤数据。
 * <p> 出勤数据的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultAttendanceData implements AttendanceData{
	
	private final String fileName;
	private final int row;
	private final Person person;
	private final CountDate countDate;
	private final Shift shift;
	private final TimeSection attendanceRecord;
	private final DateType dateType;
	private final double equivalentWorkTime;
	private final double originalWorkTime;
	
	/**
	 * 新实例。
	 * @param fileName 文件的名称。
	 * @param row 数据所在的行号。
	 * @param person 指定的员工。
	 * @param countDate 指定的统计日期。
	 * @param shift 指定的班次。
	 * @param attendanceRecord 指定的考勤记录。
	 * @param dateType 指定的日期类型。
	 * @param equivalentWorkTime 指定的等效工时。
	 * @param originalWorkTime 指定的原始工时。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultAttendanceData(String fileName, int row, Person person, CountDate countDate, Shift shift, 
			TimeSection attendanceRecord, DateType dateType, double equivalentWorkTime, double originalWorkTime) {
		Objects.requireNonNull(fileName, "入口参数 fileName 不能为 null。");
		Objects.requireNonNull(person, "入口参数 person 不能为 null。");
		Objects.requireNonNull(countDate, "入口参数 countDate 不能为 null。");
		Objects.requireNonNull(shift, "入口参数 shift 不能为 null。");
		Objects.requireNonNull(attendanceRecord, "入口参数 attendanceRecord 不能为 null。");
		Objects.requireNonNull(dateType, "入口参数 dateType 不能为 null。");

		this.fileName = fileName;
		this.row = row;
		this.person = person;
		this.countDate = countDate;
		this.shift = shift;
		this.attendanceRecord = attendanceRecord;
		this.dateType = dateType;
		this.equivalentWorkTime = equivalentWorkTime;
		this.originalWorkTime = originalWorkTime;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls#getFileName()
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls#getRow()
	 */
	@Override
	public int getRow() {
		return row;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataWithPerson#getPerson()
	 */
	@Override
	public Person getPerson() {
		return person;
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData#getDateType()
	 */
	@Override
	public DateType getDateType() {
		return dateType;
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
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData#getEquivalentWorkTime()
	 */
	@Override
	public double getEquivalentWorkTime() {
		return equivalentWorkTime;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData#getOriginalWorkTime()
	 */
	@Override
	public double getOriginalWorkTime() {
		return originalWorkTime;
	}

}
