package com.dwarfeng.jier.mh4w.core.control.obv;

import com.dwarfeng.dutil.basic.prog.RuntimeState;

/**
 * ��ʱͳ��ʵ���۲�����������
 * @author DwArFeng
 * @since 1.1.0
 */
public abstract class Mh4wAdapter implements Mh4wObverser{
	
	@Override
	public void fireStateChanged(RuntimeState oldValue, RuntimeState newValue) {}
	
}
