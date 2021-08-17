package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 班次。
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface Shift extends Name {

    /**
     * 获取正常上班的时间区间。
     *
     * @return 正常上班的时间区间。
     */
    TimeSection[] getShiftSections();

    /**
     * 获取休息的时间区间。
     *
     * @return 休息的时间区间。
     */
    TimeSection[] getRestSections();

    /**
     * 获取一阶段拖班时间。
     *
     * @return 拖班时间区间。
     */
    TimeSection[] getExtraPhase1ShiftSections();

    /**
     * 获取二阶段拖班时间。
     *
     * @return 拖班时间区间。
     */
    TimeSection[] getExtraPhase2ShiftSections();

    /**
     * 获取三阶段拖班时间。
     *
     * @return 拖班时间区间。
     */
    TimeSection[] getExtraPhase3ShiftSections();

    /**
     * 获取四阶段拖班时间。
     *
     * @return 拖班时间区间。
     */
    TimeSection[] getExtraPhase4ShiftSections();
}
