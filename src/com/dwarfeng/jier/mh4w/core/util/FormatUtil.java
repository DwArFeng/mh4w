package com.dwarfeng.jier.mh4w.core.util;

import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.struct.Staff;
import com.dwarfeng.jier.mh4w.core.model.struct.TimeSection;

/**
 * ��ʽ�����ݹ����ࡣ
 * <p> ���ڽ�ĳЩ�ṹ���и�ʽ�������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class FormatUtil {

	/**
	 * ��ʽ�����Ա����
	 * @param staff ָ����Ա����
	 * @return Ա���ĸ�ʽ�������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static String formatStaff(Staff staff){
		Objects.requireNonNull(staff, "��ڲ��� staff ����Ϊ null��");
		return String.format("%s - %s - %s", staff.getName(), staff.getWorkNumber(), staff.getDepartement());
	}
	
	/**
	 * ��ʽ�����ʱ�����䡣
	 * @param timeSection ָ����ʱ�����䡣
	 * @return ʱ������ĸ�ʽ�������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static String formatTimeSection(TimeSection timeSection){
		Objects.requireNonNull(timeSection, "��ڲ��� timeSection ����Ϊ null��");

		int a1 = (int) timeSection.getStart();
		int a2 = (int)(timeSection.getStart() * 60) % 60;
		int a3 = (int) timeSection.getEnd();
		int a4 =  (int)(timeSection.getEnd() * 60) % 60;
		
		return String.format("%02d:%02d - %02d:%02d", a1, a2, a3, a4);
	}
	
}
