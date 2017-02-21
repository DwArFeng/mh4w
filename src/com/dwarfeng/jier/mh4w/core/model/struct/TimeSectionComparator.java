package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Comparator;

/**
 * 时间区域比较器。
 * <p> 根据时间区域的起始时间比较大小，较小的起始时间要小于较大的其实时间。
 * 如果起始时间相等，则根据同样的方法比较结束时间。
 * <p> 该比较器不能比较 null 元素。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class TimeSectionComparator implements Comparator<TimeSection> {
	
	/**实例*/
	public static final TimeSectionComparator instance = new TimeSectionComparator();
	
	/**
	 * 新实例。
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
