package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.FailType;

/**
 * Ĭ��ʧ�ܡ�
 * <p> ʧ�ܽӿڵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultFail implements Fail {

	private final Object source;
	private final FailType failType;
	
	/**
	 * ��ʵ����
	 * @param source ָ����ʧ��Դ��
	 * @param failType ָ����ʧ�����͡�
	 */
	public DefaultFail(Object source, FailType failType) {
		super();
		this.source = source;
		this.failType = failType;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Fail#getSource()
	 */
	@Override
	public Object getSource() {
		return source;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Fail#getFailType()
	 */
	@Override
	public FailType getFailType() {
		return failType;
	}

}
