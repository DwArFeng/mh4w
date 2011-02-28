package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 时间区间。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class TimeSection {
	
	private final double start;
	private final double end;
	
	/**
	 * 新实例。
	 * @param start 开始时间。
	 * @param end 结束时间。
	 * @throws IllegalArgumentException 结束时间小于起始时间。
	 */
	public TimeSection(double start, double end) {
		if(end < start) throw new IllegalArgumentException("时间区间 - 时间区间的结束时间不能小于起始时间");
		
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the start
	 */
	public double getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public double getEnd() {
		return end;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Double.hashCode(start)*1440 + Double.hashCode(end);
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(Objects.isNull(obj)) return false;
		if(obj == this) return true;
		if(! (obj instanceof TimeSection)) return false;
		TimeSection that = (TimeSection) obj;
		return that.start == this.start && that.end == this.end;
	}
	
}
