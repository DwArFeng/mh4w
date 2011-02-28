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
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

public class JAttendanceDataPanel extends JPanel implements MutilangSupported{
	
	private static final long serialVersionUID = 8976563472467046721L;

	/**多语言接口*/
	private final Mutilang mutilang;

	/*
	 * final 域。
	 */
	private final JTable table;
		
	/*
	 * 各模型。
	 */
	private DataListModel<AttendanceData> attendanceDataModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final DefaultTableModel tableModel = new DefaultTableModel(){

		private static final long serialVersionUID = -1420077102578921821L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 9;
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

		private static final long serialVersionUID = -7970927293045510414L;

		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(column == 0 || column == 1 || column == 2 || column ==3 || column == 4 || column == 5){
				setHorizontalAlignment(JLabel.LEFT);
				setText((String) value);
			}
			if(column == 7 || column == 8){
				setHorizontalAlignment(JLabel.RIGHT);
				setText(FormatUtil.formatDouble((double) value));
			}
			if(column == 6){
				setHorizontalAlignment(JLabel.LEFT);
				//此处转换是安全的
				DateType dateType = (DateType) value;
				setText(getLabel(dateType.getLabelStringKey()));
			}
			return this;
		};
		
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final ListOperateObverser<AttendanceData> attendanceDataObverser = new ListOperateAdapter<AttendanceData>() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireAdded(int, java.lang.Object)
		 */
		@Override
		public void fireAdded(int index, AttendanceData value) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.insertRow(index, new Object[]{
							value.getPerson().getDepartment(),
							value.getPerson().getWorkNumber(),
							value.getPerson().getName(),
							FormatUtil.formatCountDate(value.getCountDate()),
							value.getShift().getName(),
							FormatUtil.formatTimeSection(value.getAttendanceRecord()),
							value.getDateType(),
							value.getOriginalWorkTime(),
							value.getEquivalentWorkTime()
						});
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireChanged(int, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void fireChanged(int index, AttendanceData oldValue, AttendanceData newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
					tableModel.insertRow(index, new Object[]{
							newValue.getPerson().getDepartment(),
							newValue.getPerson().getWorkNumber(),
							newValue.getPerson().getName(),
							FormatUtil.formatCountDate(newValue.getCountDate()),
							newValue.getShift().getName(),
							FormatUtil.formatTimeSection(newValue.getAttendanceRecord()),
							newValue.getDateType(),
							newValue.getOriginalWorkTime(),
							newValue.getEquivalentWorkTime()
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
	public JAttendanceDataPanel() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param attendanceDataModel 指定的考勤数据模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JAttendanceDataPanel(Mutilang mutilang, DataListModel<AttendanceData> attendanceDataModel) {
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
		table.getColumnModel().getColumn(7).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(8).setCellRenderer(tableRenderer);
		
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_5));
		table.getColumnModel().getColumn(5).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_6));
		table.getColumnModel().getColumn(6).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_7));
		table.getColumnModel().getColumn(7).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_8));
		table.getColumnModel().getColumn(8).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_9));

		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(attendanceDataModel)){
			attendanceDataModel.addObverser(attendanceDataObverser);
			attendanceDataModel.getLock().readLock().lock();
			try{
				for(AttendanceData attendanceData : attendanceDataModel){
					tableModel.addRow(new Object[]{
							attendanceData.getPerson().getDepartment(),
							attendanceData.getPerson().getWorkNumber(),
							attendanceData.getPerson().getName(),
							FormatUtil.formatCountDate(attendanceData.getCountDate()),
							attendanceData.getShift().getName(),
							FormatUtil.formatTimeSection(attendanceData.getAttendanceRecord()),
							attendanceData.getDateType(),
							attendanceData.getOriginalWorkTime(),
							attendanceData.getEquivalentWorkTime()
						});
				}
			}finally {
				attendanceDataModel.getLock().readLock().unlock();
			}
		}
		
		this.attendanceDataModel = attendanceDataModel;
		
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
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_5));
		table.getColumnModel().getColumn(5).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_6));
		table.getColumnModel().getColumn(6).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_7));
		table.getColumnModel().getColumn(7).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_8));
		table.getColumnModel().getColumn(8).setHeaderValue(getLabel(LabelStringKey.JAttendanceDataPanel_9));
		
		table.repaint();
	}

	/**
	 * @return the attendanceDataModel
	 */
	public DataListModel<AttendanceData> getAttendanceDataModel() {
		return attendanceDataModel;
	}

	/**
	 * @param attendanceDataModel the attendanceDataModel to set
	 */
	public void setAttendanceDataModel(DataListModel<AttendanceData> attendanceDataModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.attendanceDataModel)){
			this.attendanceDataModel.removeObverser(attendanceDataObverser);
		}
		
		if(Objects.nonNull(attendanceDataModel)){
			attendanceDataModel.addObverser(attendanceDataObverser);
			attendanceDataModel.getLock().readLock().lock();
			try{
				for(AttendanceData attendanceData : attendanceDataModel){
					tableModel.addRow(new Object[]{
							attendanceData.getPerson().getDepartment(),
							attendanceData.getPerson().getWorkNumber(),
							attendanceData.getPerson().getName(),
							FormatUtil.formatCountDate(attendanceData.getCountDate()),
							attendanceData.getShift().getName(),
							FormatUtil.formatTimeSection(attendanceData.getAttendanceRecord()),
							attendanceData.getDateType(),
							attendanceData.getOriginalWorkTime(),
							attendanceData.getEquivalentWorkTime()
						});
				}
			}finally {
				attendanceDataModel.getLock().readLock().unlock();
			}
		}
		
		this.attendanceDataModel = attendanceDataModel;
		
	}

	/**
	 * 释放资源。
	 */
	public void dispose() {
		if(Objects.nonNull(attendanceDataModel)){
			attendanceDataModel.removeObverser(attendanceDataObverser);
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
