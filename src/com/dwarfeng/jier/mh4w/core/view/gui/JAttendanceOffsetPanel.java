package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.ListOperateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceOffset;
import com.dwarfeng.jier.mh4w.core.model.struct.CountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.DefaultUnsafeAttendanceOffset;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.model.struct.Person;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelObverser;
import com.sun.glass.events.KeyEvent;

public class JAttendanceOffsetPanel extends JPanel implements MutilangSupported, ObverserSet<AttendanceOffsetPanelObverser> {

	/**comboBox所允许的最小宽度*/
	private static final int MIN_COMBOBOX_WIDTH = 150;
	
	/**观察器集合*/
	private final Set<AttendanceOffsetPanelObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTable table;
	private final JTextField timeTextField;
	private final JTextField descriptionTextField;
	private final JButton submitButton;
	private final JComboBox<Person> comboBox;
	private final JLabel timeLabel;
	private final JLabel descritionLabel;
	private final JButton clearButton;
	private final JButton loadButton;
	private final JButton saveButton;
	
	/**
	 * 非 final 域
	 */
	private int comboBoxWidth = 0;
	
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
	private final InnerComboBoxModel comboBoxModel = new InnerComboBoxModel();
	private final TableCellRenderer tableRenderer = new DefaultTableCellRenderer(){
		
		private static final long serialVersionUID = -2854380488244617595L;

		@Override
		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(column == 1){
				setHorizontalAlignment(JLabel.RIGHT);
				setText(FormatUtil.formatDouble((double) value));
			}
			if(column == 0 || column == 2){
				setHorizontalAlignment(JLabel.LEFT);
			}
			return this;
		};
	};
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
	private final InnerComboBoxEditor comboBoxEditor = new InnerComboBoxEditor();
	
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
							value.getDescription(),
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
							newValue.getDescription(),
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
					if(size == 0 && comboBoxModel.getSize() == 1){
						comboBox.setSelectedIndex(0);
					}
					checkComboBoxWidth();
					comboBox.setPreferredSize(new Dimension(Math.max(MIN_COMBOBOX_WIDTH, comboBoxWidth), comboBox.getPreferredSize().height));
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
					checkComboBoxWidth();
					comboBox.setPreferredSize(new Dimension(Math.max(MIN_COMBOBOX_WIDTH, comboBoxWidth), comboBox.getPreferredSize().height));
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
					checkComboBoxWidth();
					comboBox.setPreferredSize(new Dimension(Math.max(MIN_COMBOBOX_WIDTH, comboBoxWidth), comboBox.getPreferredSize().height));
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
					comboBoxModel.clear();
					checkComboBoxWidth();
					comboBox.setPreferredSize(new Dimension(Math.max(MIN_COMBOBOX_WIDTH, comboBoxWidth), comboBox.getPreferredSize().height));
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
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param attendanceOffsetModel 指定的考勤补偿模型。
	 * @param countResultModel 指定的统计结果模型。
	 */
	public JAttendanceOffsetPanel(Mutilang mutilang, DataListModel<AttendanceOffset> attendanceOffsetModel,
			DataListModel<CountResult> countResultModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
			
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "remove");
		table.getActionMap().put("remove", new AbstractAction() {
			
			private static final long serialVersionUID = 7760542975339282553L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index >= 0 && tableModel.getRowCount() >= 0){
					fireRemoveAttendanceOffset(index);
				}
			}
		});
		table.getColumnModel().getColumn(0).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(tableRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(tableRenderer);

		table.getColumnModel().getColumn(0).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_1));
		table.getColumnModel().getColumn(1).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_2));
		table.getColumnModel().getColumn(2).setHeaderValue(getLabel(LabelStringKey.JAttendanceOffsetPanel_3));

		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		comboBox = new JComboBox<>();
		comboBox.setEditable(true);
		comboBox.setEditor(comboBoxEditor);
		comboBox.setModel(comboBoxModel);
		comboBox.setRenderer(comboBoxRenderer);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		timeLabel = new JLabel();
		timeLabel.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_5));
		GridBagConstraints gbc_timeLabel = new GridBagConstraints();
		gbc_timeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_timeLabel.anchor = GridBagConstraints.EAST;
		gbc_timeLabel.gridx = 1;
		gbc_timeLabel.gridy = 0;
		panel.add(timeLabel, gbc_timeLabel);
		
		timeTextField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panel.add(timeTextField, gbc_textField);
		timeTextField.setColumns(10);
		
		submitButton = new JButton();
		submitButton.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_4));
		submitButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
		submitButton.getActionMap().put("submit", new AbstractAction() {
			
			private static final long serialVersionUID = 7760542975339282553L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(Objects.isNull(comboBox.getSelectedItem())) return;
				//此处转换是安全的。
				Person person = (Person) comboBox.getSelectedItem();
				fireSubmitAttendanceOffset(new DefaultUnsafeAttendanceOffset(person.getName(), person.getDepartment(), 
						person.getWorkNumber(), timeTextField.getText(), descriptionTextField.getText()));
			}
		});
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Objects.isNull(comboBox.getSelectedItem())) return;
				//此处转换是安全的。
				Person person = (Person) comboBox.getSelectedItem();
				fireSubmitAttendanceOffset(new DefaultUnsafeAttendanceOffset(person.getName(), person.getDepartment(), 
						person.getWorkNumber(), timeTextField.getText(), descriptionTextField.getText()));
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		panel.add(submitButton, gbc_btnNewButton);
		
		descritionLabel = new JLabel();
		descritionLabel.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_6));
		GridBagConstraints gbc_descritionLabel = new GridBagConstraints();
		gbc_descritionLabel.insets = new Insets(0, 0, 0, 5);
		gbc_descritionLabel.anchor = GridBagConstraints.EAST;
		gbc_descritionLabel.gridx = 1;
		gbc_descritionLabel.gridy = 1;
		panel.add(descritionLabel, gbc_descritionLabel);
		
		descriptionTextField = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 1;
		panel.add(descriptionTextField, gbc_textField_1);
		descriptionTextField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		clearButton = new JButton();
		clearButton.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_7));
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireClearAttendanceOffset();
			}
		});
		GridBagConstraints gbc_clearButton = new GridBagConstraints();
		gbc_clearButton.fill = GridBagConstraints.BOTH;
		gbc_clearButton.insets = new Insets(0, 0, 5, 0);
		gbc_clearButton.gridx = 0;
		gbc_clearButton.gridy = 1;
		panel_1.add(clearButton, gbc_clearButton);
		
		saveButton = new JButton();
		saveButton.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_8));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireSaveAttendanceOffset();
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 2;
		panel_1.add(saveButton, gbc_btnNewButton_2);
		
		loadButton = new JButton();
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireLoadAttendanceOffset();
			}
		});
		loadButton.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_9));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 0;
		gbc_button.gridy = 3;
		panel_1.add(loadButton, gbc_button);
		
		if(Objects.nonNull(attendanceOffsetModel)){
			attendanceOffsetModel.addObverser(attendanceOffsetObverser);
			attendanceOffsetModel.getLock().readLock().lock();
			try{
				for(AttendanceOffset attendanceOffset : attendanceOffsetModel){
					tableModel.addRow(new Object[]{
							FormatUtil.formatPerson(attendanceOffset.getPerson()),
							attendanceOffset.getValue(),
							attendanceOffset.getDescription(),
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
			checkComboBoxWidth();
			comboBox.setPreferredSize(new Dimension(Math.max(MIN_COMBOBOX_WIDTH, comboBoxWidth), comboBox.getPreferredSize().height));
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
		
		timeLabel.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_5));
		descritionLabel.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_6));
		
		submitButton.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_4));
		clearButton.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_7));

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
							attendanceOffset.getDescription(),
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
		comboBoxModel.clear();
		
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
			checkComboBoxWidth();
			comboBox.setPreferredSize(new Dimension(Math.max(MIN_COMBOBOX_WIDTH, comboBoxWidth), comboBox.getPreferredSize().height));
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
		if(Objects.nonNull(countResultModel)){
			countResultModel.removeObverser(countResultObverser);
		}
	}

	private void fireSubmitAttendanceOffset(UnsafeAttendanceOffset unsafeAttendanceOffset) {
		for(AttendanceOffsetPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSubmitAttendanceOffset(unsafeAttendanceOffset);
		}
	}

	private void fireClearAttendanceOffset() {
		for(AttendanceOffsetPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireClearAttendanceOffset();
		}
	}

	private void fireSaveAttendanceOffset() {
		for(AttendanceOffsetPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSaveAttendanceOffset();
		}
	}

	private void fireLoadAttendanceOffset() {
		for(AttendanceOffsetPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoadAttendanceOffset();
		}
	}

	private void fireRemoveAttendanceOffset(int index) {
		for(AttendanceOffsetPanelObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRemoveAttendanceOffset(index);
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
	
	private void checkComboBoxWidth(){
		int max = 0;
		for(Person person : comboBoxModel.rawList){
			if(Objects.nonNull(person)){
				String str = FormatUtil.formatPerson(person);
				max = Math.max(comboBox.getFontMetrics(comboBox.getFont()).stringWidth(str) + 50, max);
			}
		}
		comboBoxWidth = max;
	}
	
	private final class InnerComboBoxEditor extends BasicComboBoxEditor{

		private boolean adjustFlag = false;
		private Person person;
		
		public InnerComboBoxEditor() {
			editor.getDocument().addDocumentListener(new DocumentListener() {
				
				/*
				 * (non-Javadoc)
				 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
				 */
				@Override
				public void removeUpdate(DocumentEvent e) {
					action();
				}
				
				/*
				 * (non-Javadoc)
				 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
				 */
				@Override
				public void insertUpdate(DocumentEvent e) {
					action();
				}
				
				/*
				 * (non-Javadoc)
				 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
				 */
				@Override
				public void changedUpdate(DocumentEvent e) {
					action();
				}
				
				private void action(){
					if(! adjustFlag){
						comboBoxModel.setFilter(editor.getText());
						checkComboBoxWidth();
						comboBox.setPreferredSize(new Dimension(Math.max(MIN_COMBOBOX_WIDTH, comboBoxWidth), comboBox.getPreferredSize().height));
						comboBox.showPopup();
					}
				}
			});
			

			
		}
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.plaf.basic.BasicComboBoxEditor#setItem(java.lang.Object)
		 */
		@Override
		public void setItem(Object anObject) {
			if(! (anObject instanceof Person)) return;
			person = (Person) anObject;
			adjustFlag = true;
			checkComboBoxWidth();
			comboBox.setPreferredSize(new Dimension(Math.max(MIN_COMBOBOX_WIDTH, comboBoxWidth), comboBox.getPreferredSize().height));
			adjustFlag = false;
		};
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.plaf.basic.BasicComboBoxEditor#getItem()
		 */
		@Override
		public Object getItem() {
			return person;
		}
		
	}
	
	private final class InnerComboBoxModel extends AbstractListModel<Person> implements MutableComboBoxModel<Person>{

		private Person selectedItem;
		private String filter = "";
		
		private List<Person> filteredList = new ArrayList<>();
		private List<Person> rawList = new ArrayList<>();
		
		/*
		 * (non-Javadoc)
		 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
		 */
		@Override
		public void setSelectedItem(Object anItem) {
			if(! (anItem instanceof Person)) return;
			this.selectedItem = (Person) anItem;
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.ComboBoxModel#getSelectedItem()
		 */
		@Override
		public Object getSelectedItem() {
			return selectedItem;
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.ListModel#getSize()
		 */
		@Override
		public int getSize() {
			return filteredList.size();
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.ListModel#getElementAt(int)
		 */
		@Override
		public Person getElementAt(int index) {
			return filteredList.get(index);
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.MutableComboBoxModel#addElement(java.lang.Object)
		 */
		@Override
		public void addElement(Person item) {
			rawList.add(item);
			if(acceptPerson(item)){
				int index = filteredList.size();
				filteredList.add(item);
				fireIntervalAdded(this, index, index);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.MutableComboBoxModel#removeElement(java.lang.Object)
		 */
		@Override
		public void removeElement(Object obj) {
			rawList.remove(obj);
			filteredList.remove(obj);
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.MutableComboBoxModel#insertElementAt(java.lang.Object, int)
		 */
		@Override
		public void insertElementAt(Person item, int index) {
			rawList.add(index, item);
			if(acceptPerson(item)){
				if(filteredList.size() == 0){
					filteredList.add(item);
					fireIntervalAdded(this, 0, 0);
					return;
				}
				
				if(filteredList.size() == 1){
					filteredList.add(item);
					fireIntervalAdded(this, 1, 1);
					return;
				}
				for(int i = 0 ; i < filteredList.size() - 1 ; i ++){
					Person former = filteredList.get(i);
					Person later = filteredList.get(i + 1);
					
					if(rawList.indexOf(former) < index && rawList.indexOf(later) > index){
						filteredList.add(i, item);
						fireIntervalAdded(this, i, i);
						return;
					}
				}
				
				int i = filteredList.size();
				filteredList.add(item);
				fireIntervalAdded(this, i, i);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.MutableComboBoxModel#removeElementAt(int)
		 */
		@Override
		public void removeElementAt(int index) {
			Person person = rawList.remove(index);
			int i = filteredList.indexOf(person);
			filteredList.remove(person);
			if(i >= 0){
				fireIntervalRemoved(this, i, i);
			}
		}
		
		public void clear(){
			int size = filteredList.size();
			rawList.clear();
			filteredList.clear();
			if(size > 0){
				fireIntervalRemoved(this, 0, size - 1);
			}
		}
		
		public void setFilter(String filter){
			this.filter = filter;
			int size = filteredList.size();
			filteredList.clear();
			if(size > 0){
				fireIntervalRemoved(this, 0, size - 1);
			}
			
			for(int i = 0 ; i < rawList.size() ; i ++){
				Person person = rawList.get(i);
				if(acceptPerson(person)){
					size = filteredList.size();
					filteredList.add(person);
					fireIntervalAdded(this, size, size);
				}
			}
		}
		
		private boolean acceptPerson(Person person){
			if(Objects.isNull(person)) return false;
			if(filter == null || filter.equals("")) return true;
			
			String name = person.getName();
			String workNumber = person.getWorkNumber();
			
			return name.indexOf(filter) >= 0 || workNumber.indexOf(filter) >= 0;
 		}
		
	}

}
