package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultName;

public enum ImageKey implements Name {

    IMG_LOAD_FAILED(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/img_load_failed.png")),
    XLS_GREEN(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/xls_green.png")),
    XLS_RED(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/xls_red.png")),
    DETAIL_GREEN(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/detail_green.png")),
    DETAIL_RED(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/detail_red.png")),
    DETAIL_YELLOW(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/detail_yellow.png")),
    DETAIL_PURPLE(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/detail_purple.png")),
    DETAIL_GRAY(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/detail_gray.png")),
    RESET_BLUE(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/reset_blue.png")),
    CALENDAR_BLUE(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/calendar_blue.png")),
    ATTR_BLUE(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/attr_blue.png")),
    SHIFT_SECTION(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/shift_section.png")),
    EXTRA_PHASE1_SHIFT_SECTION(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/extra_phase1_shift_section.png")),
    EXTRA_PHASE2_SHIFT_SECTION(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/extra_phase2_shift_section.png")),
    EXTRA_PHASE3_SHIFT_SECTION(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/extra_phase3_shift_section.png")),
    EXTRA_PHASE4_SHIFT_SECTION(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/extra_phase4_shift_section.png")),
    REST_SECTION(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/rest_section.png")),
    PROGRAM_ICON(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/program_icon.png")),
    ARROW(new DefaultName("/com/dwarfeng/jier/mh4w/resource/image/arrow.png")),
    ;

    private final Name name;

    ImageKey(Name name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.dutil.basic.str.Name#getName()
     */
    @Override
    public String getName() {
        return name.getName();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return name.getName();
    }
}
