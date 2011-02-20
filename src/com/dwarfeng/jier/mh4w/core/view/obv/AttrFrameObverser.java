package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * 属性界面观察器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface AttrFrameObverser extends Obverser {

	/**
	 * 通知属性界面关闭。
	 */
	public void fireAttrFrameClosing();

	/**
	 * 通知重新读取程序属性。
	 */
	public void fireReloadAttr();

}
