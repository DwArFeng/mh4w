package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultName;

/**
 * ������ַ�������
 * <p> ���ַ�����ö�ټ�¼�˳��������õ��������ַ����ļ���
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
	
	DetailFrame_1(new DefaultName("DetailFrame.1")),
	DetailFrame_2(new DefaultName("DetailFrame.2")),
	DetailFrame_3(new DefaultName("DetailFrame.3")),

	AttrFrame_1(new DefaultName("AttrFrame.1")),
	AttrFrame_2(new DefaultName("AttrFrame.2")),
	AttrFrame_3(new DefaultName("AttrFrame.3")),
	AttrFrame_4(new DefaultName("AttrFrame.4")),
	AttrFrame_5(new DefaultName("AttrFrame.5")),

	JShiftPanel_1(new DefaultName("JShiftPanel.1")),
	JShiftPanel_2(new DefaultName("JShiftPanel.2")),

	JCoreConfigPanel_1(new DefaultName("JCoreConfigPanel.1")),
	JCoreConfigPanel_2(new DefaultName("JCoreConfigPanel.2")),

	CoreConfig_1(new DefaultName("CoreConfig.1")),
	CoreConfig_2(new DefaultName("CoreConfig.2")),
	CoreConfig_3(new DefaultName("CoreConfig.3")),
	CoreConfig_4(new DefaultName("CoreConfig.4")),
	CoreConfig_5(new DefaultName("CoreConfig.5")),
	CoreConfig_6(new DefaultName("CoreConfig.6")),
	CoreConfig_7(new DefaultName("CoreConfig.7")),
	CoreConfig_8(new DefaultName("CoreConfig.8")),
	CoreConfig_9(new DefaultName("CoreConfig.9")),
	CoreConfig_10(new DefaultName("CoreConfig.10")),
	CoreConfig_11(new DefaultName("CoreConfig.11")),
	CoreConfig_12(new DefaultName("CoreConfig.12")),
	CoreConfig_13(new DefaultName("CoreConfig.13")),

	JOriginalAttendanceDataPanel_1(new DefaultName("JOriginalAttendanceDataPanel.1")),
	JOriginalAttendanceDataPanel_2(new DefaultName("JOriginalAttendanceDataPanel.2")),
	JOriginalAttendanceDataPanel_3(new DefaultName("JOriginalAttendanceDataPanel.3")),
	JOriginalAttendanceDataPanel_4(new DefaultName("JOriginalAttendanceDataPanel.4")),
	JOriginalAttendanceDataPanel_5(new DefaultName("JOriginalAttendanceDataPanel.5")),
	JOriginalAttendanceDataPanel_6(new DefaultName("JOriginalAttendanceDataPanel.6")),
	
	JJobPanel_1(new DefaultName("JJobPanel.1")),
	JJobPanel_2(new DefaultName("JJobPanel.2")),
	JJobPanel_3(new DefaultName("JJobPanel.3")),
	
	JOriginalWorkticketDataPanel_1(new DefaultName("JOriginalWorkticketDataPanel.1")),
	JOriginalWorkticketDataPanel_2(new DefaultName("JOriginalWorkticketDataPanel.2")),
	JOriginalWorkticketDataPanel_3(new DefaultName("JOriginalWorkticketDataPanel.3")),
	JOriginalWorkticketDataPanel_4(new DefaultName("JOriginalWorkticketDataPanel.4")),
	JOriginalWorkticketDataPanel_5(new DefaultName("JOriginalWorkticketDataPanel.5")),

	FailFrame_1(new DefaultName("FailFrame.1")),
	FailFrame_2(new DefaultName("FailFrame.2")),
	FailFrame_3(new DefaultName("FailFrame.3")),
	FailFrame_4(new DefaultName("FailFrame.4")),

	DateTypeFrame_1(new DefaultName("DateTypeFrame.1")),
	DateTypeFrame_2(new DefaultName("DateTypeFrame.2")),
	DateTypeFrame_3(new DefaultName("DateTypeFrame.3")),
	DateTypeFrame_4(new DefaultName("DateTypeFrame.4")),
	DateTypeFrame_5(new DefaultName("DateTypeFrame.5")),
	DateTypeFrame_6(new DefaultName("DateTypeFrame.6")),
	DateTypeFrame_7(new DefaultName("DateTypeFrame.7")),
	DateTypeFrame_8(new DefaultName("DateTypeFrame.8")),
	DateTypeFrame_9(new DefaultName("DateTypeFrame.9")),
	DateTypeFrame_10(new DefaultName("DateTypeFrame.10")),

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
