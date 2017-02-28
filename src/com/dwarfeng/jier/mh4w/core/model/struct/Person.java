package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ա���ṹ��
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class Person {
	
	/**
	 * �������Ա���Ƿ��ͻ��
	 * <p> ��ͻ��ָ����Ա���Ĺ�����ȵ��ǲ��Ż���������ȡ�
	 * @param p1 Ա��1.
	 * @param p2 Ա��2.
	 * @return Ա��1 �� Ա��2 �Ƿ��ͻ��
	 */
	public static boolean isConflict(Person p1, Person p2){
		Objects.requireNonNull(p1, "��ڲ��� p1 ����Ϊ null��");
		Objects.requireNonNull(p2, "��ڲ��� p2 ����Ϊ null��");

		if(! p1.workNumber.equals(p2.workNumber)) return false;
		return !p1.equals(p2);
	}

	private final String workNumber;
	private final String departement;
	private final String name;
	
	/**
	 * ��ʵ����
	 * @param workNumber ָ���Ĺ��š�
	 * @param department ָ���Ĳ��š�
	 * @param name ָ�������ơ�
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
