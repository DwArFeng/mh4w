package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�ϰ�Ρ�
 * <p> ��νӿڵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultShift implements Shift{
	
	private final String name;
	private final TimeSection[] shiftSections;
	private final TimeSection[] restSections;
	private final TimeSection[] extraShiftSections;

	/**
	 * ��ʵ����
	 * @param name ָ�������ơ�
	 * @param shiftSections ָ�����ϰ�ʱ������
	 * @param restSections ָ������Ϣʱ������
	 * @param extraShiftSections ָ�����ϰ�ʱ������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultShift(String name, TimeSection[] shiftSections, TimeSection[] restSections, TimeSection[] extraShiftSections) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		Objects.requireNonNull(shiftSections, "��ڲ��� shiftSections ����Ϊ null��");
		Objects.requireNonNull(restSections, "��ڲ��� restSections ����Ϊ null��");
		Objects.requireNonNull(extraShiftSections, "��ڲ��� extraShiftSections ����Ϊ null��");

		this.name = name;
		this.shiftSections = shiftSections;
		this.restSections = restSections;
		this.extraShiftSections = extraShiftSections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Shift#getShiftSections()
	 */
	@Override
	public TimeSection[] getShiftSections() {
		return shiftSections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Shift#getRestSections()
	 */
	@Override
	public TimeSection[] getRestSections() {
		return restSections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Shift#getExtraShiftSections()
	 */
	@Override
	public TimeSection[] getExtraShiftSections() {
		return extraShiftSections;
	}

}
