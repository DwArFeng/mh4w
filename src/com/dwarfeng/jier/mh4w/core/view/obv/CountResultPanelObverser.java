package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 统计结果面板适配器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface CountResultPanelObverser extends Obverser{

	/**
	 * 通知导出统计结果。
	 */
	public void fireExportCountResult();

}
