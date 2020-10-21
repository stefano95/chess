package com.chess;

import java.util.Stack;

public class Board {

	private Figure[] board;
	private Stack<MyMove> moves = new Stack<MyMove>();
	
	public Board (Figure[] board) {
		this.board = board;
	}
	
	protected Board () {
	}
	
	public Figure[] getBoard () {
		return board;
	}
	
	public Figure[] makeMove (int from, int to) {
		//cache
		MyMove move = new MyMove();
		move.from = from;
		move.to = to;
		move.taken = board[to];
		moves.push(move);
		//perform move
		board[to] = board[from];
		board[from] = null;
		
		return board;
	}
	// reinstate the state of the board to the initial state
	public MyMove undoMove () {
		MyMove move = null;
		if (moves.peek() != null) {
			move = moves.pop();
			board[move.from] = board[move.to];
			board[move.to] = move.taken;
		}
		return move;
	}
	
	public Stack<MyMove> getMoves() {
		return moves;
	}

	public class MyMove {
		private int from;
		private int to;
		private Figure taken;
		
		public int getFrom() {
			return from;
		}
		
		public int getTo() {
			return to;
		}
		
		public Figure getTaken() {
			return taken;
		}
		
	}
}
