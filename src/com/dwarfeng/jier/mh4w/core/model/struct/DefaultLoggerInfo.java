package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认记录器信息。
 * <p> 记录器信息的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultLoggerInfo implements LoggerInfo {
	
	private final String name;
	
	/**
	 * 新实例。
	 * @param name 指定的名称。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DefaultLoggerInfo(String name) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.LoggerInfo#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}
