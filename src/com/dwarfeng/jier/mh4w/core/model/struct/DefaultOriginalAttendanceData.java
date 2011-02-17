package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

public final class DefaultOriginalAttendanceData implements OriginalAttendanceData {
	
	private final String workNumber;
	private final String department;
	private final String name;
	private final String date;
	private final String shift;
	private final String attendanceRecord;
	
	public DefaultOriginalAttendanceData(String workNumber, String department, String name, String date, String shift, String attendanceRecord) {
		Objects.requireNonNull(workNumber, "入口参数 workNumber 不能为 null。");
		Objects.requireNonNull(department, "入口参数 department 不能为 null。");
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(date, "入口参数 date 不能为 null。");
		Objects.requireNonNull(shift, "入口参数 shift 不能为 null。");
		Objects.requireNonNull(attendanceRecord, "入口参数 attendanceRecord 不能为 null。");

		this.workNumber = workNumber;
		this.department = department;
		this.name = name;
		this.date = date;
		this.shift = shift;
		this.attendanceRecord = attendanceRecord;
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
	public String getDepartement() {
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
