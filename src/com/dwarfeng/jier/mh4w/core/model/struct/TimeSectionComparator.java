package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Comparator;

/**
 * ʱ������Ƚ�����
 * <p> ����ʱ���������ʼʱ��Ƚϴ�С����С����ʼʱ��ҪС�ڽϴ����ʵʱ�䡣
 * �����ʼʱ����ȣ������ͬ���ķ����ȽϽ���ʱ�䡣
 * <p> �ñȽ������ܱȽ� null Ԫ�ء�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class TimeSectionComparator implements Comparator<TimeSection> {
	
	/**ʵ��*/
	public static final TimeSectionComparator instance = new TimeSectionComparator();
	
	/**
	 * ��ʵ����
	 */
	public TimeSectionComparator() {}

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(TimeSection o1, TimeSection o2) {
		double start1 = o1.getStart();
		double start2 = o2.getStart();
		if(start1 != start2){
			return start1 < start2 ? -1 : 1;
		}
		
		double end1 = o1.getEnd();
		double end2 = o2.getEnd();
		
		if(end1 != end2){
			return end1 < end2 ? -1 : 1;
		}
		
		return 0;
	}

}
