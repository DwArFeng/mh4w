package com.dwarfeng.jier.mh4w.core.model.eum;

/**
 * 统计状态。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum CountState {
	/**还未开始统计*/
	NOT_START,
	/**已经统计-并且有错误*/
	STARTED_ERROR,
	/**已经统计-并且等待导出*/
	STARTED_WAITING,
	/**已经统计-并且已经导出*/
	STARTED_EXPORTED
}
