package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;

/**
 * 统计表保存器。
 * @author DwArFeng
 * @since 1.8
 */
public interface XlsSaver extends Closeable{

	/**
	 * 关闭该保存器，并且该操作会直接将保存器中的数据进行保存，比如将编辑好的数据写入硬盘。
	 */
	@Override
	public void close() throws IOException;
	
	/**
	 * 向指定的位置写入字符串。
	 * @param sheetIndex 指定的工作簿序号。
	 * @param row 指定的行。
	 * @param column 指定的列。
	 * @param str 指定的字符串。
	 */
	public void setString(int sheetIndex, int row, int column, String str);
	
	/**
	 * 向指定的位置写入数值。
	 * @param sheetIndex 指定的工作簿序号。
	 * @param row  指定的行号。
	 * @param column 指定的列号。
	 * @param val 指定的数值。
	 */
	public void setNumber(int sheetIndex, int row, int column, double val);
	
	/**
	 * 合并单元格
	 * @param sheetIndex 指定的工作簿序号。
	 * @param row1 起始单元格的行号。
	 * @param column1 起始单元格的列号。
	 * @param row2 终止单元格的行号。
	 * @param column2 终止单元格的列号。
	 */
	public void mergeCell(int sheetIndex, int row1, int column1, int row2, int column2);
	
	/**
	 * 创建新的工作簿。
	 * @param sheetIndex 指定的工作簿序号。
	 * @param label 新的工作簿的标签。
	 */
	public void createSheet(int sheetIndex, String label);
	
	/**
	 * 设置指定的行高，以像素为单位。
	 * @param sheetIndex 指定的工作簿序号。
	 * @param rowIndex 指定的行号。
	 * @param height 指定的行高。
	 */
	public void setRowHeight(int sheetIndex, int rowIndex, int height);
	
	/**
	 * 设置是否自动调整指定的行高。
	 * @param sheetIndex 指定的工作簿序号。
	 * @param rowIndex 指定的行号。
	 * @param aFlag 是否自动调整。
	 */
	public void setRowAutoSize(int sheetIndex, int rowIndex, boolean aFlag);
	
	/**
	 * 设置指定的列宽，以像素为单位。
	 * @param sheetIndex 指定的工作簿的序号。
	 * @param columnIndex 指定的列号。
	 * @param width 指定的列宽。
	 */
	public void setColumnWidth(int sheetIndex, int columnIndex, int width);
	
	/**
	 * 设置是否自动调整指定的列宽。
	 * @param sheetIndex 指定的工作簿的序号。
	 * @param columnIndex 指定的列号。
	 * @param aFlag 是否自动调整。
	 */
	public void setColumnAutoSize(int sheetIndex, int columnIndex, boolean aFlag);
	
	/**
	 * 设置指定单元格的超链接。
	 * @param sheetIndex 指定的工作簿的序号。
	 * @param row 指定的行号。
	 * @param column 指定的列号。
	 * @param hyperlink 超链接指向的URL。
	 */
	public void setHyperlink(int sheetIndex, int row, int column, URL hyperlink);
	
	/**
	 * 以文本的形式设置指定列的格式。
	 * @param sheetIndex 指定的工作簿的序号。
	 * @param column 指定的列号。
	 * @param isBold 是否加粗。
	 * @param pointSize 指定的字号。
	 * @param alignment 对齐方式。
	 */
	public void setColumnFormatAsString(int sheetIndex, int column, boolean isBold, int pointSize, Alignment alignment);
	
	/**
	 * 以数值的形式设置指定的列的格式。
	 * @param sheetIndex 指定的工作簿的序号。
	 * @param column 指定的列号。
	 * @param format 指定的数据格式。
	 */
	public void setColumnFormatAsNumber(int sheetIndex, int column, String format);
	
	/**
	 * 设置指定的单元格的格式。
	 * @param sheetIndex 指定的工作簿的序号。
	 * @param row 指定的行号。
	 * @param column 指定的列号。
	 * @param isBold 是否加粗。
	 * @param pointSize 指定的字号。
	 * @param alignment 指定的对齐方式。
	 */
	public void setCellFormat(int sheetIndex, int row, int column, boolean isBold, int pointSize, Alignment alignment);
	
	/**
	 * 对齐方式。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Alignment{
		/**居中对齐*/
		CENTER,
		/**左对齐*/
		LEFT,
		/**右对齐*/
		RIGHT,
	}
	
}
