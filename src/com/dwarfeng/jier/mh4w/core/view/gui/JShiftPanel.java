package com.dwarfeng.jier.mh4w.core.view.gui;

import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.dutil.basic.gui.swing.MuaListModel;
import com.dwarfeng.jier.mh4w.core.model.cm.ShiftModel;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageKey;
import com.dwarfeng.jier.mh4w.core.model.eum.ImageSize;
import com.dwarfeng.jier.mh4w.core.model.eum.LabelStringKey;
import com.dwarfeng.jier.mh4w.core.model.obv.ShiftAdapter;
import com.dwarfeng.jier.mh4w.core.model.obv.ShiftObverser;
import com.dwarfeng.jier.mh4w.core.model.struct.*;
import com.dwarfeng.jier.mh4w.core.util.Constants;
import com.dwarfeng.jier.mh4w.core.util.FormatUtil;
import com.dwarfeng.jier.mh4w.core.util.ImageUtil;
import com.dwarfeng.jier.mh4w.core.util.Mh4wUtil;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Objects;

public class JShiftPanel extends JPanel implements MutilangSupported {

    private static final long serialVersionUID = 2134081134259458795L;

    /**
     * 多语言接口
     */
    private final Mutilang mutilang;

    /*
     * final 域。
     */
    private final JLabel shiftsLabel;
    private final JLabel timeSectionsLabel;
    private final JList<Shift> shiftsList;
    private final JList<TimeSection> timeSectionsList;

    private final Image shiftSectionImage;
    private final Image extraPhase1ShiftSectionImage;
    private final Image extraPhase2ShiftSectionImage;
    private final Image extraPhase3ShiftSectionImage;
    private final Image extraPhase4ShiftSectionImage;
    private final Image restSectionImage;

    /*
     * 非 final 域。
     */
    private TimeSection[] shiftSections = new TimeSection[0];
    private TimeSection[] extraPhase1ShiftSections = new TimeSection[0];
    private TimeSection[] extraPhase2ShiftSections = new TimeSection[0];
    private TimeSection[] extraPhase3ShiftSections = new TimeSection[0];
    private TimeSection[] extraPhase4ShiftSections = new TimeSection[0];
    private TimeSection[] restSections = new TimeSection[0];

    /*
     * 各模型。
     */
    private ShiftModel shiftModel;

    /*
     * 视图模型以及渲染
     */
    private final MuaListModel<Shift> shiftListModel = new MuaListModel<>();
    private final MuaListModel<TimeSection> timeSectionListModel = new MuaListModel<>();
    @SuppressWarnings("FieldCanBeLocal") // 为了提高可读性，不做格式优化处理。
    private final ListCellRenderer<Object> shiftsListRenderer = new DefaultListCellRenderer() {

        private static final long serialVersionUID = 6567980303497019985L;

        @Override
        public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Shift shift = (Shift) value;
            setText(shift.getName());
            return this;
        }
    };
    @SuppressWarnings("FieldCanBeLocal") // 为了提高可读性，不做格式优化处理。
    private final ListCellRenderer<Object> timeSectionListRenderer = new DefaultListCellRenderer() {

        private static final long serialVersionUID = -7812495443357474837L;

        @Override
        public java.awt.Component getListCellRendererComponent(
                javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            TimeSection timeSection = (TimeSection) value;

            if (ArrayUtil.contains(shiftSections, timeSection)) {
                setIcon(new ImageIcon(shiftSectionImage));
            } else if (ArrayUtil.contains(extraPhase1ShiftSections, timeSection)) {
                setIcon(new ImageIcon(extraPhase1ShiftSectionImage));
            } else if (ArrayUtil.contains(extraPhase2ShiftSections, timeSection)) {
                setIcon(new ImageIcon(extraPhase2ShiftSectionImage));
            } else if (ArrayUtil.contains(extraPhase3ShiftSections, timeSection)) {
                setIcon(new ImageIcon(extraPhase3ShiftSectionImage));
            } else if (ArrayUtil.contains(extraPhase4ShiftSections, timeSection)) {
                setIcon(new ImageIcon(extraPhase4ShiftSectionImage));
            } else if (ArrayUtil.contains(restSections, timeSection)) {
                setIcon(new ImageIcon(restSectionImage));
            } else {
                setIcon(null);
            }

            setText(FormatUtil.formatTimeSection(timeSection));

            return this;
        }
    };

    /*
     * 各模型的观察器。
     */
    private final ShiftObverser shiftObverser = new ShiftAdapter() {

        /*
         *  (non-Javadoc)
         * @see com.dwarfeng.jier.mh4w.core.model.obv.ShiftAdapter#fireShiftAdded(com.dwarfeng.jier.mh4w.core.model.struct.Shift)
         */
        @Override
        public void fireShiftAdded(Shift shift) {
            Mh4wUtil.invokeInEventQueue(() -> shiftListModel.add(shift));
        }

        /*
         * (non-Javadoc)
         * @see com.dwarfeng.jier.mh4w.core.model.obv.ShiftAdapter#fireShiftRemoved(com.dwarfeng.jier.mh4w.core.model.struct.Shift)
         */
        @Override
        public void fireShiftRemoved(Shift shift) {
            Mh4wUtil.invokeInEventQueue(() -> shiftListModel.remove(shift));
        }

        /*
         * (non-Javadoc)
         * @see com.dwarfeng.jier.mh4w.core.model.obv.ShiftAdapter#fireShiftCleared()
         */
        @Override
        public void fireShiftCleared() {
            Mh4wUtil.invokeInEventQueue(shiftListModel::clear);
        }
    };

    /**
     * 新实例。
     */
    public JShiftPanel() {
        this(Constants.getDefaultLabelMutilang(), null);
    }

    /**
     * 新实例。
     *
     * @param mutilang   指定的多语言接口，不能为 <code>null</code>。
     * @param shiftModel 指定的班次模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public JShiftPanel(Mutilang mutilang, ShiftModel shiftModel) {
        Objects.requireNonNull(mutilang, "入口参数 mutilang 不能为 null。");

        this.mutilang = mutilang;

        shiftSectionImage = ImageUtil.getImage(ImageKey.SHIFT_SECTION, ImageSize.ICON_SMALL);
        extraPhase1ShiftSectionImage = ImageUtil.getImage(ImageKey.EXTRA_PHASE1_SHIFT_SECTION, ImageSize.ICON_SMALL);
        extraPhase2ShiftSectionImage = ImageUtil.getImage(ImageKey.EXTRA_PHASE2_SHIFT_SECTION, ImageSize.ICON_SMALL);
        extraPhase3ShiftSectionImage = ImageUtil.getImage(ImageKey.EXTRA_PHASE3_SHIFT_SECTION, ImageSize.ICON_SMALL);
        extraPhase4ShiftSectionImage = ImageUtil.getImage(ImageKey.EXTRA_PHASE4_SHIFT_SECTION, ImageSize.ICON_SMALL);
        restSectionImage = ImageUtil.getImage(ImageKey.REST_SECTION, ImageSize.ICON_SMALL);

        setLayout(new BorderLayout(0, 0));

        JAdjustableBorderPanel adjustableBorderPanel = new JAdjustableBorderPanel();
        adjustableBorderPanel.setSeperatorColor(new Color(100, 149, 237));
        adjustableBorderPanel.setSeperatorThickness(5);
        adjustableBorderPanel.setWestEnabled(true);
        add(adjustableBorderPanel);

        JScrollPane shiftsScrollPane = new JScrollPane();
        adjustableBorderPanel.add(shiftsScrollPane, BorderLayout.WEST);

        shiftsLabel = new JLabel();
        shiftsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shiftsLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        shiftsLabel.setText(getLabel(LabelStringKey.JShiftPanel_1));
        shiftsScrollPane.setColumnHeaderView(shiftsLabel);

        shiftsList = new JList<>();
        shiftsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shiftsList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            Shift shift = shiftsList.getSelectedValue();
            if (Objects.nonNull(shift)) {
                timeSectionListModel.clear();

                shiftSections = shift.getShiftSections();
                extraPhase1ShiftSections = shift.getExtraPhase1ShiftSections();
                extraPhase2ShiftSections = shift.getExtraPhase2ShiftSections();
                extraPhase3ShiftSections = shift.getExtraPhase3ShiftSections();
                extraPhase4ShiftSections = shift.getExtraPhase4ShiftSections();
                restSections = shift.getRestSections();

                for (TimeSection timeSection : shift.getShiftSections()) {
                    CollectionUtil.insertByOrder(timeSectionListModel, timeSection, TimeSectionComparator.instance);
                }
                for (TimeSection timeSection : shift.getExtraPhase1ShiftSections()) {
                    CollectionUtil.insertByOrder(timeSectionListModel, timeSection, TimeSectionComparator.instance);
                }
                for (TimeSection timeSection : shift.getExtraPhase2ShiftSections()) {
                    CollectionUtil.insertByOrder(timeSectionListModel, timeSection, TimeSectionComparator.instance);
                }
                for (TimeSection timeSection : shift.getExtraPhase3ShiftSections()) {
                    CollectionUtil.insertByOrder(timeSectionListModel, timeSection, TimeSectionComparator.instance);
                }
                for (TimeSection timeSection : shift.getExtraPhase4ShiftSections()) {
                    CollectionUtil.insertByOrder(timeSectionListModel, timeSection, TimeSectionComparator.instance);
                }
                for (TimeSection timeSection : shift.getRestSections()) {
                    CollectionUtil.insertByOrder(timeSectionListModel, timeSection, TimeSectionComparator.instance);
                }
            }
        });
        shiftsScrollPane.setViewportView(shiftsList);
        shiftsList.setModel(shiftListModel);
        shiftsList.setCellRenderer(shiftsListRenderer);

        JScrollPane timeSectionsScrollPane = new JScrollPane();
        adjustableBorderPanel.add(timeSectionsScrollPane, BorderLayout.CENTER);

        timeSectionsLabel = new JLabel();
        timeSectionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeSectionsLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        timeSectionsLabel.setText(getLabel(LabelStringKey.JShiftPanel_2));
        timeSectionsScrollPane.setColumnHeaderView(timeSectionsLabel);

        timeSectionsList = new JList<>();
        timeSectionsList.setModel(timeSectionListModel);
        timeSectionsList.setCellRenderer(timeSectionListRenderer);
        timeSectionsScrollPane.setViewportView(timeSectionsList);

        // 设置班次模型
        if (Objects.nonNull(shiftModel)) {
            shiftModel.addObverser(shiftObverser);
            shiftModel.getLock().readLock().lock();
            try {
                for (Shift shift : shiftModel) {
                    shiftListModel.add(shift);
                }
            } finally {
                shiftModel.getLock().readLock().unlock();
            }
        }

        this.shiftModel = shiftModel;

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
        // 更新各标签的文本。
        shiftsLabel.setText(getLabel(LabelStringKey.JShiftPanel_1));
        timeSectionsLabel.setText(getLabel(LabelStringKey.JShiftPanel_2));
    }

    /**
     * @return the shiftModel
     */
    public ShiftModel getShiftModel() {
        return shiftModel;
    }

    /**
     * @param shiftModel the shiftModel to set
     */
    public void setShiftModel(ShiftModel shiftModel) {
        shiftListModel.clear();
        shiftsList.getSelectionModel().setValueIsAdjusting(true);
        shiftsList.getSelectionModel().clearSelection();
        shiftsList.getSelectionModel().setAnchorSelectionIndex(-1);
        shiftsList.getSelectionModel().setLeadSelectionIndex(-1);

        timeSectionListModel.clear();
        timeSectionsList.getSelectionModel().setValueIsAdjusting(true);
        timeSectionsList.getSelectionModel().clearSelection();
        timeSectionsList.getSelectionModel().setAnchorSelectionIndex(-1);
        timeSectionsList.getSelectionModel().setLeadSelectionIndex(-1);

        if (Objects.nonNull(this.shiftModel)) {
            this.shiftModel.removeObverser(shiftObverser);
        }

        if (Objects.nonNull(shiftModel)) {
            shiftModel.addObverser(shiftObverser);
            shiftModel.getLock().readLock().lock();
            try {
                for (Shift shift : shiftModel) {
                    shiftListModel.add(shift);
                }
            } finally {
                shiftModel.getLock().readLock().unlock();
            }
        }

        this.shiftModel = shiftModel;
    }

    /**
     * 释放资源。
     */
    public void dispose() {
        if (Objects.nonNull(shiftModel)) {
            shiftModel.removeObverser(shiftObverser);
        }
        shiftListModel.clear();
        timeSectionListModel.clear();
    }

    private String getLabel(LabelStringKey labelStringKey) {
        return mutilang.getString(labelStringKey.getName());
    }

}
