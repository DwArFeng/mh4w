package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 详细面板观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DetailFrameObverser extends Obverser{

	/**
	 * 通知隐藏详细界面。
	 */
	public void fireHideDetailFrame();

	/**
	 * 通知导出统计结果
	 */
	public void fireExportCountResult();

}
