package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * ��Ρ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface Shift extends Name{

	/**
	 * ��ȡ�����ϰ��ʱ�����䡣
	 * @return �����ϰ��ʱ�����䡣
	 */
	public TimeSection[] getShiftSections();
	
	/**
	 * ��ȡ��Ϣ��ʱ�����䡣
	 * @return ��Ϣ��ʱ�����䡣
	 */
	public TimeSection[] getRestSections();
	
	/**
	 * ��ȡ�ϰ�ʱ�䡣
	 * @return �ϰ�ʱ�����䡣
	 */
	public TimeSection[] getExtraShiftSections();
	
}
