package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�Ͽ��ڲ�����
 * <p> ���ڲ�����Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 1.0.0
 */
public final class DefaultAttendanceOffset implements AttendanceOffset {

	private final Person person;
	private final String description;
	private final double value;
	
	/**
	 * ��ʵ���� 
	 * @param person ָ������Ա��
	 * @param description ָ����������
	 * @param value ָ���Ĳ���ֵ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultAttendanceOffset(Person person, String description, double value) {
		Objects.requireNonNull(person, "��ڲ��� person ����Ϊ null��");
		Objects.requireNonNull(description, "��ڲ��� description ����Ϊ null��");

		this.person = person;
		this.description = description;
		this.value = value;
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
	 * @see com.dwarfeng.dutil.basic.str.Description#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceOffset#getValue()
	 */
	@Override
	public double getValue() {
		return value;
	}

}
