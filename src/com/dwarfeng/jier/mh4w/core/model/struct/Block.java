package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 阻挡接口。
 * <p> 该接口线程安全，不向外暴露同步锁。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface Block{

	/**
	 * 以指定的键所指示的标准去阻挡当前的线程。
	 * <p> 如果在方法被调用时该键所指示的标准不符合执行的条件，那么调用该方法的线程则被阻塞，
	 * 直到该键所指示的标准符合执行条件时，该线程才继续执行。
	 * @param key 指定的键。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void block(String key);
	
	/**
	 * 解除阻挡。
	 * <p> 该方法将调用此方法的线程从阻挡池中移除，并且通知其它的阻挡对象重新判断阻挡状况。
	 * @param key 指定的键。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void unblock(String key);
	
}
