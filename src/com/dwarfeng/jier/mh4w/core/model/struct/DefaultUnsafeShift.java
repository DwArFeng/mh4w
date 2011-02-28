package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.util.CountUtil;

/**
 * 默认不安全班次。
 * <p> 不安全班次的默认实现。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class DefaultUnsafeShift implements UnsafeShift {
	
	private final String name;
	private final String[][] shiftSections;
	private final String[][] restSections;
	private final String[][] extraShiftSections;
	
	/**
	 * 新实例。
	 * @param name 指定的名称。
	 * @param shiftSections 指定的工作时间区间。
	 * @param restSections 指定的休息区间。
	 * @param extraShiftSections 指定的拖班时间。
	 */
	public DefaultUnsafeShift(String name, String[][] shiftSections, String[][] restSections, String[][] extraShiftSections) {
		this.name = name;
		this.shiftSections = shiftSections;
		this.restSections = restSections;
		this.extraShiftSections = extraShiftSections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift#getName()
	 */
	@Override
	public String getName() throws ProcessException {
		try{
			return name;
		}catch (Exception e) {
			throw new ProcessException("默认不安全班次 - 无法解析班次中的名称", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift#getShiftSections()
	 */
	@Override
	public TimeSection[] getShiftSections() throws ProcessException {
		try{
			TimeSection[] timeSections = new TimeSection[shiftSections.length];
			for(int i = 0 ; i < timeSections.length; i ++){
				timeSections[i] = stringArray2TimeSection(shiftSections[i]);
			}
			return timeSections;
		}catch (Exception e) {
			throw new ProcessException("默认不安全班次 - 无法解析班次中的工作时间区域", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift#getRestSections()
	 */
	@Override
	public TimeSection[] getRestSections() throws ProcessException {
		try{
			TimeSection[] timeSections = new TimeSection[restSections.length];
			for(int i = 0 ; i < timeSections.length; i ++){
				timeSections[i] = stringArray2TimeSection(restSections[i]);
			}
			return timeSections;
		}catch (Exception e) {
			throw new ProcessException("默认不安全班次 - 无法解析班次中的休息时间区域", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift#getExtraShiftSections()
	 */
	@Override
	public TimeSection[] getExtraShiftSections() throws ProcessException {
		try{
			TimeSection[] timeSections = new TimeSection[extraShiftSections.length];
			for(int i = 0 ; i < timeSections.length; i ++){
				timeSections[i] = stringArray2TimeSection(extraShiftSections[i]);
			}
			return timeSections;
		}catch (Exception e) {
			throw new ProcessException("默认不安全班次 - 无法解析班次中的拖班时间区域", e);
		}
	}
	
	private TimeSection stringArray2TimeSection(String[] strs) throws Exception{
		double start = CountUtil.string2Hour(strs[0]);
		double end =  CountUtil.string2Hour(strs[1]);
		return new TimeSection(start, end);
	}

}
