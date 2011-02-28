package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.DateTypeModel;
import com.dwarfeng.jier.mh4w.core.model.eum.DateType;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.DateTypeAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.DateTypeObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.CountDate;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.DateTypeFrameObverser;
import com.sun.glass.events.KeyEvent;
import javax.swing.ListSelectionModel;

public class DateTypeFrame extends JDialog implements MutilangSupported, ObverserSet<DateTypeFrameObverser>{
	
	private static final long serialVersionUID = -2100678924457813559L;

	/**观察器集合*/
	private final Set<DateTypeFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());

	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JList<ListEntry> list;
	private final JLabel yearLabel;
	private final JSpinner yearSpinner;
	private final JLabel monthLabel;
	private final JSpinner monthSpinner;
	private final JLabel dayLabel;
	private final JSpinner daySpinner;
	private final JButton saveButton;
	private final JButton loadButton;
	private final JButton submitButton;
	private final JButton clearButton;
	private final  JComboBox<DateType> comboBox;

	/*
	 * 各模型。
	 */
	private DateTypeModel dateTypeModel;
	
	/*
	 * 视图模型以及渲染器。
	 */
	private final MuaListModel<ListEntry> listModel = new MuaListModel<>();
	private final ComboBoxModel<DateType> comboBoxModel = new DefaultComboBoxModel<>(
			new DateType[]{DateType.WEEKEND, DateType.HOLIDAY});
	private final ListCellRenderer<Object> listRenderer = new DefaultListCellRenderer(){
		
		private static final long serialVersionUID = -1266465854726686323L;

		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			ListEntry entry = (ListEntry) value;
			setText(getLabel(entry.value.getLabelStringKey()) + " : " + FormatUtil.formatCountDate(entry.key));
			return this;
		};
	};
	private final ListCellRenderer<Object> comboBoxRenderer = new DefaultListCellRenderer(){
		
		private static final long serialVersionUID = -8395368370503307619L;

		@Override
		public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			//此处转换是安全的
			DateType dateType = (DateType) value;
			setText(getLabel(dateType.getLabelStringKey()));
			return this;
		};
	};
	
	/*
	 * 各模型的观察器。
	 */
	private final DateTypeObverser dateTypeObverser = new DateTypeAdapter() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.DateTypeAdapter#fireDatePut(com.dwarfeng.jier.mh4w.core.model.struct.CountDate, com.dwarfeng.jier.mh4w.core.model.eum.DateType)
		 */
		@Override
		public void fireDatePut(CountDate key, DateType value) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.add(new ListEntry(key, value));
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.DateTypeAdapter#fireDateRemoved(com.dwarfeng.jier.mh4w.core.model.struct.CountDate)
		 */
		@Override
		public void fireDateRemoved(CountDate key) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = findIndex(key);
					listModel.remove(index);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.DateTypeAdapter#fireDateChanged(com.dwarfeng.jier.mh4w.core.model.struct.CountDate, com.dwarfeng.jier.mh4w.core.model.eum.DateType, com.dwarfeng.jier.mh4w.core.model.eum.DateType)
		 */
		@Override
		public void fireDateChanged(CountDate key, DateType oldValue, DateType newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					int index = findIndex(key);
					listModel.set(index, new ListEntry(key, newValue));
				}
			});
		}
		
		private int findIndex(CountDate key){
			for(int i = 0 ; i < listModel.size() ; i ++){
				ListEntry entry = listModel.get(i);
				if(entry.key.equals(key)) return i;
			}
			throw new IllegalArgumentException("未能找到指定的统计日期");
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.DateTypeAdapter#fireDateCleared()
		 */
		@Override
		public void fireDateCleared() {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					listModel.clear();
				}
			});
		}
		
	};

	/**
	 * 新实例
	 */
	public DateTypeFrame() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言模型，不能为 <code>null</code>。
	 * @param dateTypeModel 指定的日期类型模型。
	 * @throws NullPointerException  入口参数为 <code>null</code>。
	 */
	public DateTypeFrame(Mutilang mutilang, DateTypeModel dateTypeModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");

		this.mutilang = mutilang;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireHideDateTypeFrame();
			}
		});
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle(getLabel(LabelStringKey.DateTypeFrame_1));
		setBounds(100, 100, 600, 400);
		setModal(true);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(listModel);
		list.setCellRenderer(listRenderer);
		list.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "remove");
		list.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_BACKSPACE, 0), "remove");
		list.getActionMap().put("remove", new AbstractAction() {
			
			private static final long serialVersionUID = 6031789320857062665L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ListEntry entry = list.getSelectedValue();
				if(Objects.nonNull(entry)){
					fireRemoveDateTypeEntry(entry.key);
				}
			}
		});
		scrollPane.setViewportView(list);
		
		JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_southPanel = new GridBagLayout();
		gbl_southPanel.columnWidths = new int[]{80, 80, 0, 80, 0, 80, 0, 0, 0, 0};
		gbl_southPanel.rowHeights = new int[]{0, 0};
		gbl_southPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_southPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		southPanel.setLayout(gbl_southPanel);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(comboBoxModel);
		comboBox.setRenderer(comboBoxRenderer);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		southPanel.add(comboBox, gbc_comboBox);
		
		yearSpinner = new JSpinner();
		yearSpinner.setModel(new SpinnerNumberModel(new Integer(1970), new Integer(1970), null, new Integer(1)));
		GridBagConstraints gbc_yearSpinner = new GridBagConstraints();
		gbc_yearSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_yearSpinner.insets = new Insets(0, 0, 0, 5);
		gbc_yearSpinner.gridx = 1;
		gbc_yearSpinner.gridy = 0;
		southPanel.add(yearSpinner, gbc_yearSpinner);
		
		yearLabel = new JLabel();
		yearLabel.setText(getLabel(LabelStringKey.DateTypeFrame_2));
		GridBagConstraints gbc_yearLabel = new GridBagConstraints();
		gbc_yearLabel.insets = new Insets(0, 0, 0, 5);
		gbc_yearLabel.gridx = 2;
		gbc_yearLabel.gridy = 0;
		southPanel.add(yearLabel, gbc_yearLabel);
		
		monthSpinner = new JSpinner();
		monthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		GridBagConstraints gbc_monthSpinner = new GridBagConstraints();
		gbc_monthSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_monthSpinner.insets = new Insets(0, 0, 0, 5);
		gbc_monthSpinner.gridx = 3;
		gbc_monthSpinner.gridy = 0;
		southPanel.add(monthSpinner, gbc_monthSpinner);
		
		monthLabel = new JLabel();
		monthLabel.setText(getLabel(LabelStringKey.DateTypeFrame_3));
		GridBagConstraints gbc_monthLabel = new GridBagConstraints();
		gbc_monthLabel.insets = new Insets(0, 0, 0, 5);
		gbc_monthLabel.gridx = 4;
		gbc_monthLabel.gridy = 0;
		southPanel.add(monthLabel, gbc_monthLabel);
		
		daySpinner = new JSpinner();
		daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		GridBagConstraints gbc_daySpinner = new GridBagConstraints();
		gbc_daySpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_daySpinner.insets = new Insets(0, 0, 0, 5);
		gbc_daySpinner.gridx = 5;
		gbc_daySpinner.gridy = 0;
		southPanel.add(daySpinner, gbc_daySpinner);
		
		dayLabel = new JLabel();
		dayLabel.setText(getLabel(LabelStringKey.DateTypeFrame_4));
		GridBagConstraints gbc_dayLabel = new GridBagConstraints();
		gbc_dayLabel.insets = new Insets(0, 0, 0, 5);
		gbc_dayLabel.gridx = 6;
		gbc_dayLabel.gridy = 0;
		southPanel.add(dayLabel, gbc_dayLabel);
		
		submitButton = new JButton();
		submitButton.setText(getLabel(LabelStringKey.DateTypeFrame_5));
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitDateTypeEntry();
			}
		});
		submitButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
		submitButton.getActionMap().put("submit", new AbstractAction() {
			
			private static final long serialVersionUID = -2288708590670849516L;

			@Override
			public void actionPerformed(ActionEvent e) {
				submitDateTypeEntry();
			}
		});
		
		GridBagConstraints gbc_submitButton = new GridBagConstraints();
		gbc_submitButton.insets = new Insets(0, 0, 0, 5);
		gbc_submitButton.gridx = 7;
		gbc_submitButton.gridy = 0;
		southPanel.add(submitButton, gbc_submitButton);
		
		JPanel eastPanel = new JPanel();
		getContentPane().add(eastPanel, BorderLayout.EAST);
		GridBagLayout gbl_eastPanel = new GridBagLayout();
		gbl_eastPanel.columnWidths = new int[]{0, 0};
		gbl_eastPanel.rowHeights = new int[]{0, 0, 30, 0, 0, 0};
		gbl_eastPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_eastPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		eastPanel.setLayout(gbl_eastPanel);
		
		clearButton = new JButton();
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireClearDateTypeEntry();
			}
		});
		clearButton.setText(getLabel(LabelStringKey.DateTypeFrame_6));
		GridBagConstraints gbc_clearButton = new GridBagConstraints();
		gbc_clearButton.fill = GridBagConstraints.BOTH;
		gbc_clearButton.insets = new Insets(0, 0, 5, 0);
		gbc_clearButton.gridx = 0;
		gbc_clearButton.gridy = 1;
		eastPanel.add(clearButton, gbc_clearButton);
		
		saveButton = new JButton();
		saveButton.setText(getLabel(LabelStringKey.DateTypeFrame_7));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireSaveDateTypeEntry();
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 2;
		eastPanel.add(saveButton, gbc_btnNewButton_2);
		
		loadButton = new JButton();
		loadButton.setText(getLabel(LabelStringKey.DateTypeFrame_8));
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireLoadDateTypeEntry();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 3;
		eastPanel.add(loadButton, gbc_btnNewButton_1);
		
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{comboBox, 
				((JSpinner.DefaultEditor)yearSpinner.getEditor()).getTextField(),
				((JSpinner.DefaultEditor)monthSpinner.getEditor()).getTextField(),
				((JSpinner.DefaultEditor)daySpinner.getEditor()).getTextField(),
				}));
		
		if(Objects.nonNull(dateTypeModel)){
			dateTypeModel.addObverser(dateTypeObverser);
			dateTypeModel.getLock().readLock().lock();
			try{
				for(Map.Entry<CountDate, DateType> entry : dateTypeModel.entrySet()){
					listModel.add(new ListEntry(entry.getKey(), entry.getValue()));
				}
			}finally {
				dateTypeModel.getLock().readLock().unlock();
			}
		}
		
		this.dateTypeModel = dateTypeModel;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<DateTypeFrameObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(DateTypeFrameObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(DateTypeFrameObverser obverser) {
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
		setTitle(getLabel(LabelStringKey.DateTypeFrame_1));
		
		yearLabel.setText(getLabel(LabelStringKey.DateTypeFrame_2));
		monthLabel.setText(getLabel(LabelStringKey.DateTypeFrame_3));
		dayLabel.setText(getLabel(LabelStringKey.DateTypeFrame_4));
		
		comboBox.repaint();
		list.repaint();
		
		submitButton.setText(getLabel(LabelStringKey.DateTypeFrame_5));
		clearButton.setText(getLabel(LabelStringKey.DateTypeFrame_6));
		saveButton.setText(getLabel(LabelStringKey.DateTypeFrame_7));
		loadButton.setText(getLabel(LabelStringKey.DateTypeFrame_8));
	}

	/**
	 * @return the dateTypeModel
	 */
	public DateTypeModel getDateTypeModel() {
		return dateTypeModel;
	}

	/**
	 * @param dateTypeModel the dateTypeModel to set
	 */
	public void setDateTypeModel(DateTypeModel dateTypeModel) {
		listModel.clear();
		
		if(Objects.nonNull(this.dateTypeModel)){
			this.dateTypeModel.removeObverser(dateTypeObverser);
		}
		
		if(Objects.nonNull(dateTypeModel)){
			dateTypeModel.addObverser(dateTypeObverser);
			dateTypeModel.getLock().readLock().lock();
			try{
				for(Map.Entry<CountDate, DateType> entry : dateTypeModel.entrySet()){
					listModel.add(new ListEntry(entry.getKey(), entry.getValue()));
				}
			}finally {
				dateTypeModel.getLock().readLock().unlock();
			}
		}
		
		this.dateTypeModel = dateTypeModel;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		if(Objects.nonNull(dateTypeModel)){
			dateTypeModel.removeObverser(dateTypeObverser);
		}
		super.dispose();
	}

	private void fireHideDateTypeFrame() {
		for(DateTypeFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireHideDateTypeFrame();
		}
	}

	private void fireSubmitDateTypeEntry(CountDate key, DateType value) {
		for(DateTypeFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSubmitDateTypeEntry(key, value);
		}
	}

	private void fireRemoveDateTypeEntry(CountDate key) {
		for(DateTypeFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRemoveDateTypeEntry(key);
		}
	}

	private void fireClearDateTypeEntry() {
		for(DateTypeFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireClearDateTypeEntry();
		}
	}

	private void fireSaveDateTypeEntry() {
		for(DateTypeFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSaveDateTypeEntry();
		}
	}

	private void fireLoadDateTypeEntry() {
		for(DateTypeFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoadDateTypeEntry();
		}
	}

	private void submitDateTypeEntry() {
		/*
		 * 以下的方法中，所有的强制转换均是安全的。
		 */
		DateType dateType = (DateType) comboBox.getSelectedItem();
		int year = (int) yearSpinner.getModel().getValue();
		int month = (int) monthSpinner.getModel().getValue();
		int day = (int) daySpinner.getModel().getValue();
		// 以上的方法中，所有的强制转换均是安全的。
		
		fireSubmitDateTypeEntry(new CountDate(year, month, day), dateType);
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

	private static class ListEntry{
		
		public final CountDate key;
		public final DateType value;
	
		public ListEntry(CountDate key, DateType value) {
			this.key = key;
			this.value = value;
		}
		
	}
	
}


