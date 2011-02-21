package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * ������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Job extends Name{

	/**
	 * ��ȡÿСʱ�Ĺ�����ֵ��
	 * @return  ÿСʱ�Ĺ�����ֵ��
	 */
	public double getValuePerHour();
	
	/**
	 * ��ȡ�ù���������ԭʼ��������ڵ��С�
	 * @return �ù���������ԭʼ��������ڵ��С�
	 */
	public int getOriginalColumn();
	
}
