package com.dwarfeng.jier.mh4w.core.view.ctrl;

import java.awt.Component;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

/**
 * 图形交互界面控制器。
 * <p> 用于控制图形交互界面。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface GuiController<T extends Component> extends ExternalReadWriteThreadSafe{

	/**
	 * 生成一个新实例。
	 * <p> 如果前一个实例还没有被释放，则不进行任何操作，返回 <code>false</code>。
	 * <p> 该方法需要在 Swing 事件队列中运行。
	 * @return 该操作是否生成了一个新实例。
	 */
	public boolean newInstance();
	
	/**
	 * 返回该控制器是否已经拥有了一个实例。
	 * @return 是否已经拥有了一个实例。
	 */
	public boolean hasInstance();
	
	/**
	 * 获取该控制器的实例。
	 * <p> 如果没有实例，则返回 <code>null</code>。
	 * @return 该控制器的实例。
	 */
	public T getInstance();
	
	/**
	 * 释放实例。
	 * <p> 释放实例后，将实例设置为 <code>null</code>， 同时 {@link #hasInstance()} 方法将返回 <code>false</code>。
	 * <p> 如果此时控制器中的实例已经被释放了，则什么也不做并且返回 <code>false</code>。
	 * @return 实例是否被释放。
	 */
	public boolean dispose();
	
	/**
	 * 返回该控制器实例中的对象是否为可见的。
	 * <p> 如果此时控制器中没有实例，则返回 <code>false</code>。
	 * @return 该控制器实例中的对象是否为可见的。
	 */
	public boolean isVisible();
	
	/**
	 * 设置控制器中的实例是否可见。
	 * <p> 如果控制器中没有实例，则什么也不做并且返回 <code>false</code>。
	 * <p> 该方法需要在 Swing 事件队列中运行。
	 * @return 是否成功的设置。
	 */
	public boolean setVisible(boolean aFlag);
	
	/**
	 * 展示该控制器中的实例。
	 * <p> 如果该控制器中没有实例，则新建一个实例，然后将此实例设为可见；
	 * 如果控制器中有实例，则直接将其设为可见。
	 * <p> 该方法需要在 Swing 事件队列中运行。
	 */
	public void show();
	
}
