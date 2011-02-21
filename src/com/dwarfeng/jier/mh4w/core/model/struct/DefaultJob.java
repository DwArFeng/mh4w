package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

public final class DefaultJob implements Job {

	private final String name;
	private final double valuePerHour;
	private final int originalColumn;
	
	/**
	 * 新实例。 
	 * @param name
	 * @param valuePerHour
	 * @param originalColumn
	 */
	public DefaultJob(String name, double valuePerHour, int originalColumn) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null");
		
		this.name = name;
		this.valuePerHour = valuePerHour;
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Job#getValuePerHour()
	 */
	@Override
	public double getValuePerHour() {
		return valuePerHour;
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
