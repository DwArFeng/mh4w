package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.jier.mh4w.core.model.cm.DataListModel;
import com.dwarfeng.jier.mh4w.core.model.cm.StateModel;
import com.dwarfeng.jier.mh4w.core.model.eum.CountState;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageKey;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageSize;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.StateAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.StateObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.AttendanceOffset;
import com.dwarfeng.jier.mh4w.core.model.struct.CountResult;
import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalAttendanceData;
import com.dwarfeng.jier.mh4w.core.model.struct.OriginalWorkticketData;
import com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset;
import com.dwarfeng.jier.mh4w.core.model.struct.WorkticketData;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.ImageUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;
import com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelObverser;
import com.dwarfeng.jier.mh4w.core.view.obv.CountResultPanalAdapter;
import com.dwarfeng.jier.mh4w.core.view.obv.CountResultPanelObverser;
import com.dwarfeng.jier.mh4w.core.view.obv.DetailFrameObverser;

public class DetailFrame extends JFrame implements MutilangSupported, ObverserSet<DetailFrameObverser>{

	private static final long serialVersionUID = -8661030714248862553L;

	/**观察器集合*/
	private final Set<DetailFrameObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/**多语言接口*/
	private final Mutilang mutilang;
	
	/*
	 * final 域。
	 */
	private final JTabbedPane tabbedPane;
	private final JOriginalAttendanceDataPanel originalAttendanceDataPanel;
	private final JOriginalWorkticketDataPanel originalWorkticketDataPanel;
	private final JAttendanceDataPanel attendanceDataPanel;
	private final JWorkticketDataPanel workticketDataPanel;
	private final JCountResultPanel countResultPanel;
	private final JAttendanceOffsetPanel attendanceOffsetPanel;
	private final Image program_icon;

	/*
	 * 非 final 域。
	 */
	private boolean outdateFlag = false;
	private CountState countState = CountState.NOT_START;
	
	/*
	 * 视图模型以及渲染
	 */
	private StateModel stateModel;
	
	/*
	 * 各模型的观察器。
	 */
	private final StateObverser stateObverser = new StateAdapter() {

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
					checkTabbedPanelEnabled();
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
					checkTabbedPanelEnabled();
				}
			});
		}
		
	};
	private final CountResultPanelObverser countResultPanelObverser = new CountResultPanalAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.view.obv.CountResultPanalAdapter#fireExportCountResult()
		 */
		@Override
		public void fireExportCountResult() {
			DetailFrame.this.fireExportCountResult();
		};
		
	};
	private final AttendanceOffsetPanelObverser attendanceOffsetPanelObverser = new AttendanceOffsetPanelAdapter() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelAdapter#fireAddAttendanceOffset(com.dwarfeng.jier.mh4w.core.model.struct.UnsafeAttendanceOffset)
		 */
		@Override
		public void fireSubmitAttendanceOffset(UnsafeAttendanceOffset unsafeAttendanceOffset) {
			DetailFrame.this.fireSubmitAttendanceOffset(unsafeAttendanceOffset);
		};
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelAdapter#fireClearAttendanceOffset()
		 */
		@Override
		public void fireClearAttendanceOffset() {
			DetailFrame.this.fireClearAttendanceOffset();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelObverser#fireSaveAttendanceOffset()
		 */
		@Override
		public void fireSaveAttendanceOffset() {
			DetailFrame.this.fireSaveAttendanceOffset();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelAdapter#fireLoadAttendanceOffset()
		 */
		@Override
		public void fireLoadAttendanceOffset() {
			DetailFrame.this.fireLoadAttendanceOffset();
		};
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.jier.mh4w.core.view.obv.AttendanceOffsetPanelAdapter#fireRemoveAttendanceOffset()
		 */
		@Override
		public void fireRemoveAttendanceOffset(int index) {
			DetailFrame.this.fireRemoveAttendanceOffset(index);
		};
		
	};
	
	/**
	 * 新实例。
	 */
	public DetailFrame() {
		this(Constants.getDefaultLabelMutilang(), null, null, null, null, null, null, null);
	}
	
	/**
	 * 新实例。
	 * @param mutilang 指定的多语言接口，不能为 <code>null</code>。
	 * @param stateModel 指定的状态模型。
	 * @param originalAttendanceDataModel 指定的原始出勤数据模型。
	 * @param originalWorkticketDataModel 指定的原始工票数据模型。
	 * @param attendanceDataModel 指定的出勤数据模型。
	 * @param workticketDataModel 指定的工票数据模型。
	 * @param countResultModel 指定的共计结果模型。
	 * @param attendanceOffsetModel 指定的考勤补偿模型。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public DetailFrame(Mutilang mutilang, StateModel stateModel, DataListModel<OriginalAttendanceData> originalAttendanceDataModel,
			DataListModel<OriginalWorkticketData> originalWorkticketDataModel, DataListModel<AttendanceData> attendanceDataModel,
			DataListModel<WorkticketData> workticketDataModel, DataListModel<CountResult> countResultModel,
			DataListModel<AttendanceOffset> attendanceOffsetModel) {
		Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");
	
		this.mutilang = mutilang;
		
		program_icon = ImageUtil.getImage(ImageKey.PROGRAM_ICON, ImageSize.ICON_SUPER_LARGE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireHideDetailFrame();
			}
		});
		
		setIconImage(program_icon);
		setTitle(getLabel(LabelStringKey.DetailFrame_1));
		setBounds(0, 0, 1000, 800);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		originalAttendanceDataPanel = new JOriginalAttendanceDataPanel(mutilang, originalAttendanceDataModel);
		tabbedPane.addTab(getLabel(LabelStringKey.DetailFrame_2) , null, originalAttendanceDataPanel, null);
		
		originalWorkticketDataPanel = new JOriginalWorkticketDataPanel(mutilang, originalWorkticketDataModel);
		tabbedPane.addTab(getLabel(LabelStringKey.DetailFrame_3), null, originalWorkticketDataPanel, null);
		
		attendanceDataPanel = new JAttendanceDataPanel(mutilang, attendanceDataModel);
		tabbedPane.addTab(getLabel(LabelStringKey.DetailFrame_4), null, attendanceDataPanel, null);
		
		workticketDataPanel = new JWorkticketDataPanel(mutilang, workticketDataModel);
		tabbedPane.addTab(getLabel(LabelStringKey.DetailFrame_5), null, workticketDataPanel, null);
		
		attendanceOffsetPanel = new JAttendanceOffsetPanel(mutilang, attendanceOffsetModel, countResultModel);
		attendanceOffsetPanel.addObverser(attendanceOffsetPanelObverser);
		tabbedPane.addTab(getLabel(LabelStringKey.DetailFrame_6), null, attendanceOffsetPanel, null);
		
		countResultPanel = new JCountResultPanel(mutilang, countResultModel);
		countResultPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				fireUpdateCountResult();
			}
		});
		countResultPanel.addObverser(countResultPanelObverser);
		tabbedPane.addTab(getLabel(LabelStringKey.DetailFrame_7), null, countResultPanel, null);
		
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);
		tabbedPane.setEnabledAt(4, false);
		tabbedPane.setEnabledAt(5, false);

		if(Objects.nonNull(this.stateModel)){
			this.stateModel.removeObverser(stateObverser);
		}
		
		if(Objects.nonNull(stateModel)){
			stateModel.addObverser(stateObverser);
			stateModel.getLock().readLock().lock();
			try{
				outdateFlag = stateModel.isCountResultOutdated();
				countState = stateModel.getCountState();
			}finally {
				stateModel.getLock().readLock().unlock();
			}
			checkTabbedPanelEnabled();
		}
		
		this.stateModel = stateModel;
		
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
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#updateMutilang()
	 */
	@Override
	public void updateMutilang() {
		//更新子面板
		originalAttendanceDataPanel.updateMutilang();
		originalWorkticketDataPanel.updateMutilang();
		attendanceDataPanel.updateMutilang();
		workticketDataPanel.updateMutilang();
		countResultPanel.updateMutilang();
		attendanceOffsetPanel.updateMutilang();
		
		//更新各标签的文本。
		setTitle(getLabel(LabelStringKey.DetailFrame_1));
		
		tabbedPane.setTitleAt(0, getLabel(LabelStringKey.DetailFrame_2));
		tabbedPane.setTitleAt(1, getLabel(LabelStringKey.DetailFrame_3));
		tabbedPane.setTitleAt(2, getLabel(LabelStringKey.DetailFrame_4));
		tabbedPane.setTitleAt(3, getLabel(LabelStringKey.DetailFrame_5));
		tabbedPane.setTitleAt(4, getLabel(LabelStringKey.DetailFrame_6));
		tabbedPane.setTitleAt(5, getLabel(LabelStringKey.DetailFrame_7));
	}
	
	/**
	 * @return the stateModel
	 */
	public StateModel getStateModel() {
		return stateModel;
	}

	/**
	 * @param stateModel the stateModel to set
	 */
	public void setStateModel(StateModel stateModel) {
		outdateFlag = false;
		countState = CountState.NOT_START;
		checkTabbedPanelEnabled();
		
		if(Objects.nonNull(this.stateModel)){
			this.stateModel.removeObverser(stateObverser);
		}
		
		if(Objects.nonNull(stateModel)){
			stateModel.addObverser(stateObverser);
			stateModel.getLock().readLock().lock();
			try{
				outdateFlag = stateModel.isCountResultOutdated();
				countState = stateModel.getCountState();
			}finally {
				stateModel.getLock().readLock().unlock();
			}
			checkTabbedPanelEnabled();
		}
		
		this.stateModel = stateModel;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		originalAttendanceDataPanel.dispose();
		originalWorkticketDataPanel.dispose();
		attendanceDataPanel.dispose();
		workticketDataPanel.dispose();
		countResultPanel.removeObverser(countResultPanelObverser);
		countResultPanel.dispose();
		attendanceOffsetPanel.removeObverser(attendanceOffsetPanelObverser);
		attendanceOffsetPanel.dispose();
		super.dispose();
	}

	private void fireHideDetailFrame() {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireHideDetailFrame();
		}
	}

	private void fireExportCountResult() {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireExportCountResult();
		}
	}

	private void fireSubmitAttendanceOffset(UnsafeAttendanceOffset unsafeAttendanceOffset) {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSubmitAttendanceOffset(unsafeAttendanceOffset);
		}
	}

	private void fireClearAttendanceOffset() {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireClearAttendanceOffset();
		}
	}

	private void fireSaveAttendanceOffset() {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireSaveAttendanceOffset();
		}
	}

	private void fireLoadAttendanceOffset() {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireLoadAttendanceOffset();
		}
	}

	private void fireRemoveAttendanceOffset(int index) {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireRemoveAttendanceOffset(index);
		}
	}

	private void fireUpdateCountResult() {
		for(DetailFrameObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireUpdateCountResult();
		}
	}

	private String getLabel(LabelStringKey labelStringKey){
		return mutilang.getString(labelStringKey.getName());
	}
	
	private void checkTabbedPanelEnabled() {
		if((countState.equals(CountState.STARTED_WAITING) || countState.equals(CountState.STARTED_EXPORTED)) && ! outdateFlag){
			enableTablePanel();
		}else {
			disableTablePanel();
		}
	}

	private void disableTablePanel(){
		tabbedPane.setSelectedIndex(0);
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);
		tabbedPane.setEnabledAt(4, false);
		tabbedPane.setEnabledAt(5, false);
	}
	
	private void enableTablePanel(){
		tabbedPane.setEnabledAt(2, true);
		tabbedPane.setEnabledAt(3, true);
		tabbedPane.setEnabledAt(4, true);
		tabbedPane.setEnabledAt(5, true);
	}

}
