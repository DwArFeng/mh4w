package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 计数器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class Counter {

	private int i = 0;

	/**
	 * 计数。
	 */
	public void count() {
		i ++;
	}
	
	/**
	 * 获取计数。
	 * @return 当前计数。
	 */
	public int getCounts() {
		return i;
	}

}
