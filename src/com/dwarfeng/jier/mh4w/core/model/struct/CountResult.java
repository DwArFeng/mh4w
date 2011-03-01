package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ͳ�ƽ����
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface CountResult extends DataWithPerson{

	/**
	 * ��ȡ��Ч�Ĺ���ʱ�䣨�ܼƣ���
	 * @return ��Ч�Ĺ���ʱ�䣨�ܼƣ���
	 */
	public double getEquivalentWorkTime();
	
	/**
	 * ��ȡ��Ч��ʱ�Ĳ�����
	 * @return ��Ч��ʱ�Ĳ�����
	 */
	public double getEquivalentWorkTimeOffset();
	
	/**
	 * ��ȡԭʼ����ʱ�䡣
	 * @return ԭʼ����ʱ�䡣
	 */
	public double getOriginalWorkTime();
	
	/**
	 * ��ȡ�ܹ�ʱ��
	 * @return �ܹ�ʱ��
	 */
	public double getWorkticket();
	
	/**
	 * ��ȡ��Ч��Ʊ��
	 * @return ��Ч��Ʊ��
	 */
	public double getEquivalentWorkticket();
	
	/**
	 * ��ȡָ��������Ӧ�Ĺ�Ʊ��
	 * @param job ָ���Ĺ�����
	 * @return ָ��������Ӧ�Ĺ�Ʊ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public double getWorkticket(Job job); 
	
	/**
	 * ��ȡָ��������Ӧ�ĵ�Ч��Ʊ��
	 * @param job ָ���Ĺ�����
	 * @return ָ��������Ӧ�ĵ�Ч��Ʊ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public double getEquivalentWorkticket(Job job);
	
}
