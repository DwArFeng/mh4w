package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 员工结构。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class Person {
	
	/**
	 * 检测两个员工是否冲突。
	 * <p> 冲突是指两个员工的工号相等但是部门或姓名不相等。
	 * @param p1 员工1.
	 * @param p2 员工2.
	 * @return 员工1 和 员工2 是否冲突。
	 */
	public static boolean isConflict(Person p1, Person p2){
		Objects.requireNonNull(p1, "入口参数 p1 不能为 null。");
		Objects.requireNonNull(p2, "入口参数 p2 不能为 null。");

		if(! p1.workNumber.equals(p2.workNumber)) return false;
		return !p1.equals(p2);
	}

	private final String workNumber;
	private final String departement;
	private final String name;
	
	/**
	 * 新实例。
	 * @param workNumber 指定的工号。
	 * @param department 指定的部门。
	 * @param name 指定的名称。
	 */
	public Person(String workNumber, String department, String name) {
		super();
		this.workNumber = workNumber;
		this.departement = department;
		this.name = name;
	}

	/**
	 * @return the workNumber
	 */
	public String getWorkNumber() {
		return workNumber;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return departement;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return workNumber.hashCode() * 177 + departement.hashCode() * 17 + name.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(Objects.isNull(obj)) return false;
		if(obj == this) return true;
		if(! (obj instanceof Person)) return false;
		Person that = (Person) obj;
		return this.workNumber.equals(that.workNumber) && this.name.equals(that.name) && this.departement.equals(that.departement);
	}
	
}
