package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.Objects;

/**
 * ����xls��ȡ����
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractXlsLoader implements XlsLoader {

	/**������*/
	protected final InputStream in;
	
	/**
	 * ���ɡ�
	 * @param in ��������
	 * @throws XlsFailedException ʧ���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public AbstractXlsLoader(InputStream in) throws XlsFailedException{
		Objects.requireNonNull(in, "��ڲ��� in ����Ϊ null��");
		this.in = in;
	}
	
}
