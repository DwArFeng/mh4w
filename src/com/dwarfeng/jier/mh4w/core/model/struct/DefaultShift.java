package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * 默认班次。
 * <p> 班次接口的默认实现。
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class DefaultShift implements Shift {

    private final String name;
    private final TimeSection[] shiftSections;
    private final TimeSection[] restSections;
    private final TimeSection[] extraPhase1ShiftSections;
    private final TimeSection[] extraPhase2ShiftSections;
    private final TimeSection[] extraPhase3ShiftSections;
    private final TimeSection[] extraPhase4ShiftSections;

    /**
     * 新实例。
     *
     * @param name                     指定的名称。
     * @param shiftSections            指定的上班时间区域。
     * @param restSections             指定的休息时间区域。
     * @param extraPhase1ShiftSections 指定的拖班阶段一时间区域。
     * @param extraPhase2ShiftSections 指定的拖班阶段二时间区域。
     * @param extraPhase3ShiftSections 指定的拖班阶段三时间区域。
     * @param extraPhase4ShiftSections 指定的拖班阶段四时间区域。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultShift(
            String name, TimeSection[] shiftSections, TimeSection[] restSections,
            TimeSection[] extraPhase1ShiftSections, TimeSection[] extraPhase2ShiftSections,
            TimeSection[] extraPhase3ShiftSections, TimeSection[] extraPhase4ShiftSections
    ) {
        Objects.requireNonNull(name, "入口参数 name 不能为 null。");
        Objects.requireNonNull(shiftSections, "入口参数 shiftSections 不能为 null。");
        Objects.requireNonNull(restSections, "入口参数 restSections 不能为 null。");
        Objects.requireNonNull(extraPhase1ShiftSections, "入口参数 extraPhase1ShiftSections 不能为 null。");
        Objects.requireNonNull(extraPhase2ShiftSections, "入口参数 extraPhase2ShiftSections 不能为 null。");
        Objects.requireNonNull(extraPhase3ShiftSections, "入口参数 extraPhase3ShiftSections 不能为 null。");
        Objects.requireNonNull(extraPhase4ShiftSections, "入口参数 extraPhase4ShiftSections 不能为 null。");

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
     * @see com.dwarfeng.dutil.basic.str.Name#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.struct.Shift#getShiftSections()
     */
    @Override
    public TimeSection[] getShiftSections() {
        return shiftSections;
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.struct.Shift#getRestSections()
     */
    @Override
    public TimeSection[] getRestSections() {
        return restSections;
    }

    @Override
    public TimeSection[] getExtraPhase1ShiftSections() {
        return extraPhase1ShiftSections;
    }

    @Override
    public TimeSection[] getExtraPhase2ShiftSections() {
        return extraPhase2ShiftSections;
    }

    @Override
    public TimeSection[] getExtraPhase3ShiftSections() {
        return extraPhase3ShiftSections;
    }

    @Override
    public TimeSection[] getExtraPhase4ShiftSections() {
        return extraPhase4ShiftSections;
    }
}
