package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;

/**
 * �������ݡ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface AttendanceData extends DataWithPerson, DataFromXls{
	
	/**
	 * ��ó��������еĹ��ţ���������
	 * @return ���������еĹ��ţ���������
	 */
	public default String getWorkNumber(){
		return getPerson().getWorkNumber();
	}
	
	/**
	 * ��ȡ�����е�ͳ�����ڡ�
	 * @return �����е�ͳ�����ڡ�
	 */
	public CountDate getCountDate();
	
	/**
	 * ��ȡͳ�����ڵ��������͡�
	 * @return ͳ�����ڵ��������͡�
	 */
	public DateType getDateType();
	
	/**
	 * ��ȡ�����еİ����Ϣ��
	 * @return �����еİ����Ϣ��
	 */
	public Shift getShift();
	
	/**
	 * ��ȡ�����еĳ��ڼ�¼��
	 * @return �����еĳ��ڼ�¼��
	 */
	public TimeSection getAttendanceRecord();
	
	/**
	 * ��ȡ��Ч�Ĺ���ʱ�䡣
	 * @return ��Ч�Ĺ���ʱ�䡣
	 */
	public double getEquivalentWorkTime();
	
	/**
	 * ��ȡԭʼ����ʱ�䡣
	 * @return ԭʼ����ʱ�䡣
	 */
	public double getOriginalWorkTime();

}
