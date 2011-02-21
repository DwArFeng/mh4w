package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 原始工票数据。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface OriginalWorkticketData extends OriginalDataWithStaff, DataFromXls{

	/**
	 * 获取工票总时间。
	 * @return 工票总时间对应的文本。
	 */
	public String getWorkticket();
	
	/**
	 * 工具数据的工作类型。
	 * @return 数据的工作类型。
	 */
	public Job getJob();
	
}
