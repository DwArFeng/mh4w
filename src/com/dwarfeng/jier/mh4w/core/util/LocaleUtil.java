package com.dwarfeng.jier.mh4w.core.util;

import java.util.Locale;
import java.util.Objects;

import com.dwarfeng.dutil.basic.str.FactoriesByString;

/**
 * �������÷�����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class LocaleUtil {

	/**
	 * ��ָ�����ַ���ת��Ϊ������
	 * @param s ָ�����ַ�����
	 * @return ��ָ�����ַ���ת�����ɵĵ�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static Locale parseLocale(String s){
		Objects.requireNonNull(s, "��ڲ��� s ����Ϊ null��");
		
		if(s.equals("")) return null;
		return FactoriesByString.newLocale(s);
	}
	
	//��ֹ�ⲿʵ����
	private LocaleUtil() {}

}
