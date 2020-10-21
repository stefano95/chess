package com.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ai.BoardNode;
import com.ai.MinMax;
import com.ai.Move;
import com.ai.Utils;
import com.chess.Alliance;
import com.chess.Board;
import com.chess.Board.MyMove;
import com.chess.Figure;

@SuppressWarnings("serial")
public class ChessFrame extends JFrame {

	public static void main(String[] args) {
		new ChessFrame(true, true, true);
	}

	private boolean checkMoves = true;
	private boolean activeAI = true; // activeAI is made with the purpose of
										// testing
	private boolean checkKingThreat = true;

	public ChessFrame(boolean activeAI, boolean checkMoves, boolean checkKingThreat) {
		this();
		this.activeAI = activeAI;
		this.checkMoves = checkMoves;
		this.checkKingThreat = checkKingThreat;
	}

	JPanel mainPanel = new JPanel();// a panel, consisting all of the panels
	JPanel secondPanel = new JPanel(); // a panel, containing the chess
	JPanel firstPanel = new JPanel(); // panel, containing the buttons

	BoardButton[] buttons = new BoardButton[64];
	Board board;

	Color myBrown = new Color(190, 130, 21); // define color for the chess board

	int aiLevel = 5;

	// the chess board
	public ChessFrame() {
		Figure[] grid = new Figure[64];
		for (int i = 8, y = 48; i < 16; i++, y++) {
			grid[i] = Figure.BLACK_PAWN;
			grid[y] = Figure.WHITE_PAWN;
		}

		grid[0] = grid[7] = Figure.BLACK_ROOK;
		grid[56] = grid[63] = Figure.WHITE_ROOK;

		grid[1] = grid[6] = Figure.BLACK_KNIGHT;
		grid[57] = grid[62] = Figure.WHITE_KNIGHT;

		grid[2] = grid[5] = Figure.BLACK_BISHOP;
		grid[58] = grid[61] = Figure.WHITE_BISHOP;

		grid[3] = Figure.BLACK_QUEEN;
		grid[4] = Figure.BLACK_KING;

		grid[59] = Figure.WHITE_QUEEN;
		grid[60] = Figure.WHITE_KING;

		board = new Board(grid);

		// SIZES OF PANELS
		secondPanel.setPreferredSize(new Dimension(800, 800));
		firstPanel.setPreferredSize(new Dimension(300, 800));

		// BUTTONS AND ICONS
		JButton h = new JButton("");
		h.addActionListener(new HintButtonActionListener(this));
		JButton b = new JButton("");
		b.addActionListener(new BackButtonActionListener(this));
		JButton f = new JButton("");
		f.addActionListener(new ForwardButtonActionListener(this));

		ImageIcon hint = new ImageIcon("resources/hint.png");
		ImageIcon back = new ImageIcon("resources/back.png");
		ImageIcon forward = new ImageIcon("resources/forward.png");

		h.setIcon(hint);
		b.setIcon(back);
		f.setIcon(forward);

		// hide background of button
		h.setBorderPainted(false);
		h.setContentAreaFilled(true);
		h.setFocusPainted(false);
		h.setOpaque(true);

		firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));
		JPanel firstPanelNewLine = new JPanel();
		firstPanelNewLine.add(h);
		firstPanelNewLine.add(b);
		firstPanelNewLine.add(f);

		firstPanel.add(firstPanelNewLine);
		DifficultyPanel dPanel = new DifficultyPanel(aiLevel);
		dPanel.setLayout(new GridLayout(2, 0));
		dPanel.setDifficultyListener(this);
		firstPanelNewLine.add(dPanel);

		secondPanel.setLayout(new GridLayout(8, 8));
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

		mainPanel.add(firstPanel);
		add(secondPanel);

		this.setSize(1500, 800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the app
																// with close
																// button

		this.setTitle("ChessFB"); // set the title of the frame

		boolean startsWithWhite = true;

		for (int i = 0; i <= 63; i++) {
			Color color = (i % 2 == 0 && !startsWithWhite) || (startsWithWhite && i % 2 == 1) ? myBrown : Color.white;
			buttons[i] = new BoardButton(i, color);
			buttons[i].addActionListener(new BoardButtonActionListener(buttons[i], this));
			secondPanel.add(buttons[i]);

			if (i != 0 && i % 8 == 7) {
				startsWithWhite = !startsWithWhite;
			}

			if (grid[i] != null) {
				buttons[i].setIcon(grid[i].getIcon());
			}

		}

		setVisible(true);
	}

	private BoardButton lastButtonClicked;
	private List<Integer> availableMoves = new LinkedList<Integer>();
	private Alliance onMove = Alliance.WHITE;
	private Stack<MyMove> forwardMoves = new Stack<Board.MyMove>();
	private Move bestMove;
	private Move kingThreat;

	public synchronized void boardButtonClicked(BoardButton button) {
		clearHint();
		if (lastButtonClicked != null) {
			for (Integer i : availableMoves) {
				buttons[i].resetColor();
			}
			lastButtonClicked.resetColor();
			clearKingThreat();
			if (!availableMoves.contains(button.getIndex())) {
				availableMoves.clear();
				lastButtonClicked = null;
				return;
			}
			if (checkKingThreat) {
				kingThreat = checkKingThreat(lastButtonClicked.getIndex(), button.getIndex(), true);
				if (kingThreat != null) {
					buttons[kingThreat.getFrom()].setBackground(Color.red);
					buttons[kingThreat.getTo()].setBackground(Color.red);
					// Warning message if the player does mistake
					JOptionPane.showMessageDialog(null, "YOU CANT MOVE THERE AS THE KING IS IN CHECK", "INFORMATION",
							JOptionPane.INFORMATION_MESSAGE);
					clearKingThreat();
					clearAvailableMoves();
					return;
				}
			}
			makeMove(lastButtonClicked.getIndex(), button.getIndex());
			if (activeAI) {
				Move botMove = getBestMove(Alliance.BLACK, aiLevel);
				if (botMove != null) {
					makeMove(botMove.getFrom(), botMove.getTo());
					kingThreat = checkKingThreat(lastButtonClicked.getIndex(), button.getIndex(), false);
					if (kingThreat != null) {
						buttons[kingThreat.getFrom()].setBackground(Color.red);
						buttons[kingThreat.getTo()].setBackground(Color.red);
						// Warning message if the player is in check
						JOptionPane.showMessageDialog(null, "CHECK!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
						clearKingThreat();
						clearAvailableMoves();
						return;
					}
				}
			}
			clearAvailableMoves();
			availableMoves.clear();
			lastButtonClicked = null;
			forwardMoves.clear();
		} else if (board.getBoard()[button.getIndex()] != null) { //checks whether the clicked cell contains a figure 
			if (checkMoves && !board.getBoard()[button.getIndex()].getAlliance().equals(onMove)) {
				return;
			}
			lastButtonClicked = button;
			button.setBackground(Color.yellow);
			Utils.generateFigureMoves(availableMoves, board.getBoard(), button.getIndex());
			for (Integer i : availableMoves) {
				buttons[i].setBackground(Color.green);
			}
		}
	}

	private Move checkKingThreat(int from, int to, boolean makeMove) {
		if (makeMove) {
			board.makeMove(from, to);
		}
		int kingIndex = findKing(onMove);
		Move result = null;
		if (kingIndex > -1) {
			List<Move> allEnemyMoves = Utils.collectAllMoves(Alliance.change(onMove), board.getBoard());
			for (Move enemyMove : allEnemyMoves) {
				if (enemyMove.getTo() == kingIndex) {
					result = enemyMove;
					break;
				}
			}
		}
		if (makeMove) {
			board.undoMove();
		}
		return result;
	}

	//Finds the king on the board
	private int findKing(Alliance alliance) {
		Figure king = Alliance.BLACK.equals(alliance) ? Figure.BLACK_KING : Figure.WHITE_KING;
		for (int i = 0; i < board.getBoard().length; i++) {
			Figure figure = board.getBoard()[i];
			if (figure != null && figure == king) {
				return i;
			}
		}
		return -1;
	}
	
	//Gets the best move
	private Move getBestMove(Alliance forAlliance, int depth) {
		BoardNode bestChild = (BoardNode) (new MinMax(depth)).getBestChild(new BoardNode(board, forAlliance));
		if (bestChild == null) {
			return null;
		}
		return bestChild.getMove();
	}
	
	//Initiate a move from one cell to another. 
	private void makeMove(int from, int to) {
		buttons[from].setIcon(null);				//deletes the icon from the initial postition of the figure
		buttons[to].setIcon(null);
		Figure[] grid = board.makeMove(from, to);
		if (grid[to] != null) {
			buttons[to].setIcon(grid[to].getIcon());
		}
		onMove = Alliance.change(onMove);
	}

	public void undoMove() {
		availableMoves.clear();
		lastButtonClicked = null;
		clearHint();
		MyMove move = board.undoMove();
		undoMove(move);
		forwardMoves.push(move);
		move = board.undoMove();
		undoMove(move);
		forwardMoves.push(move);
	}

	private void undoMove(MyMove move) {
		if (move != null) {
			Figure movedFigure = board.getBoard()[move.getFrom()];
			buttons[move.getFrom()].setIcon(movedFigure.getIcon());
			Figure taken = move.getTaken();
			if (taken != null) {
				buttons[move.getTo()].setIcon(taken.getIcon());
			} else {
				buttons[move.getTo()].setIcon(null);
			}
		}
	}
    //takes the last played move from the stack of moves and performs it again.
	public void forwardMove() {
		if (forwardMoves.size() > 1) {
			availableMoves.clear();
			lastButtonClicked = null;
			clearHint();
			clearKingThreat();
			MyMove move = forwardMoves.pop();
			makeMove(move.getFrom(), move.getTo());
			move = forwardMoves.pop();
			makeMove(move.getFrom(), move.getTo());
		}
	}
	//uses the AI to set the background of the best move for WHITE to blue
	public void hint() {
		bestMove = getBestMove(Alliance.WHITE, 5); // Get the best move for
													// WHITE with depth 5
		if (bestMove != null) {
			buttons[bestMove.getFrom()].setBackground(Color.blue);
			buttons[bestMove.getTo()].setBackground(Color.blue);
		}
	}
	//when another button is clicked, this method resets the color of the cells, which the hint() method has colored.
	private void clearHint() {
		if (bestMove != null) {
			buttons[bestMove.getFrom()].resetColor();
			buttons[bestMove.getTo()].resetColor();
		}
		bestMove = null;
	}
	//when another button is clicked, this method resets the color of the cells, which were colored when the king is in check or exposed to threat. 
	private void clearKingThreat() {
		if (kingThreat != null) {
			buttons[kingThreat.getFrom()].resetColor();
			buttons[kingThreat.getTo()].resetColor();
		}
	}

	private void clearAvailableMoves() {
		for (Integer i : availableMoves) {
			buttons[i].resetColor();
		}
		availableMoves.clear();
		lastButtonClicked.resetColor();
		lastButtonClicked = null;
	}
	//sets the AI's level of difficulty
	public void setDifficulty(Integer level) {
		aiLevel = level;
	}
}