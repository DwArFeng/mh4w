package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.FailType;

/**
 * ʧ�ܡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Fail {

	/**
	 * ��ȡʧ��Դ��
	 * @return ʧ��Դ��
	 */
	public Object getSource();
	
	/**
	 * ��ȡʧ�ܵ����͡�
	 * @return ʧ�ܵ����͡�
	 */
	public FailType getFailType();
	
}
