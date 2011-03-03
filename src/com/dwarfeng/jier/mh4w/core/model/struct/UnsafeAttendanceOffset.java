package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ����ȫ����ƫ�á�
 * <p> ����ȫ�Ŀ���ƫ�ã�����ڿ���ƫ�ã����ȡ�ٶȽ����������ܲ����쳣��
 * @author DwArFeng
 * @since 1.0.0
 */
public interface UnsafeAttendanceOffset {

	/**
	 * ��ȡ��Ա��Ϣ��
	 * @return ��Ա��Ϣ��
	 * @throws ProcessException �����쳣��
	 */
	public Person getPerson() throws ProcessException;
	
	/**
	 * ��ȡƫ��ֵ��
	 * @return ƫ��ֵ��
	 * @throws ProcessException �����쳣��
	 */
	public double getValue() throws ProcessException;
	
	/**
	 * ��ȡ������
	 * @return ������
	 * @throws ProcessException �����쳣��
	 */
	public String getDescription() throws ProcessException;
	
}
