package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�Ϲ�Ʊ���ݡ�
 * <p> ��Ʊ���ݵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultWorkticketData implements WorkticketData{
	
	private final Staff staff;
	private final int workticket;

	/**
	 * ��ʵ����
	 * @param staff Ա����
	 * @param workticket ��Ʊ��
	 * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultWorkticketData(Staff staff, int workticket) {
		Objects.requireNonNull(staff, "��ڲ��� staff ����Ϊ null��");
		Objects.requireNonNull(workticket, "��ڲ��� workticket ����Ϊ null��");

		this.staff = staff;
		this.workticket = workticket;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataWithStaff#getStaff()
	 */
	@Override
	public Staff getStaff() {
		return staff;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData#getWorkticket()
	 */
	@Override
	public int getWorkticket() {
		return workticket;
	}

}
