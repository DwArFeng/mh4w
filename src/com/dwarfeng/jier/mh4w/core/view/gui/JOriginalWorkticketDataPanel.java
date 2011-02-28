package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

public class JOriginalWorkticketDataPanel extends JPanel implements MutilangSupported{
	
	private static final long serialVersionUID = -4579359363819266216L;

	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTable table;
	
	/*
	 * 各模型。
	 */
	private DataListModel<OriginalWorkticketData> originalWorkticketDataModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private DefaultTableModel tableModel = new DefaultTableModel(){

		private static final long serialVersionUID = -3674325199574695500L;

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
	
	/*
	 * 各模型的观察器。
	 */
	private final ListOperateAdapter<OriginalWorkticketData> originalWorkticketObverser = new ListOperateAdapter<OriginalWorkticketData>() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireAdded(int, java.lang.Object)
		 */
		@Override
		public void fireAdded(int index, OriginalWorkticketData value) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.insertRow(index, new Object[]{
						value.getDepartment(),
						value.getWorkNumber(),
						value.getName(),
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
		public void fireChanged(int index, OriginalWorkticketData oldValue, OriginalWorkticketData newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
					tableModel.insertRow(index, new Object[]{
						newValue.getDepartment(),
						newValue.getWorkNumber(),
						newValue.getName(),
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
	public JOriginalWorkticketDataPanel() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param originalWorkticketDataModel 指定的原始工票数据模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JOriginalWorkticketDataPanel(Mutilang mutilang, DataListModel<OriginalWorkticketData> originalWorkticketDataModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		table = new JTable();
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_5));

		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(originalWorkticketDataModel)){
			originalWorkticketDataModel.addObverser(originalWorkticketObverser);
			originalWorkticketDataModel.getLock().readLock().lock();
			try{
				for(OriginalWorkticketData originalWorkticketData : originalWorkticketDataModel){
					tableModel.addRow(new Object[]{
						originalWorkticketData.getDepartment(),
						originalWorkticketData.getWorkNumber(),
						originalWorkticketData.getName(),
						originalWorkticketData.getJob().getName(),
						originalWorkticketData.getWorkticket()
					});
				}
			}finally {
				originalWorkticketDataModel.getLock().readLock().unlock();
			}
		}
		
		this.originalWorkticketDataModel = originalWorkticketDataModel;

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
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JOriginalWorkticketDataPanel_5));
	}

	/**
	 * @return the originalWorkticketDataModel
	 */
	public DataListModel<OriginalWorkticketData> getOriginalWorkticketDataModel() {
		return originalWorkticketDataModel;
	}

	/**
	 * @param originalWorkticketDataModel the originalWorkticketDataModel to set
	 */
	public void setOriginalWorkticketDataModel(DataListModel<OriginalWorkticketData> originalWorkticketDataModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.originalWorkticketDataModel)){
			this.originalWorkticketDataModel.removeObverser(originalWorkticketObverser);
		}
		
		if(Objects.nonNull(originalWorkticketDataModel)){
			originalWorkticketDataModel.addObverser(originalWorkticketObverser);
			originalWorkticketDataModel.getLock().readLock().lock();
			try{
				for(OriginalWorkticketData originalWorkticketData : originalWorkticketDataModel){
					tableModel.addRow(new Object[]{
						originalWorkticketData.getDepartment(),
						originalWorkticketData.getWorkNumber(),
						originalWorkticketData.getName(),
						originalWorkticketData.getJob().getName(),
						originalWorkticketData.getWorkticket()
					});
				}
			}finally {
				originalWorkticketDataModel.getLock().readLock().unlock();
			}
		}
		
		this.originalWorkticketDataModel = originalWorkticketDataModel;
	}

	/**
	 * 释放资源。
	 */
	public void dispose() {
		if(Objects.nonNull(originalWorkticketDataModel)){
			originalWorkticketDataModel.removeObverser(originalWorkticketObverser);
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
