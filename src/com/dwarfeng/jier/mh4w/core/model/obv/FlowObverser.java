package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Flow;

/**
 * ���̹۲�����
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface FlowObverser extends Obverser{
	
	/**
	 * ָ֪ͨ���Ĺ��̶���Ľ��ȷ����ı䡣
	 * @param flow �����ı�Ĺ��̶���
	 * @param oldValue ���ȵľ�ֵ��
	 * @param newValue ���ȵ���ֵ��
	 */
	public void fireProgressChanged(Flow flow, int oldValue, int newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶�����ܽ��ȷ����ı䡣
	 * @param flow �����ı�Ĺ��̶���
	 * @param oldValue �ܽ��ȵľ�ֵ��
	 * @param newValue �ܽ��ȵ���ֵ��
	 */
	public void fireTotleProgressChanged(Flow flow, int oldValue, int newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶����ȷ���Ըı䡣
	 * @param flow �����ı�Ĺ��̶���
	 * @param oldValue �ɵ�ȷ���ԡ�
	 * @param newValue �µ�ȷ���ԡ�
	 */
	public void fireDeterminateChanged(Flow flow, boolean oldValue, boolean newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶������Ϣ�����˸ı䡣
	 * @param flow �����ı�Ĺ��̶���
	 * @param oldValue �ɵ���Ϣ��
	 * @param newValue �µ���Ϣ��
	 */
	public void fireMessageChanged(Flow flow, String oldValue, String newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶���Ŀ��׳��������˸ı䡣
	 * @param flow �����˸ı�Ĺ��̶���
	 * @param oldValue �ɵĿ��׳�����
	 * @param newValue �µĿ��׳�����
	 */
	public void fireThrowableChanged(Flow flow, Throwable oldValue, Throwable newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶����ȡ���Է����˸ı䡣
	 * @param flow �����˸ı�Ĺ��̶���
	 * @param oldValue �ɵĿ�ȡ���ԡ�
	 * @param newValue �µĿ�ȡ���ԡ�
	 */
	public void fireCancelableChanged(Flow flow, boolean oldValue, boolean newValue);

	/**
	 * ָ֪ͨ���Ĺ��̶���ȡ����
	 * @param flow ָ���Ĺ��̶���
	 */
	public void fireCanceled(Flow flow);

	/**
	 * ָ֪ͨ���Ĺ��̶�����ɡ�
	 * @param flow ָ���Ĺ��̶���
	 */
	public void fireDone(Flow flow);
	
}
