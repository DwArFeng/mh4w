package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultName;

/**
 * ×èµ²¼ü¡£
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum BlockKey implements Name{
	
	INITIALIZE(new DefaultName("initialize")),
	CLOSING(new DefaultName("closing")),
	SELECT_ATTENDANCE_FILE(new DefaultName("select_attendance_file")),
	SELECT_WORKTICKET_FILE(new DefaultName("select_workticket_file")),
	RESET_COUNT(new DefaultName("reset_count")),
	SHOW_DETAIL(new DefaultName("show_detail")),
	HIDE_DETAIL(new DefaultName("hide_detail")),
	COUNT(new DefaultName("count")),
	SHOW_ATTR_FRAME(new DefaultName("show_attr_frame")),
	HIDE_ATTR_FRAME(new DefaultName("hide_attr_frame")),
	RELOAD_ATTR(new DefaultName("reload_attr")),
	HIDE_FAIL_FRAME(new DefaultName("hide_fail_frame")),

	;

	private Name name;
	
	private BlockKey(Name name) {
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
