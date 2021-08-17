package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.jier.mh4w.core.util.CountUtil;

/**
 * 默认不安全班次。
 * <p> 不安全班次的默认实现。
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
     * 新实例。
     *
     * @param name                     指定的名称。
     * @param shiftSections            指定的工作时间区间。
     * @param restSections             指定的休息区间。
     * @param extraPhase1ShiftSections 指定的拖班阶段一时间。
     * @param extraPhase2ShiftSections 指定的拖班阶段二时间。
     * @param extraPhase3ShiftSections 指定的拖班阶段三时间。
     * @param extraPhase4ShiftSections 指定的拖班阶段四时间。
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
            throw new ProcessException("默认不安全班次 - 无法解析班次中的名称", e);
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
            throw new ProcessException("默认不安全班次 - 无法解析班次中的工作时间区域", e);
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
            throw new ProcessException("默认不安全班次 - 无法解析班次中的休息时间区域", e);
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
            throw new ProcessException("默认不安全班次 - 无法解析班次中的拖班阶段一时间区域", e);
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
            throw new ProcessException("默认不安全班次 - 无法解析班次中的拖班阶段二时间区域", e);
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
            throw new ProcessException("默认不安全班次 - 无法解析班次中的拖班阶段三时间区域", e);
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
            throw new ProcessException("默认不安全班次 - 无法解析班次中的拖班阶段四时间区域", e);
        }
    }

    private TimeSection stringArray2TimeSection(String[] strings) {
        double start = CountUtil.string2Hour(strings[0]);
        double end = CountUtil.string2Hour(strings[1]);
        return new TimeSection(start, end);
    }
}
