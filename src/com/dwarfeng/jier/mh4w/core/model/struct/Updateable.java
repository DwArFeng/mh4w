package com.dwarfeng.jier.mh4w.core.model.struct;

/**
 * 可更新接口。
 * @author  DwArFeng
 * @since 0.0.1-beta
 */
public interface Updateable{
	
	/**
	 * 更新该接口。
	 * @throws ProcessException 更新过程异常。
	 */
	public void update() throws ProcessException;

}
