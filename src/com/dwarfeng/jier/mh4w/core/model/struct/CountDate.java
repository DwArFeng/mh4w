package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * ͳ��ʱ�䡣
 * <p> ��������Ϊ���� Date �������֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class CountDate {
	
	private final int year;
	private final int month;
	private final int day;
	
	/**
	 * ��ʵ����
	 * @param year ָ������ݡ�
	 * @param month ָ�����·ݡ�
	 * @param day ָ�����졣
	 */
	public CountDate(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return year*10000 + month*100 + day;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(Objects.isNull(obj)) return false;
		if(obj == this) return true;
		if(! (obj instanceof CountDate)) return false;
		CountDate that = (CountDate) obj;
		return this.day == that.day && this.month == that.month && this.year == that.year;
	}
	
}
