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
		
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				board[r][c] = Cell.Empty;
			}
		}
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
		// Row and column values must be within the board boundaries
		if (row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new IllegalArgumentException("Rows and columns must be within the board boundaries.");
		}
		board[row][col] = cell;
	}
	
	public Cell getCellAt(int row, int col) {
		// Row and column values must be within the board boundaries
		if (row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new IllegalArgumentException("Rows and columns must be within the board boundaries.");
		}
		return board[row][col];
	}
	
	public boolean dropChip(Cell chip, int col) {
		// An empty cell cannot be dropped
		if (chip == Cell.Empty) {
			throw new IllegalArgumentException("An empty chip cannot be dropped into the board.");
		}
		
		// Check if the column value is within bounds
		if (col < 0 || col >= cols) {
			throw new IllegalArgumentException("Column value is out of bounds.");
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
	
	private boolean hasConnectFourHorizontal(int matches) {
		for (int r = 0; r < rows; r++) {
			int count = 0;
			for (int c = 0; c < cols - 1; c++) {
				if (board[r][c] != Cell.Empty && board[r][c] == board[r][c+1]) {
					count++;
					if (count == matches - 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourVertical(int matches) {
		for (int c = 0; c < cols; c++) {
			int count = 0;
			for (int r = 0; r < rows - 1; r++) {
				if (board[r][c] != Cell.Empty && board[r][c] == board[r+1][c]) {
					count++;
					if (count == matches - 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourPositiveDiagonal(int matches) {
		for (int r = 0; r < rows - matches + 1; r++) {
			for (int c = 0; c < cols - matches + 1; c++) {
				int count = 0;
				for (int i = 0; i < matches - 1; i++) {
					if (board[r+i][cols-i-1] == board[r+i+1][cols-i-2]) {
						count++;
					}
				}
				if (count == matches - 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourNegativeDiagonal(int matches) {
		for (int r = 0; r < rows - matches + 1; r++) {
			for (int c = 0; c < cols - matches + 1; c++) {
				int count = 0;
				for (int i = 0; i < matches - 1; i++) {
					if (board[r+i][c+i] == board[r+i+1][c+i+1]) {
						count++;
					}
				}
				if (count == matches - 1) {
					return true;
				}
			}
		}
		return false;
	}
}
