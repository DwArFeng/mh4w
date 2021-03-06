package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

public final class DefaultJob implements Job {

	private final String name;
	private final int originalColumn;
	
	/**
	 * 新实例。 
	 * @param name 指定的名称。
	 * @param originalColumn 原始数据所在的列号。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultJob(String name, int originalColumn) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null");
		
		this.name = name;
		this.originalColumn = originalColumn;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Job#getOriginalColumn()
	 */
	@Override
	public int getOriginalColumn() {
		return originalColumn;
	}

}
