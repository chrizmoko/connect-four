package connectfour.core;

public class Board 
{
	private int rows, cols;
	private int matchLength;
	private Cell[][] board;
	
	public Board() {
		rows = 6;
		cols = 7;
		matchLength = 4;
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
		matchLength = copy.matchLength;
		board = new Cell[rows][cols];
		
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				board[r][c] = copy.board[r][c];
			}
		}
	}
	
	public void setCellAt(Cell cell, int row, int col) throws ConnectFourException {
		if (row < 0 || row >= rows) {
			throw new ConnectFourException("Row value is out of board boundaries.");
		}
		if (col < 0 || col >= cols) {
			throw new ConnectFourException("Column value is out of board boundaries.");
		}
		board[row][col] = cell;
	}
	
	public Cell getCellAt(int row, int col) throws ConnectFourException {
		if (row < 0 || row >= rows) {
			throw new ConnectFourException("Row value is out of board boundaries.");
		}
		if (col < 0 || col >= cols) {
			throw new ConnectFourException("Column value is out of board boundaries.");
		}
		return board[row][col];
	}
	
	public boolean dropChip(Cell chip, int col) throws ConnectFourException {
		if (chip == Cell.Empty) {
			throw new ConnectFourException("An empty chip cannot be dropped into the board.");
		}
		if (col < 0 || col >= cols) {
			throw new ConnectFourException("Column value is out of board boundaries.");
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
		return (hasConnectFourHorizontal(matchLength) || hasConnectFourVertical(matchLength) ||
				hasConnectFourPositiveDiagonal(matchLength) || hasConnectFourNegativeDiagonal(matchLength));
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return cols;
	}
	
	private boolean hasConnectFourHorizontal(int matchLength) {
		for (int r = 0; r < rows; r++) {
			int count = 0;
			for (int c = 0; c < cols - 1; c++) {
				if (board[r][c] != Cell.Empty && board[r][c] == board[r][c+1]) {
					count++;
					if (count == matchLength - 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourVertical(int matchLength) {
		for (int c = 0; c < cols; c++) {
			int count = 0;
			for (int r = 0; r < rows - 1; r++) {
				if (board[r][c] != Cell.Empty && board[r][c] == board[r+1][c]) {
					count++;
					if (count == matchLength - 1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourPositiveDiagonal(int matchLength) {
		for (int r = 0; r < rows - matchLength + 1; r++) {
			for (int c = 0; c < cols - matchLength + 1; c++) {
				int count = 0;
				for (int i = 0; i < matchLength - 1; i++) {
					if (board[r+i][cols-i-1] != Cell.Empty && board[r+i][cols-i-1] == board[r+i+1][cols-i-2]) {
						count++;
					}
				}
				if (count == matchLength - 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourNegativeDiagonal(int matchLength) {
		for (int r = 0; r < rows - matchLength + 1; r++) {
			for (int c = 0; c < cols - matchLength + 1; c++) {
				int count = 0;
				for (int i = 0; i < matchLength - 1; i++) {
					if (board[r+i][c+i] != Cell.Empty && board[r+i][c+i] == board[r+i+1][c+i+1]) {
						count++;
					}
				}
				if (count == matchLength - 1) {
					return true;
				}
			}
		}
		return false;
	}
}
