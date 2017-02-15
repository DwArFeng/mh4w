package com.dwarfeng.jier.mh4w.core.view.struct;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Objects;

import javax.swing.JList;

/**
 * 与选区有关的列表鼠标动作侦听。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class JListMouseMotionListener4Selection implements MouseMotionListener{
	
	private final JList<?> list;
	
	/**
	 * 新实例。
	 * @param list 指定的列表。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JListMouseMotionListener4Selection(JList<?> list) {
		Objects.requireNonNull(list, "入口参数 list 不能为 null。");
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		action(e, true);
	}
	
	private void action(MouseEvent e, boolean isAdjusting) {
		list.getSelectionModel().setValueIsAdjusting(isAdjusting);
		int mouseIndex = list.locationToIndex(e.getPoint());
		if(mouseIndex != -1){
			mouseIndex = list.getCellBounds(mouseIndex, mouseIndex).contains(e.getPoint()) ? mouseIndex : -1;
		}
		if(mouseIndex == -1){
			list.getSelectionModel().clearSelection();
			list.getSelectionModel().setAnchorSelectionIndex(-1);
			list.getSelectionModel().setLeadSelectionIndex(-1);
		}
		if(mouseIndex >= 0){
			list.getSelectionModel().clearSelection();
			list.getSelectionModel().setLeadSelectionIndex(mouseIndex);
			list.getSelectionModel().setAnchorSelectionIndex(mouseIndex);
			list.getSelectionModel().addSelectionInterval(mouseIndex, mouseIndex);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

}
