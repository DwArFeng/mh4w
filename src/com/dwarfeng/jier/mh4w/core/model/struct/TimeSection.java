package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * ʱ�����䡣
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class TimeSection {
	
	private final int start;
	private final int end;
	
	/**
	 * ��ʵ����
	 * @param start ��ʼʱ�䡣
	 * @param end ����ʱ�䡣
	 */
	public TimeSection(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return start*1440 + end;
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
