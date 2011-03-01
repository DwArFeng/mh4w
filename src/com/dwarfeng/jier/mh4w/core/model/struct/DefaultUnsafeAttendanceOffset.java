package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�ϲ���ȫ���ڲ�����
 * <p> ����ȫ���ڲ�����Ĭ��
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class DefaultUnsafeAttendanceOffset implements UnsafeAttendanceOffset {
	
	private final String name;
	private final String department;
	private final String workNumber;
	private final String value;
	private final String description;
	
	/**
	 * ��ʵ����
	 * @param name ָ�������ơ�
	 * @param department ָ���Ĳ��š�
	 * @param workNumber ָ���Ĺ��š�
	 * @param value ָ����ֵ��
	 * @param description ָ����������
	 */
	public DefaultUnsafeAttendanceOffset(String name, String department, String workNumber, String value,
			String description) {
		this.name = name;
		this.department = department;
		this.workNumber = workNumber;
		this.value = value;
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset#getPerson()
	 */
	@Override
	public Person getPerson() throws ProcessException {
		try{
			Objects.requireNonNull(name);
			Objects.requireNonNull(workNumber);
			Objects.requireNonNull(description);
			
			return new Person(workNumber, department, name);
		}catch (Exception e) {
			throw new ProcessException("����ȫ���ڲ��� - �޷���ȡ��Ա��Ϣ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset#getValue()
	 */
	@Override
	public double getValue() throws ProcessException {
		try{
			return Double.parseDouble(value);
		}catch (Exception e) {
			throw new ProcessException("����ȫ���ڲ��� - �޷�����ƫ��ֵ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset#getDescription()
	 */
	@Override
	public String getDescription() throws ProcessException {
		try{
			Objects.requireNonNull(description);
			return description;
		}catch (Exception e) {
			throw new ProcessException("����ȫ���ڲ��� - �޷���ȡ������Ϣ", e);
		}
	}
	
}
