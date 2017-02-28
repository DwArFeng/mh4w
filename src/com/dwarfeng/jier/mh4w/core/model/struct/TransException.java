package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ת���쳣��
 * <p> �����ݴ�ԭʼ����ת���ɴμ�����ʱ��������ִ������׳����쳣��
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
