package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;

/**
 * 班次观察器。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface ShiftObverser extends Obverser{
	
	/**
	 * 通知指定的班次被添加。
	 * @param shift 指定的班次。
	 */
	public void fireShiftAdded(Shift shift);
	
	/**
	 * 通知指定的班次被移除。
	 * @param shift 指定的班次。
	 */
	public void fireShiftRemoved(Shift shift);
	
	/**
	 * 通知该班次模型清空。
	 */
	public void fireShiftCleared();
	
}
