package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * ʱ�����䡣
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class TimeSection {
	
	private final double start;
	private final double end;
	
	/**
	 * ��ʵ����
	 * @param start ��ʼʱ�䡣
	 * @param end ����ʱ�䡣
	 * @throws IllegalArgumentException ����ʱ��С����ʼʱ�䡣
	 */
	public TimeSection(double start, double end) {
		if(end < start) throw new IllegalArgumentException("ʱ������ - ʱ������Ľ���ʱ�䲻��С����ʼʱ��");
		
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
