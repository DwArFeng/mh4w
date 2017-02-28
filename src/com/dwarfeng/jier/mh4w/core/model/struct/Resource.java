package com.dwarfeng.jier.mh4w.core.model.struct;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ������ʹ�õ���Դ��
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface Resource {

	/**
	 * ����Դ����������
	 * @return ��Դ����������
	 * @throws IOException IO�쳣��
	 */
	public InputStream openInputStream() throws IOException;
	
	/**
	 * ����Դ���������
	 * @return ��Դ���������
	 * @throws IOException IO�쳣��
	 */
	public OutputStream openOutputStream() throws IOException;
	
	/**
	 * ���ļ���Դ�û���Ĭ�ϵ���Դ��
	 * @throws IOException IO�쳣��
	 */
	public void reset() throws IOException;
	
}
