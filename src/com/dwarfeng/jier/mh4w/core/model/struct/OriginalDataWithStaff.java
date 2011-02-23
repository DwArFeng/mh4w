package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 拥有员工信息的原始数据。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface OriginalDataWithStaff {

	/**
	 * 获取员工工号。
	 * @return 员工工号。
	 */
	public String getWorkNumber();
	
	/**
	 * 获取员工部门。
	 * @return 员工部门。
	 */
	public String getDepartment();

	/**
	 * 获取员工姓名。
	 * @return 员工姓名。
	 */
	public String getName();
	
}
