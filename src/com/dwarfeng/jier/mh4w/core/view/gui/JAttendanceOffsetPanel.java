package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.dwarfeng.jier.mh4w.core.model.cm.AttendanceOffsetModel;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.AttendanceOffsetAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.AttendanceOffsetObverser;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

public class JAttendanceOffsetPanel extends JPanel implements MutilangSupported {
	
	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTable table;
	
	/*
	 * 各模型。
	 */
	private AttendanceOffsetModel attendanceOffsetModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final DefaultTableModel tableModel = new DefaultTableModel(){

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 6;
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
	private final AttendanceOffsetObverser attendanceOffsetObverser = new AttendanceOffsetAdapter() {

		@Override
		public void fireWorkNumberPut(String key, Double value) {
			// TODO Auto-generated method stub
			super.fireWorkNumberPut(key, value);
		}

		@Override
		public void fireWorkNumberRemoved(String key) {
			// TODO Auto-generated method stub
			super.fireWorkNumberRemoved(key);
		}

		@Override
		public void fireWorkNumberChanged(String key, Double oldValue, Double newValue) {
			// TODO Auto-generated method stub
			super.fireWorkNumberChanged(key, oldValue, newValue);
		}

		@Override
		public void fireDateCleared() {
			// TODO Auto-generated method stub
			super.fireDateCleared();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireAdded(int, java.lang.Object)
		 */
		@Override
		public void fireAdded(int index, OriginalAttendanceData value) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.insertRow(index, new Object[]{
						value.getDepartment(),
						value.getWorkNumber(),
						value.getName(),
						value.getDate(),
						value.getShift(),
						value.getAttendanceRecord()
					});
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireChanged(int, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void fireChanged(int index, OriginalAttendanceData oldValue, OriginalAttendanceData newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
					tableModel.insertRow(index, new Object[]{
						newValue.getDepartment(),
						newValue.getWorkNumber(),
						newValue.getName(),
						newValue.getDate(),
						newValue.getShift(),
						newValue.getAttendanceRecord()
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
	public JAttendanceOffsetPanel() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param attendanceOffsetModel 指定的原始数据模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JAttendanceOffsetPanel(Mutilang mutilang, AttendanceOffsetModel attendanceOffsetModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_5));
		table.getColumnModel().getColumn(5).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_6));

		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		scrollPane.setViewportView(table);

		if(Objects.nonNull(attendanceOffsetModel)){
			attendanceOffsetModel.addObverser(attendanceOffsetObverser);
			attendanceOffsetModel.getLock().readLock().lock();
			try{
				for(OriginalAttendanceData originalAttendanceData : attendanceOffsetModel){
					tableModel.addRow(new Object[]{
							originalAttendanceData.getDepartment(),
							originalAttendanceData.getWorkNumber(),
							originalAttendanceData.getName(),
							originalAttendanceData.getDate(),
							originalAttendanceData.getShift(),
							originalAttendanceData.getAttendanceRecord()
						});
				}
			}finally {
				attendanceOffsetModel.getLock().readLock().unlock();
			}
		}
		
		this.attendanceOffsetModel = attendanceOffsetModel;
		
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
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_3));
		table.getColumnModel().getColumn(3).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_4));
		table.getColumnModel().getColumn(4).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_5));
		table.getColumnModel().getColumn(5).setHeaderValue(getLabel(LabelStringKey.JOriginalAttendanceDataPanel_6));
	}

	/**
	 * @return the originalAttendanceDataModel
	 */
	public DataListModel<OriginalAttendanceData> getOriginalAttendanceDataModel() {
		return attendanceOffsetModel;
	}

	/**
	 * @param originalAttendanceDataModel the originalAttendanceDataModel to set
	 */
	public void setOriginalAttendanceDataModel(DataListModel<OriginalAttendanceData> originalAttendanceDataModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.attendanceOffsetModel)){
			this.attendanceOffsetModel.removeObverser(attendanceOffsetObverser);
		}
		
		if(Objects.nonNull(originalAttendanceDataModel)){
			originalAttendanceDataModel.addObverser(attendanceOffsetObverser);
			originalAttendanceDataModel.getLock().readLock().lock();
			try{
				for(OriginalAttendanceData originalAttendanceData : originalAttendanceDataModel){
					tableModel.addRow(new Object[]{
							originalAttendanceData.getDepartment(),
							originalAttendanceData.getWorkNumber(),
							originalAttendanceData.getName(),
							originalAttendanceData.getDate(),
							originalAttendanceData.getShift(),
							originalAttendanceData.getAttendanceRecord()
						});
				}
			}finally {
				originalAttendanceDataModel.getLock().readLock().unlock();
			}
		}
		
		this.attendanceOffsetModel = originalAttendanceDataModel;
	}

	/**
	 * @return the attendanceOffsetModel
	 */
	public AttendanceOffsetModel getAttendanceOffsetModel() {
		return attendanceOffsetModel;
	}

	/**
	 * @param attendanceOffsetModel the attendanceOffsetModel to set
	 */
	public void setAttendanceOffsetModel(AttendanceOffsetModel attendanceOffsetModel) {
		this.attendanceOffsetModel = attendanceOffsetModel;
	}

	/**
	 * 释放资源。
	 */
	public void dispose() {
		if(Objects.nonNull(attendanceOffsetModel)){
			attendanceOffsetModel.removeObverser(attendanceOffsetObverser);
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
