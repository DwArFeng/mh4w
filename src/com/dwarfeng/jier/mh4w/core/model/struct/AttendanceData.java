package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * �������ݡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface AttendanceData {
	
	/**
	 * ��ó��������еĹ��ţ���������
	 * @return ���������еĹ��ţ���������
	 */
	public default String getWorkNumber(){
		return getStaff().getWorkNumber();
	}
	
	/**
	 * ��������е�Ա����Ϣ��
	 * @return �����е�Ա����Ϣ��
	 */
	public Staff getStaff();
	
	/**
	 * ��ȡ�����е�ͳ�����ڡ�
	 * @return �����е�ͳ�����ڡ�
	 */
	public CountDate getCountDate();
	
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

}
