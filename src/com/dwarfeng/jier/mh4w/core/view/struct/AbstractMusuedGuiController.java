package com.dwarfeng.jier.mh4w.core.view.struct;

import java.awt.Component;
import java.util.Objects;

import com.dwarfeng.jier.mh4w.core.model.struct.Mutilang;
import com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported;

/**
 * 抽象多语言支持图形交互界面控制器。
 * <p> 多语言支持图形交互界面控制器的抽象实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractMusuedGuiController<T extends Component & MutilangSupported> 
extends AbstractGuiController<T> implements MutilangSupportedGuiController<T> {

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return null;
			return component.getMutilang();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.jier.mh4w.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.jier.mh4w.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			return component.setMutilang(mutilang);
		}finally {
			lock.writeLock().unlock();
		}
	}

}
