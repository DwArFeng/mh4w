package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ��������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class Counter {

	private int i = 0;

	/**
	 * ������
	 */
	public void count() {
		i ++;
	}
	
	/**
	 * ��ȡ������
	 * @return ��ǰ������
	 */
	public int getCounts() {
		return i;
	}

}
