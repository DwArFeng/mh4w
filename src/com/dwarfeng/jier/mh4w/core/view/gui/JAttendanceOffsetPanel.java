package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageKey;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageSize;
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
import com.dwarfeng.jier.mh4w.core.util.ImageUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelObverser;
import com.sun.glass.events.KeyEvent;

public class JAttendanceOffsetPanel extends JPanel implements MutilangSupported, ObverserSet<AttendanceOffsetPanelObverser> {

	private static final long serialVersionUID = -469470576808053138L;

	/**personSelectTextField所允许的最小宽度*/
	private static final int MIN_PERSON_SELECT_TEXT_FIELD_WIDTH = 150;
	
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
	private final JLabel timeLabel;
	private final JLabel descritionLabel;
	private final JButton clearButton;
	private final JButton loadButton;
	private final JButton saveButton;
	private final JPopupMenu popup;
	private final JPersonPopupPanel personPanel;
	private final JTextField personSelectTextField;
	private final JButton personSelectButton;
	private final JPanel panel;
	private final Image arrow;
	
	/**
	 * 非 final 域。
	 */
	private Person selectedPerson = null;
	private String filterString = "";
	private boolean textAdjustFlag = false;
	
	/*
	 * 各模型。
	 */
	private DataListModel<AttendanceOffset> attendanceOffsetModel;
	private DataListModel<CountResult> countResultModel;
	
	/*
	 * 视图模型以及渲染
	 */
	private final List<Person> personList = new ArrayList<>();
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
	private final ListCellRenderer<Object> listRenderer = new DefaultListCellRenderer(){
		
		private static final long serialVersionUID = 8124941868994157832L;

		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			//此处转换是安全的。
			setText(Objects.isNull(value) ? "" : FormatUtil.formatPerson((Person) value));
			return this;
		};
		
	};
	private final MuaListModel<Person> listModel = new MuaListModel<>();
	
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
					personList.add(value.getPerson());
					adjustWork();
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
					
					personList.set(index, newValue.getPerson());
					adjustWork();
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
					personList.remove(index);
					adjustWork();
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
					personList.clear();
					adjustWork();
				}
			});
		}
		
		private void adjustWork(){
			textAdjustFlag = true;
			personSelectTextField.setText("");
			textAdjustFlag = false;
			maySelectFirstPerson();
			fitPersonSelectTextFieldWidth();
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
		
		arrow = ImageUtil.getImage(ImageKey.ARROW, ImageSize.ICON_SMALL);
		
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

		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 8, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{16, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		personSelectTextField = new JTextField();
		personSelectTextField.setAutoscrolls(false);
		personSelectTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}
			private void update(){
				if(textAdjustFlag) return;
				filterString = personSelectTextField.getText();
				updateFilter();
				maySelectFirstPerson();
				if(! popup.isVisible()){
					showPersonPanel();
				}
			}
		});
		personSelectTextField.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "previousPerson");
		personSelectTextField.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "nextPerson");
		personSelectTextField.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "confirmPerson");
		personSelectTextField.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_BACKSPACE, InputEvent.CTRL_MASK), "resetPerson");
		personSelectTextField.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "hidePopup");
		personSelectTextField.getActionMap().put("previousPerson", new AbstractAction() {
			private static final long serialVersionUID = -7236020602549865514L;
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = personPanel.getList().getSelectedIndex();
				if(index > 0){
					personPanel.getList().setSelectedIndex(index - 1);
					personPanel.getList().ensureIndexIsVisible(index - 1);
				}
			}
		});
		personSelectTextField.getActionMap().put("nextPerson", new AbstractAction() {
			private static final long serialVersionUID = -5891070508133564157L;
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = personPanel.getList().getSelectedIndex();
				if(index < listModel.getSize() - 1){
					personPanel.getList().setSelectedIndex(index + 1);
					personPanel.getList().ensureIndexIsVisible(index + 1);
				}
			}
		});
		personSelectTextField.getActionMap().put("confirmPerson", new AbstractAction() {
			private static final long serialVersionUID = 7137591399348273480L;
			@Override
			public void actionPerformed(ActionEvent e) {
				Person person = personPanel.getList().getSelectedValue();
				if(Objects.nonNull(person)){
					selectedPerson = person;
					textAdjustFlag = true;
					personSelectTextField.setText(FormatUtil.formatPerson(person));
					textAdjustFlag = false;
					if(popup.isVisible()){
						popup.setVisible(false);
					}
				}
			}
		});
		personSelectTextField.getActionMap().put("resetPerson", new AbstractAction() {
			private static final long serialVersionUID = -7675696809088020204L;
			@Override
			public void actionPerformed(ActionEvent e) {
				textAdjustFlag = true;
				personSelectTextField.setText("");
				textAdjustFlag = false;
				filterString = "";
				updateFilter();
				maySelectFirstPerson();
				if(! popup.isVisible()){
					showPersonPanel();
				}
			}
		});
		personSelectTextField.getActionMap().put("hidePopup", new AbstractAction() {
			private static final long serialVersionUID = -852449548511340389L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(popup.isVisible()){
					popup.setVisible(false);
				}
			}
		});
		
		GridBagConstraints gbc_personSelectTextField = new GridBagConstraints();
		gbc_personSelectTextField.insets = new Insets(0, 0, 5, 0);
		gbc_personSelectTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_personSelectTextField.gridx = 0;
		gbc_personSelectTextField.gridy = 0;
		panel.add(personSelectTextField, gbc_personSelectTextField);
		
		personSelectButton = new JButton();
		personSelectButton.setBorder(null);
		personSelectButton.setIcon(new ImageIcon(arrow));
		personSelectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textAdjustFlag = true;
				personSelectTextField.setText("");
				textAdjustFlag = false;
				filterString = "";
				updateFilter();
				maySelectFirstPerson();
				if(! popup.isVisible()){
					showPersonPanel();
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		panel.add(personSelectButton, gbc_btnNewButton);
		
		timeLabel = new JLabel();
		timeLabel.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_5));
		GridBagConstraints gbc_timeLabel = new GridBagConstraints();
		gbc_timeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_timeLabel.anchor = GridBagConstraints.EAST;
		gbc_timeLabel.gridx = 2;
		gbc_timeLabel.gridy = 0;
		panel.add(timeLabel, gbc_timeLabel);
		
		timeTextField = new JTextField();
		GridBagConstraints gbc_timeTextField = new GridBagConstraints();
		gbc_timeTextField.insets = new Insets(0, 0, 5, 0);
		gbc_timeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeTextField.gridx = 3;
		gbc_timeTextField.gridy = 0;
		panel.add(timeTextField, gbc_timeTextField);
		timeTextField.setColumns(10);
		
		submitButton = new JButton();
		submitButton.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_4));
		submitButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
		submitButton.getActionMap().put("submit", new AbstractAction() {
			private static final long serialVersionUID = 7760542975339282553L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Objects.isNull(selectedPerson)) return;
				fireSubmitAttendanceOffset(new DefaultUnsafeAttendanceOffset(selectedPerson.getName(), 
						selectedPerson.getDepartment(), selectedPerson.getWorkNumber(), timeTextField.getText(), 
						descriptionTextField.getText()));
			}
		});
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Objects.isNull(selectedPerson)) return;
				fireSubmitAttendanceOffset(new DefaultUnsafeAttendanceOffset(selectedPerson.getName(),
						selectedPerson.getDepartment(), selectedPerson.getWorkNumber(), timeTextField.getText(), 
						descriptionTextField.getText()));
			}
		});
		GridBagConstraints gbc_submitButton = new GridBagConstraints();
		gbc_submitButton.gridwidth = 2;
		gbc_submitButton.insets = new Insets(0, 0, 0, 5);
		gbc_submitButton.fill = GridBagConstraints.BOTH;
		gbc_submitButton.gridx = 0;
		gbc_submitButton.gridy = 1;
		panel.add(submitButton, gbc_submitButton);
		
		descritionLabel = new JLabel();
		descritionLabel.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_6));
		GridBagConstraints gbc_descritionLabel = new GridBagConstraints();
		gbc_descritionLabel.insets = new Insets(0, 0, 0, 5);
		gbc_descritionLabel.anchor = GridBagConstraints.EAST;
		gbc_descritionLabel.gridx = 2;
		gbc_descritionLabel.gridy = 1;
		panel.add(descritionLabel, gbc_descritionLabel);
		
		descriptionTextField = new JTextField();
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descriptionTextField.gridx = 3;
		gbc_descriptionTextField.gridy = 1;
		panel.add(descriptionTextField, gbc_descriptionTextField);
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
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.fill = GridBagConstraints.BOTH;
		gbc_saveButton.insets = new Insets(0, 0, 5, 0);
		gbc_saveButton.gridx = 0;
		gbc_saveButton.gridy = 2;
		panel_1.add(saveButton, gbc_saveButton);
		
		loadButton = new JButton();
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireLoadAttendanceOffset();
			}
		});
		loadButton.setText(getLabel(LabelStringKey.JAttendanceOffsetPanel_9));
		GridBagConstraints gbc_btnNewButton1 = new GridBagConstraints();
		gbc_btnNewButton1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton1.gridx = 0;
		gbc_btnNewButton1.gridy = 3;
		panel_1.add(loadButton, gbc_btnNewButton1);
		
		personPanel = new JPersonPopupPanel();
		personPanel.setPreferredSize(new Dimension(personSelectButton.getPreferredSize().width, 
				personPanel.getPreferredSize().height));
		
		popup = new JPopupMenu();
		popup.setFocusable(false);
		popup.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				personSelectTextField.requestFocus();
			}
		});
		popup.add(personPanel);
		
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
					personList.add(countResult.getPerson());
				}
				fitPersonSelectTextFieldWidth();
				updateFilter();
				maySelectFirstPerson();
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
		personList.clear();
		listModel.clear();
		textAdjustFlag = true;
		personSelectTextField.setText("");
		textAdjustFlag = false;
		filterString = "";
		
		if(Objects.nonNull(this.countResultModel)){
			this.countResultModel.removeObverser(countResultObverser);
		}
		
		if(Objects.nonNull(countResultModel)){
			countResultModel.addObverser(countResultObverser);
			countResultModel.getLock().readLock().lock();
			try{
				for(CountResult countResult : countResultModel){
					personList.add(countResult.getPerson());
				}
				fitPersonSelectTextFieldWidth();
				updateFilter();
				maySelectFirstPerson();
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

	private void fitPersonSelectTextFieldWidth(){
		int max = MIN_PERSON_SELECT_TEXT_FIELD_WIDTH;
		for(Person person : personList){
			if(Objects.isNull(person)) continue;
			String str = FormatUtil.formatPerson(person);
			int width = personPanel.getFontMetrics(personPanel.getFont()).stringWidth(str);
			max = Math.max(width, max);
		}
		max += 30;
		personSelectTextField.setPreferredSize(new Dimension(max, personSelectTextField.getPreferredSize().height));
		personPanel.setPreferredSize(new Dimension(max + personSelectButton.getPreferredSize().width - 4, 
				personPanel.getPreferredSize().height));
		personSelectTextField.revalidate();
		panel.revalidate();
	}
	
	private void updateFilter(){
		listModel.clear();
		for(Person person : personList){
			if(Objects.isNull(person)) continue;
			String str = FormatUtil.formatPerson(person);
			if(str.indexOf(filterString) >= 0){
				listModel.add(person);
			}
		}
	}
	
	private void maySelectFirstPerson(){
		if(listModel.size() >= 0){
			personPanel.getList().setSelectedIndex(0);
			personPanel.getList().ensureIndexIsVisible(0);
		}
	}
	
	private void showPersonPanel(){
		Point point = SwingUtilities.convertPoint(personSelectTextField, 0, 0, this);
		popup.show(this, point.x, point.y - personPanel.getPreferredSize().height-10);
	}
	
	private final class JPersonPopupPanel extends JPanel{
		
		private static final long serialVersionUID = 3811207208095246283L;
		
		private final JList<Person> list;
		
		public JPersonPopupPanel() {
			
			setLayout(new BorderLayout());
			
			JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.CENTER);
			
			list = new JList<>();
			list.setModel(listModel);
			list.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2){
						Person person = list.getSelectedValue();
						if(Objects.nonNull(person)){
							selectedPerson = person;
							textAdjustFlag = true;
							personSelectTextField.setText(FormatUtil.formatPerson(person));
							textAdjustFlag = false;
							if(popup.isVisible()){
								popup.setVisible(false);
							}
						}
					}
				}
			});
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setModel(listModel);
			list.setCellRenderer(listRenderer);
			list.setModel(listModel);
			
			scrollPane.getViewport().setView(list);
		}

		public JList<Person> getList() {
			return list;
		}
		
	}

}
