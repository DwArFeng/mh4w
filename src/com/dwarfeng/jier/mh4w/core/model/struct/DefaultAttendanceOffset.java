package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认考勤补偿。
 * <p> 考勤补偿的默认实现。
 * @author DwArFeng
 * @since 1.0.0
 */
public final class DefaultAttendanceOffset implements AttendanceOffset {

	private final Person person;
	private final String description;
	private final double value;
	
	/**
	 * 新实例。 
	 * @param person 指定的人员。
	 * @param description 指定的描述。
	 * @param value 指定的补偿值。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultAttendanceOffset(Person person, String description, double value) {
		Objects.requireNonNull(person, "入口参数 person 不能为 null。");
		Objects.requireNonNull(description, "入口参数 description 不能为 null。");

		this.person = person;
		this.description = description;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataWithPerson#getPerson()
	 */
	@Override
	public Person getPerson() {
		return person;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Description#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.AttendanceOffset#getValue()
	 */
	@Override
	public double getValue() {
		return value;
	}

}
