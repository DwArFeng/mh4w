package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * Ĭ�����ƽӿڡ�
 * <p> ���ƽӿڵ�Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultName implements Name {
	
	private final String name;

	public DefaultName(String name) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode() * 17;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(Objects.isNull(obj)) return false;
		if(obj == this) return true;
		if(!(obj instanceof DefaultName)) return false;
		DefaultName defaultName = (DefaultName) obj;
		return defaultName.getName().equals(this.getName());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}

}
