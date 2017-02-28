package com.dwarfeng.jier.mh4w.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.jier.mh4w.core.model.struct.Shift;

/**
 * ��ι۲�����
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface ShiftObverser extends Obverser{
	
	/**
	 * ָ֪ͨ���İ�α���ӡ�
	 * @param shift ָ���İ�Ρ�
	 */
	public void fireShiftAdded(Shift shift);
	
	/**
	 * ָ֪ͨ���İ�α��Ƴ���
	 * @param shift ָ���İ�Ρ�
	 */
	public void fireShiftRemoved(Shift shift);
	
	/**
	 * ֪ͨ�ð��ģ����ա�
	 */
	public void fireShiftCleared();
	
}
