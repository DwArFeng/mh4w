package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.InputStream;

import com.dwarfeng.jier.mh4w.core.model.io.XlsFailedException.FailedType;

import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * jxl读取器。
 * @author DwArFeng
 * @since 1.8
 */
public final class JxlXlsLoader extends AbstractXlsLoader {

	private final Workbook workbook;
	
	/**
	 * 生成。
	 * @param in 输入流。
	 * @throws XlsFailedException 创建失败时抛出的异常。
	 */
	public JxlXlsLoader(InputStream in) throws XlsFailedException {
		super(in);
		try {
			workbook = Workbook.getWorkbook(this.in);
		} catch (BiffException e) {
			throw new XlsFailedException(FailedType.NOT_SUPPORT);
		} catch (IOException e) {
			throw new XlsFailedException(FailedType.IO_FAILED);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsLoader#getStringAt(int, int, int)
	 */
	@Override
	public String getStringAt(int sheetIndex, int row, int column) {
		return workbook.getSheet(sheetIndex).getCell(column, row).getContents();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsLoader#getRows(int)
	 */
	@Override
	public int getRows(int sheetIndex) {
		return workbook.getSheet(sheetIndex).getRows();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsLoader#getColumns(int)
	 */
	@Override
	public int getColumns(int sheetIndex) {
		return workbook.getSheet(sheetIndex).getColumns();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsLoader#getSheets()
	 */
	@Override
	public int getSheets() {
		return workbook.getSheets().length;
	}

	/*
	 * (non-Javadoc)
	 * @see com.sun.xml.internal.ws.Closeable#close()
	 */
	@Override
	public void close() throws IOException{
		in.close();
		workbook.close();
	}

}
