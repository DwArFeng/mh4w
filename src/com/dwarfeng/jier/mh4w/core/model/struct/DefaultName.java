package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 默认名称接口。
 * <p> 名称接口的默认实现。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultName implements Name {
	
	private final String name;

	public DefaultName(String name) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
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
