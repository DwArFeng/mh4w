package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 统计结果。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface CountResult extends DataWithPerson{

	/**
	 * 获取等效的工作时间（总计）。
	 * @return 等效的工作时间（总计）。
	 */
	public double getEquivalentWorkTime();
	
	/**
	 * 获取等效工时的补偿。
	 * @return 等效工时的补偿。
	 */
	public double getEquivalentWorkTimeOffset();
	
	/**
	 * 获取增益系数。
	 * @return 增益系数。
	 */
	public double getAmplifyCoefficient();
	
	/**
	 * 获取原始工作时间。
	 * @return 原始工作时间。
	 */
	public double getOriginalWorkTime();
	
	/**
	 * 获取总工时。
	 * @return 总工时。
	 */
	public double getWorkticket();
	
	/**
	 * 获取等效工票。
	 * @return 等效工票。
	 */
	public double getEquivalentWorkticket();
	
	/**
	 * 获取指定工作对应的工票。
	 * @param job 指定的工作。
	 * @return 指定工作对应的工票。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public double getWorkticket(Job job); 
	
	/**
	 * 获取指定工作对应的等效工票。
	 * @param job 指定的工作。
	 * @return 指定工作对应的等效工票。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public double getEquivalentWorkticket(Job job);
	
}
