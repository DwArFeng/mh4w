package com.dwarfeng.jier.mh4w.core.model.io;

/**
 * xls失败异常。
 * @author DwArFeng
 * @since 1.8
 */
public final class XlsFailedException extends Exception{

	private static final long serialVersionUID = 7680546124391557111L;

	/**
	 * 失败类型。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum FailedType{
		/**不支持的格式*/
		NOT_SUPPORT,
		/**IO通信失败*/
		IO_FAILED,
	}
	
	private final FailedType type;
	
	/**
	 * 生成一个指定类型的xls失败异常。
	 * @param type 类型。
	 */
	public XlsFailedException(FailedType type) {
		this.type = type;
	}

	/**
	 * @return the failedType
	 */
	public FailedType getFailedType() {
		return type;
	}
	
}
