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
 * jxl��������
 * @author  DwArFeng
 * @since 1.8
 */
public final class JxlXlsSaver extends AbstractXlsSaver{

	private final WritableWorkbook workbook;
	
	/**
	 * ���ɡ�
	 * @param out ָ�����������
	 * @throws IOException IO�쳣��
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
		Objects.requireNonNull(str, "��ڲ��� str ����Ϊ null��");
		//Objects.requireNonNull(alignment, "��ڲ��� alignment ����Ϊ null��");
		
		WritableSheet sheet = workbook.getSheet(sheetIndex);
		
		try {
			sheet.addCell(new Label(column, row, str));
		} catch (WriteException e) {
			e.printStackTrace();
			//�����ڳ�����д��������ж��Ǻ�С��������˲������׳����쳣��
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
			//��Ϊ��ʽ�����ڳ�����Ԥ���趨�õģ����в����ܳ���
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
			//���ڳ����ϸ��ո�ʽ���ɱ����˲������׳�����쳣��
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
			//�ڳ����У����е��趨���Ƿ��ϱ߽�Ҫ��ģ���˲������׳���Щ�쳣��
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
			//�ڳ����У����е��趨���Ƿ��ϱ߽�Ҫ��ģ���˲������׳���Щ�쳣��
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setColumnWidth(int, int, int)
	 */
	@Override
	public void setColumnWidth(int sheetIndex, int columnIndex, int width) {
		CellView cv = new CellView();
		//������ת��Ϊjxl�еĿ��
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
			//���ڳ������ǰ��ո�ʽ����д�ģ��������׳����쳣��
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mhcount.model.io.XlsSaver#setColumnFormatAsString(int, int, boolean, com.dwarfeng.jier.mhcount.model.io.XlsSaver.Alignment)
	 */
	@Override
	public void setColumnFormatAsString(int sheetIndex, int column, boolean isBold, int pointSize, Alignment alignment) {
		Objects.requireNonNull(alignment, "��ڲ��� alignment ����Ϊ null��");
		
		WritableSheet sheet = workbook.getSheet(sheetIndex);
		WritableFont wf = new WritableFont(WritableFont.ARIAL);
		try {
			wf.setBoldStyle(isBold ? WritableFont.BOLD : WritableFont.NO_BOLD);
			wf.setPointSize(pointSize);
		} catch (WriteException e) {
			e.printStackTrace();
			//���õ��������ѭ��ʽ������ʹ��Ĭ�����壬�����ܳ����쳣��
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
			//�������׳��쳣��
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
		Objects.requireNonNull(format, "��ڲ��� format ����Ϊ null��");

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
		Objects.requireNonNull(alignment, "��ڲ��� alignment ����Ϊ null��");

		WritableSheet sheet = workbook.getSheet(sheetIndex);
		WritableCell cell = sheet.getWritableCell(column, row);
		
		WritableFont wf = new WritableFont(WritableFont.ARIAL);
		try {
			wf.setBoldStyle(isBold ? WritableFont.BOLD : WritableFont.NO_BOLD);
			wf.setPointSize(pointSize);
		} catch (WriteException e) {
			e.printStackTrace();
			//���õ��������ѭ��ʽ������ʹ��Ĭ�����壬�����ܳ����쳣��
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
			//�������׳��쳣��
		}
		
		cell.setCellFormat(wcf);
	}
	

}
