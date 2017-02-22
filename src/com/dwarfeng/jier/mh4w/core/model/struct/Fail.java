package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.FailType;

/**
 * 失败。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Fail {

	/**
	 * 获取失败源。
	 * @return 失败源。
	 */
	public Object getSource();
	
	/**
	 * 获取失败的类型。
	 * @return 失败的类型。
	 */
	public FailType getFailType();
	
}
