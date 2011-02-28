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

import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.JobAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.JobObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Job;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

public class JJobPanel extends JPanel implements MutilangSupported{

	private static final long serialVersionUID = 2486725605516206360L;

	/**多语言接口*/
	private final Mutilang mutilang;

	/*
	 * final 域。
	 */
	private final JTable table;

	/*
	 * 各模型。
	 */
	private JobModel jobModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final DefaultTableModel tableModel = new DefaultTableModel(){
		
		private static final long serialVersionUID = 5103133495977002535L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return 3;
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
	
		private static final long serialVersionUID = 1364966296903101261L;

		/*
		 * (non-Javadoc)
		 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(column == 0){
				setHorizontalAlignment(JLabel.LEFT);
			}
			if(column == 1){
				setHorizontalAlignment(JLabel.RIGHT);
			}
			if(column == 2){
				setHorizontalAlignment(JLabel.LEFT);
				int index = (int) value;
		        String result = "";
		        for (; index >= 0; index = index / 26 - 1) {
		            result = (char)((char)(index%26)+'A') + result;
		        }
		        setText(result);
			}
			return this;
		};
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final JobObverser jobObverser = new JobAdapter() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.JobAdapter#fireJobAdded(com.dwarfeng.jier.mh4w.core.model.struct.Job)
		 */
		@Override
		public void fireJobAdded(Job job) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.addRow(new Object[]{
							job.getName(),
							job.getValuePerHour(),
							job.getOriginalColumn(),
							job
					});
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.JobAdapter#fireJobRemoved(com.dwarfeng.jier.mh4w.core.model.struct.Job)
		 */
		@Override
		public void fireJobRemoved(Job job) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					for(int i = 0 ; i < tableModel.getRowCount() ; i ++){
						if(tableModel.getValueAt(i, 3) == job){
							tableModel.removeRow(i);
							return;
						}
					}
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.JobAdapter#fireJobCleared()
		 */
		@Override
		public void fireJobCleared() {
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
	public JJobPanel() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param jobModel 指定的工作模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public JJobPanel(Mutilang mutilang, JobModel jobModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JJobPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JJobPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JJobPanel_3));
		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		scrollPane.setViewportView(table);
		
		if(Objects.nonNull(jobModel)){
			jobModel.addObverser(jobObverser);
			jobModel.getLock().readLock().lock();
			try{
				for(Job job : jobModel){
					tableModel.addRow(new Object[]{
						job.getName(),
						job.getValuePerHour(),
						job.getOriginalColumn(),
						job
					});
				}
			}finally {
				jobModel.getLock().readLock().unlock();
			}
		}
		
		this.jobModel = jobModel;
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#updateMutilang()
	 */
	@Override
	public void updateMutilang() {
		//更新各标签的文本。
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JJobPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JJobPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JJobPanel_3));
	}

	/**
	 * @return the jobModel
	 */
	public JobModel getJobModel() {
		return jobModel;
	}

	/**
	 * @param jobModel the jobModel to set
	 */
	public void setJobModel(JobModel jobModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.jobModel)){
			this.jobModel.removeObverser(jobObverser);
		}
		
		if(Objects.nonNull(jobModel)){
			jobModel.addObverser(jobObverser);
			jobModel.getLock().readLock().lock();
			try{
				for(Job job : jobModel){
					tableModel.addRow(new Object[]{
						job.getName(),
						job.getValuePerHour(),
						job.getOriginalColumn(),
						job
					});
				}
			}finally {
				jobModel.getLock().readLock().unlock();
			}
		}
		
		this.jobModel = jobModel;
	}

	/**
	 * 释放资源。
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
