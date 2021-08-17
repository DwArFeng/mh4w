package com.dwarfeng.jier.mh4w.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultUnsafeShift;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeShift;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Xml ��ζ�ȡ����
 * <p> ͨ�� xml ��ȡ����ȫ�����Ϣ��
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class XmlShiftLoader extends StreamLoader<Set<UnsafeShift>> {

    /**
     * ��ʵ����
     *
     * @param in ָ������������
     * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
     */
    public XmlShiftLoader(InputStream in) {
        super(in);
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.dutil.basic.prog.Loader#load(java.lang.Object)
     */
    @Override
    public void load(Set<UnsafeShift> unsafeShifts) throws LoadFailedException {
        Objects.requireNonNull(unsafeShifts, "��ڲ��� unsafeShifts ����Ϊ null��");

        try {
            SAXReader reader = new SAXReader();

            Element root;
            try {
                root = reader.read(in).getRootElement();
            } finally {
                in.close();
            }

            /*
             * ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
             */
            @SuppressWarnings("unchecked")
            List<Element> shifts = (List<Element>) root.elements("shift");

            for (Element shift : shifts) {
                @SuppressWarnings("unchecked") // ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
                List<Element> shiftSectionElements = (List<Element>) shift.elements("shift_section");

                @SuppressWarnings("unchecked") // ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
                List<Element> restSectionElements = (List<Element>) shift.elements("rest_section");

                @SuppressWarnings("unchecked") // ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
                List<Element> extraPhase1ShiftSectionElements =
                        (List<Element>) shift.elements("extra_phase1_shift_section");

                @SuppressWarnings("unchecked") // ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
                List<Element> extraPhase2ShiftSectionElements =
                        (List<Element>) shift.elements("extra_phase2_shift_section");

                @SuppressWarnings("unchecked") // ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
                List<Element> extraPhase3ShiftSectionElements =
                        (List<Element>) shift.elements("extra_phase3_shift_section");

                @SuppressWarnings("unchecked") // ���� dom4j �����˵�����˴�ת���ǰ�ȫ�ġ�
                List<Element> extraPhase4ShiftSectionElements =
                        (List<Element>) shift.elements("extra_phase4_shift_section");

                String[][] shiftSections = fileUnsafeShiftSections(shiftSectionElements);
                String[][] restSections = fileUnsafeShiftSections(restSectionElements);
                String[][] extraPhase1ShiftSections = fileUnsafeShiftSections(extraPhase1ShiftSectionElements);
                String[][] extraPhase2ShiftSections = fileUnsafeShiftSections(extraPhase2ShiftSectionElements);
                String[][] extraPhase3ShiftSections = fileUnsafeShiftSections(extraPhase3ShiftSectionElements);
                String[][] extraPhase4ShiftSections = fileUnsafeShiftSections(extraPhase4ShiftSectionElements);

                String name = shift.element("name").attributeValue("value");

                UnsafeShift unsafeShift = new DefaultUnsafeShift(
                        name, shiftSections, restSections, extraPhase1ShiftSections, extraPhase2ShiftSections,
                        extraPhase3ShiftSections, extraPhase4ShiftSections
                );
                unsafeShifts.add(unsafeShift);
            }
        } catch (Exception e) {
            throw new LoadFailedException("����ȫ������ʷ��ȡ��-�޷���ָ���Ĳ���ȫ������ʷ�б��ж�ȡ���е�����", e);
        }
    }

    private String[][] fileUnsafeShiftSections(List<Element> shiftSectionElements) {
        String[][] shiftSections;
        shiftSections = new String[shiftSectionElements.size()][2];
        for (int i = 0; i < shiftSections.length; i++) {
            String start = shiftSectionElements.get(i).attributeValue("start");
            String end = shiftSectionElements.get(i).attributeValue("end");
            shiftSections[i][0] = start;
            shiftSections[i][1] = end;
        }
        return shiftSections;
    }
}
