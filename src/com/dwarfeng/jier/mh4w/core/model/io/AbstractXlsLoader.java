package com.dwarfeng.jier.mh4w.core.model.io;

import java.io.InputStream;
import java.util.Objects;

/**
 * 抽象xls读取器。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractXlsLoader implements XlsLoader {

	/**输入流*/
	protected final InputStream in;
	
	/**
	 * 生成。
	 * @param in 输入流。
	 * @throws XlsFailedException 失败异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public AbstractXlsLoader(InputStream in) throws XlsFailedException{
		Objects.requireNonNull(in, "入口参数 in 不能为 null。");
		this.in = in;
	}
	
}
