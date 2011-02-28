package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;

/**
 * 不安全日期类型入口。
 * <p> 入口提供了获取统计日期以及日期类型的方法，这正是日期类型模型所需要的键和值。
 * <p> 不安全的日期类型，相较于日期模型的入口，其读取速度较慢，还可能产生异常。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface UnsafeDateTypeEntry {
	
	/**
	 * 获取不安全日期类型中的统计日期。
	 * @return 不安全日期类型中的统计日期。
	 * @throws ProcessException 过程异常。
	 */
	public CountDate getCountDate() throws ProcessException;
	
	/**
	 * 获取不安全日期类型中的日期类型。
	 * @return 不安全日期类型中的日期类型。
	 * @throws ProcessException 过程异常。
	 */
	public DateType getDateType() throws ProcessException;

}
