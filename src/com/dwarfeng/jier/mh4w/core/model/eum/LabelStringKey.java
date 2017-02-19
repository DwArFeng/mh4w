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

	AttrFrame_1(new DefaultName("AttrFrame.1")),
	AttrFrame_2(new DefaultName("AttrFrame.2")),
	AttrFrame_3(new DefaultName("AttrFrame.3")),
	AttrFrame_4(new DefaultName("AttrFrame.4")),

	JShiftPanel_1(new DefaultName("JShiftPanel.1")),
	JShiftPanel_2(new DefaultName("JShiftPanel.2")),

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
