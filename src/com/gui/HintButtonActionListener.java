package com.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HintButtonActionListener implements ActionListener {

	private ChessFrame chessFrame;
	
	public HintButtonActionListener(ChessFrame chessFrame) {
		super();
		this.chessFrame = chessFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		chessFrame.hint();
	}

}
