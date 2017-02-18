package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认工票数据。
 * <p> 工票数据的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultWorkticketData implements WorkticketData{
	
	private final Staff staff;
	private final int workticket;

	/**
	 * 新实例。
	 * @param staff 员工。
	 * @param workticket 工票。
	 * @throws NullPointerException  入口参数为 <code>null</code>。
	 */
	public DefaultWorkticketData(Staff staff, int workticket) {
		Objects.requireNonNull(staff, "入口参数 staff 不能为 null。");
		Objects.requireNonNull(workticket, "入口参数 workticket 不能为 null。");

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
