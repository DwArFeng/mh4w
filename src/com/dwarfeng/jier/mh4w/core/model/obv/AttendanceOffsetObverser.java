package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ���ڲ����۲�����
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface AttendanceOffsetObverser extends Obverser{

	/**
	 * ָ֪ͨ�������ڱ���ӽ�ģ�͡�
	 * @param key ָ���ļ���
	 * @param value ָ����ֵ��
	 */
	public void fireWorkNumberPut(String key, Double value);
	
	/**
	 * ָ֪ͨ�������ݴ�ģ�����Ƴ���
	 * @param key ָ���ļ���
	 */
	public void fireWorkNumberRemoved(String key);
	
	/**
	 * ֪ͨģ����ָ���ļ����ı䡣
	 * @param key ָ���ļ���
	 * @param oldValue ����Ӧ�ľ�ֵ��
	 * @param newValue ����Ӧ����ֵ��
	 */
	public void fireWorkNumberChanged(String key, Double oldValue, Double newValue);
	
	/**
	 * ָ֪ͨ�������ݱ������
	 */
	public void fireDateCleared();
	
}
