package com.dwarfeng.jier.mh4w.core.view.struct;

import java.awt.Component;

import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;

/**
 * ������֧��ͼ�ν��������������
 * <p>֧�ֶ����Ե�ͼ�ν��������������
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface MutilangSupportedGuiController<T extends Component & MutilangSupported> 
extends GuiController<T>, MutilangSupported{
	
}
