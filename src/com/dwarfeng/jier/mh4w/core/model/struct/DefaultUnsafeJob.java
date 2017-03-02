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
	private final String originalColumn;
	
	/**
	 *��ʵ���� 
	 * @param name ָ�������ơ�
	 * @param originalColumn ָ����ԭʼ�����С�
	 */
	public DefaultUnsafeJob(String name, String originalColumn) {
		this.name = name;
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
