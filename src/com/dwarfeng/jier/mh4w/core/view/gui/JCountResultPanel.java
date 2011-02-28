package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.CountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.CountResultPanelObverser;

public class JCountResultPanel extends JPanel implements MutilangSupported, ObverserSet<CountResultPanelObverser>{
	
	private static final long serialVersionUID = 1839932303957094245L;

	/**观察器集合*/
	private final Set<CountResultPanelObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/**多语言接口*/
	private final Mutilang mutilang;

	/*
	 * final 域。
	 */
	private final JTable table;
	private final JButton exportButton;

	/*
	 * 各模型。
	 */
	private DataListModel<CountResult> countResultModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final DefaultTableModel tableModel = new DefaultTableModel(){

		private static final long serialVersionUID = -4762256534349691430L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 7;
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

		private static final long serialVersionUID = -2635802566009979208L;

		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(column == 0 || column == 1 || column == 2){
				setHorizontalAlignment(JLabel.LEFT);
				setText((String) value);
			}
			if(column == 3 || column == 4 || column ==5 || column == 6){
				setHorizontalAlignment(JLabel.RIGHT);
				setText(FormatUtil.formatDouble((double) value));
			}
			return this;
		};
		
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final ListOperateObverser<CountResult> countResultObverser = new ListOperateAdapter<CountResult>() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireAdded(int, java.lang.Object)
		 */
		@Override
		public void fireAdded(int index, CountResult value) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.insertRow(index, new Object[]{
							value.getPerson().getDepartment(),
							value.getPerson().getWorkNumber(),
							value.getPerson().getName(),
							value.getOriginalWorkTime(),
							value.getEquivalentWorkTime(),
							value.getWorkticket(),
							value.getValue()
						});
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireChanged(int, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void fireChanged(int index, CountResult oldValue, CountResult newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
					tableModel.insertRow(index, new Object[]{
							newValue.getPerson().getDepartment(),
							newValue.getPerson().getWorkNumber(),
							newValue.getPerson().getName(),
							newValue.getOriginalWorkTime(),
							newValue.getEquivalentWorkTime(),
							newValue.getWorkticket(),
							newValue.getValue()
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
	public JCountResultPanel() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param countResultModel 指定的统计结果模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JCountResultPanel(Mutilang mutilang, DataListModel<CountResult> countResultModel) {
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
		table.getColumnModel().getColumn(5).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(tableRenderer);
		
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_5));
		table.getColumnModel().getColumn(5).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_6));
		table.getColumnModel().getColumn(6).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_7));

		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(countResultModel)){
			countResultModel.addObverser(countResultObverser);
			countResultModel.getLock().readLock().lock();
			try{
				for(CountResult countResult : countResultModel){
					tableModel.addRow(new Object[]{
							countResult.getPerson().getDepartment(),
							countResult.getPerson().getWorkNumber(),
							countResult.getPerson().getName(),
							countResult.getOriginalWorkTime(),
							countResult.getEquivalentWorkTime(),
							countResult.getWorkticket(),
							countResult.getValue()
						});
				}
			}finally {
				countResultModel.getLock().readLock().unlock();
			}
		}
		
		this.countResultModel = countResultModel;
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 23, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		exportButton = new JButton();
		exportButton.setText(getLabel(LabelStringKey.JCountResultPanel_8));
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireExportCountResult();
			}
		});
		GridBagConstraints gbc_exportButton = new GridBagConstraints();
		gbc_exportButton.insets = new Insets(0, 0, 5, 0);
		gbc_exportButton.fill = GridBagConstraints.BOTH;
		gbc_exportButton.gridx = 0;
		gbc_exportButton.gridy = 1;
		panel.add(exportButton, gbc_exportButton);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<CountResultPanelObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(CountResultPanelObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(CountResultPanelObverser obverser) {
		return obversers.remove(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
	 */
	@Override
	public void clearObverser() {
		obversers.clear();
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
		exportButton.setText(getLabel(LabelStringKey.JCountResultPanel_8));
		
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_5));
		table.getColumnModel().getColumn(5).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_6));
		table.getColumnModel().getColumn(6).setHeaderValue(getLabel(LabelStringKey.JCountResultPanel_7));
		
		table.repaint();
	}

	/**
	 * @return the countResultModel
	 */
	public DataListModel<CountResult> getCountResultModel() {
		return countResultModel;
	}

	/**
	 * @param countResultModel the countResultModel to set
	 */
	public void setCountResultModel(DataListModel<CountResult> countResultModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.countResultModel)){
			this.countResultModel.removeObverser(countResultObverser);
		}
		
		if(Objects.nonNull(countResultModel)){
			countResultModel.addObverser(countResultObverser);
			countResultModel.getLock().readLock().lock();
			try{
				for(CountResult countResult : countResultModel){
					tableModel.addRow(new Object[]{
							countResult.getPerson().getDepartment(),
							countResult.getPerson().getWorkNumber(),
							countResult.getPerson().getName(),
							countResult.getOriginalWorkTime(),
							countResult.getEquivalentWorkTime(),
							countResult.getWorkticket(),
							countResult.getValue()
						});
				}
			}finally {
				countResultModel.getLock().readLock().unlock();
			}
		}
		
		this.countResultModel = countResultModel;
		
	}

	/**
	 * 释放资源。
	 */
	public void dispose() {
		if(Objects.nonNull(countResultModel)){
			countResultModel.removeObverser(countResultObverser);
		}
	}

	private void fireExportCountResult() {
		for(CountResultPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireExportCountResult();
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
