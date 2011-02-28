package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 记录器信息。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface LoggerInfo {

	/**
	 * 返回记录器信息中的记录器名称。
	 * @return 记录器名称。
	 * @throws ProcessException 过程异常。
	 */
	public String getName() throws ProcessException;
	
}
