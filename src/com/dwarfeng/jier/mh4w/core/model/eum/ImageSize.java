package com.dwarfeng.jier.mh4w.core.model.eum;

/**
 * 工具的图片类型。
 * @author DwArFeng
 * @since 0.0.1-beta
 */
public enum ImageSize {

	/**小图标*/
	ICON_SMALL(16, 16),
	
	/**中等图标*/
	ICON_MEDIUM(32, 32),
	
	/**大图标*/
	ICON_LARGE(48, 48),
	
	/**XLS 用尺寸*/
	XLS_ICON(150, 150),
	
	/**程序用超大图标*/
	ICON_SUPER_LARGE(128, 128),
	
	/**控制面板按钮专用大小*/
	CONTROL_AREA_ICON(26, 26),
	
	;
	
	private final int height;
	private final int width;
	
	private ImageSize(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * 图片的高度。
	 * @return 图片的高度。
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 图片的宽度。
	 * @return 图片的宽度。
	 */
	public int getWidth() {
		return width;
	}
}
