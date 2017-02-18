package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 工票数据。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface WorkticketData extends DataWithStaff{
	
	/**
	 * 获得出勤数据中的工号（主键）。
	 * @return 出勤数据中的工号（主键）。
	 */
	public default String getWorkNumber(){
		return getStaff().getWorkNumber();
	}
	
	/**
	 * 获取工票的上的统计时间。
	 * @return 工票上的统计时间。
	 */
	public int getWorkticket();
	
}
