package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.view.obv.AttrFrameObverser;

public class AttrFrame extends JDialog implements MutilangSupported, ObverserSet<AttrFrameObverser>{
	
	/**观察器集合*/
	private final Set<AttrFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/**多语言接口*/
	private Mutilang mutilang;

	/*
	 * final 域。
	 */
	private final JButton refreshButton;
	private final JTabbedPane tabbedPane;
	private final JShiftPanel shiftPanel;
	
	/*
	 * 非 final 域。
	 */
	
	/*
	 * 各模型。
	 */
	
	/*
	 * 各模型的观察器。
	 */
	
	/**
	 * 新实例。
	 */
	public AttrFrame() {
		this(null, Constants.getDefaultLabelMutilang(), null);
	}
	
	/**
	 * 
	 * @param mutilang
	 */
	public AttrFrame(JFrame jframe, Mutilang mutilang, ShiftModel shiftModel) {
		super(jframe, true);
		
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireAttrFrameClosing();
			}
		});
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle(getLabel(LabelStringKey.AttrFrame_2));
		setType(Type.UTILITY);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		GridBagLayout gbl_buttonPane = new GridBagLayout();
		gbl_buttonPane.columnWidths = new int[]{401, 28, 0};
		gbl_buttonPane.rowHeights = new int[]{26, 0};
		gbl_buttonPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_buttonPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		buttonPane.setLayout(gbl_buttonPane);
		
		refreshButton = new JButton();
		refreshButton.setText(getLabel(LabelStringKey.AttrFrame_1));
		
		GridBagConstraints gbc_refreshButton = new GridBagConstraints();
		gbc_refreshButton.fill = GridBagConstraints.BOTH;
		gbc_refreshButton.gridx = 1;
		gbc_refreshButton.gridy = 0;
		buttonPane.add(refreshButton, gbc_refreshButton);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab(getLabel(LabelStringKey.AttrFrame_3), null, panel, null);
		
		shiftPanel = new JShiftPanel(mutilang, shiftModel);
		tabbedPane.addTab(getLabel(LabelStringKey.AttrFrame_4), null, shiftPanel, null);
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
		
		//更新子面板
		shiftPanel.setMutilang(mutilang);
		
		//更新各标签的文本。
		setTitle(getLabel(LabelStringKey.AttrFrame_2));
		
		refreshButton.setText(getLabel(LabelStringKey.AttrFrame_1));
		
		tabbedPane.setTitleAt(0, getLabel(LabelStringKey.AttrFrame_3));
		tabbedPane.setTitleAt(1, getLabel(LabelStringKey.AttrFrame_4));

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<AttrFrameObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(AttrFrameObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(AttrFrameObverser obverser) {
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
		shiftPanel.dispose();
		super.dispose();
	}

	private void fireAttrFrameClosing() {
		for(AttrFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireAttrFrameClosing();
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
