package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 转换异常。
 * <p> 当数据从原始数据转换成次级数据时，如果出现错误，则抛出该异常。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class TransException extends Exception {

	private static final long serialVersionUID = -8601513361993390288L;

	public TransException() {
	}

	public TransException(String message) {
		super(message);
	}

	public TransException(Throwable cause) {
		super(cause);
	}

	public TransException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
