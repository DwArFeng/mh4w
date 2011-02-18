package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.Closeable;

/**
 * xls��ȡ�ӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface XlsLoader extends Closeable{
	
	/**
	 * ��ȡָ����Ԫ��Ĵ��ı���
	 * @param sheetIndex ��������š�
	 * @param row ָ�����С�
	 * @param column ָ�����С�
	 * @return ��Ӧ���ı���
	 */
	public String getStringAt(int sheetIndex, int row, int column);
	
	/**
	 * ��ȡָ���������������
	 * @param sheetIndex ��������š�
	 * @return ������
	 */
	public int getRows(int sheetIndex);
	
	/**
	 * ��ȡָ���������������
	 * @param sheetIndex ��������š�
	 * @return ������
	 */
	public int getColumns(int sheetIndex);
	
	/**
	 * ��ȡ������������
	 * @return ������������
	 */
	public int getSheets();
	
}
