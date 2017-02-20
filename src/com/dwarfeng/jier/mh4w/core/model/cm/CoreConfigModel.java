package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Locale;

/**
 * ��������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface CoreConfigModel extends SyncConfigModel {
	
	/**
	 * ��ȡ���ڱ����ʼ�С�
	 * @return ���ڱ����ʼ�С�
	 */
	public int getAttendanceStartRow();
	
	/**
	 * ��ȡ���ڱ��в������ڵ��С�
	 * @return ���ڱ��в������ڵ��С�
	 */
	public int getAttendanceDepartmentColumn();
	
	/**
	 * ��ȡ���ڱ��й������ڵ��С�
	 * @return ���ڱ��й������ڵ��С�
	 */
	public int getAttendanceWorkNumberColumn();
	
	/**
	 * ��ȡ���ڱ����������ڵ��С�
	 * @return ���ڱ����������ڵ��С�
	 */
	public int getAttendacneNameColumn();
	
	/**
	 * ��ȡ���ڱ����������ڵ��С�
	 * @return ���ڱ����������ڵ��С�
	 */
	public int getAttendanceDateColumn();
	
	/**
	 * ��ȡ���ڱ��а�����ڵ��С�
	 * @return ���ڱ��а�����ڵ��С�
	 */
	public int getAttendanceShiftColumn();
	
	/**
	 * ��ȡ���ڱ��м�¼���ڵ��С�
	 * @return ���ڱ��а�����ڵ���
	 */
	public int getAttendanceRecordColumn();
	
	/**
	 * ��ȡ��¼�������Խӿڵĵ�ǰ���ԡ�
	 * @return ��¼�������Խӿڵ�ǰ�����ԡ�
	 */
	public Locale getLoggerMutilangLocale();
	
	/**
	 * ��ȡ��ǩ�����Խӿڵĵ�ǰ���ԡ�
	 * @return ��ǩ�����Խӿڵĵ�ǰ���ԡ�
	 */
	public Locale getLabelMutilangLocale();

}
