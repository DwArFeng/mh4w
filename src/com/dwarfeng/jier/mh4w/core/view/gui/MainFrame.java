package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.Font;
import java.awt.Image;
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
import com.dwarfeng.jier.mh4w.core.model.eum.ImageKey;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageSize;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.ImageUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser;
import java.io.File;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ����������档
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class MainFrame extends JFrame implements MutilangSupported, ObverserSet<MainFrameObverser>{
	
	/**�۲�������*/
	private final Set<MainFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());

	/**�����Խӿ�*/
	private Mutilang mutilang;
	
	/*
	 * final ��
	 */
	private final Image xls_red;
	private final Image xls_green;
	private final JImagePanel attendanceFilePanel;
	private final JImagePanel workticketFilePanel;
	private final JLabel attendanceLabel;
	private final JLabel workticketLabel;

	/*
	 * ��ģ�͡�
	 */
	private FileSelectModel fileSelectModel;
	
	/*
	 * ��ģ�͵Ĺ۲�����
	 */
	private final FileSelectObverser fileSelectObverser = new FileSelectAdapter() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FileSelectAdapter#fireAttendanceFileChanged(java.io.File, java.io.File)
		 */
		@Override
		public void fireAttendanceFileChanged(File oldValue, File newValue) {
			if(Objects.nonNull(newValue)){
				attendanceFilePanel.setImage(xls_green);
				attendanceFilePanel.setToolTipText(newValue.getAbsolutePath());
			}else {
				attendanceFilePanel.setImage(xls_red);
				attendanceFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FileSelectAdapter#fireWorkticketFileChanged(java.io.File, java.io.File)
		 */
		@Override
		public void fireWorkticketFileChanged(File oldValue, File newValue) {
			if(Objects.nonNull(newValue)){
				workticketFilePanel.setImage(xls_green);
				workticketFilePanel.setToolTipText(newValue.getAbsolutePath());
			}else {
				workticketFilePanel.setImage(xls_red);
				workticketFilePanel.setToolTipText(getLabel(LabelStringKey.MainFrame_3));
			}
		}
		
	};
	/**
	 * ��ʵ����
	 */
	public MainFrame() {
		this(Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * ��ʵ����
	 * @param mutilang
	 */
	public MainFrame(Mutilang mutilang, FileSelectModel fileSelectModel) {
		Objects.requireNonNull(mutilang, "��ڲ��� mutilang ����Ϊ null��");
		
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
				fireSelectAttendanceFile();
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
				fireSelectWorkticketFile();
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
		
		JButton button = new JButton();
		button.setText((String) null);
		button.setFont(new Font("SansSerif", Font.PLAIN, 14));
		button.setBounds(245, 200, 150, 40);
		getContentPane().add(button);
		
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
		
		//�����ļ�ѡ��ģ��
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
		
		//���¸���ǩ���ı���
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
