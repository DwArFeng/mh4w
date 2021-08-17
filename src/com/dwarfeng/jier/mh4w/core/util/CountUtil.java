package com.dwarfeng.jier.mh4w.core.util;

import com.dwarfeng.dutil.basic.num.NumberUtil;
import com.dwarfeng.dutil.basic.num.unit.Time;
import com.dwarfeng.jier.mh4w.core.model.cm.*;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalAttendanceDataLoader;
import com.dwarfeng.jier.mh4w.core.model.io.XlsOriginalWorkticketDataLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * ��ͳ���йع��߷�����
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class CountUtil {

    /**
     * �� xls ����е�����ת�����е���š�
     *
     * @param column �����ơ�
     * @return �����ƶ�Ӧ����š�
     */
    public static int columnString2Int(String column) {
        Objects.requireNonNull(column, "��ڲ��� input ����Ϊ null��");
        column = column.toUpperCase();
        if (column.matches("[^A-Z]")) {
            throw new IllegalArgumentException("�ַ�ֻ�ܰ���A-Z");
        }

        int sum = 0;
        int one = Character.getNumericValue('A');

        for (int i = 0; i < column.length(); i++) {
            sum *= 26;
            char ch = column.charAt(i);
            sum += Character.getNumericValue(ch) - one + 1;
        }

        return sum - 1;
    }

    /**
     * ����һ���µ� xls ԭʼ�������ݶ�ȡ����
     *
     * @param fileSelectModel ָ�����ļ�ѡ��ģ�͡�
     * @param coreConfigModel ָ���ĺ�������ģ�͡�
     * @return ��ָ����ģ����ɵ� xls ԭʼ�������ݶ�ȡ����
     * @throws FileNotFoundException �ļ�δ�ҵ��쳣��
     * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
     */
    public static XlsOriginalAttendanceDataLoader newXlsOriginalAttendanceDataLoader(
            FileSelectModel fileSelectModel, CoreConfigModel coreConfigModel) throws FileNotFoundException {
        Objects.requireNonNull(fileSelectModel, "��ڲ��� fileSelectModel ����Ϊ null��");
        Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");

        InputStream in = new FileInputStream(fileSelectModel.getAttendanceFile());
        String fileName = fileSelectModel.getAttendanceFile().getName();
        int row_start = coreConfigModel.getAttendanceStartRow();
        int column_department = coreConfigModel.getAttendanceDepartmentColumn();
        int column_workNumber = coreConfigModel.getAttendanceWorkNumberColumn();
        int column_name = coreConfigModel.getAttendanceNameColumn();
        int column_date = coreConfigModel.getAttendanceDateColumn();
        int column_shift = coreConfigModel.getAttendanceShiftColumn();
        int column_record = coreConfigModel.getAttendanceRecordColumn();

        return new XlsOriginalAttendanceDataLoader(in, fileName, row_start, column_department,
                column_workNumber, column_name, column_date, column_shift, column_record);
    }

    /**
     * ����һ���µ� xls ԭʼ����Ʊ�ݶ�ȡ����
     *
     * @param fileSelectModel ָ�����ļ�ѡ��ģ�͡�
     * @param coreConfigModel ָ���ĺ�������ģ�͡�
     * @param jobModel        ����ģ�͡�
     * @return ��ָ����ģ����ɵ� xls ԭʼ�������ݶ�ȡ����
     * @throws FileNotFoundException �ļ�δ�ҵ��쳣��
     * @throws NullPointerException  ��ڲ���Ϊ <code>null</code>��
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
        int column_name = coreConfigModel.getAttendanceNameColumn();
        Job[] jobs = new Job[jobModel.size()];
        int i = 0;
        for (Job job : jobModel) {
            jobs[i++] = job;
        }

        return new XlsOriginalWorkticketDataLoader(in, fileName, row_start, column_department, column_workNumber,
                column_name, jobs);
    }

    /**
     * ��ʱ���ʽ���� 12:45��ת���ɸ�ʱ������������������Сʱ��
     *
     * @param string ָ����ʱ���ı���
     * @return ��ʱ���ʽ���� 12:45��ת���ɸ�ʱ������������������Сʱ��
     */
    public static double string2Hour(String string) {
        String[] strings = string.split(":");
        if (strings.length == 1) {
            return Integer.parseInt(string);
        } else if (strings.length == 2) {
            int hour = Integer.parseInt(strings[0]);
            int minute = Integer.parseInt(strings[1]);
            return NumberUtil.unitTrans((double) minute, Time.MIN, Time.HOR).doubleValue() + hour;
        } else {
            throw new IllegalArgumentException("ʱ�乤�� - ��Ч�Ĵ��������" + string);
        }
    }

    /**
     * ��ԭʼ��������ת���ɿ������ݡ�
     *
     * @param rawData         ָ����ԭʼ���ݡ�
     * @param shiftModel      ���ģ�͡�
     * @param coreConfigModel �ĺ�������ģ�͡�
     * @param dateTypeModel   ��������ģ�͡�
     * @return ��ָ����ԭʼ����ת���ɵĿ������ݡ�
     * @throws TransException       ת�����̷����쳣��
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static AttendanceData transAttendanceData(OriginalAttendanceData rawData, ShiftModel shiftModel,
                                                     CoreConfigModel coreConfigModel, DateTypeModel dateTypeModel) throws TransException {
        Objects.requireNonNull(rawData, "��ڲ��� rawData ����Ϊ null��");
        Objects.requireNonNull(shiftModel, "��ڲ��� shiftModel ����Ϊ null��");
        Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");
        Objects.requireNonNull(dateTypeModel, "��ڲ��� dateTypeModel ����Ϊ null��");

        try {
            double coefficientShift = coreConfigModel.getShiftCoefficientCount();
            double coefficientWeekend = coreConfigModel.getWeekendCoefficientCount();
            double coefficientHoliday = coreConfigModel.getHolidayCoefficientCount();
            double coefficientExtraPhase1 = coreConfigModel.getExtraCoefficientPhase1Count();
            double coefficientExtraPhase2 = coreConfigModel.getExtraCoefficientPhase2Count();
            double coefficientExtraPhase3 = coreConfigModel.getExtraCoefficientPhase3Count();
            double coefficientExtraPhase4 = coreConfigModel.getExtraCoefficientPhase4Count();
            String overdayKeyword = coreConfigModel.getAttendanceOverdayKeyword();

            String fileName = rawData.getFileName();
            int row = rawData.getRow();
            Person person = transPerson(rawData.getWorkNumber(), rawData.getDepartment(), rawData.getName());
            CountDate countDate = transCountDate(rawData.getDate());
            Shift shift = transShift(rawData.getShift(), shiftModel);
            TimeSection attendanceRecord = new TimeSection(0, 0);
            if (!rawData.getAttendanceRecord().equals("")) {
                attendanceRecord = transTimeSection(rawData.getAttendanceRecord(), overdayKeyword);
            }
            DateType dateType = dateTypeModel.getOrDefault(countDate, DateType.NORMAL);
            double equivalentWorkTime = 0;
            double originalWorkTime;

            double shiftTime = 0;
            double extraShiftTimePhase1 = 0;
            double extraShiftTimePhase2 = 0;
            double extraShiftTimePhase3 = 0;
            double extraShiftTimePhase4 = 0;

            // ���ݰ���е����ݼ��������ϰ���ϰ��ʱ�䡣
            for (TimeSection timeSection : shift.getShiftSections()) {
                shiftTime += getCoincidenceTime(timeSection, attendanceRecord);
            }
            for (TimeSection timeSection : shift.getExtraPhase1ShiftSections()) {
                extraShiftTimePhase1 += getCoincidenceTime(timeSection, attendanceRecord);
            }
            for (TimeSection timeSection : shift.getExtraPhase2ShiftSections()) {
                extraShiftTimePhase2 += getCoincidenceTime(timeSection, attendanceRecord);
            }
            for (TimeSection timeSection : shift.getExtraPhase3ShiftSections()) {
                extraShiftTimePhase3 += getCoincidenceTime(timeSection, attendanceRecord);
            }
            for (TimeSection timeSection : shift.getExtraPhase4ShiftSections()) {
                extraShiftTimePhase4 += getCoincidenceTime(timeSection, attendanceRecord);
            }

            // ���ԭʼ����ʱ��
            originalWorkTime = shiftTime + extraShiftTimePhase1 + extraShiftTimePhase2 +
                    extraShiftTimePhase3 + extraShiftTimePhase4;

            // ����ָ�����������ͼ����Ч����ʱ�䡣
            switch (dateType) {
                case HOLIDAY:
                    equivalentWorkTime = (shiftTime + extraShiftTimePhase1 + extraShiftTimePhase2 +
                            extraShiftTimePhase3 + extraShiftTimePhase4) * coefficientHoliday;
                    break;
                case NORMAL:
                    equivalentWorkTime = shiftTime * coefficientShift + extraShiftTimePhase1 * coefficientExtraPhase1
                            + extraShiftTimePhase2 * coefficientExtraPhase2
                            + extraShiftTimePhase3 * coefficientExtraPhase3
                            + extraShiftTimePhase4 * coefficientExtraPhase4;
                    break;
                case WEEKEND:
                    equivalentWorkTime = (shiftTime + extraShiftTimePhase1 + extraShiftTimePhase2 +
                            extraShiftTimePhase3 + extraShiftTimePhase4) * coefficientWeekend;
                    break;
            }

            // ���ؽ����
            return new DefaultAttendanceData(fileName, row, person, countDate, shift, attendanceRecord, dateType,
                    equivalentWorkTime, originalWorkTime);
        } catch (Exception e) {
            throw new TransException("�޷���ָ����ԭʼ��������ת��Ϊ��������", e);
        }

    }

    /**
     * ��������ʱ��������غϵ�ʱ�䡣
     *
     * @param t1 ��һ��ʱ�����䡣
     * @param t2 �ڶ���ʱ�����䡣
     * @return ����ʱ��������غϵ�ʱ�䡣
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static double getCoincidenceTime(TimeSection t1, TimeSection t2) {
        Objects.requireNonNull(t1, "��ڲ��� t1 ����Ϊ null��");
        Objects.requireNonNull(t2, "��ڲ��� t2 ����Ϊ null��");

        double n1 = t1.getStart();
        double n2 = t1.getEnd();
        double n3 = t2.getStart();
        double n4 = t2.getEnd();

        // ���ʱ�������Ƿ�û���غϵĲ���
        if (n2 < n3 || n4 < n1) return 0;

        double[] ns = new double[]{n1, n2, n3, n4};
        Arrays.sort(ns);
        return ns[2] - ns[1];
    }

    /**
     * ��ԭʼ��Ʊ����ת���ɹ�Ʊ���ݡ�
     *
     * @param rawData ָ����ԭʼ��Ʊ���ݡ�
     * @return ��ָ����ԭʼ��Ʊ����ת���ɵĹ�Ʊ���ݡ�
     * @throws TransException       ת�����̷����쳣��
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static WorkticketData transWorkticketData(OriginalWorkticketData rawData) throws TransException {
        Objects.requireNonNull(rawData, "��ڲ��� rawData ����Ϊ null��");
        try {
            String fileName = rawData.getFileName();
            int row = rawData.getRow();
            Person person = transPerson(rawData.getWorkNumber(), rawData.getDepartment(), rawData.getName());
            Job job = rawData.getJob();
            double workticket;
            if (rawData.getWorkticket().equals("")) {
                workticket = 0;
            } else {
                workticket = Double.parseDouble(rawData.getWorkticket());
            }

            return new DefaultWorkticketData(fileName, row, person, job, workticket);
        } catch (Exception e) {
            throw new TransException("�޷���ָ����ԭʼ��Ʊ����ת��Ϊ��Ʊ����", e);
        }
    }

    /**
     * ��ָ�����ַ�ת��ΪԱ����
     *
     * @param workNumber �����ַ�����
     * @param department �����ַ�����
     * @param name       �����ַ�����
     * @return ָ�����ַ�ת����Ա����
     * @throws TransException       ת���쳣��
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static Person transPerson(String workNumber, String department, String name) throws TransException {
        try {
            Objects.requireNonNull(workNumber, "��ڲ��� workNumber ����Ϊ null��");
            Objects.requireNonNull(department, "��ڲ��� department ����Ϊ null��");
            Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");

            return new Person(workNumber, department, name);
        } catch (Exception e) {
            throw new TransException(e);
        }
    }

    /**
     * ��ָ�����ַ���ת��Ϊ���ڡ�
     *
     * @param dateString ָ���������ַ�����
     * @return ��ָ�����ַ���ת�����������ڡ�
     * @throws TransException       ת���쳣��
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static CountDate transCountDate(String dateString) throws TransException {
        Objects.requireNonNull(dateString, "��ڲ��� dateString ����Ϊ null��");

        try {
            String[] strings = dateString.split("-");
            int year = Integer.parseInt(strings[0]);
            int month = Integer.parseInt(strings[1]);
            int day = Integer.parseInt(strings[2]);
            return new CountDate(year, month, day);
        } catch (Exception e) {
            throw new TransException("�޷���ָ�����ַ�ת��Ϊ�ַ��� : " + dateString, e);
        }
    }

    /**
     * ��ָ�����ַ���ת��Ϊ��Ρ�
     *
     * @param shift      ָ�����ַ�����
     * @param shiftModel ָ���İ��ģ�͡�
     * @return ��ָ�����ַ���ת�����ɵİ�Ρ�
     * @throws TransException       ת���쳣��
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static Shift transShift(String shift, ShiftModel shiftModel) throws TransException {
        Objects.requireNonNull(shiftModel, "��ڲ��� shiftModel ����Ϊ null��");

        try {
            for (Shift referenceShift : shiftModel) {
                if (referenceShift.getName().equals(shift)) {
                    return referenceShift;
                }
            }
            throw new NoSuchElementException();
        } catch (Exception e) {
            throw new TransException("�޷���ָ���İ���ı�ת��Ϊ���" + shift, e);
        }
    }

    /**
     * ��ָ�����ַ���ת��Ϊʱ�����䡣
     *
     * @param string         ָ�����ַ�����
     * @param overdayKeyword ����ʱ�䳬��һ��Ĺؼ��֡�
     * @return ��ָ�����ַ���ת�����ɵ�ʱ�����䡣
     * @throws TransException       ת���쳣��
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static TimeSection transTimeSection(String string, String overdayKeyword) throws TransException {
        Objects.requireNonNull(string, "��ڲ��� string ����Ϊ null��");
        Objects.requireNonNull(overdayKeyword, "��ڲ��� overdayKeyword ����Ϊ null��");

        try {
            String[] strings = string.split("-");

            boolean fFlag = false;
            boolean lFlag = false;

            if (!overdayKeyword.equals("")) {
                int fi;
                int li;

                if ((fi = strings[0].indexOf(overdayKeyword)) >= 0) {
                    fFlag = true;
                    strings[0] = strings[0].substring(0, fi) + strings[0].substring(fi + overdayKeyword.length());
                }

                if ((li = strings[1].indexOf(overdayKeyword)) >= 0) {
                    lFlag = true;
                    strings[1] = strings[1].substring(0, li) + strings[1].substring(li + overdayKeyword.length());
                }
            }

            String[] f = strings[0].split(":");
            String[] l = strings[1].split(":");

            double start = Double.parseDouble(f[0]) + NumberUtil.unitTrans(Double.parseDouble(f[1]), Time.MIN, Time.HOR).doubleValue();
            double end = Double.parseDouble(l[0]) + NumberUtil.unitTrans(Double.parseDouble(l[1]), Time.MIN, Time.HOR).doubleValue();

            if (fFlag) start += 24.0;
            if (lFlag) end += 24.0;

            if (end < start) throw new IllegalArgumentException("ʱ������Ľ���ʱ�䲻��С����ʼʱ��");

            return new TimeSection(start, end);
        } catch (Exception e) {
            throw new TransException("�޷���ָ����ʱ�������ı�ת��Ϊ���" + string, e);
        }
    }

    /**
     * ��Աһ���Լ�⡣
     *
     * @param attendanceDataModel ָ���Ŀ�������ģ�͡�
     * @param workticketDataModel ָ���Ĺ�Ʊ����ģ�͡�
     * @return ��һ�µ�Ա����Ϣ��
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static Set<DataFromXls> personConsistentCheck(DataListModel<AttendanceData> attendanceDataModel,
                                                         DataListModel<WorkticketData> workticketDataModel) {
        Objects.requireNonNull(attendanceDataModel, "��ڲ��� attendanceDataModel ����Ϊ null��");
        Objects.requireNonNull(workticketDataModel, "��ڲ��� workticketDataModel ����Ϊ null��");

        Map<String, Map<Person, PersonConsistentElement>> struct = new LinkedHashMap<>();
        // �����µļ��ϳ������ݣ��Է���ģ�͵������Ƶ����ռ�á�
        Set<PersonConsistentComplex> personConsistentComplexSet = new LinkedHashSet<>();

        // ��ȡ����
        attendanceDataModel.getLock().readLock().lock();
        try {
            for (AttendanceData data : attendanceDataModel) {
                personConsistentComplexSet.add(new PersonConsistentComplex(data, data));
            }
        } finally {
            attendanceDataModel.getLock().readLock().unlock();
        }
        workticketDataModel.getLock().readLock().lock();
        try {
            for (WorkticketData data : workticketDataModel) {
                personConsistentComplexSet.add(new PersonConsistentComplex(data, data));
            }
        } finally {
            workticketDataModel.getLock().readLock().unlock();
        }

        // ѭ�����ÿ������
        for (PersonConsistentComplex data : personConsistentComplexSet) {
            // ���ݹ��Ż�ȡ struct �е�ֵ
            String workNumber = data.dataWithPerson.getPerson().getWorkNumber();
            if (!struct.containsKey(workNumber)) {
                struct.put(workNumber, new LinkedHashMap<>());
            }
            Map<Person, PersonConsistentElement> personInfo = struct.get(workNumber);

            // ��ֵӳ�������Ա����Ϣ��
            Person person = data.dataWithPerson.getPerson();
            if (!personInfo.containsKey(person)) {
                personInfo.put(person, new PersonConsistentElement());
            }
            PersonConsistentElement element = personInfo.get(person);
            element.dataFromXlsSet.add(data.dataFromXls);
            element.counter.count();
        }

        // ���岻һ�¼��ϡ�
        Set<DataFromXls> inconsistentSet = new LinkedHashSet<>();

        // ѭ�����ÿ�����ţ��۲����Ƿ��ж����Ӧ��Ա����
        for (Map<Person, PersonConsistentElement> personMap : struct.values()) {
            // ���personMap����������1�����������˲�һ������
            if (personMap.size() > 1) {
                PersonConsistentElement max = null;

                // ����personMap������ֵ���ϣ����������ļ������벻һ�¼���
                for (PersonConsistentElement value : personMap.values()) {
                    if (max == null) {
                        max = value;
                        continue;
                    }

                    // �����ǰ�µ�ֵ�ļ���Ҫ�������ֵ�����ֵ��Ϊ���ֵ��ͬʱ
                    // ����һ�����ֵ�е�Ԫ�ع��벻һ�¼���
                    // ����Ѹ�ֵ��Ԫ�ع��벻һ�¼��ϡ�
                    if (value.counter.getCounts() > max.counter.getCounts()) {
                        inconsistentSet.addAll(max.dataFromXlsSet);
                        max = value;
                    } else {
                        inconsistentSet.addAll(value.dataFromXlsSet);
                    }
                }
            }
        }

        return inconsistentSet;
    }

    private final static class PersonConsistentComplex {

        public final DataFromXls dataFromXls;
        public final DataWithPerson dataWithPerson;

        public PersonConsistentComplex(DataFromXls dataFromXls, DataWithPerson dataWithPerson) {
            super();
            this.dataFromXls = dataFromXls;
            this.dataWithPerson = dataWithPerson;
        }

    }

    private final static class PersonConsistentElement {
        public final Set<DataFromXls> dataFromXlsSet = new LinkedHashSet<>();
        public final Counter counter = new Counter();
    }

    /**
     * ��Աƥ���Լ�⡣
     *
     * @param attendanceDataModel ָ���Ŀ�������ģ�͡�
     * @param workticketDataModel ָ���Ĺ�Ʊ����ģ�͡�
     * @return ��ƥ���Ա����Ϣ��
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static Set<DataFromXls> personMatchCheck(DataListModel<AttendanceData> attendanceDataModel,
                                                    DataListModel<WorkticketData> workticketDataModel) {
        Objects.requireNonNull(attendanceDataModel, "��ڲ��� attendanceDataModel ����Ϊ null��");
        Objects.requireNonNull(workticketDataModel, "��ڲ��� workticketDataModel ����Ϊ null��");

        // �����µļ��ϳ������ݣ��Է���ģ�͵������Ƶ����ռ�á�
        Set<AttendanceData> attendanceDataSet = new LinkedHashSet<>();
        Set<WorkticketData> workticketDataSet = new LinkedHashSet<>();

        attendanceDataModel.getLock().readLock().lock();
        try {
            attendanceDataSet.addAll(attendanceDataModel);
        } finally {
            attendanceDataModel.getLock().readLock().unlock();
        }
        workticketDataModel.getLock().readLock().lock();
        try {
            workticketDataSet.addAll(workticketDataModel);
        } finally {
            workticketDataModel.getLock().readLock().unlock();
        }

        // ����ӳ��ռ䡣
        Map<String, DataFromXls> attendanceMap = new LinkedHashMap<>();
        Map<String, DataFromXls> workticketMap = new LinkedHashMap<>();

        // ��������Ϣ�͹�Ʊ��Ϣ��ӵ�ӳ��ռ���
        for (AttendanceData data : attendanceDataSet) {
            attendanceMap.put(data.getWorkNumber(), data);
        }
        for (WorkticketData data : workticketDataSet) {
            workticketMap.put(data.getWorkNumber(), data);
        }

        // ���ٲ�ƥ�伯��
        Set<DataFromXls> unmatchedSet = new LinkedHashSet<>();

        // �Ա�ӳ�乤���ļ������Ѳ�ƥ��ļ���Ӧ��ֵ��ӵ���ƥ�伯����
        for (AttendanceData data : attendanceDataSet) {
            if (!workticketMap.containsKey(data.getWorkNumber())) {
                unmatchedSet.add(data);
            }
        }
        for (WorkticketData data : workticketDataSet) {
            if (!attendanceMap.containsKey(data.getWorkNumber())) {
                unmatchedSet.add(data);
            }
        }

        return unmatchedSet;
    }

    /**
     * ͳ�����ݡ�
     *
     * @param attendanceDataModel   ָ���Ŀ�������ģ�͡�
     * @param workticketDataModel   ָ���Ĺ�Ʊ����ģ�͡�
     * @param attendanceOffsetModel ָ���Ŀ��ڲ���ģ�͡�
     * @return ��ָ�����ݵõ���ͳ�ƽ����
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public static Set<CountResult> countData(DataListModel<AttendanceData> attendanceDataModel,
                                             DataListModel<WorkticketData> workticketDataModel, DataListModel<AttendanceOffset> attendanceOffsetModel) {
        Objects.requireNonNull(attendanceDataModel, "��ڲ��� attendanceDataModel ����Ϊ null��");
        Objects.requireNonNull(workticketDataModel, "��ڲ��� workticketDataModel ����Ϊ null��");
        Objects.requireNonNull(attendanceOffsetModel, "��ڲ��� attendanceOffsetModel ����Ϊ null��");

        // �Թ���Ϊ�����鵵����
        Map<String, Set<AttendanceData>> attendanceDataMap = new LinkedHashMap<>();
        Map<String, Set<WorkticketData>> workticketDataMap = new LinkedHashMap<>();
        attendanceDataModel.getLock().readLock().lock();
        try {
            for (AttendanceData data : attendanceDataModel) {
                if (!attendanceDataMap.containsKey(data.getWorkNumber())) {
                    attendanceDataMap.put(data.getWorkNumber(), new LinkedHashSet<>());
                }
                attendanceDataMap.get(data.getWorkNumber()).add(data);
            }
        } finally {
            attendanceDataModel.getLock().readLock().unlock();
        }
        workticketDataModel.getLock().readLock().lock();
        try {
            for (WorkticketData data : workticketDataModel) {
                if (!workticketDataMap.containsKey(data.getWorkNumber())) {
                    workticketDataMap.put(data.getWorkNumber(), new LinkedHashSet<>());
                }
                workticketDataMap.get(data.getWorkNumber()).add(data);
            }
        } finally {
            workticketDataModel.getLock().readLock().unlock();
        }

        // �Թ���Ϊ�����鵵���ڲ���
        Map<String, Set<AttendanceOffset>> attendanceOffsetMap = new LinkedHashMap<>();

        attendanceOffsetModel.getLock().readLock().lock();
        try {
            for (AttendanceOffset attendanceOffset : attendanceOffsetModel) {
                if (!attendanceOffsetMap.containsKey(attendanceOffset.getWorkNumber())) {
                    attendanceOffsetMap.put(attendanceOffset.getWorkNumber(), new LinkedHashSet<>());
                }
                attendanceOffsetMap.get(attendanceOffset.getWorkNumber()).add(attendanceOffset);
            }
        } finally {
            attendanceOffsetModel.getLock().readLock().unlock();
        }

        // ����ͳ�ƽ������
        Set<CountResult> countResults = new LinkedHashSet<>();

        // ���ݹ鵵ӳ��ѭ��ͳ��ÿһ�������µ�ͳ�ƽ��
        for (String workNumber : attendanceDataMap.keySet()) {
            Set<AttendanceData> attendanceDataSet = attendanceDataMap.get(workNumber);
            Set<WorkticketData> workticketDataSet = workticketDataMap.get(workNumber);

            // �Լ򵥵�ͳ����������ȡ
            Person person = null;
            double equivalentWorkTime = 0;
            double equivalentWorkTimeOffset = 0;
            double originalWorkTime = 0;
            double workticket = 0;
            double equivalentWorkticket = 0;
            double amplifyCoefficient = 0;
            Map<Job, Double> workticketMap = new LinkedHashMap<>();
            Map<Job, Double> equivalentWorkticketMap = new LinkedHashMap<>();

            for (AttendanceData data : attendanceDataSet) {
                if (Objects.isNull(person)) person = data.getPerson();
                equivalentWorkTime += data.getEquivalentWorkTime();
                originalWorkTime += data.getOriginalWorkTime();
            }

            for (WorkticketData data : workticketDataSet) {
                if (Objects.isNull(person)) person = data.getPerson();
                workticket += data.getWorkticket();
                workticketMap.put(data.getJob(), data.getWorkticket() + workticketMap.getOrDefault(data.getJob(), 0.0));
            }

            if (attendanceOffsetMap.containsKey(workNumber)) {
                for (AttendanceOffset attendanceOffset : attendanceOffsetMap.get(workNumber)) {
                    equivalentWorkTimeOffset += attendanceOffset.getValue();
                }
            }

            if (originalWorkTime != 0) {
                amplifyCoefficient = (equivalentWorkTime + equivalentWorkTimeOffset) / originalWorkTime;
                equivalentWorkticket = workticket * amplifyCoefficient;
            }

            for (Map.Entry<Job, Double> entry : workticketMap.entrySet()) {
                equivalentWorkticketMap.put(entry.getKey(), entry.getValue() * amplifyCoefficient);
            }

            countResults.add(new DefaultCountResult(person, equivalentWorkTime,
                    equivalentWorkTimeOffset, amplifyCoefficient, originalWorkTime, workticket, equivalentWorkticket,
                    workticketMap, equivalentWorkticketMap));
        }

        return countResults;

    }

    // ��ֹ�ⲿʵ������
    private CountUtil() {
    }
}
