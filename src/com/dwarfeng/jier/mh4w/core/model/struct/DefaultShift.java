package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认班次。
 * <p> 班次接口的默认实现。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultShift implements Shift{
	
	private final String name;
	private final TimeSection[] shiftSections;
	private final TimeSection[] restSections;
	private final TimeSection[] extraShiftSections;

	/**
	 * 新实例。
	 * @param name 指定的名称。
	 * @param shiftSections 指定的上班时间区域。
	 * @param restSections 指定的休息时间区域。
	 * @param extraShiftSections 指定的拖班时间区域。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultShift(String name, TimeSection[] shiftSections, TimeSection[] restSections, TimeSection[] extraShiftSections) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(shiftSections, "入口参数 shiftSections 不能为 null。");
		Objects.requireNonNull(restSections, "入口参数 restSections 不能为 null。");
		Objects.requireNonNull(extraShiftSections, "入口参数 extraShiftSections 不能为 null。");

		this.name = name;
		this.shiftSections = shiftSections;
		this.restSections = restSections;
		this.extraShiftSections = extraShiftSections;
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Shift#getShiftSections()
	 */
	@Override
	public TimeSection[] getShiftSections() {
		return shiftSections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Shift#getRestSections()
	 */
	@Override
	public TimeSection[] getRestSections() {
		return restSections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Shift#getExtraShiftSections()
	 */
	@Override
	public TimeSection[] getExtraShiftSections() {
		return extraShiftSections;
	}

}
