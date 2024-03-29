package com.dwarfeng.jier.mh4w.core.model.cm;

import com.dwarfeng.jier.mh4w.core.model.eum.CoreConfig;
import com.dwarfeng.jier.mh4w.core.util.CountUtil;
import com.dwarfeng.jier.mh4w.core.util.LocaleUtil;

import java.util.Locale;

/**
 * 默认核心配置模型。
 * <p> 核心配置模型的默认实现。
 *
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public class DefaultCoreConfigModel extends DefaultSyncConfigModel implements CoreConfigModel {

    /**
     * 新实例。
     */
    public DefaultCoreConfigModel() {
        super(CoreConfig.values());
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getAttendanceStartRow()
     */
    @Override
    public int getAttendanceStartRow() {
        lock.readLock().lock();
        try {
            return Integer.parseInt(getValidValue(CoreConfig.ATTENDANCE_ROW_START.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getAttendanceDepartmentColumn()
     */
    @Override
    public int getAttendanceDepartmentColumn() {
        lock.readLock().lock();
        try {
            return CountUtil.columnString2Int(getValidValue(CoreConfig.ATTENDANCE_COLUMN_DEPARTMENT.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getAttendanceWorkNumberColumn()
     */
    @Override
    public int getAttendanceWorkNumberColumn() {
        lock.readLock().lock();
        try {
            return CountUtil.columnString2Int(getValidValue(CoreConfig.ATTENDANCE_COLUMN_WORKNUMBER.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getAttendacneNameColumn()
     */
    @Override
    public int getAttendanceNameColumn() {
        lock.readLock().lock();
        try {
            return CountUtil.columnString2Int(getValidValue(CoreConfig.ATTENDANCE_COLUMN_NAME.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getAttendanceDateColumn()
     */
    @Override
    public int getAttendanceDateColumn() {
        lock.readLock().lock();
        try {
            return CountUtil.columnString2Int(getValidValue(CoreConfig.ATTENDANCE_COLUMN_DATE.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getAttendanceShiftColumn()
     */
    @Override
    public int getAttendanceShiftColumn() {
        lock.readLock().lock();
        try {
            return CountUtil.columnString2Int(getValidValue(CoreConfig.ATTENDANCE_COLUMN_SHIFT.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getAttendanceRecordColumn()
     */
    @Override
    public int getAttendanceRecordColumn() {
        lock.readLock().lock();
        try {
            return CountUtil.columnString2Int(getValidValue(CoreConfig.ATTENDANCE_COLUMN_RECORD.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getAttendanceOverdayKeyword()
     */
    @Override
    public String getAttendanceOverdayKeyword() {
        lock.readLock().lock();
        try {
            return getValidValue(CoreConfig.ATTENDANCE_KEYWORD_OVERDAY.getConfigKey());
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getWorkticketStartRow()
     */
    @Override
    public int getWorkticketStartRow() {
        lock.readLock().lock();
        try {
            return Integer.parseInt(getValidValue(CoreConfig.WORKTICKET_ROW_START.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getWorkticketDepartmentColumn()
     */
    @Override
    public int getWorkticketDepartmentColumn() {
        lock.readLock().lock();
        try {
            return CountUtil.columnString2Int(getValidValue(CoreConfig.WORKTICKET_COLUMN_DEPARTMENT.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getWorkticketWorkNumberColumn()
     */
    @Override
    public int getWorkticketWorkNumberColumn() {
        lock.readLock().lock();
        try {
            return CountUtil.columnString2Int(getValidValue(CoreConfig.WORKTICKET_COLUMN_WORKNUMBER.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getShiftCoefficientCount()
     */
    @Override
    public double getShiftCoefficientCount() {
        lock.readLock().lock();
        try {
            return Double.parseDouble(getValidValue(CoreConfig.COUNT_COEFFICIENT_SHIFT.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public double getExtraCoefficientPhase1Count() {
        lock.readLock().lock();
        try {
            return Double.parseDouble(getValidValue(CoreConfig.COUNT_COEFFICIENT_EXTRA_PHASE1.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public double getExtraCoefficientPhase2Count() {
        lock.readLock().lock();
        try {
            return Double.parseDouble(getValidValue(CoreConfig.COUNT_COEFFICIENT_EXTRA_PHASE2.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public double getExtraCoefficientPhase3Count() {
        lock.readLock().lock();
        try {
            return Double.parseDouble(getValidValue(CoreConfig.COUNT_COEFFICIENT_EXTRA_PHASE3.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public double getExtraCoefficientPhase4Count() {
        lock.readLock().lock();
        try {
            return Double.parseDouble(getValidValue(CoreConfig.COUNT_COEFFICIENT_EXTRA_PHASE4.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getWeekendCoefficientCount()
     */
    @Override
    public double getWeekendCoefficientCount() {
        lock.readLock().lock();
        try {
            return Double.parseDouble(getValidValue(CoreConfig.COUNT_COEFFICIENT_WEEKEND.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getHolidayCoefficientCount()
     */
    @Override
    public double getHolidayCoefficientCount() {
        lock.readLock().lock();
        try {
            return Double.parseDouble(getValidValue(CoreConfig.COUNT_COEFFICIENT_HOLIDAY.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getLoggerMutilangLocale()
     */
    @Override
    public Locale getLoggerMutilangLocale() {
        lock.readLock().lock();
        try {
            return LocaleUtil.parseLocale(getValidValue(CoreConfig.MUTILANG_LOGGER.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.dwarfeng.jier.mh4w.core.model.cm.CoreConfigModel#getLabelMutilangLocale()
     */
    @Override
    public Locale getLabelMutilangLocale() {
        lock.readLock().lock();
        try {
            return LocaleUtil.parseLocale(getValidValue(CoreConfig.MUTILANG_LABEL.getConfigKey()));
        } finally {
            lock.readLock().unlock();
        }
    }

}
