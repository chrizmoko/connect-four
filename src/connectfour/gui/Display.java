package connectfour.gui;

import connectfour.core.*;

public class Display {
	private Board board;
	private char red, yellow, empty;
	
	public Display(Board b) {
		board = b;
		red = 'r';
		yellow = 'y';
		empty = ' ';
	}
	
	public Display(Board b, char rChar, char yChar, char eChar) {
		board = b;
		red = rChar;
		yellow = yChar;
		empty = eChar;
	}
	
	public Display(Display copy) {
		board = copy.board;
		red = copy.red;
		yellow = copy.yellow;
		empty = copy.empty;
	}
	
	public void display() {
		// Print column numbers
		System.out.print("  ");
		for (int i = 0; i < board.getColumns(); i++) {
			System.out.print((i + 1) + " ");
		}
		System.out.println(" ");
		
		// Print board contents with frame and spacing
		for (int r = 0; r < board.getRows(); r++) {
			System.out.print("| ");
			for (int c = 0; c < board.getColumns(); c++) {
				try {
					System.out.print(getCellChar(board.getCellAt(r, c)) + " ");
				} catch(ConnectFourException e) {
				}
			}
			System.out.println("|");
		}
		
		// Print bottom of the board frame
		System.out.print("+-");
		for (int i = 0; i < board.getColumns(); i++) {
			System.out.print("--");
		}
		System.out.println("+");
	}
	
	public void setRedChar(char r) {
		red = r;
	}
	
	public char getRedChar() {
		return red;
	}
	
	public void setYellowChar(char y) {
		yellow = y;
	}
	
	public char getYellowChar() {
		return yellow;
	}
	
	public void setEmptyChar(char e) {
		empty = e;
	}
	
	public char getEmptyChar() {
		return empty;
	}
	
	public char getCellChar(Cell c) {
		char cellChar;
		switch (c) {
		case Red:
			cellChar = red;
			break;
		case Yellow:
			cellChar = yellow;
			break;
		default:
			cellChar = empty;
			break;
		}
		return cellChar;
	}
}
