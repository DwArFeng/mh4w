package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Flow;

/**
 * 过程观察器。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface FlowObverser extends Obverser{
	
	/**
	 * 通知指定的过程对象的进度发生改变。
	 * @param flow 发生改变的过程对象。
	 * @param oldValue 进度的旧值。
	 * @param newValue 进度的新值。
	 */
	public void fireProgressChanged(Flow flow, int oldValue, int newValue);
	
	/**
	 * 通知指定的过程对象的总进度发生改变。
	 * @param flow 发生改变的过程对象。
	 * @param oldValue 总进度的旧值。
	 * @param newValue 总进度的新值。
	 */
	public void fireTotleProgressChanged(Flow flow, int oldValue, int newValue);
	
	/**
	 * 通知指定的过程对象的确定性改变。
	 * @param flow 发生改变的过程对象。
	 * @param oldValue 旧的确定性。
	 * @param newValue 新的确定性。
	 */
	public void fireDeterminateChanged(Flow flow, boolean oldValue, boolean newValue);
	
	/**
	 * 通知指定的过程对象的消息发生了改变。
	 * @param flow 发生改变的过程对象。
	 * @param oldValue 旧的消息。
	 * @param newValue 新的消息。
	 */
	public void fireMessageChanged(Flow flow, String oldValue, String newValue);
	
	/**
	 * 通知指定的过程对象的可抛出对象发生了改变。
	 * @param flow 发生了改变的过程对象。
	 * @param oldValue 旧的可抛出对象。
	 * @param newValue 新的可抛出对象。
	 */
	public void fireThrowableChanged(Flow flow, Throwable oldValue, Throwable newValue);
	
	/**
	 * 通知指定的过程对象可取消性发生了改变。
	 * @param flow 发生了改变的过程对象。
	 * @param oldValue 旧的可取消性。
	 * @param newValue 新的可取消性。
	 */
	public void fireCancelableChanged(Flow flow, boolean oldValue, boolean newValue);

	/**
	 * 通知指定的过程对象被取消。
	 * @param flow 指定的过程对象。
	 */
	public void fireCanceled(Flow flow);

	/**
	 * 通知指定的过程对象完成。
	 * @param flow 指定的过程对象。
	 */
	public void fireDone(Flow flow);
	
}
