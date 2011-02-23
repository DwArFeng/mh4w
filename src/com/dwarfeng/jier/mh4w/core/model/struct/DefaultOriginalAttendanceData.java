package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

public final class DefaultOriginalAttendanceData implements OriginalAttendanceData {
	
	private final String fileName;
	private final int row;
	private final String workNumber;
	private final String department;
	private final String name;
	private final String date;
	private final String shift;
	private final String attendanceRecord;

	/**
	 * 新实例。
	 * @param fileName 源xls文件的名称。
	 * @param row 源xls文件中所在的行。
	 * @param workNumber 工号。
	 * @param department 部门。
	 * @param name 姓名。
	 * @param date 考勤日期。
	 * @param shift 出勤班次。
	 * @param attendanceRecord 打脸记录。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultOriginalAttendanceData(String fileName, int row, String workNumber, String department, String name, String date, String shift, String attendanceRecord) {
		Objects.requireNonNull(fileName, "入口参数 fileName 不能为 null。");
		if(row <= 0) throw new IllegalArgumentException("入口参数 row 不能小于等于0");
		Objects.requireNonNull(workNumber, "入口参数 workNumber 不能为 null。");
		Objects.requireNonNull(department, "入口参数 department 不能为 null。");
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(date, "入口参数 date 不能为 null。");
		Objects.requireNonNull(shift, "入口参数 shift 不能为 null。");
		Objects.requireNonNull(attendanceRecord, "入口参数 attendanceRecord 不能为 null。");

		this.fileName = fileName;
		this.row = row;
		this.workNumber = workNumber;
		this.department = department;
		this.name = name;
		this.date = date;
		this.shift = shift;
		this.attendanceRecord = attendanceRecord;
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalDataWithStaff#getWorkNumber()
	 */
	@Override
	public String getWorkNumber() {
		return workNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalDataWithStaff#getDepartement()
	 */
	@Override
	public String getDepartment() {
		return department;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalDataWithStaff#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData#getDate()
	 */
	@Override
	public String getDate() {
		return date;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData#getShift()
	 */
	@Override
	public String getShift() {
		return shift;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData#getAttendanceRecord()
	 */
	@Override
	public String getAttendanceRecord() {
		return attendanceRecord;
	}

}
