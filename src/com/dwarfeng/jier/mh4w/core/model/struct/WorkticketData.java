package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 工票数据。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface WorkticketData extends DataWithPerson, DataFromXls{
	
	/**
	 * 忽的工票数据中的工作类型。
	 * @return 工票数据中的工作类型。
	 */
	public Job getJob();
	
	/**
	 * 获取工票的上的统计时间。
	 * @return 工票上的统计时间。
	 */
	public double getWorkticket();
	
}
