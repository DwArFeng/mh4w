package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * ��Ρ�
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface Shift extends Name {

    /**
     * ��ȡ�����ϰ��ʱ�����䡣
     *
     * @return �����ϰ��ʱ�����䡣
     */
    TimeSection[] getShiftSections();

    /**
     * ��ȡ��Ϣ��ʱ�����䡣
     *
     * @return ��Ϣ��ʱ�����䡣
     */
    TimeSection[] getRestSections();

    /**
     * ��ȡһ�׶��ϰ�ʱ�䡣
     *
     * @return �ϰ�ʱ�����䡣
     */
    TimeSection[] getExtraPhase1ShiftSections();

    /**
     * ��ȡ���׶��ϰ�ʱ�䡣
     *
     * @return �ϰ�ʱ�����䡣
     */
    TimeSection[] getExtraPhase2ShiftSections();

    /**
     * ��ȡ���׶��ϰ�ʱ�䡣
     *
     * @return �ϰ�ʱ�����䡣
     */
    TimeSection[] getExtraPhase3ShiftSections();

    /**
     * ��ȡ�Ľ׶��ϰ�ʱ�䡣
     *
     * @return �ϰ�ʱ�����䡣
     */
    TimeSection[] getExtraPhase4ShiftSections();
}
