package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ԭʼ�������ݡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface OriginalAttendanceData extends OriginalDataWithStaff {

	/**
	 * ��ȡ��¼�����ڡ�
	 * @return ��¼�����ڡ�
	 */
	public String getDate();
	
	/**
	 * ��ȡ��¼�İ�Ρ�
	 * @return ��¼�İ�Ρ�
	 */
	public String getShift();
	
	/**
	 * ��ȡ���ڼ�¼��
	 * @return ���ڼ�¼��
	 */
	public String getAttendanceRecord();
	
}
