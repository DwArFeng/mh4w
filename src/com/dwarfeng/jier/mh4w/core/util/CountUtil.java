package com.dwarfeng.jier.mh4w.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.num.unit.Time;
import com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DateTypeModel;
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalAttendanceDataLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalWorkticketDataLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;
import com.dwarfeng.jier.mh4w.core.model.struct.CountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.Counter;
import com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls;
import com.dwarfeng.jier.mh4w.core.model.struct.DataWithPerson;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultCountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.Person;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;
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
	 * @param coreConfigModel �ĺ�������ģ�͡�
	 * @param dateTypeModel ��������ģ�͡�
	 * @return ��ָ����ԭʼ����ת���ɵĿ������ݡ�
	 * @throws TransException ת�����̷����쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static AttendanceData transAttendanceData(OriginalAttendanceData rawData, ShiftModel shiftModel,
			CoreConfigModel coreConfigModel, DateTypeModel dateTypeModel) throws TransException{
		Objects.requireNonNull(rawData, "��ڲ��� rawData ����Ϊ null��");
		Objects.requireNonNull(shiftModel, "��ڲ��� shiftModel ����Ϊ null��");
		Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");
		Objects.requireNonNull(dateTypeModel, "��ڲ��� dateTypeModel ����Ϊ null��");

		try{
			double coefficient_shift = coreConfigModel.getShiftCoefficientCount();
			double coefficient_extra = coreConfigModel.getExtraCoefficientCount();
			double coefficient_weekend = coreConfigModel.getWeekendCoefficientCount();
			double coefficient_holiday = coreConfigModel.getHolidayCoefficientCount();
			
			String fileName = rawData.getFileName();
			int row = rawData.getRow();
			Person person = transPerson(rawData.getWorkNumber(), rawData.getDepartment(), rawData.getName());
			CountDate countDate = transCountDate(rawData.getDate());
			Shift shift = transShift(rawData.getShift(), shiftModel);
			TimeSection attendanceRecord = new TimeSection(0, 0);
			if(! rawData.getAttendanceRecord().equals("")){
				attendanceRecord = transTimeSection(rawData.getAttendanceRecord());
			}
			DateType dateType = dateTypeModel.getOrDefault(countDate, DateType.NORMAL);
			double equivalentWorkTime = 0;
			double originalWorkTime = 0;
			
			double shiftTime = 0;
			double extraShiftTime = 0;
			
			//���ݰ���е����ݼ��������ϰ���ϰ��ʱ�䡣
			for(TimeSection timeSection : shift.getShiftSections()){
				shiftTime += getCoincidenceTime(timeSection, attendanceRecord);
			}
			for(TimeSection timeSection : shift.getExtraShiftSections()){
				extraShiftTime += getCoincidenceTime(timeSection, attendanceRecord);
			}
			
			//���ԭʼ����ʱ��
			originalWorkTime = shiftTime + extraShiftTime;
			
			//����ָ�����������ͼ����Ч����ʱ�䡣
			switch (dateType) {
			case HOLIDAY:
				equivalentWorkTime = (shiftTime + extraShiftTime) * coefficient_holiday;
				break;
			case NORMAL:
				equivalentWorkTime = shiftTime * coefficient_shift + extraShiftTime * coefficient_extra;
				break;
			case WEEKEND:
				equivalentWorkTime = (shiftTime + extraShiftTime) * coefficient_weekend;
				break;
			}
			
			AttendanceData data = new DefaultAttendanceData(fileName, row, person, countDate, shift, attendanceRecord, dateType,
					equivalentWorkTime, originalWorkTime);
			
			return data;
		}catch (Exception e) {
			throw new TransException("�޷���ָ����ԭʼ��������ת��Ϊ��������", e);
		}
			
	}
	
	/**
	 * ��������ʱ��������غϵ�ʱ�䡣
	 * @param t1 ��һ��ʱ�����䡣
	 * @param t2 �ڶ���ʱ�����䡣
	 * @return ����ʱ��������غϵ�ʱ�䡣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static double getCoincidenceTime(TimeSection t1, TimeSection t2){
		Objects.requireNonNull(t1, "��ڲ��� t1 ����Ϊ null��");
		Objects.requireNonNull(t2, "��ڲ��� t2 ����Ϊ null��");

		double n1 = t1.getStart();
		double n2 = t1.getEnd();
		double n3 = t2.getStart();
		double n4 = t2.getEnd();
		
		//���ʱ�������Ƿ�û���غϵĲ���
		if(n2 < n3 || n4 < n1) return 0;
		
		double[] ns = new double[]{n1, n2, n3, n4	};
		Arrays.sort(ns);
		return ns[2] - ns[1];
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
			String fileName = rawData.getFileName();
			int row = rawData.getRow();
			Person person = transPerson(rawData.getWorkNumber(), rawData.getDepartment(), rawData.getName());
			Job job = rawData.getJob();
			double workticket;
			if(rawData.getWorkticket().equals("")){
				workticket = 0;
			}else {
				workticket = Double.parseDouble(rawData.getWorkticket());
			}
			
			return new DefaultWorkticketData(fileName, row, person, job, workticket);
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
	public static Person transPerson(String workNumber, String department, String name) throws TransException{
		Objects.requireNonNull(workNumber, "��ڲ��� workNumber ����Ϊ null��");
		Objects.requireNonNull(department, "��ڲ��� department ����Ϊ null��");
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");

		return new Person(workNumber, department, name);
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
			
			if(end < start) throw new IllegalArgumentException("ʱ������Ľ���ʱ�䲻��С����ʼʱ��");
			
			return new TimeSection(start, end);
		}catch (Exception e) {
			throw new TransException("�޷���ָ����ʱ�������ı�ת��Ϊ���" + string, e);
		}
	}
	
	/**
	 * ��Աһ���Լ�⡣
	 * @param attendanceDataModel ָ���Ŀ�������ģ�͡�
	 * @param workticketDataModel ָ���Ĺ�Ʊ����ģ�͡�
	 * @return ��һ�µ�Ա����Ϣ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static Set<DataFromXls> personConsistentCheck(DataListModel<AttendanceData> attendanceDataModel,
			DataListModel<WorkticketData> workticketDataModel){
		Objects.requireNonNull(attendanceDataModel, "��ڲ��� attendanceDataModel ����Ϊ null��");
		Objects.requireNonNull(workticketDataModel, "��ڲ��� workticketDataModel ����Ϊ null��");

		Map<String, Map<Person, PersonConsistentElement>> struct = new LinkedHashMap<>();
		//�����µļ��ϳ������ݣ��Է���ģ�͵������Ƶ����ռ�á�
		Set<PersonConsistentComplex> datas = new LinkedHashSet<>();
		
		//��ȡ����
		attendanceDataModel.getLock().readLock().lock();
		try{
			for(AttendanceData data : attendanceDataModel){
				datas.add(new PersonConsistentComplex(data, data));
			}
		}finally {
			attendanceDataModel.getLock().readLock().unlock();
		}
		workticketDataModel.getLock().readLock().lock();
		try{
			for(WorkticketData data : workticketDataModel){
				datas.add(new PersonConsistentComplex(data, data));
			}
		}finally{
			workticketDataModel.getLock().readLock().unlock();
		}
		
		//ѭ�����ÿ������
		for(PersonConsistentComplex data : datas){
			//���ݹ��Ż�ȡ struct �е�ֵ
			String workNumber = data.dataWithPerson.getPerson().getWorkNumber();
			if(! struct.containsKey(workNumber)){
				struct.put(workNumber, new LinkedHashMap<>());
			}
			Map<Person, PersonConsistentElement> personInfo = struct.get(workNumber);
			
			//��ֵӳ�������Ա����Ϣ��
			Person person = data.dataWithPerson.getPerson();
			if(! personInfo.containsKey(person)){
				personInfo.put(person, new PersonConsistentElement());
			}
			PersonConsistentElement element = personInfo.get(person);
			element.datas.add(data.dataFromXls);
			element.counter.count();
		}
		
		//���岻һ�¼��ϡ�
		Set<DataFromXls> unconsistents = new LinkedHashSet<>();
		
		//ѭ�����ÿ�����ţ��۲����Ƿ��ж����Ӧ��Ա����
		for(Map<Person, PersonConsistentElement> personMap : struct.values()){
			//���personMap����������1�����������˲�һ������
			if(personMap.size() > 1){
				PersonConsistentElement max = null;
				
				next:
				//����personMap������ֵ���ϣ����������ļ������벻һ�¼���
				for(PersonConsistentElement value : personMap.values()){
					if(max == null){
						max = value;
						continue next;
					}
					
					//�����ǰ�µ�ֵ�ļ���Ҫ�������ֵ�����ֵ��Ϊ���ֵ��ͬʱ
					//����һ�����ֵ�е�Ԫ�ع��벻һ�¼���
					//����Ѹ�ֵ��Ԫ�ع��벻һ�¼��ϡ�
					if(value.counter.getCounts() > max.counter.getCounts()){
						unconsistents.addAll(max.datas);
						max = value;
					}else{
						unconsistents.addAll(value.datas);
					}
				}
			}
		}
		
		return unconsistents;
	}
	
	private final static class PersonConsistentComplex{
		
		public final DataFromXls dataFromXls;
		public final DataWithPerson dataWithPerson;
		
		public PersonConsistentComplex(DataFromXls dataFromXls, DataWithPerson dataWithPerson) {
			super();
			this.dataFromXls = dataFromXls;
			this.dataWithPerson = dataWithPerson;
		}
		
	}
	
	private final static class PersonConsistentElement{
		public final Set<DataFromXls> datas = new LinkedHashSet<>();
		public final Counter counter = new Counter();
	}
	
	/**
	 * ��Աƥ���Լ�⡣
	 * @param attendanceDataModel ָ���Ŀ�������ģ�͡�
	 * @param workticketDataModel ָ���Ĺ�Ʊ����ģ�͡�
	 * @return ��ƥ���Ա����Ϣ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static Set<DataFromXls> personMatchCheck(DataListModel<AttendanceData> attendanceDataModel,
			DataListModel<WorkticketData> workticketDataModel){
		Objects.requireNonNull(attendanceDataModel, "��ڲ��� attendanceDataModel ����Ϊ null��");
		Objects.requireNonNull(workticketDataModel, "��ڲ��� workticketDataModel ����Ϊ null��");
		
		//�����µļ��ϳ������ݣ��Է���ģ�͵������Ƶ����ռ�á�
		Set<AttendanceData> attendanceDatas = new LinkedHashSet<>();
		Set<WorkticketData> workticketDatas = new LinkedHashSet<>();
		
		attendanceDataModel.getLock().readLock().lock();
		try{
			for(AttendanceData data : attendanceDataModel){
				attendanceDatas.add(data);
			}
		}finally {
			attendanceDataModel.getLock().readLock().unlock();
		}
		workticketDataModel.getLock().readLock().lock();
		try{
			for(WorkticketData data : workticketDataModel){
				workticketDatas.add(data);
			}
		}finally{
			workticketDataModel.getLock().readLock().unlock();
		}
		
		//����ӳ��ռ䡣
		Map<String, DataFromXls> attendanceMap = new LinkedHashMap<>();
		Map<String, DataFromXls> workticketMap = new LinkedHashMap<>();
		
		//��������Ϣ�͹�Ʊ��Ϣ��ӵ�ӳ��ռ���
		for(AttendanceData data : attendanceDatas){
			attendanceMap.put(data.getWorkNumber(), data);
		}
		for(WorkticketData data : workticketDatas){
			workticketMap.put(data.getWorkNumber(), data);
		}
		
		//���ٲ�ƥ�伯��
		Set<DataFromXls> unmatches = new LinkedHashSet<>();
		
		//�Ա�ӳ�乤���ļ������Ѳ�ƥ��ļ���Ӧ��ֵ��ӵ���ƥ�伯����
		for(AttendanceData data : attendanceDatas){
			if(! workticketMap.containsKey(data.getWorkNumber())){
				unmatches.add(data);
			}
		}
		for(WorkticketData data : workticketDatas){
			if(! attendanceMap.containsKey(data.getWorkNumber())){
				unmatches.add(data);
			}
		}
		
		return unmatches;
		
	}
	
	/**
	 * ͳ�����ݡ�
	 * @param attendanceDataModel ָ���Ŀ�������ģ�͡�
	 * @param workticketDataModel ָ���Ĺ�Ʊ����ģ�͡�
	 * @return ��ָ�����ݵõ���ͳ�ƽ����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public static Set<CountResult> countData(DataListModel<AttendanceData> attendanceDataModel,
			DataListModel<WorkticketData> workticketDataModel){
		Objects.requireNonNull(attendanceDataModel, "��ڲ��� attendanceDataModel ����Ϊ null��");
		Objects.requireNonNull(workticketDataModel, "��ڲ��� workticketDataModel ����Ϊ null��");
		
		//�Թ���Ϊ�����鵵����
		Map<String, Set<AttendanceData>> attendanceDataMap = new LinkedHashMap<>();
		Map<String, Set<WorkticketData>> workticketDataMap = new LinkedHashMap<>();
		attendanceDataModel.getLock().readLock().lock();
		try{
			for(AttendanceData data : attendanceDataModel){
				if(! attendanceDataMap.containsKey(data.getWorkNumber())){
					attendanceDataMap.put(data.getWorkNumber(), new LinkedHashSet<>());
				}
				attendanceDataMap.get(data.getWorkNumber()).add(data);
			}
		}finally {
			attendanceDataModel.getLock().readLock().unlock();
		}
		workticketDataModel.getLock().readLock().lock();
		try{
			for(WorkticketData data : workticketDataModel){
				if(! workticketDataMap.containsKey(data.getWorkNumber())){
					workticketDataMap.put(data.getWorkNumber(), new LinkedHashSet<>());
				}
				workticketDataMap.get(data.getWorkNumber()).add(data);
			}
		}finally{
			workticketDataModel.getLock().readLock().unlock();
		}
		
		//����ͳ�ƽ������
		Set<CountResult> countResults = new LinkedHashSet<>();
		
		//���ݹ鵵ӳ��ѭ��ͳ��ÿһ�������µ�ͳ�ƽ��
		for(String workNumber : attendanceDataMap.keySet()){
			Set<AttendanceData> attendanceDatas = attendanceDataMap.get(workNumber);
			Set<WorkticketData> workticketDatas = workticketDataMap.get(workNumber);
			
			//�Լ򵥵�ͳ����������ȡ
			Person person = null;
			double equivalentWorkTime = 0;
			double originalWorkTime = 0;
			double workticket = 0;
			Map<Job, Double> workticketMap = new LinkedHashMap<>();
			Map<Job, Double> workticketPercentMap = new LinkedHashMap<>();
			
			for(AttendanceData data : attendanceDatas){
				if(Objects.isNull(person)) person = data.getPerson();
				equivalentWorkTime += data.getEquivalentWorkTime();
				originalWorkTime += data.getOriginalWorkTime();
			}
			
			for(WorkticketData data : workticketDatas){
				if(Objects.isNull(person)) person = data.getPerson();
				workticket += data.getWorkticket();
				workticketMap.put(data.getJob(), data.getWorkticket());
			}
			
			for(Map.Entry<Job, Double> entry : workticketMap.entrySet()){
				workticketPercentMap.put(entry.getKey(), workticket == 0 ? 0 : entry.getValue() / workticket);
			}
			
			//���� �ϸ��ӵ�ͳ����  -  value
			double value = 0;
			if(originalWorkTime > 0){
				double b = workticket / originalWorkTime;
				double c = b * equivalentWorkTime;
				for(Map.Entry<Job, Double> entry : workticketPercentMap.entrySet()){
					value += c * entry.getValue() * entry.getKey().getValuePerHour();
				}
			}

			countResults.add(new DefaultCountResult(person, equivalentWorkTime, originalWorkTime, workticket, workticketMap, workticketPercentMap, value));
		}
		
		return countResults;
		
	}
	
	//��ֹ�ⲿʵ������
	private CountUtil(){}
}
