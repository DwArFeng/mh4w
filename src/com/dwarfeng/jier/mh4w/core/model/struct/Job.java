package com.dwarfeng.jier.mh4w.core.model.struct;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 工作。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface Job extends Name{

	/**
	 * 获取该工作数据在原始表格中所在的列。
	 * @return 该工作数据在原始表格中所在的列。
	 */
	public int getOriginalColumn();
	
}
