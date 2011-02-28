package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 失败界面观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface FailFrameObverser extends Obverser {

	/**
	 * 通知隐藏失败界面。
	 */
	public void fireHideFailFrame();

}
