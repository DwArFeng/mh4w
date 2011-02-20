package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.dwarfeng.dutil.basic.io.CT;
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
	
	/**多语言接口*/
	private Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTable table;

	/*
	 * 非 final 域。
	 */
	
	/*
	 * 各模型。
	 */
	private CoreConfigModel coreConfigModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final InnerTableModel tableModel = new InnerTableModel();
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
					tableModel.changeValue(configKey, validValue);
				}
			});
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
	 * @param mutilang
	 */
	public JCoreConfigPanel(Mutilang mutilang, CoreConfigModel coreConfigModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");

		this.mutilang = mutilang;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(coreConfigModel)){
			coreConfigModel.addObverser(configObverser);
			coreConfigModel.getLock().readLock().lock();
			try{
				tableModel.addAll(Constants.getCoreConfigOrder(), coreConfigModel);
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		if(Objects.isNull(mutilang)) return false;
		if(Objects.equals(mutilang, this.mutilang)) return false;
		this.mutilang = mutilang;
		
		//更新各标签的文本。
		table.getTableHeader().repaint();
		table.repaint();
		
		return true;
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
		tableModel.clear();
		
		if(Objects.nonNull(this.coreConfigModel)){
			this.coreConfigModel.removeObverser(configObverser);
		}
		
		if(Objects.nonNull(coreConfigModel)){
			coreConfigModel.addObverser(configObverser);
			coreConfigModel.getLock().readLock().lock();
			try{
				tableModel.addAll(Constants.getCoreConfigOrder(), coreConfigModel);
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

	private final class InnerTableModel extends AbstractTableModel{
		
		private static final long serialVersionUID = -5897635629451066698L;
		
		private final List<CoreConfig> keyList = new ArrayList<>();
		private final List<String> valueList = new ArrayList<>();

		public void clear(){
			int size = keyList.size();
			keyList.clear();
			valueList.clear();
			if(size > 0){
				fireTableRowsDeleted(0, size - 1);
			}
		}
		
		public void addAll(Collection<? extends CoreConfig> c, CoreConfigModel referance){
			Objects.requireNonNull(referance, "入口参数 referance 不能为 null。");
			
			int beginIndex = keyList.size();
			keyList.addAll(c);
			int endIndex = keyList.size();
			
			for(int i = beginIndex ; i < endIndex ; i ++){
				valueList.add(i, referance.getValidValue(keyList.get(i).getConfigKey()));
			}
			
			if(endIndex > beginIndex){
				fireTableRowsInserted(beginIndex, endIndex - 1);
			}
		}
		
		public void changeValue(ConfigKey configKey, String newValue){
			int index = findConfigKey(configKey);
			valueList.set(index, newValue);
			fireTableCellUpdated(index, 1);
		}
		
		private int findConfigKey(ConfigKey configKey) {
			for(int i = 0 ; i < keyList.size() ; i ++){
				if(keyList.get(i).getConfigKey().equals(configKey)) return i;
			}
			throw new IllegalArgumentException("核心配置面板_内部表格模型 - 未能找到指定的配置键：" + configKey.getName());
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			if(column == 0){
				return getLabel(LabelStringKey.JCoreConfigPanel_1);
			}
			if(column == 1){
				return getLabel(LabelStringKey.JCoreConfigPanel_2);
			}
			
			throw new IllegalArgumentException("核心配置面板_内部表格模型 - 指定的列只能为 0 或 1");
		}
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return keyList.size();
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 2;
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if(columnIndex == 0){
				return keyList.get(rowIndex);
			}
			if(columnIndex == 1){
				return valueList.get(rowIndex);
			}
			
			throw new IllegalArgumentException("核心配置面板_内部表格模型 - 指定的列只能为 0 或 1");
		}
		
	}

}
