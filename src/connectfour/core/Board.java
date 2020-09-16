package connectfour.core;

public class Board 
{
	private int rows, cols;
	private int matches;
	private Cell[][] board;
	
	public Board() {
		rows = 6;
		cols = 7;
		matches = 4;
		board = new Cell[rows][cols];
		
		fillBoard(Cell.Empty);
	}
	
	public Board(int r, int c) {
		rows = r;
		cols = c;
		matches = 4;
		board = new Cell[rows][cols];
		
		fillBoard(Cell.Empty);
	}
	
	public Board(Board copy) {
		rows = copy.rows;
		cols = copy.cols;
		matches = copy.matches;
		board = new Cell[rows][cols];
		
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				board[r][c] = copy.board[r][c];
			}
		}
	}
	
	public void setCellAt(Cell cell, int row, int col) {
		if (row < 0 || row >= rows) {
			throw new IllegalArgumentException("Row value is out of board boundaries.");
		}
		if (col < 0 || col >= cols) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		board[row][col] = cell;
	}
	
	public Cell getCellAt(int row, int col) {
		if (row < 0 || row >= rows) {
			throw new IllegalArgumentException("Row value is out of board boundaries.");
		}
		if (col < 0 || col >= cols) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		return board[row][col];
	}
	
	public boolean dropChip(Cell chip, int col) {
		if (chip == Cell.Empty) {
			throw new IllegalArgumentException("An empty chip cannot be dropped into the board.");
		}
		if (col < 0 || col >= cols) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		
		// Search upwards from bottom of column until empty slot is found
		for (int r = rows - 1; r >= 0; r--) {
			if (board[r][col] == Cell.Empty) {
				board[r][col] = chip;
				return true;
			}
		}
		return false;
	}
	
	public boolean hasConnectFour() {
		return (hasConnectFourHorizontal(matches) || hasConnectFourVertical(matches) ||
				hasConnectFourPositiveDiagonal(matches) || hasConnectFourNegativeDiagonal(matches));
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return cols;
	}
	
	private void fillBoard(Cell cell) {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				board[r][c] = cell;
			}
		}
	}
	
	private boolean hasConnectFourHorizontal(int matchLength) {
		for (int r = 0; r < rows; r++) {
			int matchCount = 0;
			for (int c = 0; c < cols - 1; c++) {
				// Check for matching continuity among the same non-empty cells
				if (board[r][c] != Cell.Empty && board[r][c] == board[r][c+1]) {
					matchCount++;
				} else {
					matchCount = 0;
				}
				
				// Check if there has been a match of certain length
				if (matchCount == matchLength - 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourVertical(int matchLength) {
		for (int c = 0; c < cols; c++) {
			int matchCount = 0;
			for (int r = 0; r < rows - 1; r++) {
				// Check for matching continuity among the same non-empty cells
				if (board[r][c] != Cell.Empty && board[r][c] == board[r+1][c]) {
					matchCount++;
				} else {
					matchCount = 0;
				}
				
				// Check if there has been a match of certain length
				if (matchCount == matchLength - 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourPositiveDiagonal(int matchLength) {
		for (int r = matchLength - 1; r < rows; r++) {
			for (int c = 0; c < cols - matchLength + 1; c++) {
				// Manually check for a connection at each new position
				boolean matches = true;
				for (int i = 0; i < matchLength - 1; i++) {
					if (board[r][c] == Cell.Empty || board[r-i][c+i] != board[r-i-1][c+i+1]) {
						matches = false;
						break;
					}
				}
				
				// Return if there has been a complete match
				if (matches) {
					return matches;
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourNegativeDiagonal(int matchLength) {
		for (int r = 0; r < rows - matchLength + 1; r++) {
			for (int c = 0; c < cols - matchLength + 1; c++) {
				// Manually check for a connection at each new position
				boolean matches = true;
				for (int i = 0; i < matchLength - 1; i++) {
					if (board[r][c] == Cell.Empty || board[r+i][c+i] != board[r+i+1][c+i+1]) {
						matches = false;
						break;
					}
				}
				
				// Return if there has been a complete match
				if (matches) {
					return matches;
				}
			}
		}
		return false;
	}
}
