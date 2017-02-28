package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ��Ʊ���ݡ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface WorkticketData extends DataWithPerson, DataFromXls{
	
	/**
	 * ��ù�Ʊ�����еĹ��ţ���������
	 * @return ���������еĹ��ţ���������
	 */
	public default String getWorkNumber(){
		return getPerson().getWorkNumber();
	}
	
	/**
	 * ���Ĺ�Ʊ�����еĹ������͡�
	 * @return ��Ʊ�����еĹ������͡�
	 */
	public Job getJob();
	
	/**
	 * ��ȡ��Ʊ���ϵ�ͳ��ʱ�䡣
	 * @return ��Ʊ�ϵ�ͳ��ʱ�䡣
	 */
	public double getWorkticket();
	
}
