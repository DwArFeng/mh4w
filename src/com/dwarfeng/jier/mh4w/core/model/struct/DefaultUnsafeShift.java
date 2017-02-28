package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.util.CountUtil;

/**
 * Ĭ�ϲ���ȫ��Ρ�
 * <p> ����ȫ��ε�Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class DefaultUnsafeShift implements UnsafeShift {
	
	private final String name;
	private final String[][] shiftSections;
	private final String[][] restSections;
	private final String[][] extraShiftSections;
	
	/**
	 * ��ʵ����
	 * @param name ָ�������ơ�
	 * @param shiftSections ָ���Ĺ���ʱ�����䡣
	 * @param restSections ָ������Ϣ���䡣
	 * @param extraShiftSections ָ�����ϰ�ʱ�䡣
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
			throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е�����", e);
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
			throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������еĹ���ʱ������", e);
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
			throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е���Ϣʱ������", e);
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
			throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е��ϰ�ʱ������", e);
		}
	}
	
	private TimeSection stringArray2TimeSection(String[] strs) throws Exception{
		double start = CountUtil.string2Hour(strs[0]);
		double end =  CountUtil.string2Hour(strs[1]);
		return new TimeSection(start, end);
	}

}
