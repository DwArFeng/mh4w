package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ��ԭʼ��Ʊ���ݡ�
 * <p> ԭʼ��Ʊ���ݵ�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultOriginalWorkticketData implements OriginalWorkticketData {
	
	private final String fileName;
	private final int row;
	private final String workNumber;
	private final String department;
	private final String name;
	private final String workticket;
	private final Job job;

	/**
	 * ��ʵ����
	 * @param fileName Դxls�ļ����ơ�
	 * @param row Դxls�ļ����ڵ��С�
	 * @param workNumber ���š�
	 * @param department ���š�
	 * @param name ������
	 * @param workticket ��Ʊ��
	 * @param job �������͡�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultOriginalWorkticketData(String fileName, int row, String workNumber, String department, String name,
			String workticket, Job job) {
		Objects.requireNonNull(fileName, "��ڲ��� fileName ����Ϊ null��");
		if(row <= 0) throw new IllegalArgumentException("��ڲ��� row ����С�ڵ���0");
		Objects.requireNonNull(workNumber, "��ڲ��� workNumber ����Ϊ null��");
		Objects.requireNonNull(department, "��ڲ��� department ����Ϊ null��");
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		Objects.requireNonNull(workticket, "��ڲ��� workticket ����Ϊ null��");
		Objects.requireNonNull(job, "��ڲ��� job ����Ϊ null��");

		this.fileName = fileName;
		this.row = row;
		this.workNumber = workNumber;
		this.department = department;
		this.name = name;
		this.workticket = workticket;
		this.job = job;
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalDataWithStaff#getWorkNumber()
	 */
	@Override
	public String getWorkNumber() {
		return workNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalDataWithStaff#getDepartement()
	 */
	@Override
	public String getDepartment() {
		return department;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalDataWithStaff#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData#getWorkticket()
	 */
	@Override
	public String getWorkticket() {
		return workticket;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData#getJob()
	 */
	@Override
	public Job getJob() {
		return job;
	}

}
