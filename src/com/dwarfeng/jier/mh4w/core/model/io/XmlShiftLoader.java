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
 * Xml 班次读取器。
 * <p> 通过 xml 读取不安全班次信息。
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public final class XmlShiftLoader extends StreamLoader<Set<UnsafeShift>> {

    /**
     * 新实例。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
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
        Objects.requireNonNull(unsafeShifts, "入口参数 unsafeShifts 不能为 null。");

        try {
            SAXReader reader = new SAXReader();

            Element root;
            try {
                root = reader.read(in).getRootElement();
            } finally {
                in.close();
            }

            /*
             * 根据 dom4j 的相关说明，此处转换是安全的。
             */
            @SuppressWarnings("unchecked")
            List<Element> shifts = (List<Element>) root.elements("shift");

            for (Element shift : shifts) {
                @SuppressWarnings("unchecked") // 根据 dom4j 的相关说明，此处转换是安全的。
                List<Element> shiftSectionElements = (List<Element>) shift.elements("shift_section");

                @SuppressWarnings("unchecked") // 根据 dom4j 的相关说明，此处转换是安全的。
                List<Element> restSectionElements = (List<Element>) shift.elements("rest_section");

                @SuppressWarnings("unchecked") // 根据 dom4j 的相关说明，此处转换是安全的。
                List<Element> extraPhase1ShiftSectionElements =
                        (List<Element>) shift.elements("extra_phase1_shift_section");

                @SuppressWarnings("unchecked") // 根据 dom4j 的相关说明，此处转换是安全的。
                List<Element> extraPhase2ShiftSectionElements =
                        (List<Element>) shift.elements("extra_phase2_shift_section");

                @SuppressWarnings("unchecked") // 根据 dom4j 的相关说明，此处转换是安全的。
                List<Element> extraPhase3ShiftSectionElements =
                        (List<Element>) shift.elements("extra_phase3_shift_section");

                @SuppressWarnings("unchecked") // 根据 dom4j 的相关说明，此处转换是安全的。
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
            throw new LoadFailedException("不安全工具历史读取器-无法向指定的不安全工具历史列表中读取流中的数据", e);
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
