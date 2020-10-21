package com.chess;
import javax.swing.ImageIcon;

public enum Figure {
	
	WHITE_PAWN (Alliance.WHITE, "wP", "wpawn.png"), WHITE_KNIGHT (Alliance.WHITE, "wH", "wknight.png"), WHITE_BISHOP (Alliance.WHITE, "wB", "wbishop.png"),
	WHITE_ROOK (Alliance.WHITE, "wR", "wrook.png"), WHITE_QUEEN (Alliance.WHITE, "wQ", "wqueen.png"), WHITE_KING (Alliance.WHITE, "wK", "wking.png"),
	BLACK_PAWN (Alliance.BLACK, "bP", "bpawn.png"), BLACK_KNIGHT (Alliance.BLACK, "bH", "bknight.png"), BLACK_BISHOP (Alliance.BLACK, "bB", "bbishop.png"),
	BLACK_ROOK (Alliance.BLACK, "bR", "brook.png"), BLACK_QUEEN (Alliance.BLACK, "bQ", "bqueen.png"), BLACK_KING (Alliance.BLACK, "bK", "bking.png");
	
	private static final String IMAGES_PATH = "resources/";
	
	private Alliance alliance;
	private String initials;
	private ImageIcon icon;
	
	private Figure (Alliance alliance, String initials, String icon) {
		this.alliance = alliance;
		this.initials = initials;
		this.icon = new ImageIcon(IMAGES_PATH + icon);
	}
	
	public Alliance getAlliance () {
		return alliance;
	}
	
	public String getInitials () {
		return initials;
	}
	
	public ImageIcon getIcon () {
		return icon;
	}
}
