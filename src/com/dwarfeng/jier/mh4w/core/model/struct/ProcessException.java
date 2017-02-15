package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 过程异常。
 * <p> 该异常用于指示程序在处理过程中发生异常。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public class ProcessException extends Exception{

	private static final long serialVersionUID = 3395463093993235210L;

	public ProcessException() {
	}

	public ProcessException(String message) {
		super(message);
	}

	public ProcessException(Throwable cause) {
		super(cause);
	}

	public ProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
