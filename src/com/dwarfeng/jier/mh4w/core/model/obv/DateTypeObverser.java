package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;

/**
 * �������͹۲�����
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DateTypeObverser extends Obverser{

	/**
	 * ָ֪ͨ�������ڱ���ӽ�ģ�͡�
	 * @param key ָ���ļ���
	 * @param value ָ����ֵ��
	 */
	public void fireDatePut(CountDate key, DateType value);
	
	/**
	 * ָ֪ͨ�������ݴ�ģ�����Ƴ���
	 * @param key ָ���ļ���
	 */
	public void fireDateRemoved(CountDate key);
	
	/**
	 * ֪ͨģ����ָ���ļ����ı䡣
	 * @param key ָ���ļ���
	 * @param oldValue ����Ӧ�ľ�ֵ��
	 * @param newValue ����Ӧ����ֵ��
	 */
	public void fireDateChanged(CountDate key, DateType oldValue, DateType newValue);
	
	/**
	 * ָ֪ͨ�������ݱ������
	 */
	public void fireDateCleared();
	
}
