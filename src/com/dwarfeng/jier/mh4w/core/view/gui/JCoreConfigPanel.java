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

import com.dwarfeng.dutil.develop.cfg.ConfigAdapter;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigObverser;
import com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.eum.CoreConfig;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

/**
 * 核心配置面板。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class JCoreConfigPanel extends JPanel implements MutilangSupported{
	
	private static final long serialVersionUID = 2838610136352340152L;

	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTable table;

	/*
	 * 各模型。
	 */
	private CoreConfigModel coreConfigModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final DefaultTableModel tableModel = new DefaultTableModel(){
		
		private static final long serialVersionUID = 889100337992501921L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 2;
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
	private final TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer(){
		
		private static final long serialVersionUID = 3649911097672285093L;

		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(column == 0){
				CoreConfig coreConfig = (CoreConfig) value;
				setText(getLabel(coreConfig.getLabelStringKey()));
			}
			if(column == 1){
				String string = (String) value;
				setText(string);
			}
			return this;
		};
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final ConfigObverser configObverser = new ConfigAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dutil.develop.cfg.ConfigAdapter#fireCurrentValueChanged(com.dwarfeng.dutil.develop.cfg.ConfigKey, java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int rowIndex = findConfigKey(configKey);
					tableModel.removeRow(rowIndex);
					tableModel.insertRow(rowIndex, new Object[]{
							Constants.getCoreConfigOrder().get(rowIndex),
							validValue
					});
				}
			});
		}

		private int findConfigKey(ConfigKey configKey) {
			for(int i = 0 ; i < Constants.getCoreConfigOrder().size() ; i ++){
				if(Constants.getCoreConfigOrder().get(i).getConfigKey().equals(configKey)) return i;
			}
			throw new IllegalArgumentException("核心配置面板_内部表格模型 - 未能找到指定的配置键：" + configKey.getName());
		};
	};

	/**
	 * 新实例。
	 */
	public JCoreConfigPanel() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param coreConfigModel 指定的核心配置模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JCoreConfigPanel(Mutilang mutilang, CoreConfigModel coreConfigModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");

		this.mutilang = mutilang;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JCoreConfigPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JCoreConfigPanel_2));
		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(coreConfigModel)){
			coreConfigModel.addObverser(configObverser);
			coreConfigModel.getLock().readLock().lock();
			try{
				for(int i = 0 ; i < Constants.getCoreConfigOrder().size() ; i ++){
					tableModel.addRow(new Object[]{
							Constants.getCoreConfigOrder().get(i),
							coreConfigModel.getValidValue(Constants.getCoreConfigOrder().get(i).getConfigKey())
					});
				}
			}finally {
				coreConfigModel.getLock().readLock().unlock();
			}
		}
		
		this.coreConfigModel = coreConfigModel;

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
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JCoreConfigPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JCoreConfigPanel_2));

		table.repaint();
	}
	
	/**
	 * @return the coreConfigModel
	 */
	public CoreConfigModel getCoreConfigModel() {
		return coreConfigModel;
	}

	/**
	 * @param coreConfigModel the coreConfigModel to set
	 */
	public void setCoreConfigModel(CoreConfigModel coreConfigModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.coreConfigModel)){
			this.coreConfigModel.removeObverser(configObverser);
		}
		
		if(Objects.nonNull(coreConfigModel)){
			coreConfigModel.addObverser(configObverser);
			coreConfigModel.getLock().readLock().lock();
			try{
				for(int i = 0 ; i < Constants.getCoreConfigOrder().size() ; i ++){
					tableModel.addRow(new Object[]{
							Constants.getCoreConfigOrder().get(i),
							coreConfigModel.getValidValue(Constants.getCoreConfigOrder().get(i).getConfigKey())
					});
				}
			}finally {
				coreConfigModel.getLock().readLock().unlock();
			}
		}
		
		this.coreConfigModel = coreConfigModel;
	}
	
	/**
	 * 释放资源。
	 */
	public void dispose(){
		if(Objects.nonNull(coreConfigModel)){
			coreConfigModel.removeObverser(configObverser);
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
