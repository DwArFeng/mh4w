package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 抽象xls保存器。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractXlsSaver implements XlsSaver{

	/**输入流*/
	protected final OutputStream out;
	
	/**
	 * 生成。
	 * @param out 输入流。
	 * @throws IOException 失败异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public AbstractXlsSaver(OutputStream out) throws IOException{
		Objects.requireNonNull(out, "入口参数 in 不能为 null。");
		this.out = out;
	}
	
}
