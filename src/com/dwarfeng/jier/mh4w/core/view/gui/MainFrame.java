package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.dwarfeng.dutil.basic.gui.swing.JImagePanel;
import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectModel;
import com.dwarfeng.jier.mh4w.core.model.cm.StateModel;
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
	
	/**观察器集合*/
	private final Set<MainFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());

	/**多语言接口*/
	private Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final Image xls_red;
	private final Image xls_green;
	private final JImagePanel attendanceFilePanel;
	private final JImagePanel workticketFilePanel;
	private final JLabel attendanceLabel;
	private final JButton countButton;

	private final JLabel workticketLabel;
	
	/*
	 * 非 final 域。
	 */
	private boolean attendanceClickLock = false;
	private boolean workticketClickLock = false;
	
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
	private final StateObverser countReadyObverser = new StateAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.StateAdapter#fireReadyForCountChanged(boolean)
		 */
		@Override
		public void fireReadyForCountChanged(boolean newValue) {
			Mh4wUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					countButton.setEnabled(newValue);
				}
			});
		};
		
	};
	
	
	/**
	 * 新实例。
	 */
	public MainFrame() {
		this(Constants.getDefaultLabelMutilang(), null, null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang
	 */
	public MainFrame(
			Mutilang mutilang, 
			FileSelectModel fileSelectModel,
			StateModel stateModel
			) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		xls_green = ImageUtil.getImage(ImageKey.XLS_GREEN, ImageSize.XLS_ICON);
		xls_red = ImageUtil.getImage(ImageKey.XLS_RED, ImageSize.XLS_ICON);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireFireWindowClosing();
			}
		});
		
		setResizable(false);
		setBounds(100, 100, 427, 295);
		
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
		workticketFilePanel.setBounds(245, 17, 150, 150);
		getContentPane().add(workticketFilePanel);
		
		workticketLabel = new JLabel();
		workticketLabel.setText(getLabel(LabelStringKey.MainFrame_2));
		workticketLabel.setHorizontalAlignment(SwingConstants.CENTER);
		workticketLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		workticketLabel.setBounds(245, 167, 150, 25);
		getContentPane().add(workticketLabel);
		
		countButton = new JButton();
		countButton.setText(getLabel(LabelStringKey.MainFrame_4));
		countButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
		countButton.setBounds(245, 200, 150, 40);
		getContentPane().add(countButton);
		
		JToggleButton toggleButton = new JToggleButton();
		toggleButton.setToolTipText((String) null);
		toggleButton.setBounds(190, 200, 40, 40);
		getContentPane().add(toggleButton);
		
		attendanceLabel = new JLabel();
		attendanceLabel.setText(getLabel(LabelStringKey.MainFrame_1));
		attendanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		attendanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		attendanceLabel.setBounds(25, 167, 150, 25);
		getContentPane().add(attendanceLabel);
		
		JButton button_1 = new JButton();
		button_1.setToolTipText((String) null);
		button_1.setBounds(80, 200, 40, 40);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton();
		button_2.setToolTipText((String) null);
		button_2.setOpaque(true);
		button_2.setBounds(25, 200, 40, 40);
		getContentPane().add(button_2);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(25, 250, 370, 15);
		getContentPane().add(lblNewLabel);
		
		JButton button_3 = new JButton();
		button_3.setToolTipText((String) null);
		button_3.setBounds(135, 200, 40, 40);
		getContentPane().add(button_3);
		
		//设置文件选择模型
		if(Objects.nonNull(fileSelectModel)){
			fileSelectModel.addObverser(fileSelectObverser);
			if(Objects.nonNull(fileSelectModel.getAttendanceFile())){
				attendanceFilePanel.setImage(xls_green);
				attendanceFilePanel.setToolTipText(fileSelectModel.getAttendanceFile().getAbsolutePath());
			}else{
				attendanceFilePanel.setImage(xls_red);
				attendanceFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
			}
			if(Objects.nonNull(fileSelectModel.getWorkticketFile())){
				workticketFilePanel.setImage(xls_green);
				workticketFilePanel.setToolTipText(fileSelectModel.getWorkticketFile().getAbsolutePath());
			}else{
				workticketFilePanel.setImage(xls_red);
				workticketFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
			}
		}
		
		this.fileSelectModel = fileSelectModel;
		
		//设置统计准备模型
		if(Objects.nonNull(stateModel)){
			stateModel.addObverser(countReadyObverser);
			countButton.setEnabled(stateModel.isReadyForCount());
		}else{
			countButton.setEnabled(false);
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		if(Objects.isNull(mutilang)) return false;
		if(Objects.equals(mutilang, this.mutilang)) return false;
		this.mutilang = mutilang;
		
		//更新各标签的文本。
		attendanceLabel.setText(getLabel(LabelStringKey.MainFrame_1));
		workticketLabel.setText(getLabel(LabelStringKey.MainFrame_2));

		return true;
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
		if(Objects.nonNull(this.fileSelectModel)){
			this.fileSelectModel.removeObverser(fileSelectObverser);
		}
		if(Objects.nonNull(fileSelectModel)){
			fileSelectModel.addObverser(fileSelectObverser);
			if(Objects.nonNull(fileSelectModel.getAttendanceFile())){
				attendanceFilePanel.setImage(xls_green);
				attendanceFilePanel.setToolTipText(fileSelectModel.getAttendanceFile().getAbsolutePath());
			}else{
				attendanceFilePanel.setImage(xls_red);
				attendanceFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
			}
			if(Objects.nonNull(fileSelectModel.getWorkticketFile())){
				workticketFilePanel.setImage(xls_green);
				workticketFilePanel.setToolTipText(fileSelectModel.getWorkticketFile().getAbsolutePath());
			}else{
				workticketFilePanel.setImage(xls_red);
				workticketFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
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
	public void setCountReadyModel(StateModel stateModel) {
		if(Objects.nonNull(this.stateModel)){
			this.stateModel.removeObverser(countReadyObverser);
		}
		
		if(Objects.nonNull(stateModel)){
			stateModel.addObverser(countReadyObverser);
			countButton.setEnabled(stateModel.isReadyForCount());
		}else{
			countButton.setEnabled(false);
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

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
}
