package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认不安全考勤补偿。
 * <p> 不安全考勤补偿的默认
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class DefaultUnsafeAttendanceOffset implements UnsafeAttendanceOffset {
	
	private final String name;
	private final String department;
	private final String workNumber;
	private final String value;
	private final String description;
	
	/**
	 * 新实例。
	 * @param name 指定的名称。
	 * @param department 指定的部门。
	 * @param workNumber 指定的工号。
	 * @param value 指定的值。
	 * @param description 指定的描述。
	 */
	public DefaultUnsafeAttendanceOffset(String name, String department, String workNumber, String value,
			String description) {
		this.name = name;
		this.department = department;
		this.workNumber = workNumber;
		this.value = value;
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset#getPerson()
	 */
	@Override
	public Person getPerson() throws ProcessException {
		try{
			Objects.requireNonNull(name);
			Objects.requireNonNull(workNumber);
			Objects.requireNonNull(description);
			
			return new Person(workNumber, department, name);
		}catch (Exception e) {
			throw new ProcessException("不安全考勤补偿 - 无法读取人员信息", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset#getValue()
	 */
	@Override
	public double getValue() throws ProcessException {
		try{
			return Double.parseDouble(value);
		}catch (Exception e) {
			throw new ProcessException("不安全考勤补偿 - 无法解析偏移值", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset#getDescription()
	 */
	@Override
	public String getDescription() throws ProcessException {
		try{
			Objects.requireNonNull(description);
			return description;
		}catch (Exception e) {
			throw new ProcessException("不安全考勤补偿 - 无法读取描述信息", e);
		}
	}
	
}
