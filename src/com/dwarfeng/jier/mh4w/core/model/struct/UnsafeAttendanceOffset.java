package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 不安全考勤偏置。
 * <p> 不安全的考勤偏置，相较于考勤偏置，其读取速度较慢，还可能产生异常。
 * @author DwArFeng
 * @since 1.0.0
 */
public interface UnsafeAttendanceOffset {

	/**
	 * 获取人员信息。
	 * @return 人员信息。
	 * @throws ProcessException 过程异常。
	 */
	public Person getPerson() throws ProcessException;
	
	/**
	 * 获取偏移值。
	 * @return 偏移值。
	 * @throws ProcessException 过程异常。
	 */
	public double getValue() throws ProcessException;
	
	/**
	 * 获取描述。
	 * @return 描述。
	 * @throws ProcessException 过程异常。
	 */
	public String getDescription() throws ProcessException;
	
}
