package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 拥有员工信息的数据。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DataWithPerson {
	
	/**
	 * 获取数据中的员工。
	 * @return 数据中的员工。
	 */
	public Person getPerson();

}
