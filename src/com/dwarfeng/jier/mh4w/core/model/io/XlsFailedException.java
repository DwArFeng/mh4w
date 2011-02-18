package com.dwarfeng.jier.mh4w.core.model.io;

/**
 * xlsʧ���쳣��
 * @author DwArFeng
 * @since 1.8
 */
public final class XlsFailedException extends Exception{

	private static final long serialVersionUID = 7680546124391557111L;

	/**
	 * ʧ�����͡�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum FailedType{
		/**��֧�ֵĸ�ʽ*/
		NOT_SUPPORT,
		/**IOͨ��ʧ��*/
		IO_FAILED,
	}
	
	private final FailedType type;
	
	/**
	 * ����һ��ָ�����͵�xlsʧ���쳣��
	 * @param type ���͡�
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
