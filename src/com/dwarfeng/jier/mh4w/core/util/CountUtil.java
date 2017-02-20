package com.dwarfeng.jier.mh4w.core.util;

import java.util.Objects;

/**
 *��ͳ���йع��߷�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class CountUtil {

	/**
	 * �� xls ����е�����ת�����е���š�
	 * @param column �����ơ�
	 * @return �����ƶ�Ӧ����š�
	 */
	public static int columnString2Int(String column) {
		Objects.requireNonNull(column, "��ڲ��� input ����Ϊ null��");
		column = column.toUpperCase();
		if(column.matches("[^A-Z]")){
			throw new IllegalArgumentException("�ַ�ֻ�ܰ���A-Z");
		}
		
		int sum = 0;
		int one = Character.getNumericValue('A');
		
		for(int i = 0 ; i < column.length() ; i ++){
			sum *= 26;
			char ch = column.charAt(i);
			sum += Character.getNumericValue(ch) - one +1;
		}
		
		return sum - 1;
	}
	
	//��ֹ�ⲿʵ������
	private CountUtil(){}
}
