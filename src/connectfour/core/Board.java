package connectfour.core;

public class Board {
	private static int DEFAULT_NUM_ROWS = 6;
	private static int DEFAULT_NUM_COLUMNS = 7;
	private static int DEFAULT_MATCH_LENGTH = 4;
	
	private int numRows;
	private int numCols;
	private int matchLength;
	private Cell[][] board;
	
	// Keeps track of the row of the lowest empty cell (for speedup purposes)
	private int[] lowestEmptyRows;
	
	public Board() {
		this(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLUMNS, DEFAULT_MATCH_LENGTH);
	}
	
	public Board(int rows, int columns, int matches) {
		if (rows < 0) {
			throw new IllegalArgumentException("Board cannot have negative rows.");
		}
		if (columns < 0) {
			throw new IllegalArgumentException("Board cannot have negative columns.");
		}
		if (matches < 1) {
			throw new IllegalArgumentException("Match requirement must be at least 1.");
		}
		
		numRows = rows;
		numCols = columns;
		matchLength = matches;
		board = new Cell[numRows][numCols];
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				board[r][c] = Cell.EMPTY;
			}
		}
		
		lowestEmptyRows = new int[numCols];
		for (int c = 0; c < numCols; c++) {
			lowestEmptyRows[c] = numRows - 1;
		}
	}
	
	public Board(Board copy) {
		numRows = copy.numRows;
		numCols = copy.numCols;
		matchLength = copy.matchLength;
		board = new Cell[numRows][numCols];
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				board[r][c] = copy.board[r][c];
			}
		}
		
		lowestEmptyRows = new int[numCols];
		for (int c = 0; c < numCols; c++) {
			lowestEmptyRows[c] = copy.lowestEmptyRows[c];
		}
	}
	
	public void setCellAt(Cell cell, int row, int column) {
		if (row < 0 || row >= numRows) {
			throw new IllegalArgumentException("Row value is out of board boundaries.");
		}
		if (column < 0 || column >= numCols) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		board[row][column] = cell;
	}
	
	public Cell getCellAt(int row, int column) {
		if (row < 0 || row >= numRows) {
			throw new IllegalArgumentException("Row value is out of board boundaries.");
		}
		if (column < 0 || column >= numCols) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		return board[row][column];
	}
	
	public void setLowestEmptyRow(Cell cell, int column) {
		if (column < 0 || column >= numCols) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		board[lowestEmptyRows[column]][column] = cell;
		lowestEmptyRows[column]--;
	}
	
	public int getLowestEmptyRow(int column) {
		if (column < 0 || column >= numCols) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		return lowestEmptyRows[column];
	}
	
	public void dropChip(Cell cell, int column) {
		if (cell == Cell.EMPTY) {
			throw new IllegalArgumentException("An empty chip cannot be dropped into the board.");
		}
		if (column < 0 || column >= numCols) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		setLowestEmptyRow(cell, column);
	}
	
	public boolean hasConnectFour() {
		return (hasConnectFourHorizontal() || hasConnectFourVertical() ||
				hasConnectFourPositiveDiagonal() || hasConnectFourNegativeDiagonal()
		);
	}
	
	public int getMatchLength() {
		return matchLength;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numCols;
	}
	
	private boolean hasConnectFourHorizontal() {
		for (int r = 0; r < numRows; r++) {
			int matchCount = 1;
			for (int c = 0; c < numCols - 1; c++) {
				if (board[r][c] != Cell.EMPTY && board[r][c] == board[r][c+1]) {
					matchCount++;
					if (matchCount == matchLength) {
						return true;
					}
				} else {
					matchCount = 1;
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourVertical() {
		for (int c = 0; c < numCols; c++) {
			int matchCount = 0;
			for (int r = 0; r < numRows - 1; r++) {
				// Check for matching continuity among the same non-empty cells
				if (board[r][c] != Cell.EMPTY && board[r][c] == board[r+1][c]) {
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
	
	private boolean hasConnectFourPositiveDiagonal() {
		for (int r = 0; r < numRows - matchLength + 1; r++) {
			for (int c = 0; c < numCols - matchLength + 1; c++) {
				// Manually check for a connection at each new position
				boolean matches = true;
				for (int i = 0; i < matchLength - 1; i++) {
					if (board[r][c] == Cell.EMPTY || board[r+i][c+i] != board[r+i+1][c+i+1]) {
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
	
	private boolean hasConnectFourNegativeDiagonal() {
		for (int r = matchLength - 1; r < numRows; r++) {
			for (int c = 0; c < numCols - matchLength + 1; c++) {
				// Manually check for a connection at each new position
				boolean matches = true;
				for (int i = 0; i < matchLength - 1; i++) {
					if (board[r][c] == Cell.EMPTY || board[r-i][c+i] != board[r-i-1][c+i+1]) {
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
