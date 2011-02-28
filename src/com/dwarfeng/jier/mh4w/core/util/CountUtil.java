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
 *与统计有关工具方法。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class CountUtil {

	/**
	 * 将 xls 表格中的列名转换成列的序号。
	 * @param column 列名称。
	 * @return 列名称对应的序号。
	 */
	public static int columnString2Int(String column) {
		Objects.requireNonNull(column, "入口参数 input 不能为 null。");
		column = column.toUpperCase();
		if(column.matches("[^A-Z]")){
			throw new IllegalArgumentException("字符只能包含A-Z");
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
	 * 返回一个新的 xls 原始出勤数据读取器。
	 * @param fileSelectModel 指定的文件选择模型。
	 * @param coreConfigModel 指定的核心配置模型。
	 * @return 由指定的模型组成的 xls 原始出勤数据读取器。
	 * @throws FileNotFoundException 文件未找到异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static XlsOriginalAttendanceDataLoader newXlsOriginalAttendanceDataLoader(
			FileSelectModel fileSelectModel, CoreConfigModel coreConfigModel) throws FileNotFoundException{
		Objects.requireNonNull(fileSelectModel, "入口参数 fileSelectModel 不能为 null。");
		Objects.requireNonNull(coreConfigModel, "入口参数 coreConfigModel 不能为 null。");

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
	 * 返回一个新的 xls 原始出工票据读取器。
	 * @param fileSelectModel 指定的文件选择模型。
	 * @param coreConfigModel 指定的核心配置模型。
	 * @param jobModel 工作模型。
	 * @return 由指定的模型组成的 xls 原始出勤数据读取器。
	 * @throws FileNotFoundException 文件未找到异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static XlsOriginalWorkticketDataLoader newXlsOriginalWorkticketDataLoader(FileSelectModel fileSelectModel,
			CoreConfigModel coreConfigModel, JobModel jobModel) throws FileNotFoundException {
		Objects.requireNonNull(fileSelectModel, "入口参数 fileSelectModel 不能为 null。");
		Objects.requireNonNull(coreConfigModel, "入口参数 coreConfigModel 不能为 null。");
		Objects.requireNonNull(jobModel, "入口参数 jobModel 不能为 null。");

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
	 * 将时间格式（如 12:45）转换成该时间相对于零点所经过的小时。
	 * @param string 指定的时间文本。
	 * @return 将时间格式（如 12:45）转换成该时间相对于零点所经过的小时。
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
			throw new IllegalArgumentException("时间工具 - 无效的传入参数：" + string);
		}
	}
	
	/**
	 * 将原始考勤数据转换成考勤数据。
	 * @param rawData 指定的原始数据。
	 * @param shiftModel 班次模型。
	 * @param coreConfigModel 的核心配置模型。
	 * @param dateTypeModel 日期类型模型。
	 * @return 由指定的原始数据转化成的考勤数据。
	 * @throws TransException 转化过程发生异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static AttendanceData transAttendanceData(OriginalAttendanceData rawData, ShiftModel shiftModel,
			CoreConfigModel coreConfigModel, DateTypeModel dateTypeModel) throws TransException{
		Objects.requireNonNull(rawData, "入口参数 rawData 不能为 null。");
		Objects.requireNonNull(shiftModel, "入口参数 shiftModel 不能为 null。");
		Objects.requireNonNull(coreConfigModel, "入口参数 coreConfigModel 不能为 null。");
		Objects.requireNonNull(dateTypeModel, "入口参数 dateTypeModel 不能为 null。");

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
			
			//根据班次中的数据计算正常上班和拖班的时间。
			for(TimeSection timeSection : shift.getShiftSections()){
				shiftTime += getCoincidenceTime(timeSection, attendanceRecord);
			}
			for(TimeSection timeSection : shift.getExtraShiftSections()){
				extraShiftTime += getCoincidenceTime(timeSection, attendanceRecord);
			}
			
			//算出原始工作时间
			originalWorkTime = shiftTime + extraShiftTime;
			
			//根据指定的日期类型计算等效工作时间。
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
			throw new TransException("无法将指定的原始考勤数据转化为考勤数据", e);
		}
			
	}
	
	/**
	 * 返回两个时间区间的重合的时间。
	 * @param t1 第一个时间区间。
	 * @param t2 第二个时间区间。
	 * @return 两个时间区间的重合的时间。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static double getCoincidenceTime(TimeSection t1, TimeSection t2){
		Objects.requireNonNull(t1, "入口参数 t1 不能为 null。");
		Objects.requireNonNull(t2, "入口参数 t2 不能为 null。");

		double n1 = t1.getStart();
		double n2 = t1.getEnd();
		double n3 = t2.getStart();
		double n4 = t2.getEnd();
		
		//检测时间区间是否没有重合的部分
		if(n2 < n3 || n4 < n1) return 0;
		
		double[] ns = new double[]{n1, n2, n3, n4	};
		Arrays.sort(ns);
		return ns[2] - ns[1];
	}
	
	/**
	 * 将原始工票数据转换成工票数据。
	 * @param rawData 指定的原始工票数据。
	 * @return 由指定的原始工票数据转换成的工票数据。
	 * @throws TransException 转化过程发生异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static WorkticketData transWorkticketData(OriginalWorkticketData rawData) throws TransException{
		Objects.requireNonNull(rawData, "入口参数 rawData 不能为 null。");
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
			throw new TransException("无法将指定的原始工票数据转化为工票数据", e);
		}
	}
	
	/**
	 * 将指定的字符转换为员工。
	 * @param workNumber 工号字符串。
	 * @param department 部门字符串。
	 * @param name 姓名字符串。
	 * @return 指定的字符转换的员工。
	 * @throws TransException 转换异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static Person transPerson(String workNumber, String department, String name) throws TransException{
		Objects.requireNonNull(workNumber, "入口参数 workNumber 不能为 null。");
		Objects.requireNonNull(department, "入口参数 department 不能为 null。");
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");

		return new Person(workNumber, department, name);
	}
	
	/**
	 * 将指定的字符串转换为日期。
	 * @param dateString 指定的日期字符串。
	 * @return 由指定的字符串转化而来的日期。
	 * @throws TransException 转换异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static CountDate transCountDate(String dateString) throws TransException{
		Objects.requireNonNull(dateString, "入口参数 dateString 不能为 null。");

		try{
			String[] strs = dateString.split("-");
			int year = Integer.parseInt(strs[0]);
			int month = Integer.parseInt(strs[1]);
			int day = Integer.parseInt(strs[2]);
			return new CountDate(year, month, day);
		}catch (Exception e) {
			throw new TransException("无法将指定的字符转换为字符串 : " + dateString, e);
		}
	}
	
	/**
	 * 将指定的字符串转化为班次。
	 * @param shift 指定的字符串。
	 * @param shiftModel 指定的班次模型。
	 * @return 由指定的字符串转化而成的班次。
	 * @throws TransException 转换异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static Shift transShift(String shift, ShiftModel shiftModel) throws TransException{
		Objects.requireNonNull(shiftModel, "入口参数 shiftModel 不能为 null。");
		
		try{
			for(Shift referanceShift : shiftModel){
				if(referanceShift.getName().equals(shift)){
					return referanceShift;
				}
			}
			throw new NoSuchElementException();
		}catch (Exception e) {
			throw new TransException("无法将指定的班次文本转换为班次" + shift, e);
		}
	}
	
	/**
	 * 将指定的字符串转换为时间区间。
	 * @param string 指定的字符串。
	 * @return 由指定的字符串转化而成的时间区间。
	 * @throws TransException 转换异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static TimeSection transTimeSection(String string) throws TransException{
		try{
			String[] strs = string.split("-");
			String[] f = strs[0].split(":");
			String[] l = strs[1].split(":");
			
			double start = Double.parseDouble(f[0]) + NumberUtil.unitTrans(Double.parseDouble(f[1]), Time.MIN, Time.HOR).doubleValue();
			double end = Double.parseDouble(l[0]) + NumberUtil.unitTrans(Double.parseDouble(l[1]), Time.MIN, Time.HOR).doubleValue();
			
			if(end < start) throw new IllegalArgumentException("时间区间的结束时间不能小于起始时间");
			
			return new TimeSection(start, end);
		}catch (Exception e) {
			throw new TransException("无法将指定的时间区间文本转换为班次" + string, e);
		}
	}
	
	/**
	 * 人员一致性监测。
	 * @param attendanceDataModel 指定的考勤数据模型。
	 * @param workticketDataModel 指定的工票数据模型。
	 * @return 不一致的员工信息。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static Set<DataFromXls> personConsistentCheck(DataListModel<AttendanceData> attendanceDataModel,
			DataListModel<WorkticketData> workticketDataModel){
		Objects.requireNonNull(attendanceDataModel, "入口参数 attendanceDataModel 不能为 null。");
		Objects.requireNonNull(workticketDataModel, "入口参数 workticketDataModel 不能为 null。");

		Map<String, Map<Person, PersonConsistentElement>> struct = new LinkedHashMap<>();
		//开辟新的集合持有数据，以防对模型的锁造成频繁的占用。
		Set<PersonConsistentComplex> datas = new LinkedHashSet<>();
		
		//读取数据
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
		
		//循环检测每个数据
		for(PersonConsistentComplex data : datas){
			//根据工号获取 struct 中的值
			String workNumber = data.dataWithPerson.getPerson().getWorkNumber();
			if(! struct.containsKey(workNumber)){
				struct.put(workNumber, new LinkedHashMap<>());
			}
			Map<Person, PersonConsistentElement> personInfo = struct.get(workNumber);
			
			//向值映射中添加员工信息。
			Person person = data.dataWithPerson.getPerson();
			if(! personInfo.containsKey(person)){
				personInfo.put(person, new PersonConsistentElement());
			}
			PersonConsistentElement element = personInfo.get(person);
			element.datas.add(data.dataFromXls);
			element.counter.count();
		}
		
		//定义不一致集合。
		Set<DataFromXls> unconsistents = new LinkedHashSet<>();
		
		//循环检查每个工号，观察其是否含有多个对应的员工。
		for(Map<Person, PersonConsistentElement> personMap : struct.values()){
			//如果personMap的数量大于1，则代表产生了不一致现象。
			if(personMap.size() > 1){
				PersonConsistentElement max = null;
				
				next:
				//遍历personMap的所有值集合，将不是最大的计数归入不一致集合
				for(PersonConsistentElement value : personMap.values()){
					if(max == null){
						max = value;
						continue next;
					}
					
					//如果当前新的值的计数要大于最大值，则该值变为最大值，同时
					//把上一个最大值中的元素归入不一致集合
					//否则把该值的元素归入不一致集合。
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
	 * 人员匹配性检测。
	 * @param attendanceDataModel 指定的考勤数据模型。
	 * @param workticketDataModel 指定的工票数据模型。
	 * @return 不匹配的员工信息。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static Set<DataFromXls> personMatchCheck(DataListModel<AttendanceData> attendanceDataModel,
			DataListModel<WorkticketData> workticketDataModel){
		Objects.requireNonNull(attendanceDataModel, "入口参数 attendanceDataModel 不能为 null。");
		Objects.requireNonNull(workticketDataModel, "入口参数 workticketDataModel 不能为 null。");
		
		//开辟新的集合持有数据，以防对模型的锁造成频繁的占用。
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
		
		//开辟映射空间。
		Map<String, DataFromXls> attendanceMap = new LinkedHashMap<>();
		Map<String, DataFromXls> workticketMap = new LinkedHashMap<>();
		
		//将出勤信息和工票信息添加到映射空间中
		for(AttendanceData data : attendanceDatas){
			attendanceMap.put(data.getWorkNumber(), data);
		}
		for(WorkticketData data : workticketDatas){
			workticketMap.put(data.getWorkNumber(), data);
		}
		
		//开辟不匹配集合
		Set<DataFromXls> unmatches = new LinkedHashSet<>();
		
		//对比映射工件的键，并把不匹配的键对应的值添加到不匹配集合中
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
	 * 统计数据。
	 * @param attendanceDataModel 指定的考勤数据模型。
	 * @param workticketDataModel 指定的工票数据模型。
	 * @return 由指定数据得到的统计结果。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public static Set<CountResult> countData(DataListModel<AttendanceData> attendanceDataModel,
			DataListModel<WorkticketData> workticketDataModel){
		Objects.requireNonNull(attendanceDataModel, "入口参数 attendanceDataModel 不能为 null。");
		Objects.requireNonNull(workticketDataModel, "入口参数 workticketDataModel 不能为 null。");
		
		//以工号为主键归档数据
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
		
		//开辟统计结果集合
		Set<CountResult> countResults = new LinkedHashSet<>();
		
		//根据归档映射循环统计每一个工号下的统计结果
		for(String workNumber : attendanceDataMap.keySet()){
			Set<AttendanceData> attendanceDatas = attendanceDataMap.get(workNumber);
			Set<WorkticketData> workticketDatas = workticketDataMap.get(workNumber);
			
			//对简单的统计量进行提取
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
			
			//计算 较复杂的统计量  -  value
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
	
	//禁止外部实例化。
	private CountUtil(){}
}
