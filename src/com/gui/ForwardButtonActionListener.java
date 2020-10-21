package com.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForwardButtonActionListener implements ActionListener {

	private ChessFrame chessFrame;
	
	public ForwardButtonActionListener(ChessFrame chessFrame) {
		super();
		this.chessFrame = chessFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		chessFrame.forwardMove();
	}

}
