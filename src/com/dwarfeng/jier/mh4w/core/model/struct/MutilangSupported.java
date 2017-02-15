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
	 * 设置该对象中的多语言接口为指定的多语言接口。
	 * @param mutilang 指定的多语言接口。
	 * @return 该操作是否对该对象造成了改变。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public boolean setMutilang(Mutilang mutilang);

}
