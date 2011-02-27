package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�Ϲ�Ʊ���ݡ�
 * <p> ��Ʊ���ݵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultWorkticketData implements WorkticketData{
	
	private final String fileName;
	private final int row;
	private final Person person;
	private final Job job;
	private final double workticket;

	/**
	 * ��ʵ����
	 * @param fileName �ļ������֡�
	 * @param row ���ݵ��кš�
	 * @param person Ա����
	 * @param job �������͡�
	 * @param workticket ��Ʊ��
	 * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultWorkticketData(String fileName, int row, Person person, Job job, double workticket) {
		Objects.requireNonNull(fileName, "��ڲ��� fileName ����Ϊ null��");
		Objects.requireNonNull(person, "��ڲ��� person ����Ϊ null��");
		Objects.requireNonNull(job, "��ڲ��� job ����Ϊ null��");
		Objects.requireNonNull(workticket, "��ڲ��� workticket ����Ϊ null��");

		this.fileName = fileName;
		this.row = row;
		this.person = person;
		this.job = job;
		this.workticket = workticket;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls#getFileName()
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls#getRow()
	 */
	@Override
	public int getRow() {
		return row;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.DataWithPerson#getPerson()
	 */
	@Override
	public Person getPerson() {
		return person;
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
