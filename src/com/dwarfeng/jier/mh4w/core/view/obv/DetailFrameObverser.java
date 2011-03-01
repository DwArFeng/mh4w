package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset;

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

	/**
	 * ֪ͨ�ύ���ڲ�����
	 * @param unsafeAttendanceOffset ����ȫ���ڲ�����
	 */
	public void fireSubmitAttendanceOffset(UnsafeAttendanceOffset unsafeAttendanceOffset);

	/**
	 * ֪ͨ������ڲ�����
	 */
	public void fireClearAttendanceOffset();

}
