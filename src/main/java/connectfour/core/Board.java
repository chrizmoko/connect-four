package connectfour.core;

public class Board {
	private static final String ROW_ERROR_MESSAGE = "Row value is out of boundaries.";
	private static final String COLUMN_ERROR_MESSAGE = "Column value is out of boundaries.";

	private static int DEFAULT_NUM_ROWS = 6;
	private static int DEFAULT_NUM_COLUMNS = 7;
	private static int DEFAULT_MATCH_LENGTH = 4;
	
	private int numRows;
	private int numCols;
	private int matchLength;
	private Cell[][] board;
	
	// Cache the lowest empty row of each column for quick access
	private int[] lowestEmptyRows;
	private int numEmptyCells;
	
	public Board() {
		this(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLUMNS, DEFAULT_MATCH_LENGTH);
	}
	
	public Board(int rows, int columns, int matches) {
		if (rows < 1) {
			throw new IllegalArgumentException("Board must have at least one row.");
		}
		if (columns < 1) {
			throw new IllegalArgumentException("Board must have at least one column.");
		}
		if (matches < 1) {
			throw new IllegalArgumentException("Match requirement must be at least 1.");
		}
		if (matches > rows || matches > columns) {
			throw new IllegalArgumentException("Match requirement exceeds board dimensions.");
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

		numEmptyCells = rows * columns;
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

		numEmptyCells = copy.numEmptyCells;
	}
	
	public void setCellAt(Cell cell, int row, int column) {
		if (!isRowInBounds(row)) {
			throw new RowOutOfBoundsException(ROW_ERROR_MESSAGE);
		}
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		// Update cell count
		if (board[row][column] != Cell.EMPTY && cell == Cell.EMPTY) {
			numEmptyCells++;
		} else if (board[row][column] == Cell.EMPTY && cell != Cell.EMPTY) {
			numEmptyCells--;
		}
		// Update cache (this can cause a lot of invalidations)
		board[row][column] = cell;
	}
	
	public Cell getCellAt(int row, int column) {
		if (!isRowInBounds(row)) {
			throw new RowOutOfBoundsException(ROW_ERROR_MESSAGE);
		}
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		return board[row][column];
	}
	
	public boolean dropChip(Cell cell, int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		int row = lowestEmptyRows[column];
		if (row == -1) {
			return false;
		}
		if (cell != Cell.EMPTY) {
			board[row][column] = cell;
			lowestEmptyRows[column]--;
		}
		return true;
	}

	public boolean pickupChip(int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		int row = lowestEmptyRows[column];
		if (row == numRows - 1) {
			return false;
		}
		board[lowestEmptyRows[column] + 1][column] = Cell.EMPTY;
		lowestEmptyRows[column]++;
		return true;
	}
	
	public boolean hasConnectFour() {
		return (hasConnectFourHorizontal() || hasConnectFourVertical() ||
				hasConnectFourPositiveDiagonal() || hasConnectFourNegativeDiagonal()
		);
	}

	public int getLowestEmptyRow(int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		return lowestEmptyRows[column];
	}

	public boolean isColumnEmpty(int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		return lowestEmptyRows[column] == numRows;
	}

	public boolean isColumnFull(int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		return lowestEmptyRows[column] == -1;
	}

	public int countEmptyCells() {
		return numEmptyCells;
	}

	public int countFilledCells() {
		return (numRows * numCols) - numEmptyCells;
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

	private boolean isRowInBounds(int row) {
		return row >= 0 && row < numRows;
	}

	private boolean isColumnInBounds(int column) {
		return column >= 0 && column < numCols;
	}
}
