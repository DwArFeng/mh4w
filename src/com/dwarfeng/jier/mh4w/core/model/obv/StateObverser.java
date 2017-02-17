package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.eum.CountState;

/**
 * ״̬�۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface StateObverser extends Obverser{
	
	/**
	 * ֪ͨģ��׼����ͳ��״̬�ı䡣
	 * @param newValue �µ�״̬��
	 */
	public void fireReadyForCountChanged(boolean newValue);
	
	/**
	 * ֪ͨģ�͵�ͳ��״̬�����ı䡣
	 * @param oldValue �ɵ�ͳ��״̬��
	 * @param newValue �µ�ͳ��״̬��
	 */
	public void fireCountStateChanged(CountState oldValue, CountState newValue);
	
	/**
	 * ֪ͨģ�͵Ĺ�ʱ״̬�����ı䡣
	 * @param newValue �µĹ�ʱ״̬��
	 */
	public void fireCountResultOutdatedChanged(boolean newValue);
	
}
