package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 不安全班次。
 * <p> 不安全班次与班次接口相比，速度要更慢，而且其返回方法可能会抛出异常。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface UnsafeShift {

    /**
     * 返回该不安全班次的名称。
     *
     * @return 该不安全班次的名称。
     * @throws ProcessException 过程异常。
     */
    String getName() throws ProcessException;

    /**
     * 获取正常上班的时间区间。
     *
     * @return 正常上班的时间区间。
     * @throws ProcessException 过程异常。
     */
    TimeSection[] getShiftSections() throws ProcessException;

    /**
     * 获取休息的时间区间。
     *
     * @return 休息的时间区间。
     * @throws ProcessException 过程异常。
     */
    TimeSection[] getRestSections() throws ProcessException;

    /**
     * 获取一阶段拖班时间。
     *
     * @return 拖班时间区间。
     * @throws ProcessException 过程异常。
     */
    TimeSection[] getExtraPhase1ShiftSections() throws ProcessException;

    /**
     * 获取二阶段拖班时间。
     *
     * @return 拖班时间区间。
     * @throws ProcessException 过程异常。
     */
    TimeSection[] getExtraPhase2ShiftSections() throws ProcessException;

    /**
     * 获取三阶段拖班时间。
     *
     * @return 拖班时间区间。
     * @throws ProcessException 过程异常。
     */
    TimeSection[] getExtraPhase3ShiftSections() throws ProcessException;

    /**
     * 获取四阶段拖班时间。
     *
     * @return 拖班时间区间。
     * @throws ProcessException 过程异常。
     */
    TimeSection[] getExtraPhase4ShiftSections() throws ProcessException;
}
