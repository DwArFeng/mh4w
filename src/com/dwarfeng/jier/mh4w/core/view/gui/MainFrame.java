package com.dwarfeng.jier.mh4w.core.view.gui;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.JFrame;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.view.obv.MainFrameObverser;
import com.dwarfeng.dutil.basic.gui.swing.JImagePanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JToggleButton;

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
	
	/**
	 * 新实例。
	 */
	public MainFrame() {
		setResizable(false);
		setBounds(100, 100, 406, 275);
		
		getContentPane().setLayout(null);
		
		JImagePanel imagePanel = new JImagePanel();
		imagePanel.setOpaque(false);
		imagePanel.setBounds(25, 17, 150, 150);
		getContentPane().add(imagePanel);
		
		JImagePanel imagePanel_1 = new JImagePanel();
		imagePanel_1.setBounds(225, 17, 150, 150);
		getContentPane().add(imagePanel_1);
		
		JLabel label = new JLabel();
		label.setText((String) null);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 16));
		label.setBounds(225, 167, 150, 25);
		getContentPane().add(label);
		
		JButton button = new JButton();
		button.setText((String) null);
		button.setFont(new Font("SansSerif", Font.PLAIN, 14));
		button.setBounds(225, 200, 150, 40);
		getContentPane().add(button);
		
		JToggleButton toggleButton = new JToggleButton();
		toggleButton.setToolTipText((String) null);
		toggleButton.setBounds(135, 200, 40, 40);
		getContentPane().add(toggleButton);
		
		JLabel label_1 = new JLabel();
		label_1.setText((String) null);
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

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
	
	private void fireFireWindowClosing() {
		for(MainFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireWindowClosing();
		}
	}
}
