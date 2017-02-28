package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.util.CountUtil;

/**
 * Ĭ�ϲ���ȫ������
 * <p> ����ȫ������Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultUnsafeJob implements UnsafeJob{

	private final String name;
	private final String valuePerHour;
	private final String originalColumn;
	
	/**
	 *��ʵ���� 
	 * @param name ָ�������ơ�
	 * @param valuePerHour ָ����ÿСʱ��ֵ��
	 * @param originalColumn ָ����ԭʼ�����С�
	 */
	public DefaultUnsafeJob(String name, String valuePerHour, String originalColumn) {
		this.name = name;
		this.valuePerHour = valuePerHour;
		this.originalColumn = originalColumn;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeJob#getName()
	 */
	@Override
	public String getName() throws ProcessException {
		try{
			return name;
		}catch (Exception e) {
			throw new ProcessException("Ĭ�ϲ���ȫ���� - �޷����������е�����", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeJob#getValuePerHour()
	 */
	@Override
	public double getValuePerHour() throws ProcessException {
		try{
			return Double.parseDouble(valuePerHour);
		}catch (Exception e) {
			throw new ProcessException("Ĭ�ϲ���ȫ���� - �޷����������е�ÿСʱ��ֵ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeJob#getOriginalColumn()
	 */
	@Override
	public int getOriginalColumn() throws ProcessException {
		try{
			return CountUtil.columnString2Int(originalColumn);
		}catch (Exception e) {
			throw new ProcessException("Ĭ�ϲ���ȫ���� - �޷�����������ԭʼ������", e);
		}
	}

}
