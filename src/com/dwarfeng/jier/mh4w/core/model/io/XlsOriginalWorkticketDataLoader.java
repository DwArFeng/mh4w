package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultOriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;

/**
 * Xls ԭʼ��Ʊ����ȡ����
 * <p> ���� xls �ļ���ȡԭʼ��Ʊ���ݡ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class XlsOriginalWorkticketDataLoader extends StreamLoader<DataListModel<OriginalWorkticketData>>{

	private final String fileName;
	
	private final int row_start;
	private final int column_department;
	private final int column_workNumber;
	private final int column_name;
	private final Job[] jobs;
	
	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @param fileName ��Ʊ�ļ������ơ�
	 * @param row_start ���ݿ�ʼ���С�
	 * @param column_department �������ڵ��С�
	 * @param column_workNumber �������ڵ��С�
	 * @param column_name �������ڵ��С�
	 * @param jobs �������顣
	 */
	public XlsOriginalWorkticketDataLoader(InputStream in, String fileName, int row_start, int column_department, int column_workNumber,
			int column_name, Job[] jobs) {
		super(in);
		Objects.requireNonNull(jobs, "��ڲ��� jobs ����Ϊ null��");
		Objects.requireNonNull(fileName, "��ڲ��� fileName ����Ϊ null��");

		this.fileName = fileName;
		this.row_start = row_start;
		this.column_department = column_department;
		this.column_workNumber = column_workNumber;
		this.column_name = column_name;
		this.jobs = jobs;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(DataListModel<OriginalWorkticketData> originalWorkticketDatas) throws LoadFailedException {
		Objects.requireNonNull(originalWorkticketDatas, "��ڲ��� originalWorkticketDatas ����Ϊ null��");
		
		XlsLoader loader = null;
		try{
			loader = new JxlXlsLoader(in);
			
			for(int i = row_start - 1 ; i < loader.getRows(0) ; i ++){
				String workNumber = loader.getStringAt(0, i, column_workNumber);
				String department = loader.getStringAt(0, i, column_department);
				String name = loader.getStringAt(0, i, column_name);
				
				for(Job job : jobs){
					String workticket = loader.getStringAt(0, i, job.getOriginalColumn());
					
					OriginalWorkticketData orWorkticketData = new DefaultOriginalWorkticketData(fileName, i + 1,
							workNumber, department, name, workticket, job);
					originalWorkticketDatas.add(orWorkticketData);
				}
				
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
