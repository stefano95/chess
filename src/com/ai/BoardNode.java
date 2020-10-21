package com.ai;

import java.util.LinkedList;
import java.util.Queue;

import com.chess.Alliance;
import com.chess.Board;
import com.chess.Figure;

public class BoardNode implements Node {

	private class MyIterator implements ChildrenIterator {

		Queue<Integer> figurePositions = new LinkedList<Integer>();
		LinkedList<Integer> availableMoves = new LinkedList<>();
		Integer moveFrom;
		
		public MyIterator() {
			Figure[] board = BoardNode.this.board.getBoard();
			for (int i = 0; i < board.length; i++) {
				if (board[i] != null &&
						onMove.equals(board[i].getAlliance())) {
					figurePositions.add(i);
				}
			}
		}
		
		@Override
		public boolean hasNext() {
			Figure[] board = BoardNode.this.board.getBoard();
			while (availableMoves.isEmpty() && !figurePositions.isEmpty()) {
				moveFrom = figurePositions.poll();
				Utils.generateFigureMoves(availableMoves, board, moveFrom);
			}
			return !availableMoves.isEmpty();
		}

		@Override
		public Node getNext() {
			Integer moveTo = availableMoves.poll();
			board.makeMove(moveFrom, moveTo);
			Board.MyMove lastMove = board.getMoves().peek();
			BoardNode result = new BoardNode(board,
					Utils.switchMove(onMove),
					new Move(lastMove.getFrom(), lastMove.getTo()),
					Utils.getFigureValue(lastMove.getTaken()));
			return result;
		}

		@Override
		public void clearLast() {
			board.undoMove();
		}		
	}
	
	private Board board;
	private Alliance onMove;
	private Move move;
	private int value;
	
	public BoardNode (Board board, Alliance onMove) {
		this(board, onMove, null, 0);
	}
	
	private BoardNode (Board board, Alliance onMove, Move move, int value) {
		this.board = board;
		this.onMove = onMove;
		this.move = move;
		this.value = value;
	}
	
	public Move getMove () {
		return move;
	}
	
	@Override
	public ChildrenIterator getChildrenIterator() {
		return new MyIterator();
	}

	@Override
	public int getValue() {
		return value;
	};
	
}
