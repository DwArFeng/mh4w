package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�ϼ�¼����Ϣ��
 * <p> ��¼����Ϣ��Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultLoggerInfo implements LoggerInfo {
	
	private final String name;
	
	/**
	 * ��ʵ����
	 * @param name ָ�������ơ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultLoggerInfo(String name) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}
