package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.jier.mh4w.core.model.struct.Shift;

/**
 * ∞‡¥Œπ€≤Ï∆˜  ≈‰∆˜°£
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class ShiftAdapter implements ShiftObverser {

	@Override
	public void fireShiftAdded(Shift shift) {}
	@Override
	public void fireShiftRemoved(Shift shift) {}
	@Override
	public void fireShiftCleared() {}
	
}
