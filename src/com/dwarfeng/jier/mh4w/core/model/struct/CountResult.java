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
	 * ��ȡָ��������Ӧ�Ĺ�Ʊ��
	 * @param job ָ���Ĺ�����
	 * @return ָ��������Ӧ�Ĺ�Ʊ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public double getWorkticket(Job job); 
	
	/**
	 * ��ȡָ��������Ӧ�Ĺ�Ʊռ�ȡ�
	 * @param job ָ���Ĺ�����
	 * @return ָ���Ĺ�����Ӧ�Ĺ�Ʊռ�ȡ�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public double getWorkticketPercent(Job job);
	
}
