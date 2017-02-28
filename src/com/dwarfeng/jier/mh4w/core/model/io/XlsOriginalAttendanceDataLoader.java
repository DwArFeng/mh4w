package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultOriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;

/**
 * Xls ԭʼ��������ȡ����
 * <p> ���� xls �ļ���ȡԭʼ�������ݡ�
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class XlsOriginalAttendanceDataLoader extends StreamLoader<DataListModel<OriginalAttendanceData>>{

	private final String fileName;
	
	private final int row_start;
	private final int column_department;
	private final int column_workNumber;
	private final int column_name;
	private final int column_date;
	private final int column_shift;
	private final int column_record;

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @param fileName �����ļ������ơ�
	 * @param row_start ��ʼ�����С�
	 * @param column_department �������ڵ��С�
	 * @param column_workNumber �������ڵ��С�
	 * @param column_name �������ڵ��С�
	 * @param column_date �������ڵ��С�
	 * @param column_shift ������ڵ��С�
	 * @param column_record ��¼���ڵ��С�
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XlsOriginalAttendanceDataLoader(InputStream in, String fileName, int row_start, int column_department, int column_workNumber,
			int column_name, int column_date, int column_shift, int column_record) {
		super(in);
		Objects.requireNonNull(fileName, "��ڲ��� fileName ����Ϊ null��");
		
		this.fileName = fileName;
		this.row_start = row_start;
		this.column_department = column_department;
		this.column_workNumber = column_workNumber;
		this.column_name = column_name;
		this.column_date = column_date;
		this.column_shift = column_shift;
		this.column_record = column_record;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(DataListModel<OriginalAttendanceData> originalAttendanceDatas) throws LoadFailedException {
		Objects.requireNonNull(originalAttendanceDatas, "��ڲ��� originalAttendanceDatas ����Ϊ null��");
		
		XlsLoader loader = null;
		try{
			loader = new JxlXlsLoader(in);
			
			for(int i = row_start - 1 ; i < loader.getRows(0) ; i ++){
				String workNumber = loader.getStringAt(0, i, column_workNumber);
				String department = loader.getStringAt(0, i, column_department);
				String name = loader.getStringAt(0, i, column_name);
				String date = loader.getStringAt(0, i, column_date);
				String shift = loader.getStringAt(0, i, column_shift);
				String attendanceRecord = loader.getStringAt(0, i, column_record);

				OriginalAttendanceData originalAttendanceData = new DefaultOriginalAttendanceData(fileName, 
						i + 1, workNumber, department, name, date, shift, attendanceRecord);
				originalAttendanceDatas.add(originalAttendanceData);
			}
			
		}catch (Exception e) {
			throw new LoadFailedException("���ڱ��ȡ�� - �޷���ȷ��������б�ģ���ж�ȡ����", e);
		}finally {
			if(Objects.nonNull(loader)){
				try {
					loader.close();
				} catch (IOException ignore) {
					//���ᷢ������쳣��
				}
			}
		}
	}

}
