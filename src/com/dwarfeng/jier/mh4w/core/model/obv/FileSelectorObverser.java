package com.dwarfeng.jier.mh4w.core.model.obv;

import java.io.File;

import com.dwarfeng.dutil.basic.prog.Obverser;

/**
 * �ļ�ѡ����ģ�͹۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface FileSelectorObverser extends Obverser {

	/**
	 * ֪ͨģ���еĳ����ļ������˸ı䡣
	 * @param oldValue �ɵĳ����ļ�������Ϊ <code>null</code>��
	 * @param newValue �µĳ����ļ�������Ϊ <code>null</code>��
	 */
	public void fireAttendanceFileChanged(File oldValue, File newValue);
	
	/**
	 * ֪ͨģ���еĹ�Ʊ�ļ������˸ı䡣
	 * @param oldValue �ɵĹ�Ʊ�ļ�������Ϊ <code>null</code>��
	 * @param newValue �µĹ�Ʊ�ļ�������Ϊ <code>null</code>��
	 */
	public void fireWorkticketFileChanged(File oldValue, File newValue);
	
	/**
	 * ֪ͨģ�͵ľ���״̬�����˸ı䡣
	 * @param isReady ģ�͵�ǰ�ľ���״̬��
	 */
	public void fireReadyChanged(boolean isReady);
	
}
