package com.dwarfeng.jier.mh4w.core.model.eum;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultName;

/**
 * 记录器文本键。
 * <p> 该键枚举用于实现记录器的多语言。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum LoggerStringKey implements Name{

	FinishedFlowTaker_1(new DefaultName("FinishedFlowTaker.1")),
	FinishedFlowTaker_2(new DefaultName("FinishedFlowTaker.2")),
	FinishedFlowTaker_3(new DefaultName("FinishedFlowTaker.3")),
	FinishedFlowTaker_4(new DefaultName("FinishedFlowTaker.4")),

	Mh4w_FlowProvider_1(new DefaultName("Mh4w.FlowProvider.1")),
	Mh4w_FlowProvider_2(new DefaultName("Mh4w.FlowProvider.2")),
	Mh4w_FlowProvider_3(new DefaultName("Mh4w.FlowProvider.3")),
	Mh4w_FlowProvider_4(new DefaultName("Mh4w.FlowProvider.4")),
	Mh4w_FlowProvider_5(new DefaultName("Mh4w.FlowProvider.5")),
	Mh4w_FlowProvider_6(new DefaultName("Mh4w.FlowProvider.6")),
	Mh4w_FlowProvider_7(new DefaultName("Mh4w.FlowProvider.7")),
	Mh4w_FlowProvider_8(new DefaultName("Mh4w.FlowProvider.8")),
	Mh4w_FlowProvider_9(new DefaultName("Mh4w.FlowProvider.9")),
	Mh4w_FlowProvider_10(new DefaultName("Mh4w.FlowProvider.10")),

	Update_LoggerMutilang_1(new DefaultName("Update.LoggerMutilang.1")),
	Update_LabelMutilang_1(new DefaultName("Update.LabelMutilang.1")),
	Update_Logger_1(new DefaultName("Update.Logger.1")),

	Mh4w_Exitor_1(new DefaultName("Mh4w.Exitor.1")),
	Mh4w_Exitor_2(new DefaultName("Mh4w.Exitor.2")),
	Mh4w_Exitor_3(new DefaultName("Mh4w.Exitor.3")),
	Mh4w_Exitor_4(new DefaultName("Mh4w.Exitor.4")),
	Mh4w_Exitor_5(new DefaultName("Mh4w.Exitor.5")),
	Mh4w_Exitor_6(new DefaultName("Mh4w.Exitor.6")),
	Mh4w_Exitor_7(new DefaultName("Mh4w.Exitor.7")),
	Mh4w_Exitor_8(new DefaultName("Mh4w.Exitor.8")),
	Mh4w_Exitor_9(new DefaultName("Mh4w.Exitor.9")),
	Mh4w_Exitor_10(new DefaultName("Mh4w.Exitor.10")),
	Mh4w_Exitor_11(new DefaultName("Mh4w.Exitor.11")),
	Mh4w_Exitor_12(new DefaultName("Mh4w.Exitor.12")),
	Mh4w_Exitor_13(new DefaultName("Mh4w.Exitor.13")),
	Mh4w_Exitor_14(new DefaultName("Mh4w.Exitor.14")),
	Mh4w_Exitor_15(new DefaultName("Mh4w.Exitor.15")),
	Mh4w_Exitor_16(new DefaultName("Mh4w.Exitor.16")),

	;

	private Name name;
	
	private LoggerStringKey(Name name) {
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
