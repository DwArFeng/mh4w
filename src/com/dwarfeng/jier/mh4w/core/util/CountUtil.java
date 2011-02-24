package com.dwarfeng.jier.mh4w.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.num.unit.Time;
import com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalAttendanceDataLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalWorkticketDataLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;
import com.dwarfeng.jier.mh4w.core.model.struct.Staff;
import com.dwarfeng.jier.mh4w.core.model.struct.TimeSection;
import com.dwarfeng.jier.mh4w.core.model.struct.TransException;
import com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData;

/**
 *��ͳ���йع��߷�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class CountUtil {

	/**
	 * �� xls ����е�����ת�����е���š�
	 * @param column �����ơ�
	 * @return �����ƶ�Ӧ����š�
	 */
	public static int columnString2Int(String column) {
		Objects.requireNonNull(column, "��ڲ��� input ����Ϊ null��");
		column = column.toUpperCase();
		if(column.matches("[^A-Z]")){
			throw new IllegalArgumentException("�ַ�ֻ�ܰ���A-Z");
		}
		
		int sum = 0;
		int one = Character.getNumericValue('A');
		
		for(int i = 0 ; i < column.length() ; i ++){
			sum *= 26;
			char ch = column.charAt(i);
			sum += Character.getNumericValue(ch) - one +1;
		}
		
		return sum - 1;
	}
	
	/**
	 * ����һ���µ� xls ԭʼ�������ݶ�ȡ����
	 * @param fileSelectModel ָ�����ļ�ѡ��ģ�͡�
	 * @param coreConfigModel ָ���ĺ�������ģ�͡�
	 * @return ��ָ����ģ����ɵ� xls ԭʼ�������ݶ�ȡ����
	 * @throws FileNotFoundException �ļ�δ�ҵ��쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static XlsOriginalAttendanceDataLoader newXlsOriginalAttendanceDataLoader(
			FileSelectModel fileSelectModel, CoreConfigModel coreConfigModel) throws FileNotFoundException{
		Objects.requireNonNull(fileSelectModel, "��ڲ��� fileSelectModel ����Ϊ null��");
		Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");

		InputStream in = new FileInputStream(fileSelectModel.getAttendanceFile());
		String fileName = fileSelectModel.getAttendanceFile().getName();
		int row_start = coreConfigModel.getAttendanceStartRow();
		int column_department = coreConfigModel.getAttendanceDepartmentColumn();
		int column_workNumber = coreConfigModel.getAttendanceWorkNumberColumn();
		int column_name = coreConfigModel.getAttendacneNameColumn();
		int column_date = coreConfigModel.getAttendanceDateColumn();
		int column_shift = coreConfigModel.getAttendanceShiftColumn();
		int column_record = coreConfigModel.getAttendanceRecordColumn();
		
		return new XlsOriginalAttendanceDataLoader(in, fileName, row_start, column_department, 
				column_workNumber, column_name, column_date, column_shift, column_record);
	}
	
	/**
	 * ����һ���µ� xls ԭʼ����Ʊ�ݶ�ȡ����
	 * @param fileSelectModel ָ�����ļ�ѡ��ģ�͡�
	 * @param coreConfigModel ָ���ĺ�������ģ�͡�
	 * @param jobModel ����ģ�͡�
	 * @return ��ָ����ģ����ɵ� xls ԭʼ�������ݶ�ȡ����
	 * @throws FileNotFoundException �ļ�δ�ҵ��쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static XlsOriginalWorkticketDataLoader newXlsOriginalWorkticketDataLoader(FileSelectModel fileSelectModel,
			CoreConfigModel coreConfigModel, JobModel jobModel) throws FileNotFoundException {
		Objects.requireNonNull(fileSelectModel, "��ڲ��� fileSelectModel ����Ϊ null��");
		Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");
		Objects.requireNonNull(jobModel, "��ڲ��� jobModel ����Ϊ null��");

		InputStream in = new FileInputStream(fileSelectModel.getWorkticketFile());
		String fileName = fileSelectModel.getWorkticketFile().getName();
		int row_start = coreConfigModel.getWorkticketStartRow();
		int column_department = coreConfigModel.getWorkticketDepartmentColumn();
		int column_workNumber = coreConfigModel.getWorkticketWorkNumberColumn();
		int column_name = coreConfigModel.getAttendacneNameColumn();
		Job[] jobs = new Job[jobModel.size()];
		int i = 0;
		for(Job job : jobModel){
			jobs[i++] = job;
		}

		return new XlsOriginalWorkticketDataLoader(in, fileName, row_start, column_department, column_workNumber,
				column_name, jobs);
	}
	
	/**
	 * ��ʱ���ʽ���� 12:45��ת���ɸ�ʱ������������������Сʱ��
	 * @param string ָ����ʱ���ı���
	 * @return ��ʱ���ʽ���� 12:45��ת���ɸ�ʱ������������������Сʱ��
	 */
	public static double string2Hour(String string){
		String[] strs = string.split(":");
		if(strs.length == 1){
			return Integer.parseInt(string);
		}else if(strs.length == 2){
			int hour = Integer.parseInt(strs[0]);
			int minute = Integer.parseInt(strs[1]);
			return NumberUtil.unitTrans((double) minute, Time.MIN, Time.HOR).doubleValue() + hour;
		}else{
			throw new IllegalArgumentException("ʱ�乤�� - ��Ч�Ĵ��������" + string);
		}
	}
	
	/**
	 * ��ԭʼ��������ת���ɿ������ݡ�
	 * @param rawData ָ����ԭʼ���ݡ�
	 * @param shiftModel ���ģ�͡�
	 * @return ��ָ����ԭʼ����ת���ɵĿ������ݡ�
	 * @throws TransException ת�����̷����쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static AttendanceData transAttendanceData(OriginalAttendanceData rawData, ShiftModel shiftModel) throws TransException{
		Objects.requireNonNull(rawData, "��ڲ��� rawData ����Ϊ null��");
		Objects.requireNonNull(shiftModel, "��ڲ��� shiftModel ����Ϊ null��");

		try{
			Staff staff = transStaff(rawData.getWorkNumber(), rawData.getDepartment(), rawData.getName());
			CountDate countDate = transCountDate(rawData.getDate());
			Shift shift = transShift(rawData.getShift(), shiftModel);
			TimeSection attendanceRecord = transTimeSection(rawData.getAttendanceRecord());
			
			AttendanceData data = new DefaultAttendanceData(staff, countDate, shift, attendanceRecord);
			return data;
		}catch (Exception e) {
			throw new TransException("�޷���ָ����ԭʼ��������ת��Ϊ��������", e);
		}
			
	}
	
	/**
	 * ��ԭʼ��Ʊ����ת���ɹ�Ʊ���ݡ�
	 * @param rawData ָ����ԭʼ��Ʊ���ݡ�
	 * @return ��ָ����ԭʼ��Ʊ����ת���ɵĹ�Ʊ���ݡ�
	 * @throws TransException ת�����̷����쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static WorkticketData transWorkticketData(OriginalWorkticketData rawData) throws TransException{
		Objects.requireNonNull(rawData, "��ڲ��� rawData ����Ϊ null��");
		try{
			Staff staff = transStaff(rawData.getWorkNumber(), rawData.getDepartment(), rawData.getName());
			Job job = rawData.getJob();
			double workticket = Double.parseDouble(rawData.getWorkticket());
			
			return new DefaultWorkticketData(staff, job, workticket);
		}catch (Exception e) {
			throw new TransException("�޷���ָ����ԭʼ��Ʊ����ת��Ϊ��Ʊ����", e);
		}
	}
	
	/**
	 * ��ָ�����ַ�ת��ΪԱ����
	 * @param workNumber �����ַ�����
	 * @param department �����ַ�����
	 * @param name �����ַ�����
	 * @return ָ�����ַ�ת����Ա����
	 * @throws TransException ת���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static Staff transStaff(String workNumber, String department, String name) throws TransException{
		Objects.requireNonNull(workNumber, "��ڲ��� workNumber ����Ϊ null��");
		Objects.requireNonNull(department, "��ڲ��� department ����Ϊ null��");
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");

		return new Staff(workNumber, department, name);
	}
	
	/**
	 * ��ָ�����ַ���ת��Ϊ���ڡ�
	 * @param dateString ָ���������ַ�����
	 * @return ��ָ�����ַ���ת�����������ڡ�
	 * @throws TransException ת���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static CountDate transCountDate(String dateString) throws TransException{
		Objects.requireNonNull(dateString, "��ڲ��� dateString ����Ϊ null��");

		try{
			String[] strs = dateString.split("-");
			int year = Integer.parseInt(strs[0]);
			int month = Integer.parseInt(strs[1]);
			int day = Integer.parseInt(strs[2]);
			return new CountDate(year, month, day);
		}catch (Exception e) {
			throw new TransException("�޷���ָ�����ַ�ת��Ϊ�ַ��� : " + dateString, e);
		}
	}
	
	/**
	 * ��ָ�����ַ���ת��Ϊ��Ρ�
	 * @param shift ָ�����ַ�����
	 * @param shiftModel ָ���İ��ģ�͡�
	 * @return ��ָ�����ַ���ת�����ɵİ�Ρ�
	 * @throws TransException ת���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static Shift transShift(String shift, ShiftModel shiftModel) throws TransException{
		Objects.requireNonNull(shiftModel, "��ڲ��� shiftModel ����Ϊ null��");
		
		try{
			for(Shift referanceShift : shiftModel){
				if(referanceShift.getName().equals(shift)){
					return referanceShift;
				}
			}
			throw new NoSuchElementException();
		}catch (Exception e) {
			throw new TransException("�޷���ָ���İ���ı�ת��Ϊ���" + shift, e);
		}
	}
	
	/**
	 * ��ָ�����ַ���ת��Ϊʱ�����䡣
	 * @param string ָ�����ַ�����
	 * @return ��ָ�����ַ���ת�����ɵ�ʱ�����䡣
	 * @throws TransException ת���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static TimeSection transTimeSection(String string) throws TransException{
		try{
			String[] strs = string.split("-");
			String[] f = strs[0].split(":");
			String[] l = strs[1].split(":");
			
			double start = Double.parseDouble(f[0]) + NumberUtil.unitTrans(Double.parseDouble(f[1]), Time.MIN, Time.HOR).doubleValue();
			double end = Double.parseDouble(l[0]) + NumberUtil.unitTrans(Double.parseDouble(l[1]), Time.MIN, Time.HOR).doubleValue();

			return new TimeSection(start, end);
		}catch (Exception e) {
			throw new TransException("�޷���ָ����ʱ�������ı�ת��Ϊ���" + string, e);
		}
	}
	
	
	
	//��ֹ�ⲿʵ������
	private CountUtil(){}
}
