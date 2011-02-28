package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;

/**
 * 默认不安全日期类型入口。
 * <p> 不安全日期类型入口的默认实现。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultUnsafeDateTypeEntry implements UnsafeDateTypeEntry{
	
	private final String year;
	private final String month;
	private final String day;
	private final String dateType;

	/**
	 * 新实例。
	 * @param year 指定的年。
	 * @param month 指定的月份。
	 * @param day 指定的日期。
	 * @param dateType 指定的日期类型。
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
			throw new ProcessException("不安全日期类型入口 - 无法读取入口的统计日期", e);
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
			throw new ProcessException("不安全日期类型入口 - 无法读取入口的日期类型", e);
		}
	}

}
