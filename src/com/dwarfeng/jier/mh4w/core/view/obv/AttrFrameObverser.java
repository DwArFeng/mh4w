package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ���Խ���۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface AttrFrameObverser extends Obverser {

	/**
	 * ֪ͨ���Խ���رա�
	 */
	public void fireAttrFrameClosing();

	/**
	 * ֪ͨ���¶�ȡ�������ԡ�
	 */
	public void fireReloadAttr();

}
