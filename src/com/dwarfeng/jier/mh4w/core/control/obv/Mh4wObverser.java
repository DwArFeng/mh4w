package com.dwarfeng.jier.mh4w.core.control.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.dutil.basic.prog.RuntimeState;

/**
 * ��ʱͳ��ʵ���۲�����
 * @author DwArFeng
 * @since 1.1.0
 */
public interface Mh4wObverser extends Obverser{

	/**
	 * ֪ͨʵ����״̬�����ı䡣
	 * @param oldValue �ɵ�״̬��
	 * @param newValue �µ�״̬��
	 */
	public void fireStateChanged(RuntimeState oldValue, RuntimeState newValue);
	
}
