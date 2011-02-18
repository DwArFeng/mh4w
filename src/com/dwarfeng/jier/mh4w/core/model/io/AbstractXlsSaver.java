package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * ����xls��������
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractXlsSaver implements XlsSaver{

	/**������*/
	protected final OutputStream out;
	
	/**
	 * ���ɡ�
	 * @param out ��������
	 * @throws IOException ʧ���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public AbstractXlsSaver(OutputStream out) throws IOException{
		Objects.requireNonNull(out, "��ڲ��� in ����Ϊ null��");
		this.out = out;
	}
	
}
