package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

public final class DefaultJob implements Job {

	private final String name;
	private final int originalColumn;
	
	/**
	 * ��ʵ���� 
	 * @param name ָ�������ơ�
	 * @param originalColumn ԭʼ�������ڵ��кš�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultJob(String name, int originalColumn) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null");
		
		this.name = name;
		this.originalColumn = originalColumn;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Job#getOriginalColumn()
	 */
	@Override
	public int getOriginalColumn() {
		return originalColumn;
	}

}
