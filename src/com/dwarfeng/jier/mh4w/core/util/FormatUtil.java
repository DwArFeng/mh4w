package com.dwarfeng.jier.mh4w.core.util;

import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;
import com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls;
import com.dwarfeng.jier.mh4w.core.model.struct.Person;
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
	 * @param person ָ����Ա����
	 * @return Ա���ĸ�ʽ�������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static String formatPerson(Person person){
		Objects.requireNonNull(person, "��ڲ��� person ����Ϊ null��");
		return String.format("%s - %s - %s", person.getName(), person.getWorkNumber(), person.getDepartment());
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
	
	/**
	 * ��ʽ�� DataFromXls ����
	 * @param dataFromXls ָ���� DataFromXls ����
	 * @return DataFromXls ����ĸ�ʽ�������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static String formatDataFromXls(DataFromXls dataFromXls){
		Objects.requireNonNull(dataFromXls, "��ڲ��� dataFromXls ����Ϊ null��");
		return String.format("%s:%d", dataFromXls.getFileName(), dataFromXls.getRow());
	}
	
	/**
	 * ��ʽ��ͳ�����ڶ���
	 * @param countDate ָ����ͳ�����ڡ�
	 * @return ָ�������ڶ���ĸ�ʽ�������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static String formatCountDate(CountDate countDate){
		Objects.requireNonNull(countDate, "��ڲ��� countDate ����Ϊ null��");
		
		return String.format("%04d - %02d - %02d", countDate.getYear(), countDate.getMonth(), countDate.getDay());
	}
	
	/**
	 * ��ʽ��˫���ȸ�������
	 * @param value ָ����˫���ȸ�������
	 * @return ָ����˫���ȸ������ĸ�ʽ�������
	 */
	public static String formatDouble(double value){
		return String.format("%.2f", value);
	}
}
