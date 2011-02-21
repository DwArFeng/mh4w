package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameObverser;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DetailFrame extends JFrame implements MutilangSupported, ObverserSet<DetailFrameObverser>{

	/**观察器集合*/
	private final Set<DetailFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/**多语言接口*/
	private Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTabbedPane tabbedPane;
	private final JOriginalAttendanceDataPanel originalAttendanceDataPanel;
	private final JOriginalWorkticketDataPanel originalWorkticketDataPanel;
	
	/*
	 * 视图模型以及渲染
	 */
	
	/*
	 * 各模型的观察器。
	 */
	
	/**
	 * 新实例。
	 */
	public DetailFrame() {
		this(Constants.getDefaultLabelMutilang(), null, null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang
	 */
	public DetailFrame(Mutilang mutilang, DataListModel<OriginalAttendanceData> originalAttendanceDataModel,
			DataListModel<OriginalWorkticketData> originalWorkticketDataModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
	
		this.mutilang = mutilang;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireHideDetailFrame();
			}
		});
		
		setTitle(getLabel(LabelStringKey.DetailFrame_1));
		setBounds(0, 0, 700, 800);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		originalAttendanceDataPanel = new JOriginalAttendanceDataPanel(mutilang, originalAttendanceDataModel);
		tabbedPane.addTab(getLabel(LabelStringKey.DetailFrame_2) , null, originalAttendanceDataPanel, null);
		
		originalWorkticketDataPanel = new JOriginalWorkticketDataPanel(mutilang, originalWorkticketDataModel);
		tabbedPane.addTab("New tab", null, originalWorkticketDataPanel, null);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_4, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<DetailFrameObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(DetailFrameObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(DetailFrameObverser obverser) {
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		if(Objects.isNull(mutilang)) return false;
		if(Objects.equals(this.mutilang, mutilang)) return false;
		this.mutilang = mutilang;
		
		//更新子面板
		originalAttendanceDataPanel.setMutilang(mutilang);
		
		//更新各标签的文本。
		setTitle(getLabel(LabelStringKey.DetailFrame_1));
		
		tabbedPane.setTitleAt(0, getLabel(LabelStringKey.DetailFrame_2));

		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		originalAttendanceDataPanel.dispose();
		super.dispose();
	}

	private void fireHideDetailFrame() {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireHideDetailFrame();
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}

}
