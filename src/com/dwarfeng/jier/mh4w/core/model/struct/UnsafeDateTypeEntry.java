package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.DateType;

/**
 * ����ȫ����������ڡ�
 * <p> ����ṩ�˻�ȡͳ�������Լ��������͵ķ�������������������ģ������Ҫ�ļ���ֵ��
 * <p> ����ȫ���������ͣ����������ģ�͵���ڣ����ȡ�ٶȽ����������ܲ����쳣��
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface UnsafeDateTypeEntry {
	
	/**
	 * ��ȡ����ȫ���������е�ͳ�����ڡ�
	 * @return ����ȫ���������е�ͳ�����ڡ�
	 * @throws ProcessException �����쳣��
	 */
	public CountDate getCountDate() throws ProcessException;
	
	/**
	 * ��ȡ����ȫ���������е��������͡�
	 * @return ����ȫ���������е��������͡�
	 * @throws ProcessException �����쳣��
	 */
	public DateType getDateType() throws ProcessException;

}
