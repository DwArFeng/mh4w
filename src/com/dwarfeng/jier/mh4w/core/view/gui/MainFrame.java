package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.dwarfeng.dutil.basic.gui.swing.JImagePanel;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.StateModel;
import com.dwarfeng.jier.mh4w.core.model.eum.CountState;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageKey;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageSize;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectObverser;
import com.dwarfeng.jier.mh4w.core.model.obv.StateAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.StateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.ImageUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser;

/**
 * 程序的主界面。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class MainFrame extends JFrame implements MutilangSupported, ObverserSet<MainFrameObverser>{

	private static final long serialVersionUID = -2422775159586791544L;

	/**观察器集合*/
	private final Set<MainFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());

	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final Image xls_red;
	private final Image xls_green;
	private final Image detail_green;
	private final Image detail_red;
	private final Image detail_yellow;
	private final Image detail_purple;
	private final Image detail_gray;
	private final Image reset_blue;
	private final Image calendar_blue;
	private final Image attr_blue;
	private final Image program_icon;

	private final JImagePanel attendanceFilePanel;
	private final JImagePanel workticketFilePanel;
	private final JLabel attendanceLabel;
	private final JButton countButton;
	private final JLabel workticketLabel;
	private final JToggleButton detailButton;
	private final JButton resetButton;
	private final JButton calendarButton;
	private JButton attrButton;

	/*
	 * 非 final 域。
	 */
	private boolean attendanceClickLock = false;
	private boolean workticketClickLock = false;
	private boolean detailButtonAdjusting = false;
	private boolean outdateFlag = false;
	private CountState countState;
	private boolean isCounting = false;
	private boolean countReadyFlag = false;
	/*
	 * 各模型。
	 */
	private FileSelectModel fileSelectModel;
	private StateModel stateModel;
	
	/*
	 * 各模型的观察器。
	 */
	private final FileSelectObverser fileSelectObverser = new FileSelectAdapter() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FileSelectAdapter#fireAttendanceFileChanged(java.io.File, java.io.File)
		 */
		@Override
		public void fireAttendanceFileChanged(File oldValue, File newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					if(Objects.nonNull(newValue)){
						attendanceFilePanel.setImage(xls_green);
						attendanceFilePanel.setToolTipText(newValue.getAbsolutePath());
					}else {
						attendanceFilePanel.setImage(xls_red);
						attendanceFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
					}
				}
			});

		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FileSelectAdapter#fireWorkticketFileChanged(java.io.File, java.io.File)
		 */
		@Override
		public void fireWorkticketFileChanged(File oldValue, File newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					if(Objects.nonNull(newValue)){
						workticketFilePanel.setImage(xls_green);
						workticketFilePanel.setToolTipText(newValue.getAbsolutePath());
					}else {
						workticketFilePanel.setImage(xls_red);
						workticketFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
					}
				}
			});
		}
		
	};
	private final StateObverser stateObverser = new StateAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.StateAdapter#fireReadyForCountChanged(boolean)
		 */
		@Override
		public void fireReadyForCountChanged(boolean newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					countReadyFlag = newValue;
					checkCountButton();
				}
			});
		};

		/* 
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.StateAdapter#fireCountStateChanged(com.dwarfeng.jier.mh4w.core.model.eum.CountState, com.dwarfeng.jier.mh4w.core.model.eum.CountState)
		 */
		@Override
		public void fireCountStateChanged(CountState oldValue, CountState newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					countState = newValue;
					checkDetailButton();
				}
			});
		}

		/*
		 *  (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.StateAdapter#fireCountResultOutdatedChanged(boolean)
		 */
		@Override
		public void fireCountResultOutdatedChanged(boolean newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					outdateFlag = newValue;
					checkDetailButton();
				}
			});
		};
		
	};
	/**
	 * 新实例。
	 */
	public MainFrame() {
		this(Constants.getDefaultLabelMutilang(), new DefaultVersion.Builder().build(), null, null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param version 指定的版本，不能为 <code>null</code>。
	 * @param fileSelectModel 指定的文件选择模型。
	 * @param stateModel 指定的状态模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public MainFrame(Mutilang mutilang, Version version, FileSelectModel fileSelectModel, StateModel stateModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		Objects.requireNonNull(version, "入口参数 version 不能为 null。");

		this.mutilang = mutilang;
		
		xls_green = ImageUtil.getImage(ImageKey.XLS_GREEN, ImageSize.XLS_ICON);
		xls_red = ImageUtil.getImage(ImageKey.XLS_RED, ImageSize.XLS_ICON);
		detail_green = ImageUtil.getImage(ImageKey.DETAIL_GREEN, ImageSize.CONTROL_AREA_ICON);
		detail_red = ImageUtil.getImage(ImageKey.DETAIL_RED, ImageSize.CONTROL_AREA_ICON);
		detail_yellow = ImageUtil.getImage(ImageKey.DETAIL_YELLOW, ImageSize.CONTROL_AREA_ICON);
		detail_purple = ImageUtil.getImage(ImageKey.DETAIL_PURPLE, ImageSize.CONTROL_AREA_ICON);
		detail_gray = ImageUtil.getImage(ImageKey.DETAIL_GRAY, ImageSize.CONTROL_AREA_ICON);
		reset_blue =  ImageUtil.getImage(ImageKey.RESET_BLUE, ImageSize.CONTROL_AREA_ICON);
		calendar_blue =  ImageUtil.getImage(ImageKey.CALENDAR_BLUE, ImageSize.CONTROL_AREA_ICON);
		attr_blue =  ImageUtil.getImage(ImageKey.ATTR_BLUE, ImageSize.CONTROL_AREA_ICON);
		program_icon = ImageUtil.getImage(ImageKey.PROGRAM_ICON, ImageSize.ICON_SUPER_LARGE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireFireWindowClosing();
			}
		});
		
		setResizable(false);
		setBounds(100, 100, 427, 295);
		setTitle(getLabel(LabelStringKey.MainFrame_5) + " - 丰沛_211618");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(program_icon);
		
		getContentPane().setLayout(null);
		
		attendanceFilePanel = new JImagePanel();
		attendanceFilePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(! attendanceClickLock){
					attendanceClickLock = true;
					fireSelectAttendanceFile();
				}
			}
		});
		attendanceFilePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		attendanceFilePanel.setImage(xls_red);
		attendanceFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
		attendanceFilePanel.setBounds(25, 17, 150, 150);
		getContentPane().add(attendanceFilePanel);
		
		workticketFilePanel = new JImagePanel();
		workticketFilePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(! workticketClickLock){
					workticketClickLock = true;
					fireSelectWorkticketFile();
				}
			}
		});
		workticketFilePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		workticketFilePanel.setImage(xls_red);
		workticketFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
		workticketFilePanel.setBounds(245, 17, 150, 150);
		getContentPane().add(workticketFilePanel);
		
		workticketLabel = new JLabel();
		workticketLabel.setText(getLabel(LabelStringKey.MainFrame_2));
		workticketLabel.setHorizontalAlignment(SwingConstants.CENTER);
		workticketLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		workticketLabel.setBounds(245, 167, 150, 25);
		getContentPane().add(workticketLabel);
		
		countButton = new JButton();
		countButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isCounting = true;
				checkCountButton();
				fireCount();
			}
		});
		countButton.setEnabled(false);
		countButton.setText(getLabel(LabelStringKey.MainFrame_4));
		countButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		countButton.setBounds(245, 200, 150, 40);
		getContentPane().add(countButton);
		
		detailButton = new JToggleButton();
		detailButton.setToolTipText(getLabel(LabelStringKey.MainFrame_10));
		detailButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(! detailButtonAdjusting){
					if(detailButton.isSelected()){
						fireShowDetail();
					}else{
						fireHideDetail();
					}
				}
			}
		});
		detailButton.setToolTipText((String) null);
		detailButton.setIcon(new ImageIcon(detail_purple));
		detailButton.setBounds(190, 200, 40, 40);
		getContentPane().add(detailButton);
		
		attendanceLabel = new JLabel();
		attendanceLabel.setText(getLabel(LabelStringKey.MainFrame_1));
		attendanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		attendanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		attendanceLabel.setBounds(25, 167, 150, 25);
		getContentPane().add(attendanceLabel);
		
		calendarButton = new JButton();
		calendarButton.setToolTipText(getLabel(LabelStringKey.MainFrame_8));
		calendarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireShowDateTypeFrame();
			}
		});
		calendarButton.setIcon(new ImageIcon(calendar_blue));
		calendarButton.setToolTipText((String) null);
		calendarButton.setBounds(80, 200, 40, 40);
		getContentPane().add(calendarButton);
		
		attrButton = new JButton();
		attrButton.setToolTipText(getLabel(LabelStringKey.MainFrame_6));
		attrButton.setIcon(new ImageIcon(attr_blue));
		attrButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireShowAttrFrame();
			}
		});
		attrButton.setOpaque(true);
		attrButton.setBounds(25, 200, 40, 40);
		getContentPane().add(attrButton);
		
		JLabel versionLabel = new JLabel(version.getLongName());
		versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		versionLabel.setBounds(25, 250, 370, 15);
		getContentPane().add(versionLabel);
		
		resetButton = new JButton();
		resetButton.setToolTipText(getLabel(LabelStringKey.MainFrame_9));
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireCountReset();
			}
		});
		resetButton.setIcon(new ImageIcon(reset_blue));
		resetButton.setToolTipText((String) null);
		resetButton.setBounds(135, 200, 40, 40);
		getContentPane().add(resetButton);
		
		//设置文件选择模型
		if(Objects.nonNull(fileSelectModel)){
			fileSelectModel.addObverser(fileSelectObverser);
			fileSelectModel.getLock().readLock().lock();
			try{
				if(Objects.nonNull(fileSelectModel.getAttendanceFile())){
					attendanceFilePanel.setImage(xls_green);
					attendanceFilePanel.setToolTipText(fileSelectModel.getAttendanceFile().getAbsolutePath());
				}
				if(Objects.nonNull(fileSelectModel.getWorkticketFile())){
					workticketFilePanel.setImage(xls_green);
					workticketFilePanel.setToolTipText(fileSelectModel.getWorkticketFile().getAbsolutePath());
				}
			}finally {
				fileSelectModel.getLock().readLock().unlock();
			}
		}
		
		this.fileSelectModel = fileSelectModel;
		
		//设置统计准备模型
		if(Objects.nonNull(stateModel)){
			stateModel.addObverser(stateObverser);
			stateModel.getLock().readLock().lock();
			try{
				outdateFlag = stateModel.isCountResultOutdated();
				countState = stateModel.getCountState();
				countReadyFlag = stateModel.isReadyForCount();
			}finally {
				stateModel.getLock().readLock().unlock();
			}
			checkDetailButton();
			checkCountButton();
		}
		
		this.stateModel = stateModel;
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
		setTitle(getLabel(LabelStringKey.MainFrame_5));
		
		attendanceLabel.setText(getLabel(LabelStringKey.MainFrame_1));
		workticketLabel.setText(getLabel(LabelStringKey.MainFrame_2));
		
		if(Objects.isNull(fileSelectModel.getAttendanceFile())){
			attendanceFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
		}
		if(Objects.isNull(fileSelectModel.getWorkticketFile())){
			workticketFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
		}
		
		if(isCounting){
			countButton.setText(getLabel(LabelStringKey.MainFrame_7));
		}else{
			countButton.setText(getLabel(LabelStringKey.MainFrame_4));
		}
		attrButton.setToolTipText(getLabel(LabelStringKey.MainFrame_6));
		calendarButton.setToolTipText(getLabel(LabelStringKey.MainFrame_8));
		resetButton.setToolTipText(getLabel(LabelStringKey.MainFrame_9));
		detailButton.setToolTipText(getLabel(LabelStringKey.MainFrame_10));

	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<MainFrameObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(MainFrameObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(MainFrameObverser obverser) {
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
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		if(Objects.nonNull(fileSelectModel)){
			fileSelectModel.removeObverser(fileSelectObverser);
		}
		if(Objects.nonNull(stateModel)){
			stateModel.removeObverser(stateObverser);
		}
		super.dispose();
	}

	/**
	 * @return the fileSelectModel
	 */
	public FileSelectModel getFileSelectModel() {
		return fileSelectModel;
	}

	/**
	 * @param fileSelectModel the fileSelectModel to set
	 */
	public void setFileSelectModel(FileSelectModel fileSelectModel) {
		attendanceFilePanel.setImage(xls_red);
		attendanceFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
		workticketFilePanel.setImage(xls_red);
		workticketFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
		
		if(Objects.nonNull(this.fileSelectModel)){
			this.fileSelectModel.removeObverser(fileSelectObverser);
		}
		
		if(Objects.nonNull(fileSelectModel)){
			fileSelectModel.addObverser(fileSelectObverser);
			fileSelectModel.getLock().readLock().lock();
			try{
				if(Objects.nonNull(fileSelectModel.getAttendanceFile())){
					attendanceFilePanel.setImage(xls_green);
					attendanceFilePanel.setToolTipText(fileSelectModel.getAttendanceFile().getAbsolutePath());
				}
				if(Objects.nonNull(fileSelectModel.getWorkticketFile())){
					workticketFilePanel.setImage(xls_green);
					workticketFilePanel.setToolTipText(fileSelectModel.getWorkticketFile().getAbsolutePath());
				}
			}finally {
				fileSelectModel.getLock().readLock().unlock();
			}
		}
		
		this.fileSelectModel = fileSelectModel;
	}
	
	/**
	 * @return the countReadyModel
	 */
	public StateModel getStateModel() {
		return stateModel;
	}

	/**
	 * @param stateModel the stateModel to set
	 */
	public void setStateModel(StateModel stateModel) {
		countState = CountState.NOT_START;
		outdateFlag = false;
		countReadyFlag = false;
		checkDetailButton();
		checkCountButton();
		
		if(Objects.nonNull(this.stateModel)){
			this.stateModel.removeObverser(stateObverser);
		}
		
		if(Objects.nonNull(stateModel)){
			stateModel.addObverser(stateObverser);
			stateModel.getLock().readLock().lock();
			try{
				outdateFlag = stateModel.isCountResultOutdated();
				countState = stateModel.getCountState();
				countReadyFlag = stateModel.isReadyForCount();
			}finally {
				stateModel.getLock().readLock().unlock();
			}
			checkDetailButton();
			checkCountButton();
		}
		
		this.stateModel = stateModel;
	}

	/**
	 * 解除考勤文件面板的点击锁定。
	 */
	public void attendanceClickUnlock(){
		attendanceClickLock = false;
	}
	
	/**
	 * 解除工时文件面本的点击锁定。
	 */
	public void workticketClickUnlock(){
		workticketClickLock = false;
	}

	/**
	 * 通知主面板统计过程已经结束。
	 */
	public void knockCountFinished() {
		isCounting = false;
		checkCountButton();
	}

	/**
	 * 设置详细按钮的选择状态。
	 * @param value 指定的选择状态。
	 * @param isAdjusting 是否属于调整。
	 */
	public void setDetailButtonSelect(boolean value, boolean isAdjusting) {
		try{
			detailButtonAdjusting = isAdjusting;
			detailButton.setSelected(value);
		}finally {
			detailButtonAdjusting = false;
		}
	}

	private void fireFireWindowClosing() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWindowClosing();
		}
	}

	private void fireSelectAttendanceFile() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSelectAttendanceFile();
		}
	}

	private void fireSelectWorkticketFile() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSelectWorkticketFile();
		}
	}

	private void fireCountReset() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCountReset();
		}
	}

	private void fireHideDetail() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireHideDetail();
		}
	}

	private void fireShowDetail() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireShowDetail();
		}
	}
	
	private void fireCount(){
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCount();
		}
	}
	
	private void fireShowAttrFrame(){
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireShowAttrFrame();
		}
	}
	
	private void fireShowDateTypeFrame(){
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireShowDateTypeFrame();
		}
	}

	private void checkDetailButton() {
		if(countState.equals(CountState.NOT_START)){
			detailButton.setIcon(new ImageIcon(detail_purple));
		}else if(outdateFlag){
			detailButton.setIcon(new ImageIcon(detail_gray));
		}else{
			switch (countState) {
			case NOT_START:
				detailButton.setIcon(new ImageIcon(detail_purple));
				break;
			case STARTED_ERROR:
				detailButton.setIcon(new ImageIcon(detail_red));
				break;
			case STARTED_EXPORTED:
				detailButton.setIcon(new ImageIcon(detail_green));
				break;
			case STARTED_WAITING:
				detailButton.setIcon(new ImageIcon(detail_yellow));
				break;
			}
		}
	}
	
	private void checkCountButton(){
		if(! isCounting && countReadyFlag){
			countButton.setEnabled(true);
		}else{
			countButton.setEnabled(false);
		}
		
		if(isCounting){
			countButton.setText(getLabel(LabelStringKey.MainFrame_7));
		}else{
			countButton.setText(getLabel(LabelStringKey.MainFrame_4));
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
	
}
