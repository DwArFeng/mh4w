package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;

/**
 * ͳ�Ʊ�������
 * @author DwArFeng
 * @since 1.8
 */
public interface XlsSaver extends Closeable{

	/**
	 * �رոñ����������Ҹò�����ֱ�ӽ��������е����ݽ��б��棬���罫�༭�õ�����д��Ӳ�̡�
	 */
	@Override
	public void close() throws IOException;
	
	/**
	 * ��ָ����λ��д���ַ�����
	 * @param sheetIndex ָ���Ĺ�������š�
	 * @param row ָ�����С�
	 * @param column ָ�����С�
	 * @param str ָ�����ַ�����
	 */
	public void setString(int sheetIndex, int row, int column, String str);
	
	/**
	 * ��ָ����λ��д����ֵ��
	 * @param sheetIndex ָ���Ĺ�������š�
	 * @param row  ָ�����кš�
	 * @param column ָ�����кš�
	 * @param val ָ������ֵ��
	 */
	public void setNumber(int sheetIndex, int row, int column, double val);
	
	/**
	 * �ϲ���Ԫ��
	 * @param sheetIndex ָ���Ĺ�������š�
	 * @param row1 ��ʼ��Ԫ����кš�
	 * @param column1 ��ʼ��Ԫ����кš�
	 * @param row2 ��ֹ��Ԫ����кš�
	 * @param column2 ��ֹ��Ԫ����кš�
	 */
	public void mergeCell(int sheetIndex, int row1, int column1, int row2, int column2);
	
	/**
	 * �����µĹ�������
	 * @param sheetIndex ָ���Ĺ�������š�
	 * @param label �µĹ������ı�ǩ��
	 */
	public void createSheet(int sheetIndex, String label);
	
	/**
	 * ����ָ�����иߣ�������Ϊ��λ��
	 * @param sheetIndex ָ���Ĺ�������š�
	 * @param rowIndex ָ�����кš�
	 * @param height ָ�����иߡ�
	 */
	public void setRowHeight(int sheetIndex, int rowIndex, int height);
	
	/**
	 * �����Ƿ��Զ�����ָ�����иߡ�
	 * @param sheetIndex ָ���Ĺ�������š�
	 * @param rowIndex ָ�����кš�
	 * @param aFlag �Ƿ��Զ�������
	 */
	public void setRowAutoSize(int sheetIndex, int rowIndex, boolean aFlag);
	
	/**
	 * ����ָ�����п�������Ϊ��λ��
	 * @param sheetIndex ָ���Ĺ���������š�
	 * @param columnIndex ָ�����кš�
	 * @param width ָ�����п�
	 */
	public void setColumnWidth(int sheetIndex, int columnIndex, int width);
	
	/**
	 * �����Ƿ��Զ�����ָ�����п�
	 * @param sheetIndex ָ���Ĺ���������š�
	 * @param columnIndex ָ�����кš�
	 * @param aFlag �Ƿ��Զ�������
	 */
	public void setColumnAutoSize(int sheetIndex, int columnIndex, boolean aFlag);
	
	/**
	 * ����ָ����Ԫ��ĳ����ӡ�
	 * @param sheetIndex ָ���Ĺ���������š�
	 * @param row ָ�����кš�
	 * @param column ָ�����кš�
	 * @param hyperlink ������ָ���URL��
	 */
	public void setHyperlink(int sheetIndex, int row, int column, URL hyperlink);
	
	/**
	 * ���ı�����ʽ����ָ���еĸ�ʽ��
	 * @param sheetIndex ָ���Ĺ���������š�
	 * @param column ָ�����кš�
	 * @param isBold �Ƿ�Ӵ֡�
	 * @param pointSize ָ�����ֺš�
	 * @param alignment ���뷽ʽ��
	 */
	public void setColumnFormatAsString(int sheetIndex, int column, boolean isBold, int pointSize, Alignment alignment);
	
	/**
	 * ����ֵ����ʽ����ָ�����еĸ�ʽ��
	 * @param sheetIndex ָ���Ĺ���������š�
	 * @param column ָ�����кš�
	 * @param format ָ�������ݸ�ʽ��
	 */
	public void setColumnFormatAsNumber(int sheetIndex, int column, String format);
	
	/**
	 * ����ָ���ĵ�Ԫ��ĸ�ʽ��
	 * @param sheetIndex ָ���Ĺ���������š�
	 * @param row ָ�����кš�
	 * @param column ָ�����кš�
	 * @param isBold �Ƿ�Ӵ֡�
	 * @param pointSize ָ�����ֺš�
	 * @param alignment ָ���Ķ��뷽ʽ��
	 */
	public void setCellFormat(int sheetIndex, int row, int column, boolean isBold, int pointSize, Alignment alignment);
	
	/**
	 * ���뷽ʽ��
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Alignment{
		/**���ж���*/
		CENTER,
		/**�����*/
		LEFT,
		/**�Ҷ���*/
		RIGHT,
	}
	
}
