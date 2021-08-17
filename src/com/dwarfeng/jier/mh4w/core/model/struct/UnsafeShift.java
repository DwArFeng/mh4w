package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * ����ȫ��Ρ�
 * <p> ����ȫ������νӿ���ȣ��ٶ�Ҫ�����������䷵�ط������ܻ��׳��쳣��
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface UnsafeShift {

    /**
     * ���ظò���ȫ��ε����ơ�
     *
     * @return �ò���ȫ��ε����ơ�
     * @throws ProcessException �����쳣��
     */
    String getName() throws ProcessException;

    /**
     * ��ȡ�����ϰ��ʱ�����䡣
     *
     * @return �����ϰ��ʱ�����䡣
     * @throws ProcessException �����쳣��
     */
    TimeSection[] getShiftSections() throws ProcessException;

    /**
     * ��ȡ��Ϣ��ʱ�����䡣
     *
     * @return ��Ϣ��ʱ�����䡣
     * @throws ProcessException �����쳣��
     */
    TimeSection[] getRestSections() throws ProcessException;

    /**
     * ��ȡһ�׶��ϰ�ʱ�䡣
     *
     * @return �ϰ�ʱ�����䡣
     * @throws ProcessException �����쳣��
     */
    TimeSection[] getExtraPhase1ShiftSections() throws ProcessException;

    /**
     * ��ȡ���׶��ϰ�ʱ�䡣
     *
     * @return �ϰ�ʱ�����䡣
     * @throws ProcessException �����쳣��
     */
    TimeSection[] getExtraPhase2ShiftSections() throws ProcessException;

    /**
     * ��ȡ���׶��ϰ�ʱ�䡣
     *
     * @return �ϰ�ʱ�����䡣
     * @throws ProcessException �����쳣��
     */
    TimeSection[] getExtraPhase3ShiftSections() throws ProcessException;

    /**
     * ��ȡ�Ľ׶��ϰ�ʱ�䡣
     *
     * @return �ϰ�ʱ�����䡣
     * @throws ProcessException �����쳣��
     */
    TimeSection[] getExtraPhase4ShiftSections() throws ProcessException;
}
