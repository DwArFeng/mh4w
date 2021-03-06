package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

public class JWorkticketDataPanel extends JPanel implements MutilangSupported{
	
	private static final long serialVersionUID = -3486691847064292483L;

	/**多语言接口*/
	private final Mutilang mutilang;

	/*
	 * final 域。
	 */
	private final JTable table;
		
	/*
	 * 各模型。
	 */
	private DataListModel<WorkticketData> workticketDataModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final DefaultTableModel tableModel = new DefaultTableModel(){

		private static final long serialVersionUID = 1970017120180102838L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 5;
		};
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	private final TableCellRenderer tableRenderer = new DefaultTableCellRenderer(){

		private static final long serialVersionUID = 6052555869051608588L;

		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(column == 0 || column == 1 || column == 2 || column ==3){
				setHorizontalAlignment(JLabel.LEFT);
				setText((String) value);
			}
			if(column == 4) {
				setHorizontalAlignment(JLabel.RIGHT);
				setText(FormatUtil.formatDouble((double) value));
			}
			return this;
		};
		
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final ListOperateObverser<WorkticketData> attendanceDataObverser = new ListOperateAdapter<WorkticketData>() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireAdded(int, java.lang.Object)
		 */
		@Override
		public void fireAdded(int index, WorkticketData value) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.insertRow(index, new Object[]{
							value.getPerson().getDepartment(),
							value.getPerson().getWorkNumber(),
							value.getPerson().getName(),
							value.getJob().getName(),
							value.getWorkticket()
						});
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireChanged(int, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void fireChanged(int index, WorkticketData oldValue, WorkticketData newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
					tableModel.insertRow(index, new Object[]{
							newValue.getPerson().getDepartment(),
							newValue.getPerson().getWorkNumber(),
							newValue.getPerson().getName(),
							newValue.getJob().getName(),
							newValue.getWorkticket()
						});
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireRemoved(int)
		 */
		@Override
		public void fireRemoved(int index) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireCleared()
		 */
		@Override
		public void fireCleared() {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int count = tableModel.getRowCount();
					for(int i = 0 ; i < count ; i ++){
						tableModel.removeRow(0);
					}
				}
			});
		}
		
	};
	
	/**
	 * 新实例。
	 */
	public JWorkticketDataPanel() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param workticketDataModel 工票数据模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JWorkticketDataPanel(Mutilang mutilang, DataListModel<WorkticketData> workticketDataModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		
		table.getColumnModel().getColumn(0).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(tableRenderer);
		
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_5));

		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(workticketDataModel)){
			workticketDataModel.addObverser(attendanceDataObverser);
			workticketDataModel.getLock().readLock().lock();
			try{
				for(WorkticketData workticketData : workticketDataModel){
					tableModel.addRow(new Object[]{
							workticketData.getPerson().getDepartment(),
							workticketData.getPerson().getWorkNumber(),
							workticketData.getPerson().getName(),
							workticketData.getJob().getName(),
							workticketData.getWorkticket(),
						});
				}
			}finally {
				workticketDataModel.getLock().readLock().unlock();
			}
		}
		
		this.workticketDataModel = workticketDataModel;
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		return mutilang;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#updateMutilang()
	 */
	@Override
	public void updateMutilang() {
		//更新各标签的文本。
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JWorkticketDataPanel_5));
		
		table.repaint();
	}

	/**
	 * @return the workticketDataModel
	 */
	public DataListModel<WorkticketData> getWorkticketDataModel() {
		return workticketDataModel;
	}

	/**
	 * @param workticketDataModel the workticketDataModel to set
	 */
	public void setWorkticketDataModel(DataListModel<WorkticketData> workticketDataModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.workticketDataModel)){
			this.workticketDataModel.removeObverser(attendanceDataObverser);
		}
		
		if(Objects.nonNull(workticketDataModel)){
			workticketDataModel.addObverser(attendanceDataObverser);
			workticketDataModel.getLock().readLock().lock();
			try{
				for(WorkticketData workticketData : workticketDataModel){
					tableModel.addRow(new Object[]{
							workticketData.getPerson().getDepartment(),
							workticketData.getPerson().getWorkNumber(),
							workticketData.getPerson().getName(),
							workticketData.getJob().getName(),
							workticketData.getWorkticket()
						});
				}
			}finally {
				workticketDataModel.getLock().readLock().unlock();
			}
		}
		
		this.workticketDataModel = workticketDataModel;
		
	}

	/**
	 * 释放资源。
	 */
	public void dispose() {
		if(Objects.nonNull(workticketDataModel)){
			workticketDataModel.removeObverser(attendanceDataObverser);
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
