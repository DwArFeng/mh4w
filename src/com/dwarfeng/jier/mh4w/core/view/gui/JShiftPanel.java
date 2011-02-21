package com.dwarfeng.jier.mh4w.core.view.gui;

import java.util.Objects;

import javax.swing.JPanel;

import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ShiftAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.ShiftObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;
import com.dwarfeng.jier.mh4w.core.model.struct.TimeSection;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.BevelBorder;

public class JShiftPanel extends JPanel implements MutilangSupported{

	/**多语言接口*/
	private Mutilang mutilang;

	/*
	 * final 域。
	 */
	private final JLabel shiftsLabel;
	private final JLabel timeSectionsLabel;
	private final JList<Shift> shiftsList;
	private final JList<TimeSection> timeSectionsList;
	
	/*
	 * 非 final 域。
	 */
	
	/*
	 * 各模型。
	 */
	private ShiftModel shiftModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final MuaListModel<Shift> shiftsListModel = new MuaListModel<>();
	private final MuaListModel<TimeSection> timeSectionsListModel = new MuaListModel<>();
	private final ListCellRenderer<Object> shiftsListRenderer = new DefaultListCellRenderer(){
		
		private static final long serialVersionUID = 6567980303497019985L;

		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			/*
			 * 此处转换是安全的。
			 */
			Shift shift = (Shift) value;
			setText(shift.getName());
			return this;
		};
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final ShiftObverser shiftObverser = new ShiftAdapter() {

		/*
		 *  (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ShiftAdapter#fireShiftAdded(com.dwarfeng.jier.mh4w.core.model.struct.Shift)
		 */
		@Override
		public void fireShiftAdded(Shift shift) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					shiftsListModel.add(shift);
				}
			});
		}

		/* 
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ShiftAdapter#fireShiftRemoved(com.dwarfeng.jier.mh4w.core.model.struct.Shift)
		 */
		@Override
		public void fireShiftRemoved(Shift shift) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					shiftsListModel.remove(shift);
				}
			});
		}

		/* 
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ShiftAdapter#fireShiftCleared()
		 */
		@Override
		public void fireShiftCleared() {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					shiftsListModel.clear();
				}
			});
		}
		
		
		
	};

	/**
	 * 新实例。
	 */
	public JShiftPanel() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 
	 * @param mutilang
	 */
	public JShiftPanel(Mutilang mutilang, ShiftModel shiftModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		setLayout(new BorderLayout(0, 0));
		
		JAdjustableBorderPanel adjustableBorderPanel = new JAdjustableBorderPanel();
		adjustableBorderPanel.setSeperatorColor(new Color(100, 149, 237));
		adjustableBorderPanel.setSeperatorThickness(5);
		adjustableBorderPanel.setWestEnabled(true);
		add(adjustableBorderPanel);
		
		JScrollPane shiftsScrollPane = new JScrollPane();
		adjustableBorderPanel.add(shiftsScrollPane, BorderLayout.WEST);
		
		shiftsLabel = new JLabel();
		shiftsLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		shiftsLabel.setText(getLabel(LabelStringKey.JShiftPanel_1));
		shiftsScrollPane.setColumnHeaderView(shiftsLabel);
		
		shiftsList = new JList<>();
		shiftsScrollPane.setViewportView(shiftsList);
		shiftsList.setModel(shiftsListModel);
		shiftsList.setCellRenderer(shiftsListRenderer);
		
		JScrollPane timeSectionsScrollPane = new JScrollPane();
		adjustableBorderPanel.add(timeSectionsScrollPane, BorderLayout.CENTER);
		
		timeSectionsLabel = new JLabel();
		timeSectionsLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		timeSectionsLabel.setText(getLabel(LabelStringKey.JShiftPanel_2));
		timeSectionsScrollPane.setColumnHeaderView(timeSectionsLabel);
		
		timeSectionsList = new JList<>();
		timeSectionsList.setModel(timeSectionsListModel);
		timeSectionsScrollPane.setViewportView(timeSectionsList);
		
		//设置班次模型
		if(Objects.nonNull(shiftModel)){
			shiftModel.addObverser(shiftObverser);
			shiftModel.getLock().readLock().lock();
			try{
				for(Shift shift : shiftModel){
					shiftsListModel.add(shift);
				}
			}finally {
				shiftModel.getLock().readLock().unlock();
			}
		}
		
		this.shiftModel = shiftModel;
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		return this.mutilang;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		if(Objects.isNull(mutilang)) return false;
		if(Objects.equals(this.mutilang, mutilang)) return false;
		this.mutilang = mutilang;
		
		//更新各标签的文本。
		shiftsLabel.setText(getLabel(LabelStringKey.JShiftPanel_1));
		timeSectionsLabel.setText(getLabel(LabelStringKey.JShiftPanel_2));
		
		return true;
	}

	/**
	 * @return the shiftModel
	 */
	public ShiftModel getShiftModel() {
		return shiftModel;
	}

	/**
	 * @param shiftModel the shiftModel to set
	 */
	public void setShiftModel(ShiftModel shiftModel) {
		shiftsListModel.clear();
		shiftsList.getSelectionModel().setValueIsAdjusting(true);
		shiftsList.getSelectionModel().clearSelection();
		shiftsList.getSelectionModel().setAnchorSelectionIndex(-1);
		shiftsList.getSelectionModel().setLeadSelectionIndex(-1);
		
		timeSectionsListModel.clear();
		timeSectionsList.getSelectionModel().setValueIsAdjusting(true);
		timeSectionsList.getSelectionModel().clearSelection();
		timeSectionsList.getSelectionModel().setAnchorSelectionIndex(-1);
		timeSectionsList.getSelectionModel().setLeadSelectionIndex(-1);

		if(Objects.nonNull(this.shiftModel)){
			this.shiftModel.removeObverser(shiftObverser);
		}
		
		if(Objects.nonNull(shiftModel)){
			shiftModel.addObverser(shiftObverser);
			shiftModel.getLock().readLock().lock();
			try{
				for(Shift shift : shiftModel){
					shiftsListModel.add(shift);
				}
			}finally {
				shiftModel.getLock().readLock().unlock();
			}
		}
		
		this.shiftModel = shiftModel;
	}

	/**
	 * 释放资源。
	 */
	public void dispose() {
		if(Objects.nonNull(shiftModel)){
			shiftModel.removeObverser(shiftObverser);
		}
		shiftsListModel.clear();
		timeSectionsListModel.clear();
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
}
