package com.gui;

import java.awt.Color;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class BoardButton extends JButton {
	private int index;
	private Color color;
	
	public BoardButton (int index, Color color) {
		this.index = index;
		this.color = color;
		setBackground(color);
	}
	
	public int getIndex() {
		return index;
	}
	
	public void resetColor() {
		setBackground(color);
	}
	
}
