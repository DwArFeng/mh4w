package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * �б�����۲�����
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface ListOperateObverser<T> extends Obverser {

	/**
	 * ֪ͨ���ݱ���ӡ�
	 * @param index ��š�
	 * @param value ���ݡ�
	 */
	public void fireAdded(int index, T value);
	
	/**
	 * ֪ͨ���ݱ����ġ�
	 * @param index ��š�
	 * @param oldValue �����ݡ�
	 * @param newValue �����ݡ�
	 */
	public void fireChanged(int index, T oldValue, T newValue);
	
	/**
	 * ָ֪ͨ����ŵ����ݱ��Ƴ���
	 * @param index ��š�
	 */
	public void fireRemoved(int index);
	
	/**
	 * ֪ͨ���ݱ������
	 */
	public void fireCleared();
	
}
