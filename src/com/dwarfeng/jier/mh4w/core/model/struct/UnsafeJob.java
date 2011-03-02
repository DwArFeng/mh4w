package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 不安全工作。
 * <p> 不安全工作与工作接口相比，速度要更慢，而且其返回方法可能会抛出异常。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface UnsafeJob {
	
	/**
	 * 获取不安全工作的名称。
	 * @return 不安全工作的名称。
	 * @throws ProcessException 过程异常。
	 */
	public String getName() throws ProcessException;
	
	/**
	 * 获取该工作数据在原始表格中所在的列。
	 * @return 该工作数据在原始表格中所在的列。
	 * @throws ProcessException 过程异常。
	 */
	public int getOriginalColumn() throws ProcessException;
	
}
