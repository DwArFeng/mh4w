package com.dwarfeng.jier.mh4w.core.model.struct;

import java.util.Objects;

/**
 * Ĭ�ϰ�Ρ�
 * <p> ��νӿڵ�Ĭ��ʵ�֡�
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
     * ��ʵ����
     *
     * @param name                     ָ�������ơ�
     * @param shiftSections            ָ�����ϰ�ʱ������
     * @param restSections             ָ������Ϣʱ������
     * @param extraPhase1ShiftSections ָ�����ϰ�׶�һʱ������
     * @param extraPhase2ShiftSections ָ�����ϰ�׶ζ�ʱ������
     * @param extraPhase3ShiftSections ָ�����ϰ�׶���ʱ������
     * @param extraPhase4ShiftSections ָ�����ϰ�׶���ʱ������
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public DefaultShift(
            String name, TimeSection[] shiftSections, TimeSection[] restSections,
            TimeSection[] extraPhase1ShiftSections, TimeSection[] extraPhase2ShiftSections,
            TimeSection[] extraPhase3ShiftSections, TimeSection[] extraPhase4ShiftSections
    ) {
        Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
        Objects.requireNonNull(shiftSections, "��ڲ��� shiftSections ����Ϊ null��");
        Objects.requireNonNull(restSections, "��ڲ��� restSections ����Ϊ null��");
        Objects.requireNonNull(extraPhase1ShiftSections, "��ڲ��� extraPhase1ShiftSections ����Ϊ null��");
        Objects.requireNonNull(extraPhase2ShiftSections, "��ڲ��� extraPhase2ShiftSections ����Ϊ null��");
        Objects.requireNonNull(extraPhase3ShiftSections, "��ڲ��� extraPhase3ShiftSections ����Ϊ null��");
        Objects.requireNonNull(extraPhase4ShiftSections, "��ڲ��� extraPhase4ShiftSections ����Ϊ null��");

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
