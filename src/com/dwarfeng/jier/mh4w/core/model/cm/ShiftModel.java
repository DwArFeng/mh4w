package com.dwarfeng.jier.mh4w.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.jier.mh4w.core.model.obv.ShiftObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;

/**
 * 班次模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface ShiftModel extends ExternalReadWriteThreadSafe, ObverserSet<ShiftObverser>, Iterable<Shift>{
	
	/**
	 * 向模型中增加指定的班次。
	 * @param shift 指定的班次。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean add(Shift shift);
	
	/**
	 * 从模型中移除指定名称的班次。
	 * @param name 指定的名称。
	 * @return 该操作是否改变了模型本身。
	 */
	public boolean remove(String name);
	
	/**
	 * 从模型中移除名称为指定名称对象的班次。
	 * @param name 指定的名称对象。
	 * @return 该操作是否改变了模型本身。
	 */
	public default boolean remove(Name name){
		return remove(name.getName());
	}
	
	/**
	 * 获取该模型的数量。
	 * @return 该模型的数量。
	 */
	public int size();
	
	/**
	 * 获取模型中是否包含指定名称的班次。
	 * @param name 指定的名称。
	 * @return 是否包含指定名称的班次。
	 */
	public boolean contains(String name);
	
	/**
	 * 获取模型中是否包含名称为指定名称对象的班次。
	 * @param name 指定的名称对象。
	 * @return 是否包含名称为指定名称对象的班次。
	 */
	public default boolean contains(Name name){
		return contains(name.getName());
	}
	
	/**
	 * 获取模型中名称为指定值的班次。
	 * <p> 如果模型中不存在指定的班次，则返回 <code>null</code>。
	 * @param name  指定的名称。
	 * @return 模型中名称为指定值的班次。
	 */
	public Shift get(String name);
	
	/**
	 * 获取模型中名称为指定的名称对象的班次。
	 * <p> 如果模型中不存在指定的班次，则返回 <code>null</code>。
	 * @param name 指定的名称对象。
	 * @return 模型中名称为指定的名称对象的班次。
	 */
	public default Shift get(Name name){
		return get(name.getName());
	}
	
	/**
	 * 清除模型中的所有数据。
	 */
	public void clear();

}
