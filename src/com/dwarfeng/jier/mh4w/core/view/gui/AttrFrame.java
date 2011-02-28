package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel;
import com.dwarfeng.jier.mh4w.core.model.cm.JobModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.view.obv.AttrFrameObverser;

public class AttrFrame extends JDialog implements MutilangSupported, ObverserSet<AttrFrameObverser>{
	
	private static final long serialVersionUID = 4773527467684527902L;

	/**观察器集合*/
	private final Set<AttrFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/**多语言接口*/
	private final Mutilang mutilang;

	/*
	 * final 域。
	 */
	private final JButton refreshButton;
	private final JTabbedPane tabbedPane;
	private final JShiftPanel shiftPanel;
	private final JCoreConfigPanel coreConfigPanel;
	private final JJobPanel jobPanel;
	
	/**
	 * 新实例。
	 */
	public AttrFrame() {
		this(Constants.getDefaultLabelMutilang(), null, null, null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param coreConfigModel 指定的核心配置模型。
	 * @param shiftModel 指定的班次模型。
	 * @param jobModel 指定的工作模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public AttrFrame(Mutilang mutilang, CoreConfigModel coreConfigModel, ShiftModel shiftModel,
			JobModel jobModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
		
		this.mutilang = mutilang;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireHideAttrFrame();
			}
		});
		
		setModal(true);
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
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireReloadAttr();
			}
		});
		refreshButton.setText(getLabel(LabelStringKey.AttrFrame_1));
		
		GridBagConstraints gbc_refreshButton = new GridBagConstraints();
		gbc_refreshButton.fill = GridBagConstraints.BOTH;
		gbc_refreshButton.gridx = 1;
		gbc_refreshButton.gridy = 0;
		buttonPane.add(refreshButton, gbc_refreshButton);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		coreConfigPanel = new JCoreConfigPanel(mutilang, coreConfigModel);
		tabbedPane.addTab(getLabel(LabelStringKey.AttrFrame_3), null, coreConfigPanel, null);
		
		shiftPanel = new JShiftPanel(mutilang, shiftModel);
		tabbedPane.addTab(getLabel(LabelStringKey.AttrFrame_4), null, shiftPanel, null);
		
		jobPanel = new JJobPanel(mutilang, jobModel);
		tabbedPane.addTab(getLabel(LabelStringKey.AttrFrame_5), null, jobPanel, null);
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
	
		//更新子面板
		shiftPanel.updateMutilang();
		coreConfigPanel.updateMutilang();
		jobPanel.updateMutilang();
		
		//更新各标签的文本。
		setTitle(getLabel(LabelStringKey.AttrFrame_2));
		
		refreshButton.setText(getLabel(LabelStringKey.AttrFrame_1));
		
		tabbedPane.setTitleAt(0, getLabel(LabelStringKey.AttrFrame_3));
		tabbedPane.setTitleAt(1, getLabel(LabelStringKey.AttrFrame_4));
		tabbedPane.setTitleAt(2, getLabel(LabelStringKey.AttrFrame_5));
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
		coreConfigPanel.dispose();
		shiftPanel.dispose();
		jobPanel.dispose();
		
		super.dispose();
	}

	private void fireHideAttrFrame() {
		for(AttrFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireHideAttrFrame();
		}
	}

	private void fireReloadAttr() {
		for(AttrFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireReloadAttr();
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
