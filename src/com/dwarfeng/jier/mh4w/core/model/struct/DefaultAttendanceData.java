package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;

/**
 * Ĭ�ϳ������ݡ�
 * <p> �������ݵ�Ĭ��ʵ�֡�
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
	 * ��ʵ����
	 * @param fileName �ļ������ơ�
	 * @param row �������ڵ��кš�
	 * @param person ָ����Ա����
	 * @param countDate ָ����ͳ�����ڡ�
	 * @param shift ָ���İ�Ρ�
	 * @param attendanceRecord ָ���Ŀ��ڼ�¼��
	 * @param dateType ָ�����������͡�
	 * @param equivalentWorkTime ָ���ĵ�Ч��ʱ��
	 * @param originalWorkTime ָ����ԭʼ��ʱ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultAttendanceData(String fileName, int row, Person person, CountDate countDate, Shift shift, 
			TimeSection attendanceRecord, DateType dateType, double equivalentWorkTime, double originalWorkTime) {
		Objects.requireNonNull(fileName, "��ڲ��� fileName ����Ϊ null��");
		Objects.requireNonNull(person, "��ڲ��� person ����Ϊ null��");
		Objects.requireNonNull(countDate, "��ڲ��� countDate ����Ϊ null��");
		Objects.requireNonNull(shift, "��ڲ��� shift ����Ϊ null��");
		Objects.requireNonNull(attendanceRecord, "��ڲ��� attendanceRecord ����Ϊ null��");
		Objects.requireNonNull(dateType, "��ڲ��� dateType ����Ϊ null��");

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
