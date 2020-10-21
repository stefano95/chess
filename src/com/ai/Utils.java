package com.ai;

import java.util.List;
import java.util.LinkedList;

import com.chess.Alliance;
import com.chess.Figure;

public class Utils {

	public static final int GREEDY_ALGORITHM = -30000;
	
	public static boolean isGameOver (int evaluation, int algorithm) {
		switch (algorithm) {
			case GREEDY_ALGORITHM:
				return evaluation < GREEDY_ALGORITHM ||
						evaluation > (GREEDY_ALGORITHM * -1);
			default:
				return true;
		}
	}
	
	public static int getFigureValue (Figure figure) {
		if (figure == null) {
			return 0;
		}
		switch (figure) {
		case WHITE_PAWN:
		case BLACK_PAWN:
			return 100;
		case WHITE_BISHOP:
		case WHITE_KNIGHT:
		case BLACK_BISHOP:
		case BLACK_KNIGHT:
			return 330;
		case WHITE_ROOK:
		case BLACK_ROOK:
			return 500;
		case WHITE_QUEEN:
		case BLACK_QUEEN:
			return 985;
		case WHITE_KING:
		case BLACK_KING:
			return MinMax.GAME_WINNING_VALUE;
		}
		return -1;
	}
	
	public static Alliance switchMove (Alliance current) {
		return Alliance.WHITE.equals(current) ? Alliance.BLACK : Alliance.WHITE;
	}
	
	public static void generateFigureMoves (List<Integer> result,
			Figure[] table, int position) {
		Figure figure = table[position];
		switch (figure) {
		case WHITE_BISHOP:
		case BLACK_BISHOP:
			addDiagonals(result, table, position);
			break;
		case WHITE_KNIGHT:
		case BLACK_KNIGHT:
			addKnightMoves(result, table, position);
			break;
		case WHITE_QUEEN:
		case BLACK_QUEEN:
			addDiagonals(result, table, position);
		case WHITE_ROOK:
		case BLACK_ROOK:
			addLines(result, table, position);
			break;
		case WHITE_PAWN:
		case BLACK_PAWN:
			addPawnMoves(result, table, position);
			break;
		case WHITE_KING:
		case BLACK_KING:
			addKingMoves(result, table, position);
			break;
		default:
			break;
		}
	}
	
	private static void addPawnMoves(List<Integer> result,
			Figure[] table, int position) {
		int i;
		Figure figure = table[position];
		if (figure.getAlliance() == Alliance.BLACK) {
			i = position + 8;
			if (i < 64 && table[i] == null)
				result.add(i);
			i--;
			if (i < 64 && (i % 8 < position % 8) &&
					((table[i] != null && table[i].getAlliance() != figure.getAlliance())))
				result.add(i);
			i += 2;
			if (i < 64 && (i % 8 > position % 8) &&
					((table[i] != null && table[i].getAlliance() != figure.getAlliance())))
				result.add(i);
			i = position + 16;
			if (position / 8 == 1 && table[i] == null && table[i - 8] == null)
				result.add(i);
		} else {
			i = position - 8;
			if (i > -1 && table[i] == null)
				result.add(i);
			i--;
			if (i > -1 && (i % 8 < position % 8) &&
					((table[i] != null && table[i].getAlliance() != figure.getAlliance())))
				result.add(i);
			i += 2;
			if (i > -1 && (i % 8 > position % 8) &&
					((table[i] != null && table[i].getAlliance() != figure.getAlliance())))
				result.add(i);
			i = position - 16;
			if (position / 8 == 6 && table[i] == null && table[i + 8] == null)
				result.add(i);
		}
	}

	private static void addKnightMoves(List<Integer> result,
			Figure[] table, int position) {
		Figure figure = table[position];
		int i = position + 17;
		if (i < 64 && (i % 8 > position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position + 10;
		if (i < 64 && (i % 8 > position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position - 6;
		if (i > 0 && (i % 8 > position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position - 15;
		if (i > 0 && (i % 8 > position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position + 15;
		if (i < 64 && (i % 8 < position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position + 6;
		if (i < 64 && (i % 8 < position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position - 10;
		if (i > 0 && (i % 8 < position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position - 17;
		if (i > 0 && (i % 8 < position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
	}

	private static void addKingMoves(List<Integer> result,
			Figure[] table, int position) {
		Figure figure = table[position];
		int i = position + 1;
		if (i < 64 && (i % 8 > position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position - 1;
		if (i > -1 && (i % 8 < position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position + 8;
		if (i < 64 && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i--;
		if (i < 64 && (i % 8 < position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i += 2;
		if (i < 64 && (i % 8 > position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i = position - 8;
		if (i > -1 && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i--;
		if (i > -1 && (i % 8 < position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
		i += 2;
		if (i > -1 && (i % 8 > position % 8) && !(table[i] != null && table[i].getAlliance() == figure.getAlliance()))
			result.add(i);
	}

	private static void addDiagonals (List<Integer> result,
			Figure[] table, int position) {
		int i;
		Figure figure = table[position];
		//upper left diagonal
		i = position + 7;
		while (i < 64 && (i % 8 < position % 8)) {
			if (table[i] != null) {
				if (table[i].getAlliance() != figure.getAlliance())
					result.add(i);
				break;
			}
			result.add(i);
			i += 7;
		}
		//upper right diagonal
		i = position + 9;
		while (i < 64 && (i % 8 > position % 8)) {
			if (table[i] != null) {
				if (table[i].getAlliance() != figure.getAlliance())
					result.add(i);
				break;
			}
			result.add(i);
			i += 9;
		}
		//lower right diagonal
		i = position - 7;
		while (i > -1 && (i % 8 > position % 8)) {
			if (table[i] != null) {
				if (table[i].getAlliance() != figure.getAlliance())
					result.add(i);
				break;
			}
			result.add(i);
			i -= 7;
		}
		//lower left diagonal
		i = position - 9;
		while (i > -1 && (i % 8 < position % 8)) {
			if (table[i] != null) {
				if (table[i].getAlliance() != figure.getAlliance())
					result.add(i);
				break;
			}
			result.add(i);
			i -= 9;
		}
	}
	
	private static void addLines (List<Integer> result,
			Figure[] table, int position) {
		int i;
		Figure figure = table[position];
		//up
		i = position + 8;
		while (i < 64) {
			if (table[i] != null) {
				if (table[i].getAlliance() != figure.getAlliance())
					result.add(i);
				break;
			}
			result.add(i);
			i += 8;
		}
		//down
		i = position - 8;
		while (i > -1) {
			if (table[i] != null) {
				if (table[i].getAlliance() != figure.getAlliance())
					result.add(i);
				break;
			}
			result.add(i);
			i -= 8;
		}
		//left
		i = position - 1;
		while (i > -1 && (i % 8 < position % 8)) {
			if (table[i] != null) {
				if (table[i].getAlliance() != figure.getAlliance())
					result.add(i);
				break;
			}
			result.add(i);
			i--;
		}
		//right
		i = position + 1;
		while (i < 64 && (i % 8 > position % 8)) {
			if (table[i] != null) {
				if (table[i].getAlliance() != figure.getAlliance())
					result.add(i);
				break;
			}
			result.add(i);
			i++;
		}
	}
	//collects all available moves in order to make checks
	public static List<Move> collectAllMoves(Alliance alliance, Figure[] board) {
		List<Integer> figurePositions = new LinkedList<Integer>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] != null &&
					alliance.equals(board[i].getAlliance())) {
				figurePositions.add(i);
			}
		}
		List<Move> result = new LinkedList<Move>();
		for (Integer position : figurePositions) {
			List<Integer> figureMoves = new LinkedList<Integer>();
			generateFigureMoves(figureMoves, board, position);
			for (Integer fMove : figureMoves) {
				result.add(new Move(position, fMove));
			}
		}
		return result;
	}
}
