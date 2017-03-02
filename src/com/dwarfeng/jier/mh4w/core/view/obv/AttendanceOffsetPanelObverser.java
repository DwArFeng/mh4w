package com.dwarfeng.jier.mh4w.core.view.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset;

/**
 * ���ڲ������۲�����
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface AttendanceOffsetPanelObverser extends Obverser{

	/**
	 * ֪ͨ�ύ���ڲ�����
	 * @param unsafeAttendanceOffset ����ȫ���ڲ�����
	 */
	public void fireSubmitAttendanceOffset(UnsafeAttendanceOffset unsafeAttendanceOffset);

	/**
	 * ֪ͨ������ڲ�����
	 */
	public void fireClearAttendanceOffset();

	/**
	 * ֪ͨ���濼�ڲ�����
	 */
	public void fireSaveAttendanceOffset();

	/**
	 * ֪ͨ��ȡ���ڲ�����
	 */
	public void fireLoadAttendanceOffset();

	/**
	 * ֪ͨ�Ƴ����ڲ�����
	 * @param index ��Ҫ�Ƴ�����š�
	 */
	public void fireRemoveAttendanceOffset(int index);

}
