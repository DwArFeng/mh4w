package com.dwarfeng.jier.mh4w.core.view.struct;

import java.awt.Component;

import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;

/**
 * 多语言支持图形交互界面控制器。
 * <p>支持多语言的图形交互界面控制器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangSupportedGuiController<T extends Component & MutilangSupported> 
extends GuiController<T>, MutilangSupported{
	
}
