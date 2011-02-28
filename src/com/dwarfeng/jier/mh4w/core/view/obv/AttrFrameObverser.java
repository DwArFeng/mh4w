package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 属性界面观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface AttrFrameObverser extends Obverser {

	/**
	 * 通知属性界面关闭。
	 */
	public void fireHideAttrFrame();

	/**
	 * 通知重新读取程序属性。
	 */
	public void fireReloadAttr();

}
