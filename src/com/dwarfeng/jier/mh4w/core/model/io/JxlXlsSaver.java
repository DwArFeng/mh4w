package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * jxl保存器。
 * @author  DwArFeng
 * @since 1.8
 */
public final class JxlXlsSaver extends AbstractXlsSaver{

	private final WritableWorkbook workbook;
	
	/**
	 * 生成。
	 * @param out 指定的输出流。
	 * @throws IOException IO异常。
	 */
	public JxlXlsSaver(OutputStream out) throws IOException {
		super(out);
		workbook = Workbook.createWorkbook(out);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#close()
	 */
	@Override
	public void close() throws IOException {
		workbook.write();
		try {
			workbook.close();
		} catch (WriteException e) {
			IOException ioe = new IOException("write failed");
			ioe.setStackTrace(e.getStackTrace());
			throw ioe;
		}finally{
			try{
				out.close();
			}catch (IOException e) {
				IOException ioe = new IOException("close failed");
				ioe.setStackTrace(e.getStackTrace());
				throw ioe;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setString(int, int, int, java.lang.String, boolean, int, com.dwarfeng.jier.mhcount.model.io.XlsSaver.Alignment)
	 */
	@Override
	public void setString(int sheetIndex, int row, int column, String str) {
		Objects.requireNonNull(str, "入口参数 str 不能为 null。");
		//Objects.requireNonNull(alignment, "入口参数 alignment 不能为 null。");
		
		WritableSheet sheet = workbook.getSheet(sheetIndex);
		
		try {
			sheet.addCell(new Label(column, row, str));
		} catch (WriteException e) {
			e.printStackTrace();
			//由于在程序中写入的行与列都是很小的数，因此不可能抛出该异常。
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#writeNumber(int, int, int, double, java.lang.String)
	 */
	@Override
	public void setNumber(int sheetIndex, int row, int column, double val) {
		WritableSheet sheet = workbook.getSheet(sheetIndex);
		try {
			sheet.addCell(new jxl.write.Number(column, row, val));
		} catch (WriteException e) {
			e.printStackTrace();
			//因为格式都是在程序中预先设定好的，所有不可能出错。
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#mergeCell(int, int, int, int, int)
	 */
	@Override
	public void mergeCell(int sheetIndex, int row1, int column1, int row2, int column2) {
		WritableSheet sheet = workbook.getSheet(sheetIndex);
		try {
			sheet.mergeCells(column1, row1, column2, row2);
		} catch (WriteException e) {
			e.printStackTrace();
			//由于程序严格按照格式生成表格，因此不可能抛出这个异常。
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#createSheet(int, java.lang.String)
	 */
	@Override
	public void createSheet(int sheetIndex, String label) {
		
		workbook.createSheet(label, sheetIndex);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setRowHeight(int, int, int)
	 */
	@Override
	public void setRowHeight(int sheetIndex, int rowIndex, int height) {
		CellView cv = new CellView();
		cv.setSize((int) (height * 15.151515151515151515151515151515d));
		try {
			workbook.getSheet(sheetIndex).setRowView(rowIndex, cv);
		} catch (RowsExceededException | IndexOutOfBoundsException e) {
			e.printStackTrace();
			//在程序中，所有的设定都是符合边界要求的，因此不可能抛出这些异常。
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setRowAutoSize(int, int, boolean)
	 */
	@Override
	public void setRowAutoSize(int sheetIndex, int rowIndex, boolean aFlag) {
		CellView cv = new CellView();
		cv.setAutosize(aFlag);
		try {
			workbook.getSheet(sheetIndex).setRowView(rowIndex, cv);
		} catch (RowsExceededException | IndexOutOfBoundsException e) {
			e.printStackTrace();
			//在程序中，所有的设定都是符合边界要求的，因此不可能抛出这些异常。
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setColumnWidth(int, int, int)
	 */
	@Override
	public void setColumnWidth(int sheetIndex, int columnIndex, int width) {
		CellView cv = new CellView();
		//由像素转换为jxl中的宽度
		cv.setSize((int) (width * 36.571428571428571428571428571429d));
		workbook.getSheet(sheetIndex).setColumnView(columnIndex, cv);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setColumnAutoSize(int, int, boolean)
	 */
	@Override
	public void setColumnAutoSize(int sheetIndex, int columnIndex, boolean aFlag) {
		CellView cv = new CellView();
		cv.setAutosize(aFlag);
		workbook.getSheet(sheetIndex).setColumnView(columnIndex, cv);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setHyperlink(int, int, int, java.net.URL)
	 */
	@Override
	public void setHyperlink(int sheetIndex, int row, int column, URL hyperlink) {
		WritableSheet sheet = workbook.getSheet(sheetIndex);
		WritableHyperlink wh = new WritableHyperlink(column, row, hyperlink);
		try {
			sheet.addHyperlink(wh);
		} catch (WriteException e) {
			e.printStackTrace();
			//由于超链接是按照格式来编写的，不可能抛出该异常。
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setColumnFormatAsString(int, int, boolean, com.dwarfeng.jier.mhcount.model.io.XlsSaver.Alignment)
	 */
	@Override
	public void setColumnFormatAsString(int sheetIndex, int column, boolean isBold, int pointSize, Alignment alignment) {
		Objects.requireNonNull(alignment, "入口参数 alignment 不能为 null。");
		
		WritableSheet sheet = workbook.getSheet(sheetIndex);
		WritableFont wf = new WritableFont(WritableFont.ARIAL);
		try {
			wf.setBoldStyle(isBold ? WritableFont.BOLD : WritableFont.NO_BOLD);
			wf.setPointSize(pointSize);
		} catch (WriteException e) {
			e.printStackTrace();
			//设置的字体均遵循格式，字体使用默认字体，不可能出现异常。
		}
		
		CellView cv = new CellView(sheet.getColumnView(column));
		WritableCellFormat wcf = new WritableCellFormat(wf);
		
		try{
			switch (alignment) {
				case CENTER:
					wcf.setAlignment(jxl.format.Alignment.CENTRE);
					break;
				case LEFT:
					wcf.setAlignment(jxl.format.Alignment.LEFT);
					break;
				case RIGHT:
					wcf.setAlignment(jxl.format.Alignment.RIGHT);
					break;
			}
		}catch(WriteException e){
			e.printStackTrace();
			//不可能抛出异常。
		}
		
		cv.setFormat(wcf);
		sheet.setColumnView(column, cv);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setColumnFormatAsNumber(int, int, java.lang.String)
	 */
	@Override
	public void setColumnFormatAsNumber(int sheetIndex, int column, String format) {
		Objects.requireNonNull(format, "入口参数 format 不能为 null。");

		WritableSheet sheet = workbook.getSheet(sheetIndex);
		CellView cv = new CellView(sheet.getColumnView(column));
		
		NumberFormat nf = new NumberFormat(format);
		WritableCellFormat wcf = new WritableCellFormat(nf);
		
		cv.setFormat(wcf);
		sheet.setColumnView(column, cv);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setCellFormat(int, int, int, boolean, com.dwarfeng.jier.mhcount.model.io.XlsSaver.Alignment)
	 */
	@Override
	public void setCellFormat(int sheetIndex, int row, int column, boolean isBold, int pointSize, Alignment alignment) {
		Objects.requireNonNull(alignment, "入口参数 alignment 不能为 null。");

		WritableSheet sheet = workbook.getSheet(sheetIndex);
		WritableCell cell = sheet.getWritableCell(column, row);
		
		WritableFont wf = new WritableFont(WritableFont.ARIAL);
		try {
			wf.setBoldStyle(isBold ? WritableFont.BOLD : WritableFont.NO_BOLD);
			wf.setPointSize(pointSize);
		} catch (WriteException e) {
			e.printStackTrace();
			//设置的字体均遵循格式，字体使用默认字体，不可能出现异常。
		}

		WritableCellFormat wcf = new WritableCellFormat(wf);
		
		try{
			switch (alignment) {
				case CENTER:
					wcf.setAlignment(jxl.format.Alignment.CENTRE);
					break;
				case LEFT:
					wcf.setAlignment(jxl.format.Alignment.LEFT);
					break;
				case RIGHT:
					wcf.setAlignment(jxl.format.Alignment.RIGHT);
					break;
			}
		}catch(WriteException e){
			e.printStackTrace();
			//不可能抛出异常。
		}
		
		cell.setCellFormat(wcf);
	}
	

}
