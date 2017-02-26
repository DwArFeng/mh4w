package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * �������ͽ���۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface DateTypeFrameObverser extends Obverser {

	/**
	 * ֪ͨ�����������ͽ��档
	 */
	public void fireHideDateTypeFrame();

	/**
	 * ֪ͨ�ύָ��������������ڡ�
	 * @param key ��ڵļ���
	 * @param value ��ڵ�ֵ��
	 */
	public void fireSubmitDateTypeEntry(CountDate key, DateType value);

	/**
	 * ֪ͨ�Ƴ�ָ��������������ڡ�
	 * @param key ��ڵļ���
	 */
	public void fireRemoveDateTypeEntry(CountDate key);

	/**
	 * ֪ͨ�������������ڡ�
	 */
	public void fireClearDateTypeEntry();

	/**
	 * ֪ͨ��������������ڡ�
	 */
	public void fireSaveDateTypeEntry();

	/**
	 * ֪ͨ��ȡ����������ڡ�
	 */
	public void fireLoadDateTypeEntry();

}
