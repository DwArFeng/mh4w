package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.util.CountUtil;

/**
 * Ĭ�ϲ���ȫ��Ρ�
 * <p> ����ȫ��ε�Ĭ��ʵ�֡�
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class DefaultUnsafeShift implements UnsafeShift {

    private final String name;
    private final String[][] shiftSections;
    private final String[][] restSections;
    private final String[][] extraPhase1ShiftSections;
    private final String[][] extraPhase2ShiftSections;
    private final String[][] extraPhase3ShiftSections;
    private final String[][] extraPhase4ShiftSections;

    /**
     * ��ʵ����
     *
     * @param name                     ָ�������ơ�
     * @param shiftSections            ָ���Ĺ���ʱ�����䡣
     * @param restSections             ָ������Ϣ���䡣
     * @param extraPhase1ShiftSections ָ�����ϰ�׶�һʱ�䡣
     * @param extraPhase2ShiftSections ָ�����ϰ�׶ζ�ʱ�䡣
     * @param extraPhase3ShiftSections ָ�����ϰ�׶���ʱ�䡣
     * @param extraPhase4ShiftSections ָ�����ϰ�׶���ʱ�䡣
     */
    public DefaultUnsafeShift(
            String name, String[][] shiftSections, String[][] restSections, String[][] extraPhase1ShiftSections,
            String[][] extraPhase2ShiftSections, String[][] extraPhase3ShiftSections,
            String[][] extraPhase4ShiftSections
    ) {
        this.name = name;
        this.shiftSections = shiftSections;
        this.restSections = restSections;
        this.extraPhase1ShiftSections = extraPhase1ShiftSections;
        this.extraPhase2ShiftSections = extraPhase2ShiftSections;
        this.extraPhase3ShiftSections = extraPhase3ShiftSections;
        this.extraPhase4ShiftSections = extraPhase4ShiftSections;
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift#getName()
     */
    @Override
    public String getName() throws ProcessException {
        try {
            return name;
        } catch (Exception e) {
            throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е�����", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift#getShiftSections()
     */
    @Override
    public TimeSection[] getShiftSections() throws ProcessException {
        try {
            TimeSection[] timeSections = new TimeSection[shiftSections.length];
            for (int i = 0; i < timeSections.length; i++) {
                timeSections[i] = stringArray2TimeSection(shiftSections[i]);
            }
            return timeSections;
        } catch (Exception e) {
            throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������еĹ���ʱ������", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift#getRestSections()
     */
    @Override
    public TimeSection[] getRestSections() throws ProcessException {
        try {
            TimeSection[] timeSections = new TimeSection[restSections.length];
            for (int i = 0; i < timeSections.length; i++) {
                timeSections[i] = stringArray2TimeSection(restSections[i]);
            }
            return timeSections;
        } catch (Exception e) {
            throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е���Ϣʱ������", e);
        }
    }

    @Override
    public TimeSection[] getExtraPhase1ShiftSections() throws ProcessException {
        try {
            TimeSection[] timeSections = new TimeSection[extraPhase1ShiftSections.length];
            for (int i = 0; i < timeSections.length; i++) {
                timeSections[i] = stringArray2TimeSection(extraPhase1ShiftSections[i]);
            }
            return timeSections;
        } catch (Exception e) {
            throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е��ϰ�׶�һʱ������", e);
        }
    }

    @Override
    public TimeSection[] getExtraPhase2ShiftSections() throws ProcessException {
        try {
            TimeSection[] timeSections = new TimeSection[extraPhase2ShiftSections.length];
            for (int i = 0; i < timeSections.length; i++) {
                timeSections[i] = stringArray2TimeSection(extraPhase2ShiftSections[i]);
            }
            return timeSections;
        } catch (Exception e) {
            throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е��ϰ�׶ζ�ʱ������", e);
        }
    }

    @Override
    public TimeSection[] getExtraPhase3ShiftSections() throws ProcessException {
        try {
            TimeSection[] timeSections = new TimeSection[extraPhase3ShiftSections.length];
            for (int i = 0; i < timeSections.length; i++) {
                timeSections[i] = stringArray2TimeSection(extraPhase3ShiftSections[i]);
            }
            return timeSections;
        } catch (Exception e) {
            throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е��ϰ�׶���ʱ������", e);
        }
    }

    @Override
    public TimeSection[] getExtraPhase4ShiftSections() throws ProcessException {
        try {
            TimeSection[] timeSections = new TimeSection[extraPhase4ShiftSections.length];
            for (int i = 0; i < timeSections.length; i++) {
                timeSections[i] = stringArray2TimeSection(extraPhase4ShiftSections[i]);
            }
            return timeSections;
        } catch (Exception e) {
            throw new ProcessException("Ĭ�ϲ���ȫ��� - �޷���������е��ϰ�׶���ʱ������", e);
        }
    }

    private TimeSection stringArray2TimeSection(String[] strings) {
        double start = CountUtil.string2Hour(strings[0]);
        double end = CountUtil.string2Hour(strings[1]);
        return new TimeSection(start, end);
    }
}
