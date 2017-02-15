package com.dwarfeng.jier.mh4w.core.view.struct;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

import javax.swing.JList;

/**
 * 与选区有关的列表鼠标侦听。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class JListMouseListener4Selection implements MouseListener{
	
	private final JList<?> list;
	
	/**
	 * 新实例。
	 * @param list 指定的列表。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JListMouseListener4Selection(JList<?> list) {
		Objects.requireNonNull(list, "入口参数 list 不能为 null。");
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		action(e, false);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		action(e, false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		action(e, false);
	}
	
	private void action(MouseEvent e, boolean isAdjusting){
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
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
