package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认原始工票数据。
 * <p> 原始工票数据的默认实现。
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
	 * 新实例。
	 * @param fileName 源xls文件名称。
	 * @param row 源xls文件所在的行。
	 * @param workNumber 工号。
	 * @param department 部门。
	 * @param name 姓名。
	 * @param workticket 工票。
	 * @param job 工作类型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultOriginalWorkticketData(String fileName, int row, String workNumber, String department, String name,
			String workticket, Job job) {
		Objects.requireNonNull(fileName, "入口参数 fileName 不能为 null。");
		if(row <= 0) throw new IllegalArgumentException("入口参数 row 不能小于等于0");
		Objects.requireNonNull(workNumber, "入口参数 workNumber 不能为 null。");
		Objects.requireNonNull(department, "入口参数 department 不能为 null。");
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(workticket, "入口参数 workticket 不能为 null。");
		Objects.requireNonNull(job, "入口参数 job 不能为 null。");

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
