package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ����ȫ������
 * <p> ����ȫ�����빤���ӿ���ȣ��ٶ�Ҫ�����������䷵�ط������ܻ��׳��쳣��
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface UnsafeJob {
	
	/**
	 * ��ȡ����ȫ���������ơ�
	 * @return ����ȫ���������ơ�
	 * @throws ProcessException �����쳣��
	 */
	public String getName() throws ProcessException;
	
	/**
	 * ��ȡ�ù���������ԭʼ��������ڵ��С�
	 * @return �ù���������ԭʼ��������ڵ��С�
	 * @throws ProcessException �����쳣��
	 */
	public int getOriginalColumn() throws ProcessException;
	
}
