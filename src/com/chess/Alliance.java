package com.chess;

public enum Alliance {
	WHITE, BLACK;
	
	public static Alliance change(Alliance target) {
		return WHITE.equals(target) ? BLACK : WHITE;
	}
}
