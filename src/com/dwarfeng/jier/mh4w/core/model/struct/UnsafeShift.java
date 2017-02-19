package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ����ȫ��Ρ�
 * <p> ����ȫ������νӿ���ȣ��ٶ�Ҫ�����������䷵�ط������ܻ��׳��쳣��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface UnsafeShift {
	
	/**
	 *���ظò���ȫ��ε����ơ� 
	 * @return �ò���ȫ��ε����ơ�
	 * @throws ProcessException �����쳣��
	 */
	public String getName() throws ProcessException;

	/**
	 * ��ȡ�����ϰ��ʱ�����䡣
	 * @return �����ϰ��ʱ�����䡣
	 * @throws ProcessException �����쳣��
	 */
	public TimeSection[] getShiftSections() throws ProcessException;
	
	/**
	 * ��ȡ��Ϣ��ʱ�����䡣
	 * @return ��Ϣ��ʱ�����䡣
	 * @throws ProcessException �����쳣��
	 */
	public TimeSection[] getRestSections() throws ProcessException;
	
	/**
	 * ��ȡ�ϰ�ʱ�䡣
	 * @return �ϰ�ʱ�����䡣
	 * @throws ProcessException �����쳣��
	 */
	public TimeSection[] getExtraShiftSections() throws ProcessException;
	


}
