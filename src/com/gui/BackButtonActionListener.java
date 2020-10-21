package com.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButtonActionListener implements ActionListener {

	private ChessFrame chessFrame;
	
	public BackButtonActionListener(ChessFrame chessFrame) {
		super();
		this.chessFrame = chessFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		chessFrame.undoMove();
	}

}

