package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.FailType;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.DataFromXls;
import com.dwarfeng.jier.mh4w.core.model.struct.Fail;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.FailFrameObverser;

public final class FailFrame extends JFrame implements MutilangSupported, ObverserSet<FailFrameObverser>{
	
	private static final long serialVersionUID = 7871583751089485861L;

	/**观察器集合*/
	private final Set<FailFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());

	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTable table;
	
	/*
	 * 各模型。
	 */
	private DataListModel<Fail> failModel;
	
	/*
	 * 视图模型以及渲染器。
	 */
	private DefaultTableModel tableModel = new DefaultTableModel(){
		
		private static final long serialVersionUID = 7248027225568406763L;

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
	private TableCellRenderer tableRenderer = new DefaultTableCellRenderer(){
		
		private static final long serialVersionUID = -996625633554229211L;

		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(column == 0){
				if(value instanceof DataFromXls){
					DataFromXls dataFromXls = (DataFromXls) value;
					setText(FormatUtil.formatDataFromXls(dataFromXls));
				}
			}
			if(column == 1){
				FailType failType = (FailType) value;
				setText(getLabel(failType.getLabelStringKey()));
			}
			return this;
		};
		
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final ListOperateObverser<Fail> failModelObverser = new ListOperateAdapter<Fail>() {

		/* 
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireAdded(int, java.lang.Object)
		 */
		@Override
		public void fireAdded(int index, Fail value) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.insertRow(index, new Object[]{
							value.getSource(),
							value.getFailType()
					});
				}
			});
		}

		/*
		 *  (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireChanged(int, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void fireChanged(int index, Fail oldValue, Fail newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
					tableModel.insertRow(index, new Object[]{
							newValue.getSource(),
							newValue.getFailType()
					});
				}
			});
		}

		/*
		 *  (non-Javadoc)
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
	 * 新实例
	 */
	public FailFrame() {
		this(Constants.getDefaultLabelMutilang(), null);
	}

	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param failModel 指定的失败模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public FailFrame(Mutilang mutilang, DataListModel<Fail> failModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");

		this.mutilang = mutilang;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireHideFailFrame();
			}
		});
		
		setTitle(getLabel(LabelStringKey.FailFrame_3));
		setBounds(new Rectangle(100, 100, 427, 505));
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.FailFrame_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.FailFrame_2));
		table.getColumnModel().getColumn(0).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(tableRenderer);

		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(failModel)){
			failModel.addObverser(failModelObverser);
			failModel.getLock().readLock().lock();
			try{
				for(Fail fail : failModel){	
					tableModel.addRow(new Object[]{
							fail.getSource(),
							fail.getFailType()
					});
				}
			}finally {
				failModel.getLock().readLock().unlock();
			}
			
		}
		
		this.failModel = failModel;
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<FailFrameObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(FailFrameObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(FailFrameObverser obverser) {
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
		setTitle(getLabel(LabelStringKey.FailFrame_3));

		table.repaint();
		
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.FailFrame_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.FailFrame_2));
	}

	/**
	 * @return the failModel
	 */
	public DataListModel<Fail> getFailModel() {
		return failModel;
	}

	/**
	 * @param failModel the failModel to set
	 */
	public void setFailModel(DataListModel<Fail> failModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.failModel)){
			this.failModel.removeObverser(failModelObverser);
		}
		
		if(Objects.nonNull(failModel)){
			failModel.addObverser(failModelObverser);
			failModel.getLock().readLock().lock();
			try{
				for(Fail fail : failModel){	
					tableModel.addRow(new Object[]{
							fail.getSource(),
							fail.getFailType()
					});
				}
			}finally {
				failModel.getLock().readLock().unlock();
			}
			
		}
		
		this.failModel = failModel;
	}

	private void fireHideFailFrame() {
		for(FailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireHideFailFrame();
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
