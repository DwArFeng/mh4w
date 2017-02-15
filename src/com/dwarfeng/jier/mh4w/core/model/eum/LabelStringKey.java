package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultName;

/**
 * 程序的字符串键。
 * <p> 该字符串键枚举记录了程序中所用到的所有字符串的键。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum LabelStringKey implements Name{
	
	MainFrame_1(new DefaultName("MainFrame.1")),
	MainFrame_2(new DefaultName("MainFrame.2")),
	MainFrame_3(new DefaultName("MainFrame.3")),
	MainFrame_4(new DefaultName("MainFrame.4")),
	MainFrame_5(new DefaultName("MainFrame.5")),
	MainFrame_6(new DefaultName("MainFrame.6")),
	MainFrame_7(new DefaultName("MainFrame.7")),

	JTpconsole_1(new DefaultName("JTpconsole.1")),
	JTpconsole_2(new DefaultName("JTpconsole.2")),
	JTpconsole_3(new DefaultName("JTpconsole.3")),
	
	JToolRuntimePanel_1(new DefaultName("JToolRuntimePanel.1")),
	JToolRuntimePanel_2(new DefaultName("JToolRuntimePanel.2")),
	JToolRuntimePanel_3(new DefaultName("JToolRuntimePanel.3")),
	JToolRuntimePanel_4(new DefaultName("JToolRuntimePanel.4")),

	JToolHistoryPanel_1(new DefaultName("JToolHistoryPanel.1")),
	JToolHistoryPanel_2(new DefaultName("JToolHistoryPanel.2")),
	JToolHistoryPanel_3(new DefaultName("JToolHistoryPanel.3")),
	JToolHistoryPanel_4(new DefaultName("JToolHistoryPanel.4")),

	;

	private Name name;
	
	private LabelStringKey(Name name) {
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
