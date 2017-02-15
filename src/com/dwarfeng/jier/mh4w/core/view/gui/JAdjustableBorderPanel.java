package com.dwarfeng.jier.mh4w.core.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.StringFieldKey;

/**
 * 可调整的边界布局面板。
 * <p>该面板的布局类似于边界布局样式中的布局，不过边界与边界之前有拖动条。通过拖动这些拖动条
 * 可以调整边界的大小，以达到动态调整边界大小的效果。
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class JAdjustableBorderPanel extends JPanel{
	
	private static final long serialVersionUID = 8778731300497488535L;
	
	/**
	 * 水平模板：默认显示东、中、西三个方向的空间，所有控件最小度量100
	 */
	public final static int MOUDLE_HOR = 1;
	/**
	 * 竖直模板：默认显示北，中，南三个方向的控件，所有控件最小度量100
	 */
	public final static int MOUDLE_VEC = 2;
	/**
	 * 中下模板：默认显示中，南两个方向的控件，所有控件最小度量100
	 */
	public final static int MOUDLE_CD = 3;
	/**
	 * 全向模板：默认显示所有控件，最小度量100
	 */
	public final static int MOUDLE_AL = 4;
	
	//----------------整体布局成员变量---------------------------------------------------------------
	/**
	 * 西方的整体布局，包括拖动条。
	 */
	private JPanel westPan;
	/**
	 * 西方布局是否启用。
	 */
	private boolean westEnabled;
	/**
	 * 西方控件的最小度量。
	 */
	private int westMinValue;
	/**
	 * 西方控件的表现度量。
	 */
	private int westPreferredValue;
	/**
	 * 东方的整体布局，包括拖动条。
	 */
	private JPanel eastPan;
	/**
	 * 东方布局是否启用。
	 */
	private boolean eastEnabled;
	/**
	 * 东方控件的最小度量。
	 */
	private int eastMinValue;
	/**
	 * 东方控件的表现度量。
	 */
	private int eastPreferredValue;
	/**
	 * 北方的整体布局，包括拖动条。
	 */
	private JPanel northPan;
	/**
	 * 北方的整体布局是否启用。
	 */
	private boolean northEnabled;
	/**
	 * 北方控件的最小度量。
	 */
	private int northMinValue;
	/**
	 * 北方控件的表现度量。
	 */
	private int northPreferredValue;
	/**
	 * 南方的整体布局，包括拖动条。
	 */
	private JPanel southPan;
	/**
	 * 南方布局是否启用。
	 */
	private boolean southEnabled;
	/**
	 * 南方控件的最小度量。
	 */
	private int southMinValue;
	/**
	 * 南方控件的表现度量。
	 */
	private int southPreferredValue;
	/**
	 * 中方的整体布局。
	 */
	private JPanel centerPan;
	
	//-------------------拖动条有关成员变量-----------------------------------------------------------------------------------------------
	
	/**
	 * 西方的拖动条。
	 */
	private JSeparator westSeparator;
	/**
	 * 西方的拖动条是否启用
	 */
	private boolean westSeparatorEnabled;
	/**
	 * 东方的拖动条
	 */
	private JSeparator eastSeparator;
	/**
	 * 东方的拖动条是否启用
	 */
	private boolean eastSeparatorEnabled;
	/**
	 * 北方的拖动条。
	 */
	private JSeparator northSeparator;
	/**
	 * 北方的拖动条是否启用。
	 */
	private boolean northSeparatorEnabled;
	/**
	 * 南方的拖动条。
	 */
	private JSeparator southSeparator;
	/**
	 * 南方的拖动条是否启用。
	 */
	private boolean southSeparatorEnabled;
	
	//---------------------------组件成员变量-----------------------------------------------------------------------------------------
	
	private Component west;
	
	private Component east;
	
	private Component north;
	
	private Component south;
	
	private Component center;
	
	//--------------------------细节变量-------------------------------------------------------------------------------------------------------
	/**
	 * 拖动条的背景颜色。
	 */
	protected Color seperatorColor;
	/**
	 * 拖动条的粗细。
	 */
	protected int seperatorThickness;

	/**
	 * 生成一个默认的可调整边界布局面板。
	 */
	public JAdjustableBorderPanel() {
		init();
	}
	
	/**
	 * 设置中心区域的最小度量。
	 * @param dimension 中心区域的最小度量。
	 */
	public void setCenterMinSize(Dimension dimension){
		centerPan.setMinimumSize(dimension);
		if(centerPan.getPreferredSize().height < dimension.height)
			centerPan.setPreferredSize(new Dimension(centerPan.getPreferredSize().width,dimension.height));
		if(centerPan.getPreferredSize().width < dimension.width)
			centerPan.setPreferredSize(new Dimension(dimension.width,centerPan.getPreferredSize().height));
		updateUI();
	}
	/**
	 * 返回中心区域的最小度量。
	 * @return 中心区域的最小度量。
	 */
	public Dimension getCenterMinSize(){
		return centerPan.getMinimumSize();
	}
	//--------------------------------组件设置方法----------------------------------------------------------------------------------------------------------------
	/**
	 * 向控件内添加组件的方法。
	 * <p>该方法重写了父类方法，使其可以通过不同的约束向不同方的可拖动控件中添加指定的组组件。
	 * <br>约束条件只能是{@linkplain BorderLayout}中的约束条件中的一个，否则会抛出异常。
	 * @param component 需要向该控件中添加的组件。
	 * @param constraints 约束条件，只能是{@code BorderLayout.EAST}，{@code BorderLayout.WEST}，{@code BorderLayout.NORTH}，
	 * {@code BorderLayout.SOUTH}，{@code BorderLayout.CENTER} 中的一个。
	 * @throws IllegalArgumentException 当约束不等于{@code BorderLayout}中的任何一个约束时抛出该异常。
	 */
	@Override
	public void add(Component component,Object constraints) throws IllegalArgumentException{
		if(constraints.equals(BorderLayout.NORTH)){
			setNorth(component);
			return;
		}
		if(constraints.equals(BorderLayout.SOUTH)){
			setSouth(component);
			return;
		}
		if(constraints.equals(BorderLayout.EAST)){
			setEast(component);
			return;
		}
		if(constraints.equals(BorderLayout.WEST)){
			setWest(component);
			return;
		}
		if(constraints.equals(BorderLayout.CENTER)){
			setCenter(component);
			return;
		}
		throw new IllegalArgumentException(DwarfUtil.getStringField(StringFieldKey.JAdjustableBorderPanel_0));
	}
	/**
	 * 获取该控件的北方组件。
	 * @return 该控件的北方组件。
	 */
	public Component getNorth() {
		return north;
	}
	/**
	 * 设置该控件的北方组件。
	 * @param north 指定的北方组件。
	 */
	public void setNorth(Component north) {
		if(north == null){
			if(this.north != null && isNorthEnabled()){
				northPan.remove(this.north);
			}
		}else{
			if(isNorthEnabled()) northPan.add(north,BorderLayout.CENTER);
		}
		this.north = north;
		updateUI();
	}
	/**
	 * 获取该控件的南方组件。
	 * @return 该控件的南方组件。
	 */
	public Component getSouth() {
		return south;
	}
	/**
	 * 设置该控件的南方组件。
	 * @param south 该控件的南方组件。
	 */
	public void setSouth(Component south) {
		if(south == null){
			if(this.south != null && isSouthEnabled()){
				southPan.remove(this.south);
			}
		}else{
			if(isSouthEnabled()) southPan.add(south,BorderLayout.CENTER);
		}
		this.south = south;
		updateUI();
	}
	/**
	 * 返回该工件的东方组件。
	 * @return 该控件的东方组件。
	 */
	public Component getEast() {
		return east;
	}
	/**
	 * 设置该控件的东方组件。
	 * @param east 指定的东方组件。
	 */
	public void setEast(Component east) {
		if(east == null){
			if(this.east != null && isEastEnabled()){
				eastPan.remove(this.east);
			}
		}else{
			if(isEastEnabled()) eastPan.add(east,BorderLayout.CENTER);
		}
		this.east = east;
		updateUI();
	}
	/**
	 * 获取该控件西方组件。
	 * @return  该控件的西方组件。
	 */
	public Component getWest() {
		return west;
	}
	/**
	 * 设置该控件的西方组件。
	 * @param west 指定的西方组件。
	 */
	public void setWest(Component west) {
		if(west == null){
			if(this.west != null && isWestEnabled()){
				westPan.remove(this.west);
			}
		}else{
			if(isWestEnabled()) westPan.add(west,BorderLayout.CENTER);
		}
		this.west = west;
		updateUI();
	}
	/**
	 * 返回该控件的中方控件。
	 * @return 该控件的中方控件。
	 */
	public Component getCenter() {
		return center;
	}
	/**
	 * 设置该控件的中方控件。
	 * @param center 指定的中方控件。
	 */
	public void setCenter(Component center) {
		if(center == null){
			centerPan.remove(this.center);
		}else{
			centerPan.add(center,BorderLayout.CENTER);
		}
		this.center = center;
	}
	//--------------------------布局启用和拖动条启用方法------------------------------------------------------------------------------------------------
	/**
	 * 获取北方布局是否被启用。
	 * @return 是否被启用。
	 */
	public boolean isNorthEnabled() {
		return northEnabled;
	}
	/**
	 * 设置北方布局是否被启用。
	 * @param northEnabled 是否被启用。
	 */
	public void setNorthEnabled(boolean northEnabled) {
		if(!northEnabled){
			if(northPan != null){
				remove(northPan);
				northPan = null;
			}
		}else{
			if(northPan == null){
				northPan = new JPanel();
				northPan.setMinimumSize(new Dimension(getNorthMinValue(),getNorthMinValue()));
				northPan.setPreferredSize(new Dimension(getNorthPreferredValue(),getNorthPreferredValue()));
				northPan.setLayout(new BorderLayout());
				super.add(northPan,BorderLayout.NORTH);
				if(isNorthSeparatorEnabled()){
					northSeparator = createNorthSeparator();
					northPan.add(northSeparator,BorderLayout.SOUTH);
				}
				if(north != null) northPan.add(north,BorderLayout.CENTER);
			}
		}
		this.northEnabled = northEnabled;
		if(northPan != null) northPan.revalidate();
	}
	/**
	 * 获取北方控件的最小度量（高度）。
	 * @return 北方控件的最小度量（高度）。
	 */
	public int getNorthMinValue() {
		return northMinValue;
	}
	/**
	 * 设置北方控件的最小度量（高度）。
	 * @param northMinValue 北方控件的最小度量（高度）。
	 */
	public void setNorthMinValue(int northMinValue) {
		if(northPan != null){
			northPan.setMinimumSize(new Dimension(0,northMinValue));
		}
		this.northMinValue = northMinValue;
		if(northPan != null) northPan.revalidate();
	}
	/**
	 * 获取南方布局是否被启用。
	 * @return 南方布局是否被启用。
	 */
	public boolean isSouthEnabled() {
		return southEnabled;
	}
	/**
	 * 设置南方控件是否被启用。
	 * @param southEnabled 南方控件是否被启用。
	 */
	public void setSouthEnabled(boolean southEnabled) {
		if(!southEnabled){
			if(southPan != null){
				remove(southPan);
				southPan = null;
			}
		}else{
			if(southPan == null){
				southPan = new JPanel();
				southPan.setMinimumSize(new Dimension(getSouthMinValue(),getSouthMinValue()));
				southPan.setPreferredSize(new Dimension(getSouthPreferredValue(),getSouthPreferredValue()));
				southPan.setLayout(new BorderLayout());
				super.add(southPan,BorderLayout.SOUTH);
				if(southSeparatorEnabled){
					southSeparator = createSouthSeparator();
					southPan.add(southSeparator,BorderLayout.NORTH);
				}
				if(south != null) southPan.add(south,BorderLayout.CENTER);
			}
		}
		this.southEnabled = southEnabled;
		if(southPan != null) southPan.revalidate();
	}
	/**
	 * 返回南方控件的最小度量（高度）。
	 * @return 南方控件的最小度量（高度）。
	 */
	public int getSouthMinValue() {
		return southMinValue;
	}
	/**
	 * 设置南方控件的最小度量（高度）。
	 * @param southMinValue 南方控件的最小度量（高度）。
	 */
	public void setSouthMinValue(int southMinValue) {
		if(southPan != null){
			southPan.setMinimumSize(new Dimension(0, southMinValue));
		}
		this.southMinValue = southMinValue;
		if(southPan != null) southPan.revalidate();
	}
	/**
	 * 返回东方布局是否被启用。
	 * @return 东方布局是否被启用。
	 */
	public boolean isEastEnabled() {
		return eastEnabled;
	}
	/**
	 * 设置东方布局是否被启用。
	 * @param eastEnabled 东方布局是否被启用。
	 */
	public void setEastEnabled(boolean eastEnabled) {
		if(!eastEnabled){
			if(eastPan != null){
				remove(eastPan);
				eastPan = null;
			}
		}else{
			if(eastPan == null){
				eastPan = new JPanel();
				eastPan.setMinimumSize(new Dimension(getEastMinValue(),getEastMinValue()));
				eastPan.setPreferredSize(new Dimension(getEastPreferredValue(),getEastPreferredValue()));
				eastPan.setLayout(new BorderLayout());
				super.add(eastPan,BorderLayout.EAST);
				if(eastSeparatorEnabled){
					eastSeparator = createEastSeparator();
					eastPan.add(eastSeparator, BorderLayout.WEST);
				}
				if(east != null) eastPan.add(east, BorderLayout.CENTER);
			}
		}
		this.eastEnabled = eastEnabled;
		if(eastPan != null) eastPan.revalidate();
	}
	/**
	 * 获取东方布局的最小度量（宽度）。
	 * @return 东方布局的最小度量（宽度）。
	 */
	public int getEastMinValue() {
		return eastMinValue;
	}
	/**
	 * 设置东方布局的最小度量（宽度）。
	 * @param eastMinValue 东方布局的最小度量（宽度）。
	 */
	public void setEastMinValue(int eastMinValue) {
		if(eastPan != null){
			eastPan.setMinimumSize(new Dimension(eastMinValue, 0));
		}
		this.eastMinValue = eastMinValue;
		if(eastPan != null) eastPan.revalidate();
	}
	/**
	 * 获取西方布局是否被启用。
	 * @return 西方布局是否被启用。
	 */
	public boolean isWestEnabled() {
		return westEnabled;
	}
	/**
	 * 设置西方布局是否被启用。
	 * @param westEnabled 西方布局是否被启用。
	 */
	public void setWestEnabled(boolean westEnabled) {
		if(!westEnabled){
			if(westPan != null){
				remove(westPan);
				westPan = null;
			}
		}else{
			if(westPan == null){
				westPan = new JPanel();
				westPan.setMinimumSize(new Dimension(getWestMinValue(),getWestMinValue()));
				westPan.setPreferredSize(new Dimension(getWestPreferredValue(),getWestPreferredValue()));
				westPan.setLayout(new BorderLayout());
				super.add(westPan,BorderLayout.WEST);
				if(westSeparatorEnabled){
					westSeparator = createWestSeparator();
					westPan.add(westSeparator, BorderLayout.EAST);
				}
				if(west != null) westPan.add(west,BorderLayout.CENTER);
			}
		}
		this.westEnabled = westEnabled;
		if(westPan != null) westPan.revalidate();
	}
	/**
	 * 获取西方布局的最小度量（宽度）。
	 * @return 西方布局的最小度量（宽度）。
	 */
	public int getWestMinValue() {
		return westMinValue;
	}
	/**
	 * 设置西方布局的最小度量（宽度）。
	 * @param westMinValue 西方布局的最小度量（宽度）。
	 */
	public void setWestMinValue(int westMinValue) {
		if(westPan != null){
			westPan.setMinimumSize(new Dimension(westMinValue,0));
		}
		this.westMinValue = westMinValue;
		if(westPan != null) westPan.revalidate();
	}
	
	/**
	 * 获取北方拖动条是否被禁用。
	 * @return 北方拖动条是否被禁用。
	 */
	public boolean isNorthSeparatorEnabled() {
		return northSeparatorEnabled;
	}
	/**
	 * 设置北方拖动条是否被禁用。
	 * @param northSeparatorEnabled 北方拖动条是否被禁用。
	 */
	public void setNorthSeparatorEnabled(boolean northSeparatorEnabled) {
		if(!northSeparatorEnabled){
			if(isNorthEnabled()) northPan.remove(northSeparator);
		}else{
			if(northPan != null){
				northSeparator = createNorthSeparator(); 
			}
		}
		this.northSeparatorEnabled = northSeparatorEnabled;
		if(northPan != null) northPan.repaint();
	}
	/**
	 * 获取南方拖动条是否被禁用。
	 * @return 南方拖动条是否被禁用。
	 */
	public boolean isSouthSeparatorEnabled() {
		return southSeparatorEnabled;
	}
	/**
	 * 设置南方拖动条是否被禁用。
	 * @param southSeparatorEnabled 南方拖动条是否被禁用。
	 */
	public void setSouthSeparatorEnabled(boolean southSeparatorEnabled) {
		if(!southSeparatorEnabled){
			if(isSouthEnabled()) southPan.remove(northSeparator);
		}else{
			if(southPan != null){
				southSeparator = createSouthSeparator(); 
			}
		}
		this.southSeparatorEnabled = southSeparatorEnabled;
		if(southPan != null) southPan.repaint();
	}
	/**
	 * 返回东方拖动条是否被禁用。
	 * @return 东方拖动条是否被禁用。
	 */
	public boolean isEastSeparatorEnabled() {
		return eastSeparatorEnabled;
	}
	/**
	 * 设置东方拖动条是否被禁用。
	 * @param eastSeparatorEnabled 东方拖动条是否被禁用。
	 */
	public void setEastSeparatorEnabled(boolean eastSeparatorEnabled) {
		if(!southSeparatorEnabled){
			if(isSouthEnabled()) southPan.remove(northSeparator);
		}else{
			if(southPan != null){
				southSeparator = createSouthSeparator(); 
			}
		}
		this.eastSeparatorEnabled = eastSeparatorEnabled;
		if(eastPan != null) eastPan.repaint();
	}
	/**
	 * 返回西方进度条是否被禁用。
	 * @return 设置西方进度条是否被禁用。
	 */
	public boolean isWestSeparatorEnabled() {
		return westSeparatorEnabled;
	}
	/**
	 * 设置西方进度条是否被禁用。
	 * @param westSeparatorEnabled 西方进度条是否被禁用。
	 */
	public void setWestSeparatorEnabled(boolean westSeparatorEnabled) {
		if(!westSeparatorEnabled){
			if(isWestEnabled()) westPan.remove(westSeparator);
		}else{
			if(westPan != null){
				westSeparator = createWestSeparator(); 
			}
		}
		this.westSeparatorEnabled = westSeparatorEnabled;
		if(westPan != null) westPan.repaint();
	}
	/**
	 * 返回北方控件的表现度量。
	 * @return 北方控件的表现度量。
	 */
	public int getNorthPreferredValue() {
		return northPreferredValue;
	}
	/**
	 * 设置北方控件的表现度量。
	 * @param northPreferredValue 北方控件的表现度量。
	 */
	public void setNorthPreferredValue(int northPreferredValue) {
		this.northPreferredValue = northPreferredValue;
		if(northPan != null) northPan.setPreferredSize(new Dimension(northPreferredValue,northPreferredValue));
	}
	/**
	 * 获取南方控件的表现度量。
	 * @return 南方控件的表现度量。
	 */
	public int getSouthPreferredValue() {
		return southPreferredValue;
	}
	/**
	 * 设置南方控件的表现度量。
	 * @param southPreferredValue 南方控件的表现度量。
	 */
	public void setSouthPreferredValue(int southPreferredValue) {
		this.southPreferredValue = southPreferredValue;
		if(southPan != null) southPan.setPreferredSize(new Dimension(southPreferredValue,southPreferredValue));
	}
	/**
	 * 获取东方控件的表现度量。
	 * @return 东方控件的表现度量。
	 */
	public int getEastPreferredValue() {
		return eastPreferredValue;
	}
	/**
	 * 设置东方控件的表现度量。
	 * @param eastPreferredValue 东方控件的表现度量。
	 */
	public void setEastPreferredValue(int eastPreferredValue) {
		this.eastPreferredValue = eastPreferredValue;
		if(eastPan != null) eastPan.setPreferredSize(new Dimension(eastPreferredValue,eastPreferredValue));
	}
	/**
	 * 获取西方控件的表现度量。
	 * @return 西方控件的表现度量。
	 */
	public int getWestPreferredValue() {
		return westPreferredValue;
	}
	/**
	 * 设置西方控件的表现度量。
	 * @param westPreferredValue 西方控件的表现度量。
	 */
	public void setWestPreferredValue(int westPreferredValue) {
		this.westPreferredValue = westPreferredValue;
		if(westPan != null) westPan.setPreferredSize(new Dimension(westPreferredValue,westPreferredValue));
	}
	/**
	 * 返回北方控件是否可见。
	 * @return 北方控件是否可见。
	 */
	public boolean isNorthVisible(){
		if(northPan == null) return false;
		return northPan.isVisible();
	}
	/**
	 * 设置北方控件是否可见，只有当北方控件启用时才有效。
	 * @param aFlag 北方控件是否可见。
	 */
	public void setNorthVisible(boolean aFlag){
		if(northPan == null) return;
		northPan.setVisible(aFlag);
	}
	/**
	 * 返回南方控件是否可见。
	 * @return 南方控件是否可见。
	 */
	public boolean isSouthVisible(){
		if(southPan == null) return false;
		return southPan.isVisible();
	}
	/**
	 * 设置南方控件是否可见，只有南方控件启用时才有效。
	 * @param aFlag 南方控件是否可见。
	 */
	public void setSouthVisible(boolean aFlag){
		if(southPan == null) return;
		southPan.setVisible(aFlag);
	}
	/**
	 * 返回东方控件是否可见。
	 * @return 东方控件是否可见。
	 */
	public boolean isEastVisible(){
		if(eastPan == null) return false;
		return eastPan.isVisible();
	}
	/**
	 * 设置东方控件是否可见，只有东方控件启用时才有效。
	 * @param aFlag 东方控件是否可见。
	 */
	public void setEastVisible(boolean aFlag){
		if(eastPan == null) return;
		eastPan.setVisible(aFlag);
	}
	/**
	 * 获取西方控件是否可见。
	 * @return 西方控件是可见。
	 */
	public boolean isWestVisible(){
		if(westPan == null)return false;
		return westPan.isVisible();
	}
	/**
	 * 设置西方控件是否可见，只有当西方控件被启用时才有效。
	 * @param aFlag 西方控件是否可见。
	 */
	public void setWestVisible(boolean aFlag){
		if(westPan == null) return;
		westPan.setVisible(aFlag);
	}
	
	//--------------------拖动条细节方法------------------------------------------------------
	/**
	 * 返回拖动条的背景颜色。
	 * @return 拖动条的背景颜色。
	 */
	public Color getSeparatorBackground(){
		return northSeparator.getBackground();
	}
	/**
	 * 设置拖动条的背景颜色。
	 * @param seperatorColor 拖动条的背景颜色，可以指定为null，代表拖动条没有任何颜色。
	 */
	public void setSeperatorColor(Color seperatorColor) {
		this.seperatorColor = seperatorColor;
		if(northSeparator != null) northSeparator.setBackground(seperatorColor);
		if(southSeparator != null) southSeparator.setBackground(seperatorColor);
		if(westSeparator != null) westSeparator.setBackground(seperatorColor);
		if(eastSeparator != null) eastSeparator.setBackground(seperatorColor);
		if(northPan != null) northPan.repaint();
		if(southPan != null) southPan.repaint();
		if(eastPan != null) eastPan.repaint();
		if(westPan != null) westPan.repaint();
	}
	/**
	 * 返回拖动条的粗细。
	 * @return 拖动条的粗细。
	 */
	public int getSeperatorThickness() {
		return seperatorThickness;
	}
	/**
	 * 设置拖动条的粗细。
	 * @param seperatorThickness 拖动条的粗细，以像素为单位。
	 */
	public void setSeperatorThickness(int seperatorThickness) {
		this.seperatorThickness = seperatorThickness;
		if(northSeparator != null) northSeparator.setPreferredSize(new Dimension(this.seperatorThickness,this.seperatorThickness));
		if(southSeparator != null) southSeparator.setPreferredSize(new Dimension(this.seperatorThickness,this.seperatorThickness));
		if(westSeparator != null) westSeparator.setPreferredSize(new Dimension(this.seperatorThickness,this.seperatorThickness));
		if(eastSeparator != null) eastSeparator.setPreferredSize(new Dimension(this.seperatorThickness,this.seperatorThickness));
		if(northPan != null) northPan.repaint();
		if(southPan != null) southPan.repaint();
		if(eastPan != null) eastPan.repaint();
		if(westPan != null) westPan.repaint();
	}
	/**
	 * 初始化时进行的调度。
	 */
	private void init(){
		//设置自身属性
		setLayout(new BorderLayout());
		//初始化成员变量
		setSeperatorColor(Color.GRAY);
		setSeperatorThickness(8);
		setNorthEnabled(false);
		setSouthEnabled(false);
		setEastEnabled(false);
		setWestEnabled(false);
		setNorthSeparatorEnabled(true);
		setSouthSeparatorEnabled(true);
		setEastSeparatorEnabled(true);
		setWestSeparatorEnabled(true);
		setNorthMinValue(30);
		setSouthMinValue(30);
		setEastMinValue(100);
		setWestMinValue(100);
		setNorthPreferredValue(30);
		setSouthPreferredValue(30);
		setEastPreferredValue(150);
		setWestPreferredValue(150);
		//添加中方控件
		centerPan = new JPanel();
		centerPan.setLayout(new BorderLayout());
		setCenterMinSize(new Dimension(100, 60));
		super.add(centerPan, BorderLayout.CENTER);
		updateUI();
	}
	/**
	 * 生成北方拖动条。
	 * @return 北方拖动条。
	 */
	private JSeparator createNorthSeparator(){
		JSeparator separator = createSeparator(SwingConstants.HORIZONTAL);
		separator.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				nPanSep_MouseDragged(e);
			}
		});
		return separator;
	}
	/**
	 * 生成南方拖动条。
	 * @return 南方拖动条。
	 */
	private JSeparator createSouthSeparator(){
		JSeparator separator = createSeparator(SwingConstants.HORIZONTAL);
		separator.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				sPanSep_MouseDragged(e);
			}
		});
		return separator;
	}
	/**
	 * 生成东方拖动条。
	 * @return 东方拖动条。
	 */
	private JSeparator createEastSeparator(){
		JSeparator separator = createSeparator(SwingConstants.VERTICAL);
		separator.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				ePanSep_MouseDragged(e);
			}
		});
		return separator;
	}
	/**
	 * 生成西方拖动条。
	 * @return 西方拖动条。
	 */
	private JSeparator createWestSeparator(){
		JSeparator separator = createSeparator(SwingConstants.VERTICAL);
		separator.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){
				wPanSep_MouseDragged(e);
			}
		});
		return separator;
	}
	/**
	 * 生成一个具有指定方向的拖动条。
	 * @param direction 指定的方向，为{@linkplain SwingConstants#HORIZONTAL}}与{@linkplain SwingConstants#VERTICAL}} 中的一个。
	 * @return 生成的拖动条。
	 */
	private JSeparator createSeparator(int direction){
		JSeparator separator = new JSeparator();
		//设置边界样式，为线性边界。
		separator.setBorder(new LineBorder(new Color(0, 0, 0)));
		//设置背景颜色为指定的颜色。
		separator.setBackground(this.seperatorColor);
		//绘制边界的所有像素。
		separator.setOpaque(true);
		//设置方向
		separator.setOrientation(direction);
		//设置鼠标样式
		separator.setCursor(direction == SwingConstants.HORIZONTAL ? 
				Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR) : Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		//设置拖动条粗细。
		separator.setPreferredSize(new Dimension(this.seperatorThickness,this.seperatorThickness));
		return separator;
	}
	
	/**
	 * 北方拖动条拖动时进行的调度。
	 * @param e 响应的鼠标事件。
	 */
	private void nPanSep_MouseDragged(MouseEvent e) {
		int nmin = northPan == null || !northPan.isVisible() ? 0 : getNorthMinValue();
		int climit = centerPan.getHeight() - centerPan.getMinimumSize().height;
		int nlimit = -northPan.getHeight() + nmin;
		int delta = e.getY();
		if(delta >= 0 && delta>climit) delta = climit >= 0 ? climit : 0;
		if(delta < 0 && delta<nlimit) delta = nlimit <= 0 ? nlimit : 0;
//		northPan.setPreferredSize(new Dimension(northPan.getPreferredSize().width,
//				northPan.getPreferredSize().height + delta
//		));
		setNorthPreferredValue(getNorthPreferredValue() + delta);
//		updateUI();			
		northPan.revalidate();
	}
	/**
	 * 南方拖动条拖动时进行的调度。
	 * @param e 响应的鼠标事件。
	 */
	private void sPanSep_MouseDragged(MouseEvent e) {
		int smin = southPan == null || !southPan.isVisible() ? 0 : getSouthMinValue();
		int climit = -centerPan.getHeight() + centerPan.getMinimumSize().height;
		int slimit = southPan.getHeight() - smin;
		int delta = e.getY();
		if(delta >= 0 && delta>slimit) delta = slimit >= 0 ? slimit : 0;
		if(delta < 0 && delta<climit) delta = climit <= 0 ? climit : 0;
//		southPan.setPreferredSize(new Dimension(southPan.getPreferredSize().width,
//				southPan.getPreferredSize().height - delta
//		));
		setSouthPreferredValue(getSouthPreferredValue() - delta);
//		updateUI();			
		southPan.revalidate();
	}
	/**
	 * 东方拖动条拖动时进行的调度。
	 * @param e 响应的鼠标事件。
	 */
	private void ePanSep_MouseDragged(MouseEvent e) {
		int emin = eastPan == null || !eastPan.isVisible() ? 0 : getEastMinValue();
		int climit = -centerPan.getWidth() + centerPan.getMinimumSize().width;
		int elimit = eastPan.getWidth() - emin;
		int delta = e.getX();
		if(delta >= 0 && delta>elimit) delta = elimit >= 0 ? elimit : 0;
		if(delta < 0 && delta<climit) delta = climit <= 0 ? climit : 0;
//		eastPan.setPreferredSize(new Dimension(eastPan.getPreferredSize().width - delta,
//				eastPan.getPreferredSize().height
//		));
		setEastPreferredValue(getEastPreferredValue() - delta);
//		updateUI();	
		eastPan.revalidate();
	}
	/**
	 * 西方拖动条拖动时进行的调度。
	 * @param e 响应的鼠标事件。
	 */
	private void wPanSep_MouseDragged(MouseEvent e) {
		int wmin = westPan == null || !westPan.isVisible() ? 0 : getWestMinValue();
		int climit = centerPan.getWidth() - centerPan.getMinimumSize().width;
		int wlimit = -westPan.getWidth() +wmin;
		int delta = e.getX();
		if(delta >= 0 && delta>climit) delta = climit >= 0 ? climit : 0;
		if(delta < 0 && delta<wlimit) delta = wlimit <= 0 ? wlimit : 0;
//		westPan.setPreferredSize(new Dimension(westPan.getPreferredSize().width + delta,
//				westPan.getPreferredSize().height
//		));
		setWestPreferredValue(getWestPreferredValue() + delta);
//		updateUI();
		westPan.revalidate();
	}
	
}
