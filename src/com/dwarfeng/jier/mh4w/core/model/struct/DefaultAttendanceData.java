package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�ϳ������ݡ�
 * <p> �������ݵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultAttendanceData implements AttendanceData{
	
	private final Staff staff;
	private final CountDate countDate;
	private final Shift shift;
	private final TimeSection attendanceRecord;
	
	/**
	 * ��ʵ����
	 * @param staff Ա����
	 * @param countDate ͳ�����ڡ�
	 * @param shift ��Ρ�
	 * @param attendanceRecord ���ڼ�¼��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultAttendanceData(Staff staff, CountDate countDate, Shift shift, TimeSection attendanceRecord) {
		Objects.requireNonNull(staff, "��ڲ��� staff ����Ϊ null��");
		Objects.requireNonNull(countDate, "��ڲ��� countDate ����Ϊ null��");
		Objects.requireNonNull(shift, "��ڲ��� shift ����Ϊ null��");
		Objects.requireNonNull(attendanceRecord, "��ڲ��� attendanceRecord ����Ϊ null��");

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
