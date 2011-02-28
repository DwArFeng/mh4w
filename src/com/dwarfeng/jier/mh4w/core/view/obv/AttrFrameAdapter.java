package com.dwarfeng.jier.mh4w.core.view.obv;

/**
 * 属性界面观察器适配器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public abstract class AttrFrameAdapter implements AttrFrameObverser {

	@Override
	public void fireHideAttrFrame() {}
	@Override
	public void fireReloadAttr() {}
	
}
