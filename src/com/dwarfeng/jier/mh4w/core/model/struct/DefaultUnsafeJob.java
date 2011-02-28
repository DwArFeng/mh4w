package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.util.CountUtil;

/**
 * 默认不安全工作。
 * <p> 不安全工作的默认实现。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultUnsafeJob implements UnsafeJob{

	private final String name;
	private final String valuePerHour;
	private final String originalColumn;
	
	/**
	 *新实例。 
	 * @param name 指定的名称。
	 * @param valuePerHour 指定的每小时价值。
	 * @param originalColumn 指定的原始数据列。
	 */
	public DefaultUnsafeJob(String name, String valuePerHour, String originalColumn) {
		this.name = name;
		this.valuePerHour = valuePerHour;
		this.originalColumn = originalColumn;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeJob#getName()
	 */
	@Override
	public String getName() throws ProcessException {
		try{
			return name;
		}catch (Exception e) {
			throw new ProcessException("默认不安全工作 - 无法解析工作中的名称", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeJob#getValuePerHour()
	 */
	@Override
	public double getValuePerHour() throws ProcessException {
		try{
			return Double.parseDouble(valuePerHour);
		}catch (Exception e) {
			throw new ProcessException("默认不安全工作 - 无法解析工作中的每小时价值", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeJob#getOriginalColumn()
	 */
	@Override
	public int getOriginalColumn() throws ProcessException {
		try{
			return CountUtil.columnString2Int(originalColumn);
		}catch (Exception e) {
			throw new ProcessException("默认不安全工作 - 无法解析工作中原始数据列", e);
		}
	}

}
