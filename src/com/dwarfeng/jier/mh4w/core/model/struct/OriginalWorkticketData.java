package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ԭʼ��Ʊ���ݡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface OriginalWorkticketData extends OriginalDataWithStaff, DataFromXls{

	/**
	 * ��ȡ��Ʊ��ʱ�䡣
	 * @return ��Ʊ��ʱ���Ӧ���ı���
	 */
	public String getWorkticket();
	
	/**
	 * �������ݵĹ������͡�
	 * @return ���ݵĹ������͡�
	 */
	public Job getJob();
	
}
