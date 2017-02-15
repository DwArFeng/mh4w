package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Flow;

public interface BackgroundObverser extends Obverser {
	
	/**
	 * ָ֪ͨ���Ĺ��̶���Ľ��ȷ����ı䡣
	 * @param flow �����ı�Ĺ��̶���
	 * @param oldValue ���ȵľ�ֵ��
	 * @param newValue ���ȵ���ֵ��
	 */
	public void fireFlowProgressChanged(Flow flow, int oldValue, int newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶�����ܽ��ȷ����ı䡣
	 * @param flow �����ı�Ĺ��̶���
	 * @param oldValue �ܽ��ȵľ�ֵ��
	 * @param newValue �ܽ��ȵ���ֵ��
	 */
	public void fireFlowTotleProgressChanged(Flow flow, int oldValue, int newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶����ȷ���Ըı䡣
	 * @param flow �����ı�Ĺ��̶���
	 * @param oldValue �ɵ�ȷ���ԡ�
	 * @param newValue �µ�ȷ���ԡ�
	 */
	public void fireFlowDeterminateChanged(Flow flow, boolean oldValue, boolean newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶������Ϣ�����˸ı䡣
	 * @param flow �����ı�Ĺ��̶���
	 * @param oldValue �ɵ���Ϣ��
	 * @param newValue �µ���Ϣ��
	 */
	public void fireFlowMessageChanged(Flow flow, String oldValue, String newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶���Ŀ��׳��������˸ı䡣
	 * @param flow �����˸ı�Ĺ��̶���
	 * @param oldValue �ɵĿ��׳�����
	 * @param newValue �µĿ��׳�����
	 */
	public void fireFlowThrowableChanged(Flow flow, Throwable oldValue, Throwable newValue);
	
	/**
	 * ָ֪ͨ���Ķ���Ŀ�ȡ���Է����˸ı䡣
	 * @param flow �����˸ı�Ĺ��̶���
	 * @param oldValue �ɵĿ�ȡ���ԡ�
	 * @param newValue �µĿ�ȡ���ԡ�
	 */
	public void fireFlowCancelableChanged(Flow flow, boolean oldValue, boolean newValue);
	
	/**
	 * ָ֪ͨ���Ĺ��̶���ȡ����
	 * @param flow ָ���Ĺ��̶���
	 */
	public void fireFlowCanceled(Flow flow);

	/**
	 * ָ֪ͨ���Ĺ��̶�����ɡ�
	 * @param flow ָ���Ĺ��̶���
	 */
	public void fireFlowDone(Flow flow);
	
	/**
	 * ָ֪ͨ���Ĺ��̶�����ӡ�
	 * @param flow ָ���Ĺ��̶���
	 */
	public void fireFlowAdded(Flow flow);
	
	/**
	 * ָ֪ͨ���Ĺ��̶����Ƴ���
	 * @param flow ָ���Ĺ��̶���
	 */
	public void fireFlowRemoved(Flow flow);
	
	
}
