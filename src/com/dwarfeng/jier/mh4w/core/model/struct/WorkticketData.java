package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ��Ʊ���ݡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface WorkticketData extends DataWithStaff{
	
	/**
	 * ��ó��������еĹ��ţ���������
	 * @return ���������еĹ��ţ���������
	 */
	public default String getWorkNumber(){
		return getStaff().getWorkNumber();
	}
	
	/**
	 * ��ȡ��Ʊ���ϵ�ͳ��ʱ�䡣
	 * @return ��Ʊ�ϵ�ͳ��ʱ�䡣
	 */
	public int getWorkticket();
	
}
