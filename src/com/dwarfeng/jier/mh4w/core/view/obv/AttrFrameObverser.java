package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ���Խ���۲�����
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface AttrFrameObverser extends Obverser {

	/**
	 * ֪ͨ���Խ���رա�
	 */
	public void fireHideAttrFrame();

	/**
	 * ֪ͨ���¶�ȡ�������ԡ�
	 */
	public void fireReloadAttr();

}
