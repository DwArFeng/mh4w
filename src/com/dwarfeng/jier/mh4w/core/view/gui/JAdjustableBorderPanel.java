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
 * �ɵ����ı߽粼����塣
 * <p>�����Ĳ��������ڱ߽粼����ʽ�еĲ��֣������߽���߽�֮ǰ���϶�����ͨ���϶���Щ�϶���
 * ���Ե����߽�Ĵ�С���Դﵽ��̬�����߽��С��Ч����
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class JAdjustableBorderPanel extends JPanel{
	
	private static final long serialVersionUID = 8778731300497488535L;
	
	/**
	 * ˮƽģ�壺Ĭ����ʾ�����С�����������Ŀռ䣬���пؼ���С����100
	 */
	public final static int MOUDLE_HOR = 1;
	/**
	 * ��ֱģ�壺Ĭ����ʾ�����У�����������Ŀؼ������пؼ���С����100
	 */
	public final static int MOUDLE_VEC = 2;
	/**
	 * ����ģ�壺Ĭ����ʾ�У�����������Ŀؼ������пؼ���С����100
	 */
	public final static int MOUDLE_CD = 3;
	/**
	 * ȫ��ģ�壺Ĭ����ʾ���пؼ�����С����100
	 */
	public final static int MOUDLE_AL = 4;
	
	//----------------���岼�ֳ�Ա����---------------------------------------------------------------
	/**
	 * ���������岼�֣������϶�����
	 */
	private JPanel westPan;
	/**
	 * ���������Ƿ����á�
	 */
	private boolean westEnabled;
	/**
	 * �����ؼ�����С������
	 */
	private int westMinValue;
	/**
	 * �����ؼ��ı��ֶ�����
	 */
	private int westPreferredValue;
	/**
	 * ���������岼�֣������϶�����
	 */
	private JPanel eastPan;
	/**
	 * ���������Ƿ����á�
	 */
	private boolean eastEnabled;
	/**
	 * �����ؼ�����С������
	 */
	private int eastMinValue;
	/**
	 * �����ؼ��ı��ֶ�����
	 */
	private int eastPreferredValue;
	/**
	 * ���������岼�֣������϶�����
	 */
	private JPanel northPan;
	/**
	 * ���������岼���Ƿ����á�
	 */
	private boolean northEnabled;
	/**
	 * �����ؼ�����С������
	 */
	private int northMinValue;
	/**
	 * �����ؼ��ı��ֶ�����
	 */
	private int northPreferredValue;
	/**
	 * �Ϸ������岼�֣������϶�����
	 */
	private JPanel southPan;
	/**
	 * �Ϸ������Ƿ����á�
	 */
	private boolean southEnabled;
	/**
	 * �Ϸ��ؼ�����С������
	 */
	private int southMinValue;
	/**
	 * �Ϸ��ؼ��ı��ֶ�����
	 */
	private int southPreferredValue;
	/**
	 * �з������岼�֡�
	 */
	private JPanel centerPan;
	
	//-------------------�϶����йس�Ա����-----------------------------------------------------------------------------------------------
	
	/**
	 * �������϶�����
	 */
	private JSeparator westSeparator;
	/**
	 * �������϶����Ƿ�����
	 */
	private boolean westSeparatorEnabled;
	/**
	 * �������϶���
	 */
	private JSeparator eastSeparator;
	/**
	 * �������϶����Ƿ�����
	 */
	private boolean eastSeparatorEnabled;
	/**
	 * �������϶�����
	 */
	private JSeparator northSeparator;
	/**
	 * �������϶����Ƿ����á�
	 */
	private boolean northSeparatorEnabled;
	/**
	 * �Ϸ����϶�����
	 */
	private JSeparator southSeparator;
	/**
	 * �Ϸ����϶����Ƿ����á�
	 */
	private boolean southSeparatorEnabled;
	
	//---------------------------�����Ա����-----------------------------------------------------------------------------------------
	
	private Component west;
	
	private Component east;
	
	private Component north;
	
	private Component south;
	
	private Component center;
	
	//--------------------------ϸ�ڱ���-------------------------------------------------------------------------------------------------------
	/**
	 * �϶����ı�����ɫ��
	 */
	protected Color seperatorColor;
	/**
	 * �϶����Ĵ�ϸ��
	 */
	protected int seperatorThickness;

	/**
	 * ����һ��Ĭ�ϵĿɵ����߽粼����塣
	 */
	public JAdjustableBorderPanel() {
		init();
	}
	
	/**
	 * ���������������С������
	 * @param dimension �����������С������
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
	 * ���������������С������
	 * @return �����������С������
	 */
	public Dimension getCenterMinSize(){
		return centerPan.getMinimumSize();
	}
	//--------------------------------������÷���----------------------------------------------------------------------------------------------------------------
	/**
	 * ��ؼ����������ķ�����
	 * <p>�÷�����д�˸��෽����ʹ�����ͨ����ͬ��Լ����ͬ���Ŀ��϶��ؼ������ָ�����������
	 * <br>Լ������ֻ����{@linkplain BorderLayout}�е�Լ�������е�һ����������׳��쳣��
	 * @param component ��Ҫ��ÿؼ�����ӵ������
	 * @param constraints Լ��������ֻ����{@code BorderLayout.EAST}��{@code BorderLayout.WEST}��{@code BorderLayout.NORTH}��
	 * {@code BorderLayout.SOUTH}��{@code BorderLayout.CENTER} �е�һ����
	 * @throws IllegalArgumentException ��Լ��������{@code BorderLayout}�е��κ�һ��Լ��ʱ�׳����쳣��
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
	 * ��ȡ�ÿؼ��ı��������
	 * @return �ÿؼ��ı��������
	 */
	public Component getNorth() {
		return north;
	}
	/**
	 * ���øÿؼ��ı��������
	 * @param north ָ���ı��������
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
	 * ��ȡ�ÿؼ����Ϸ������
	 * @return �ÿؼ����Ϸ������
	 */
	public Component getSouth() {
		return south;
	}
	/**
	 * ���øÿؼ����Ϸ������
	 * @param south �ÿؼ����Ϸ������
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
	 * ���ظù����Ķ��������
	 * @return �ÿؼ��Ķ��������
	 */
	public Component getEast() {
		return east;
	}
	/**
	 * ���øÿؼ��Ķ��������
	 * @param east ָ���Ķ��������
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
	 * ��ȡ�ÿؼ����������
	 * @return  �ÿؼ������������
	 */
	public Component getWest() {
		return west;
	}
	/**
	 * ���øÿؼ������������
	 * @param west ָ�������������
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
	 * ���ظÿؼ����з��ؼ���
	 * @return �ÿؼ����з��ؼ���
	 */
	public Component getCenter() {
		return center;
	}
	/**
	 * ���øÿؼ����з��ؼ���
	 * @param center ָ�����з��ؼ���
	 */
	public void setCenter(Component center) {
		if(center == null){
			centerPan.remove(this.center);
		}else{
			centerPan.add(center,BorderLayout.CENTER);
		}
		this.center = center;
	}
	//--------------------------�������ú��϶������÷���------------------------------------------------------------------------------------------------
	/**
	 * ��ȡ���������Ƿ����á�
	 * @return �Ƿ����á�
	 */
	public boolean isNorthEnabled() {
		return northEnabled;
	}
	/**
	 * ���ñ��������Ƿ����á�
	 * @param northEnabled �Ƿ����á�
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
	 * ��ȡ�����ؼ�����С�������߶ȣ���
	 * @return �����ؼ�����С�������߶ȣ���
	 */
	public int getNorthMinValue() {
		return northMinValue;
	}
	/**
	 * ���ñ����ؼ�����С�������߶ȣ���
	 * @param northMinValue �����ؼ�����С�������߶ȣ���
	 */
	public void setNorthMinValue(int northMinValue) {
		if(northPan != null){
			northPan.setMinimumSize(new Dimension(0,northMinValue));
		}
		this.northMinValue = northMinValue;
		if(northPan != null) northPan.revalidate();
	}
	/**
	 * ��ȡ�Ϸ������Ƿ����á�
	 * @return �Ϸ������Ƿ����á�
	 */
	public boolean isSouthEnabled() {
		return southEnabled;
	}
	/**
	 * �����Ϸ��ؼ��Ƿ����á�
	 * @param southEnabled �Ϸ��ؼ��Ƿ����á�
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
	 * �����Ϸ��ؼ�����С�������߶ȣ���
	 * @return �Ϸ��ؼ�����С�������߶ȣ���
	 */
	public int getSouthMinValue() {
		return southMinValue;
	}
	/**
	 * �����Ϸ��ؼ�����С�������߶ȣ���
	 * @param southMinValue �Ϸ��ؼ�����С�������߶ȣ���
	 */
	public void setSouthMinValue(int southMinValue) {
		if(southPan != null){
			southPan.setMinimumSize(new Dimension(0, southMinValue));
		}
		this.southMinValue = southMinValue;
		if(southPan != null) southPan.revalidate();
	}
	/**
	 * ���ض��������Ƿ����á�
	 * @return ���������Ƿ����á�
	 */
	public boolean isEastEnabled() {
		return eastEnabled;
	}
	/**
	 * ���ö��������Ƿ����á�
	 * @param eastEnabled ���������Ƿ����á�
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
	 * ��ȡ�������ֵ���С��������ȣ���
	 * @return �������ֵ���С��������ȣ���
	 */
	public int getEastMinValue() {
		return eastMinValue;
	}
	/**
	 * ���ö������ֵ���С��������ȣ���
	 * @param eastMinValue �������ֵ���С��������ȣ���
	 */
	public void setEastMinValue(int eastMinValue) {
		if(eastPan != null){
			eastPan.setMinimumSize(new Dimension(eastMinValue, 0));
		}
		this.eastMinValue = eastMinValue;
		if(eastPan != null) eastPan.revalidate();
	}
	/**
	 * ��ȡ���������Ƿ����á�
	 * @return ���������Ƿ����á�
	 */
	public boolean isWestEnabled() {
		return westEnabled;
	}
	/**
	 * �������������Ƿ����á�
	 * @param westEnabled ���������Ƿ����á�
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
	 * ��ȡ�������ֵ���С��������ȣ���
	 * @return �������ֵ���С��������ȣ���
	 */
	public int getWestMinValue() {
		return westMinValue;
	}
	/**
	 * �����������ֵ���С��������ȣ���
	 * @param westMinValue �������ֵ���С��������ȣ���
	 */
	public void setWestMinValue(int westMinValue) {
		if(westPan != null){
			westPan.setMinimumSize(new Dimension(westMinValue,0));
		}
		this.westMinValue = westMinValue;
		if(westPan != null) westPan.revalidate();
	}
	
	/**
	 * ��ȡ�����϶����Ƿ񱻽��á�
	 * @return �����϶����Ƿ񱻽��á�
	 */
	public boolean isNorthSeparatorEnabled() {
		return northSeparatorEnabled;
	}
	/**
	 * ���ñ����϶����Ƿ񱻽��á�
	 * @param northSeparatorEnabled �����϶����Ƿ񱻽��á�
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
	 * ��ȡ�Ϸ��϶����Ƿ񱻽��á�
	 * @return �Ϸ��϶����Ƿ񱻽��á�
	 */
	public boolean isSouthSeparatorEnabled() {
		return southSeparatorEnabled;
	}
	/**
	 * �����Ϸ��϶����Ƿ񱻽��á�
	 * @param southSeparatorEnabled �Ϸ��϶����Ƿ񱻽��á�
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
	 * ���ض����϶����Ƿ񱻽��á�
	 * @return �����϶����Ƿ񱻽��á�
	 */
	public boolean isEastSeparatorEnabled() {
		return eastSeparatorEnabled;
	}
	/**
	 * ���ö����϶����Ƿ񱻽��á�
	 * @param eastSeparatorEnabled �����϶����Ƿ񱻽��á�
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
	 * ���������������Ƿ񱻽��á�
	 * @return ���������������Ƿ񱻽��á�
	 */
	public boolean isWestSeparatorEnabled() {
		return westSeparatorEnabled;
	}
	/**
	 * ���������������Ƿ񱻽��á�
	 * @param westSeparatorEnabled �����������Ƿ񱻽��á�
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
	 * ���ر����ؼ��ı��ֶ�����
	 * @return �����ؼ��ı��ֶ�����
	 */
	public int getNorthPreferredValue() {
		return northPreferredValue;
	}
	/**
	 * ���ñ����ؼ��ı��ֶ�����
	 * @param northPreferredValue �����ؼ��ı��ֶ�����
	 */
	public void setNorthPreferredValue(int northPreferredValue) {
		this.northPreferredValue = northPreferredValue;
		if(northPan != null) northPan.setPreferredSize(new Dimension(northPreferredValue,northPreferredValue));
	}
	/**
	 * ��ȡ�Ϸ��ؼ��ı��ֶ�����
	 * @return �Ϸ��ؼ��ı��ֶ�����
	 */
	public int getSouthPreferredValue() {
		return southPreferredValue;
	}
	/**
	 * �����Ϸ��ؼ��ı��ֶ�����
	 * @param southPreferredValue �Ϸ��ؼ��ı��ֶ�����
	 */
	public void setSouthPreferredValue(int southPreferredValue) {
		this.southPreferredValue = southPreferredValue;
		if(southPan != null) southPan.setPreferredSize(new Dimension(southPreferredValue,southPreferredValue));
	}
	/**
	 * ��ȡ�����ؼ��ı��ֶ�����
	 * @return �����ؼ��ı��ֶ�����
	 */
	public int getEastPreferredValue() {
		return eastPreferredValue;
	}
	/**
	 * ���ö����ؼ��ı��ֶ�����
	 * @param eastPreferredValue �����ؼ��ı��ֶ�����
	 */
	public void setEastPreferredValue(int eastPreferredValue) {
		this.eastPreferredValue = eastPreferredValue;
		if(eastPan != null) eastPan.setPreferredSize(new Dimension(eastPreferredValue,eastPreferredValue));
	}
	/**
	 * ��ȡ�����ؼ��ı��ֶ�����
	 * @return �����ؼ��ı��ֶ�����
	 */
	public int getWestPreferredValue() {
		return westPreferredValue;
	}
	/**
	 * ���������ؼ��ı��ֶ�����
	 * @param westPreferredValue �����ؼ��ı��ֶ�����
	 */
	public void setWestPreferredValue(int westPreferredValue) {
		this.westPreferredValue = westPreferredValue;
		if(westPan != null) westPan.setPreferredSize(new Dimension(westPreferredValue,westPreferredValue));
	}
	/**
	 * ���ر����ؼ��Ƿ�ɼ���
	 * @return �����ؼ��Ƿ�ɼ���
	 */
	public boolean isNorthVisible(){
		if(northPan == null) return false;
		return northPan.isVisible();
	}
	/**
	 * ���ñ����ؼ��Ƿ�ɼ���ֻ�е������ؼ�����ʱ����Ч��
	 * @param aFlag �����ؼ��Ƿ�ɼ���
	 */
	public void setNorthVisible(boolean aFlag){
		if(northPan == null) return;
		northPan.setVisible(aFlag);
	}
	/**
	 * �����Ϸ��ؼ��Ƿ�ɼ���
	 * @return �Ϸ��ؼ��Ƿ�ɼ���
	 */
	public boolean isSouthVisible(){
		if(southPan == null) return false;
		return southPan.isVisible();
	}
	/**
	 * �����Ϸ��ؼ��Ƿ�ɼ���ֻ���Ϸ��ؼ�����ʱ����Ч��
	 * @param aFlag �Ϸ��ؼ��Ƿ�ɼ���
	 */
	public void setSouthVisible(boolean aFlag){
		if(southPan == null) return;
		southPan.setVisible(aFlag);
	}
	/**
	 * ���ض����ؼ��Ƿ�ɼ���
	 * @return �����ؼ��Ƿ�ɼ���
	 */
	public boolean isEastVisible(){
		if(eastPan == null) return false;
		return eastPan.isVisible();
	}
	/**
	 * ���ö����ؼ��Ƿ�ɼ���ֻ�ж����ؼ�����ʱ����Ч��
	 * @param aFlag �����ؼ��Ƿ�ɼ���
	 */
	public void setEastVisible(boolean aFlag){
		if(eastPan == null) return;
		eastPan.setVisible(aFlag);
	}
	/**
	 * ��ȡ�����ؼ��Ƿ�ɼ���
	 * @return �����ؼ��ǿɼ���
	 */
	public boolean isWestVisible(){
		if(westPan == null)return false;
		return westPan.isVisible();
	}
	/**
	 * ���������ؼ��Ƿ�ɼ���ֻ�е������ؼ�������ʱ����Ч��
	 * @param aFlag �����ؼ��Ƿ�ɼ���
	 */
	public void setWestVisible(boolean aFlag){
		if(westPan == null) return;
		westPan.setVisible(aFlag);
	}
	
	//--------------------�϶���ϸ�ڷ���------------------------------------------------------
	/**
	 * �����϶����ı�����ɫ��
	 * @return �϶����ı�����ɫ��
	 */
	public Color getSeparatorBackground(){
		return northSeparator.getBackground();
	}
	/**
	 * �����϶����ı�����ɫ��
	 * @param seperatorColor �϶����ı�����ɫ������ָ��Ϊnull�������϶���û���κ���ɫ��
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
	 * �����϶����Ĵ�ϸ��
	 * @return �϶����Ĵ�ϸ��
	 */
	public int getSeperatorThickness() {
		return seperatorThickness;
	}
	/**
	 * �����϶����Ĵ�ϸ��
	 * @param seperatorThickness �϶����Ĵ�ϸ��������Ϊ��λ��
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
	 * ��ʼ��ʱ���еĵ��ȡ�
	 */
	private void init(){
		//������������
		setLayout(new BorderLayout());
		//��ʼ����Ա����
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
		//����з��ؼ�
		centerPan = new JPanel();
		centerPan.setLayout(new BorderLayout());
		setCenterMinSize(new Dimension(100, 60));
		super.add(centerPan, BorderLayout.CENTER);
		updateUI();
	}
	/**
	 * ���ɱ����϶�����
	 * @return �����϶�����
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
	 * �����Ϸ��϶�����
	 * @return �Ϸ��϶�����
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
	 * ���ɶ����϶�����
	 * @return �����϶�����
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
	 * ���������϶�����
	 * @return �����϶�����
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
	 * ����һ������ָ��������϶�����
	 * @param direction ָ���ķ���Ϊ{@linkplain SwingConstants#HORIZONTAL}}��{@linkplain SwingConstants#VERTICAL}} �е�һ����
	 * @return ���ɵ��϶�����
	 */
	private JSeparator createSeparator(int direction){
		JSeparator separator = new JSeparator();
		//���ñ߽���ʽ��Ϊ���Ա߽硣
		separator.setBorder(new LineBorder(new Color(0, 0, 0)));
		//���ñ�����ɫΪָ������ɫ��
		separator.setBackground(this.seperatorColor);
		//���Ʊ߽���������ء�
		separator.setOpaque(true);
		//���÷���
		separator.setOrientation(direction);
		//���������ʽ
		separator.setCursor(direction == SwingConstants.HORIZONTAL ? 
				Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR) : Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		//�����϶�����ϸ��
		separator.setPreferredSize(new Dimension(this.seperatorThickness,this.seperatorThickness));
		return separator;
	}
	
	/**
	 * �����϶����϶�ʱ���еĵ��ȡ�
	 * @param e ��Ӧ������¼���
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
	 * �Ϸ��϶����϶�ʱ���еĵ��ȡ�
	 * @param e ��Ӧ������¼���
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
	 * �����϶����϶�ʱ���еĵ��ȡ�
	 * @param e ��Ӧ������¼���
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
	 * �����϶����϶�ʱ���еĵ��ȡ�
	 * @param e ��Ӧ������¼���
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
