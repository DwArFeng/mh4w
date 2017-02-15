package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.Font;
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
import com.dwarfeng.jier.mh4w.core.model.cm.FileSelectorModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectorAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.FileSelectorObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser;
import java.awt.Toolkit;
import java.io.File;
import java.awt.Component;
import javax.swing.Box;

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
	private JImagePanel attendanceFilePanel;
	private JImagePanel workticketFilePanel;
	
	/*
	 * 各模型。
	 */
	private FileSelectorModel fileSelectorModel;
	
	/*
	 * 各模型的观察器。
	 */
	private final FileSelectorObverser fileSelectorObverser = new FileSelectorAdapter() {

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FileSelectorAdapter#fireAttendanceFileChanged(java.io.File, java.io.File)
		 */
		@Override
		public void fireAttendanceFileChanged(File oldValue, File newValue) {
			// TODO Auto-generated method stub
			super.fireAttendanceFileChanged(oldValue, newValue);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FileSelectorAdapter#fireWorkticketFileChanged(java.io.File, java.io.File)
		 */
		@Override
		public void fireWorkticketFileChanged(File oldValue, File newValue) {
			// TODO Auto-generated method stub
			super.fireWorkticketFileChanged(oldValue, newValue);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.model.obv.FileSelectorAdapter#fireReadyChanged(boolean)
		 */
		@Override
		public void fireReadyChanged(boolean isReady) {
			// TODO Auto-generated method stub
			super.fireReadyChanged(isReady);
		}
		
		
		
	};
	
	/**
	 * 新实例。
	 */
	public MainFrame() {
		this(Constants.getDefaultLabelMutilang());
	}
	
	/**
	 * 新实例。
	 * @param mutilang
	 */
	public MainFrame(Mutilang mutilang) {
		setResizable(false);
		setBounds(100, 100, 427, 295);
		
		getContentPane().setLayout(null);
		
		attendanceFilePanel = new JImagePanel();
		attendanceFilePanel.setAutoResize(true);
		attendanceFilePanel.setOpaque(false);
		attendanceFilePanel.setBounds(25, 17, 150, 150);
		getContentPane().add(attendanceFilePanel);
		
		workticketFilePanel = new JImagePanel();
		workticketFilePanel.setAutoResize(true);
		workticketFilePanel.setBounds(245, 17, 150, 150);
		getContentPane().add(workticketFilePanel);
		
		JLabel label = new JLabel();
		label.setText("123321");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 16));
		label.setBounds(245, 167, 150, 25);
		getContentPane().add(label);
		
		JButton button = new JButton();
		button.setText((String) null);
		button.setFont(new Font("SansSerif", Font.PLAIN, 14));
		button.setBounds(245, 200, 150, 40);
		getContentPane().add(button);
		
		JToggleButton toggleButton = new JToggleButton();
		toggleButton.setToolTipText((String) null);
		toggleButton.setBounds(190, 200, 40, 40);
		getContentPane().add(toggleButton);
		
		JLabel label_1 = new JLabel();
		label_1.setText("123321");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Dialog", Font.BOLD, 16));
		label_1.setBounds(25, 167, 150, 25);
		getContentPane().add(label_1);
		
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
		Objects.requireNonNull(mutilang , "入口参数 mutilang 不能为 null。");
		
		if(Objects.equals(mutilang, this.mutilang)) return false;
		this.mutilang = mutilang;
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
	 * @return the fileSelectorModel
	 */
	public FileSelectorModel getFileSelectorModel() {
		return fileSelectorModel;
	}

	/**
	 * @param fileSelectorModel the fileSelectorModel to set
	 */
	public void setFileSelectorModel(FileSelectorModel fileSelectorModel) {
		this.fileSelectorModel = fileSelectorModel;
	}

	private void fireFireWindowClosing() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWindowClosing();
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
}
