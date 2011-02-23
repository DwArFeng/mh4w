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
	private final Job job;
	private final double workticket;

	/**
	 * ��ʵ����
	 * @param staff Ա����
	 * @param job �������͡�
	 * @param workticket ��Ʊ��
	 * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultWorkticketData(Staff staff, Job job, double workticket) {
		Objects.requireNonNull(staff, "��ڲ��� staff ����Ϊ null��");
		Objects.requireNonNull(job, "��ڲ��� job ����Ϊ null��");
		Objects.requireNonNull(workticket, "��ڲ��� workticket ����Ϊ null��");

		this.staff = staff;
		this.job = job;
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData#getJob()
	 */
	@Override
	public Job getJob() {
		return job;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData#getWorkticket()
	 */
	@Override
	public double getWorkticket() {
		return workticket;
	}

}
