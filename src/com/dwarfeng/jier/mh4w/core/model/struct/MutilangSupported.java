package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 多语言支持接口。
 * <p> 实现该接口意味着该对象是一个接受Mutilang以实现多语言化的对象。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangSupported {
	
	/**
	 * 获取该对象中的多语言接口。
	 * @return 该对象中的多语言接口。
	 */
	public Mutilang getMutilang();
	
	/**
	 * 更新多语言。
	 * <p> 该方法一般用于在多语言模型更新后，通知多语言支持接口进行相关的更新。
	 */
	public void updateMutilang();

}
