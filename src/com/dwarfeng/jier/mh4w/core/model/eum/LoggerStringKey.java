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
	Mh4w_FlowProvider_11(new DefaultName("Mh4w.FlowProvider.11")),
	Mh4w_FlowProvider_12(new DefaultName("Mh4w.FlowProvider.12")),
	Mh4w_FlowProvider_13(new DefaultName("Mh4w.FlowProvider.13")),
	Mh4w_FlowProvider_14(new DefaultName("Mh4w.FlowProvider.14")),
	Mh4w_FlowProvider_15(new DefaultName("Mh4w.FlowProvider.15")),
	Mh4w_FlowProvider_16(new DefaultName("Mh4w.FlowProvider.16")),
	Mh4w_FlowProvider_17(new DefaultName("Mh4w.FlowProvider.17")),
	Mh4w_FlowProvider_18(new DefaultName("Mh4w.FlowProvider.18")),
	Mh4w_FlowProvider_19(new DefaultName("Mh4w.FlowProvider.19")),
	Mh4w_FlowProvider_20(new DefaultName("Mh4w.FlowProvider.20")),
	Mh4w_FlowProvider_21(new DefaultName("Mh4w.FlowProvider.21")),
	Mh4w_FlowProvider_22(new DefaultName("Mh4w.FlowProvider.22")),
	Mh4w_FlowProvider_23(new DefaultName("Mh4w.FlowProvider.23")),
	Mh4w_FlowProvider_24(new DefaultName("Mh4w.FlowProvider.24")),
	Mh4w_FlowProvider_25(new DefaultName("Mh4w.FlowProvider.25")),
	Mh4w_FlowProvider_26(new DefaultName("Mh4w.FlowProvider.26")),
	Mh4w_FlowProvider_27(new DefaultName("Mh4w.FlowProvider.27")),
	Mh4w_FlowProvider_28(new DefaultName("Mh4w.FlowProvider.28")),
	Mh4w_FlowProvider_29(new DefaultName("Mh4w.FlowProvider.29")),
	Mh4w_FlowProvider_30(new DefaultName("Mh4w.FlowProvider.30")),
	Mh4w_FlowProvider_31(new DefaultName("Mh4w.FlowProvider.31")),
	Mh4w_FlowProvider_32(new DefaultName("Mh4w.FlowProvider.32")),
	Mh4w_FlowProvider_33(new DefaultName("Mh4w.FlowProvider.33")),
	Mh4w_FlowProvider_34(new DefaultName("Mh4w.FlowProvider.34")),
	Mh4w_FlowProvider_35(new DefaultName("Mh4w.FlowProvider.35")),
	Mh4w_FlowProvider_36(new DefaultName("Mh4w.FlowProvider.36")),
	Mh4w_FlowProvider_37(new DefaultName("Mh4w.FlowProvider.37")),
	Mh4w_FlowProvider_38(new DefaultName("Mh4w.FlowProvider.38")),
	Mh4w_FlowProvider_39(new DefaultName("Mh4w.FlowProvider.39")),
	Mh4w_FlowProvider_40(new DefaultName("Mh4w.FlowProvider.40")),
	Mh4w_FlowProvider_41(new DefaultName("Mh4w.FlowProvider.41")),
	Mh4w_FlowProvider_42(new DefaultName("Mh4w.FlowProvider.42")),

	Update_LoggerMutilang_1(new DefaultName("Update.LoggerMutilang.1")),
	Update_LabelMutilang_1(new DefaultName("Update.LabelMutilang.1")),
	Update_Logger_1(new DefaultName("Update.Logger.1")),

	Mh4w_Exitor_1(new DefaultName("Mh4w.Exitor.1")),
	Mh4w_Exitor_2(new DefaultName("Mh4w.Exitor.2")),
	Mh4w_Exitor_3(new DefaultName("Mh4w.Exitor.3")),
	Mh4w_Exitor_4(new DefaultName("Mh4w.Exitor.4")),
	Mh4w_Exitor_5(new DefaultName("Mh4w.Exitor.5")),

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
