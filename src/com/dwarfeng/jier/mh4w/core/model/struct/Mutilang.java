package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * �����Խӿڡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Mutilang {
	
	/**
	 * ��ȡ��ǰ������ָ���ַ�������Ӧ���ַ�����
	 * @param key ָ�����ַ�����
	 * @return ָ�����ַ�������Ӧ���ַ���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @throws IllegalArgumentException ��ڲ������ܸö����Խӿڵ�֧�֡�
	 */
	public String getString(String key);

}
