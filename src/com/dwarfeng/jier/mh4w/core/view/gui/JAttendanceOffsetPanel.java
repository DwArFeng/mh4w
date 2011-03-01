package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceOffset;
import com.dwarfeng.jier.mh4w.core.model.struct.CountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.model.struct.Person;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelObverser;
import com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameObverser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JAttendanceOffsetPanel extends JPanel implements MutilangSupported, ObverserSet<AttendanceOffsetPanelObverser> {
	
	/**观察器集合*/
	private final Set<AttendanceOffsetPanelObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private final JComboBox<Person> comboBox;

	/*
	 * 各模型。
	 */
	private DataListModel<AttendanceOffset> attendanceOffsetModel;
	private DataListModel<CountResult> countResultModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final DefaultTableModel tableModel = new DefaultTableModel(){

		private static final long serialVersionUID = 1995931789304479415L;

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
	private final DefaultComboBoxModel<Person> comboBoxModel = new DefaultComboBoxModel<>();
	private final ListCellRenderer<Object> comboBoxRenderer = new DefaultListCellRenderer(){
	
		private static final long serialVersionUID = -6046116864760471615L;

		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			//该类型转换是安全的
			if(Objects.nonNull(value)){
				setText(FormatUtil.formatPerson((Person) value));
			}
			return this;
		};
		
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final ListOperateAdapter<AttendanceOffset> attendanceOffsetObverser = new ListOperateAdapter<AttendanceOffset>() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireAdded(int, java.lang.Object)
		 */
		@Override
		public void fireAdded(int index, AttendanceOffset value) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.insertRow(index, new Object[]{
							FormatUtil.formatPerson(value.getPerson()),
							value.getValue(),
							value.getDescription()
					});
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter#fireChanged(int, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void fireChanged(int index, AttendanceOffset oldValue, AttendanceOffset newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					tableModel.removeRow(index);
					tableModel.insertRow(index, new Object[]{
							FormatUtil.formatPerson(newValue.getPerson()),
							newValue.getValue(),
							newValue.getDescription()
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
					int size = comboBoxModel.getSize();
					comboBoxModel.insertElementAt(value.getPerson(), index);
					if(size == 0){
						comboBox.setSelectedIndex(0);
					}
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
					comboBoxModel.removeElementAt(index);
					comboBoxModel.insertElementAt(newValue.getPerson(), index);
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
					comboBoxModel.removeElementAt(index);
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
					comboBoxModel.removeAllElements();
				}
			});
		}
		
	};
	
	/**
	 * 新实例。
	 */
	public JAttendanceOffsetPanel() {
		this(Constants.getDefaultLabelMutilang(), null, null);
	}

	/**
	 * 
	 * @param mutilang
	 * @param attendanceOffsetModel
	 */
	public JAttendanceOffsetPanel(Mutilang mutilang, DataListModel<AttendanceOffset> attendanceOffsetModel,
			DataListModel<CountResult> countResultModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_3));

		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(comboBoxModel);
		comboBox.setRenderer(comboBoxRenderer);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 1;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 1;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnNewButton_1 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 1;
		panel_1.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 2;
		panel_1.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JButton button = new JButton("New button");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 0;
		gbc_button.gridy = 3;
		panel_1.add(button, gbc_button);
		
		if(Objects.nonNull(attendanceOffsetModel)){
			attendanceOffsetModel.addObverser(attendanceOffsetObverser);
			attendanceOffsetModel.getLock().readLock().lock();
			try{
				for(AttendanceOffset attendanceOffset : attendanceOffsetModel){
					tableModel.addRow(new Object[]{
							FormatUtil.formatPerson(attendanceOffset.getPerson()),
							attendanceOffset.getValue(),
							attendanceOffset.getDescription()
						});
				}
			}finally {
				attendanceOffsetModel.getLock().readLock().unlock();
			}
		}
		
		this.attendanceOffsetModel = attendanceOffsetModel;
		
		if(Objects.nonNull(countResultModel)){
			countResultModel.addObverser(countResultObverser);
			countResultModel.getLock().readLock().lock();
			try{
				for(CountResult countResult : countResultModel){
					comboBoxModel.addElement(countResult.getPerson());
				}
			}finally {
				countResultModel.getLock().readLock().unlock();
			}
		}
		
		this.countResultModel = countResultModel;
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<AttendanceOffsetPanelObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(AttendanceOffsetPanelObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(AttendanceOffsetPanelObverser obverser) {
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
		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_3));
	}

	/**
	 * @return the attendanceOffsetModel
	 */
	public DataListModel<AttendanceOffset> getAttendanceOffsetModel() {
		return attendanceOffsetModel;
	}

	/**
	 * @param attendanceOffsetModel the attendanceOffsetModel to set
	 */
	public void setAttendanceOffsetModel(DataListModel<AttendanceOffset> attendanceOffsetModel) {
		int count = tableModel.getRowCount();
		for(int i = 0 ; i < count ; i ++){
			tableModel.removeRow(0);
		}
		
		if(Objects.nonNull(this.attendanceOffsetModel)){
			this.attendanceOffsetModel.removeObverser(attendanceOffsetObverser);
		}
		
		if(Objects.nonNull(attendanceOffsetModel)){
			attendanceOffsetModel.addObverser(attendanceOffsetObverser);
			attendanceOffsetModel.getLock().readLock().lock();
			try{
				for(AttendanceOffset attendanceOffset : attendanceOffsetModel){
					tableModel.addRow(new Object[]{
							FormatUtil.formatPerson(attendanceOffset.getPerson()),
							attendanceOffset.getValue(),
							attendanceOffset.getDescription()
						});
				}
			}finally {
				attendanceOffsetModel.getLock().readLock().unlock();
			}
		}
		
		this.attendanceOffsetModel = attendanceOffsetModel;
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
		comboBoxModel.removeAllElements();
		
		if(Objects.nonNull(this.countResultModel)){
			this.countResultModel.removeObverser(countResultObverser);
		}
		
		if(Objects.nonNull(countResultModel)){
			countResultModel.addObverser(countResultObverser);
			countResultModel.getLock().readLock().lock();
			try{
				for(CountResult countResult : countResultModel){
					comboBoxModel.addElement(countResult.getPerson());
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
		if(Objects.nonNull(attendanceOffsetModel)){
			attendanceOffsetModel.removeObverser(attendanceOffsetObverser);
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
