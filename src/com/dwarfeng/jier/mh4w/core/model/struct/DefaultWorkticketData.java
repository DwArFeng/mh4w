package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认工票数据。
 * <p> 工票数据的默认实现。
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
	 * 新实例。
	 * @param fileName 文件的名字。
	 * @param row 数据的行号。
	 * @param person 员工。
	 * @param job 工作类型。
	 * @param workticket 工票。
	 * @throws NullPointerException  入口参数为 <code>null</code>。
	 */
	public DefaultWorkticketData(String fileName, int row, Person person, Job job, double workticket) {
		Objects.requireNonNull(fileName, "入口参数 fileName 不能为 null。");
		Objects.requireNonNull(person, "入口参数 person 不能为 null。");
		Objects.requireNonNull(job, "入口参数 job 不能为 null。");
		Objects.requireNonNull(workticket, "入口参数 workticket 不能为 null。");

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
