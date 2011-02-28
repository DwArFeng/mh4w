package com.dwarfeng.jier.mh4w.core.model.struct;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 程序中使用的资源。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public interface Resource {

	/**
	 * 打开资源的输入流。
	 * @return 资源的输入流。
	 * @throws IOException IO异常。
	 */
	public InputStream openInputStream() throws IOException;
	
	/**
	 * 打开资源的输出流。
	 * @return 资源的输出流。
	 * @throws IOException IO异常。
	 */
	public OutputStream openOutputStream() throws IOException;
	
	/**
	 * 将文件资源置换成默认的资源。
	 * @throws IOException IO异常。
	 */
	public void reset() throws IOException;
	
}
