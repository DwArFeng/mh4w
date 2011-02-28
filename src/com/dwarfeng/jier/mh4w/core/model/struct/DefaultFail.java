package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.model.eum.FailType;

/**
 * 默认失败。
 * <p> 失败接口的默认实现。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultFail implements Fail {

	private final Object source;
	private final FailType failType;
	
	/**
	 * 新实例。
	 * @param source 指定的失败源。
	 * @param failType 指定的失败类型。
	 */
	public DefaultFail(Object source, FailType failType) {
		super();
		this.source = source;
		this.failType = failType;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Fail#getSource()
	 */
	@Override
	public Object getSource() {
		return source;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.Fail#getFailType()
	 */
	@Override
	public FailType getFailType() {
		return failType;
	}

}
