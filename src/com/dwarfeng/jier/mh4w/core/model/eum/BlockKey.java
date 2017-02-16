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
	LOAD_LIB(new DefaultName("load_lib")),
	CLOSING(new DefaultName("closing")),
	SELECT_ATTENDANCE_FILE(new DefaultName("select_attendance_file")),
	SELECT_WORKTICKET_FILE(new DefaultName("select_workticket_file")),
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
