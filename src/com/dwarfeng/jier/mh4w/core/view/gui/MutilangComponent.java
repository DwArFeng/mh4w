package com.dwarfeng.jier.mh4w.core.view.gui;

import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;

/**
 * 具有多语言功能的界面。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangComponent {
	
	/**
	 * 更新所有的标签。
	 * <p> 该动作将会使界面中的所有文本字段更新为入口多语言接口中指定的文本。
	 * @param labelMutilang 参与更新的标签多语言接口。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void refreshLabels(Mutilang mutilang);

}
