package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * ��ϸ���۲�����
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface DetailFrameObverser extends Obverser{

	/**
	 * ֪ͨ������ϸ���档
	 */
	public void fireHideDetailFrame();

	/**
	 * ֪ͨ����ͳ�ƽ��
	 */
	public void fireExportCountResult();

}
