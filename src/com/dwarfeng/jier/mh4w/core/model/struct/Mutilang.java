package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 多语言接口。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface Mutilang {
	
	/**
	 * 获取当前语言下指定字符域所对应的字符串。
	 * @param key 指定的字符键。
	 * @return 指定的字符键所对应的字符。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 * @throws IllegalArgumentException 入口参数不受该多语言接口的支持。
	 */
	public String getString(String key);

}
