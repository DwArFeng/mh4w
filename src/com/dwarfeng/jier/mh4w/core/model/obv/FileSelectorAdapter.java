package com.dwarfeng.jier.mh4w.core.model.obv;

import java.io.File;

/**
 * 文件选择模型观察器适配器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class FileSelectorAdapter implements FileSelectorObverser{

	@Override
	public void fireAttendanceFileChanged(File oldValue, File newValue) {}
	@Override
	public void fireWorkticketFileChanged(File oldValue, File newValue) {}
	@Override
	public void fireReadyChanged(boolean isReady) {}
	
}
