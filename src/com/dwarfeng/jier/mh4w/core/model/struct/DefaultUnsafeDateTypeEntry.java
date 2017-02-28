package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;

/**
 * Ĭ�ϲ���ȫ����������ڡ�
 * <p> ����ȫ����������ڵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultUnsafeDateTypeEntry implements UnsafeDateTypeEntry{
	
	private final String year;
	private final String month;
	private final String day;
	private final String dateType;

	/**
	 * ��ʵ����
	 * @param year ָ�����ꡣ
	 * @param month ָ�����·ݡ�
	 * @param day ָ�������ڡ�
	 * @param dateType ָ�����������͡�
	 */
	public DefaultUnsafeDateTypeEntry(String year, String month, String day, String dateType) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.dateType = dateType;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeDateTypeEntry#getCountDate()
	 */
	@Override
	public CountDate getCountDate() throws ProcessException {
		try{
			int year = Integer.parseInt(this.year);
			int month = Integer.parseInt(this.month);
			int day = Integer.parseInt(this.day);
			return new CountDate(year, month, day);
		}catch (Exception e) {
			throw new ProcessException("����ȫ����������� - �޷���ȡ��ڵ�ͳ������", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeDateTypeEntry#getDateType()
	 */
	@Override
	public DateType getDateType() throws ProcessException {
		try{
			return DateType.valueOf(dateType);
		}catch (Exception e) {
			throw new ProcessException("����ȫ����������� - �޷���ȡ��ڵ���������", e);
		}
	}

}
