package com.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardButtonActionListener implements ActionListener {

	private BoardButton button;
	private ChessFrame chess;
	
	public BoardButtonActionListener(BoardButton button, ChessFrame chess) {
		super();
		this.button = button;
		this.chess = chess;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		chess.boardButtonClicked(button);
	}
	
}
