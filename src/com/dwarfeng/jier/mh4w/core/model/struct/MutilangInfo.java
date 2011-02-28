package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Map;

/**
 * 多语言的属性。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface MutilangInfo {
	
	/**
	 * 获取属性中的标签。
	 * @return 信息中的标签。
	 * @throws ProcessException 过程异常。
	 */
	public String getLabel() throws ProcessException;
	
	/**
	 * 获取属性中的键值映射。
	 * @return 属性中的键值映射。
	 * @throws ProcessException 过程异常。
	 */
	public Map<String, String> getMutilangMap() throws ProcessException;

}
