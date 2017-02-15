package com.dwarfeng.jier.mh4w.core.model.eum;

/**
 * ���ߵ�ͼƬ���͡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public enum ImageSize {

	/**Сͼ��*/
	ICON_SMALL(16,16),
	
	/**�е�ͼ��*/
	ICON_MEDIUM(32,32),
	
	/**��ͼ��*/
	ICON_LARGE(48,48),
	
	/**�����ó���ͼ��*/
	ICON_SUPER_LARGE(128,128),
	
	;
	
	private final int height;
	private final int width;
	
	private ImageSize(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * ͼƬ�ĸ߶ȡ�
	 * @return ͼƬ�ĸ߶ȡ�
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * ͼƬ�Ŀ�ȡ�
	 * @return ͼƬ�Ŀ�ȡ�
	 */
	public int getWidth() {
		return width;
	}
}
