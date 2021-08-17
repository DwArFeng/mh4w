package com.dwarfeng.jier.mh4w.core.model.cm;

import java.util.Locale;

/**
 * ��������ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface CoreConfigModel extends SyncConfigModel {

    /**
     * ��ȡ���ڱ����ʼ�С�
     *
     * @return ���ڱ����ʼ�С�
     */
    int getAttendanceStartRow();

    /**
     * ��ȡ���ڱ��в������ڵ��С�
     *
     * @return ���ڱ��в������ڵ��С�
     */
    int getAttendanceDepartmentColumn();

    /**
     * ��ȡ���ڱ��й������ڵ��С�
     *
     * @return ���ڱ��й������ڵ��С�
     */
    int getAttendanceWorkNumberColumn();

    /**
     * ��ȡ���ڱ����������ڵ��С�
     *
     * @return ���ڱ����������ڵ��С�
     */
    int getAttendanceNameColumn();

    /**
     * ��ȡ���ڱ����������ڵ��С�
     *
     * @return ���ڱ����������ڵ��С�
     */
    int getAttendanceDateColumn();

    /**
     * ��ȡ���ڱ��а�����ڵ��С�
     *
     * @return ���ڱ��а�����ڵ��С�
     */
    int getAttendanceShiftColumn();

    /**
     * ��ȡ���ڱ��м�¼���ڵ��С�
     *
     * @return ���ڱ��а�����ڵ���
     */
    int getAttendanceRecordColumn();

    /**
     * ��ȡ���ڱ��й�������һ��Ĺؼ��֡�
     *
     * @return ���ڱ��й�������һ��Ĺؼ��֡�
     */
    String getAttendanceOverdayKeyword();

    /**
     * ��ȡ��Ʊ�����ʼ�С�
     *
     * @return ��Ʊ�����ʼ�С�
     */
    int getWorkticketStartRow();

    /**
     * ��ȡ��Ʊ���в������ڵ��С�
     *
     * @return ��Ʊ���в������ڵ��С�
     */
    int getWorkticketDepartmentColumn();

    /**
     * ��ȡ��Ʊ���й������ڵ��С�
     *
     * @return ��Ʊ���й������ڵ��С�
     */
    int getWorkticketWorkNumberColumn();

    /**
     * ��ȡͳ��ʱ���õ������ϰ�Ĺ�ʱϵ����
     *
     * @return ͳ�����õ������ϰ�Ĺ�ʱϵ����
     */
    double getShiftCoefficientCount();

    /**
     * ��ȡͳ��ʱ���õ�һ�׶��ϰ�ʱ�Ĺ���ϵ����
     *
     * @return ͳ�����õ�һ�׶��ϰ�ʱ�Ĺ���ϵ����
     */
    double getExtraCoefficientPhase1Count();

    /**
     * ��ȡͳ��ʱ���õĶ��׶��ϰ�ʱ�Ĺ���ϵ����
     *
     * @return ͳ�����õĶ��׶��ϰ�ʱ�Ĺ���ϵ����
     */
    double getExtraCoefficientPhase2Count();

    /**
     * ��ȡͳ��ʱ���õ����׶��ϰ�ʱ�Ĺ���ϵ����
     *
     * @return ͳ�����õ����׶��ϰ�ʱ�Ĺ���ϵ����
     */
    double getExtraCoefficientPhase3Count();

    /**
     * ��ȡͳ��ʱ���õ��Ľ׶��ϰ�ʱ�Ĺ���ϵ����
     *
     * @return ͳ�����õ��Ľ׶��ϰ�ʱ�Ĺ���ϵ����
     */
    double getExtraCoefficientPhase4Count();

    /**
     * ��ȡͳ��ʱ���õ���ĩ�ϰ�Ĺ�ʱϵ����
     *
     * @return ͳ�����õ���ĩ�ϰ�Ĺ�ʱϵ����
     */
    double getWeekendCoefficientCount();

    /**
     * ��ȡͳ��ʱ���õĽڼ����ϰ�Ĺ�ʱϵ����
     *
     * @return ͳ�����õĽڼ����ϰ�Ĺ�ʱϵ����
     */
    double getHolidayCoefficientCount();

    /**
     * ��ȡ��¼�������Խӿڵĵ�ǰ���ԡ�
     *
     * @return ��¼�������Խӿڵ�ǰ�����ԡ�
     */
    Locale getLoggerMutilangLocale();

    /**
     * ��ȡ��ǩ�����Խӿڵĵ�ǰ���ԡ�
     *
     * @return ��ǩ�����Խӿڵĵ�ǰ���ԡ�
     */
    Locale getLabelMutilangLocale();

}
