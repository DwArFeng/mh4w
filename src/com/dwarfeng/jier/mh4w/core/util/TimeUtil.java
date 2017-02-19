package com.dwarfeng.jier.mh4w.core.util;

/**
 * �й���ʱ��Ĺ��ߡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class TimeUtil {
	
	/**
	 * ��ʱ���ʽ������ 12:45��ת���ɷ��ӡ�
	 * @param str ָ����ʱ���ʽ��
	 * @return ʱ���ʽ��Ӧ�ķ��ӡ�
	 */
	public static int parseMinute(String str){
		String[] strs = str.split(":");
		if(strs.length == 1){
			return Integer.parseInt(str);
		}else if(strs.length == 2){
			int hour = Integer.parseInt(strs[0]);
			int minute = Integer.parseInt(strs[1]);
			return hour * 60 + minute;
		}else{
			throw new IllegalArgumentException("ʱ�乤�� - ��Ч�Ĵ��������" + str);
		}
	}
	
	//��ֹ�ⲿʵ������
	private TimeUtil(){}

}
