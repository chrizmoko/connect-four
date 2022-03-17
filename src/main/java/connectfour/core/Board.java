package connectfour.core;

/**
 * Represents a Connect Four board and provides functionality for setting or getting content from
 * the board.
 */
public class Board {
	private static final String ROW_ERROR_MESSAGE = "Row value is out of boundaries.";
	private static final String COLUMN_ERROR_MESSAGE = "Column value is out of boundaries.";

	private static int DEFAULT_NUM_ROWS = 6;
	private static int DEFAULT_NUM_COLUMNS = 7;
	private static int DEFAULT_MATCH_LENGTH = 4;
	
	private int numRows;
	private int numColumns;
	private int matchLength;
	private Cell[][] board;
	
	// Cache the lowest empty row of each column for quick access
	private int[] lowestEmptyRows;
	private boolean[] lowestEmptyRowsDirty;

	// Cache connect four state (prevents repeat check for larger boards)
	private boolean isConnectFourKnown;

	private int numEmptyCells;
	
	/**
	 * Creates a Connect Four <code>Board</code> object with standard properties. The standard
	 * Connect Four board has six rows and seven columns with an expected match length of four chips
	 * to constitute a win.
	 */
	public Board() {
		this(DEFAULT_NUM_ROWS, DEFAULT_NUM_COLUMNS, DEFAULT_MATCH_LENGTH);
	}
	
	/**
	 * Creates a Connect Four <code>Board</code> object with custom properties.
	 * 
	 * @param rows the number of rows of the board
	 * @param columns the number of columns of the board
	 * @param matches the expected match length
	 */
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
		numColumns = columns;
		matchLength = matches;
		board = new Cell[numRows][numColumns];
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				board[r][c] = Cell.EMPTY;
			}
		}
		
		lowestEmptyRows = new int[numColumns];
		lowestEmptyRowsDirty = new boolean[numColumns];

		for (int c = 0; c < numColumns; c++) {
			lowestEmptyRows[c] = numRows - 1;
			lowestEmptyRowsDirty[c] = false;
		}

		isConnectFourKnown = false;

		numEmptyCells = rows * columns;
	}
	
	/**
	 * Creates a deep copy of a <code>Board</code> object.
	 * 
	 * @param copy the <code>Board</code> to be copied
	 */
	public Board(Board copy) {
		numRows = copy.numRows;
		numColumns = copy.numColumns;
		matchLength = copy.matchLength;
		board = new Cell[numRows][numColumns];
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				board[r][c] = copy.board[r][c];
			}
		}
		
		lowestEmptyRows = new int[numColumns];
		lowestEmptyRowsDirty = new boolean[numColumns];

		for (int c = 0; c < numColumns; c++) {
			lowestEmptyRows[c] = copy.lowestEmptyRows[c];
			lowestEmptyRowsDirty[c] = copy.lowestEmptyRowsDirty[c];
		}

		numEmptyCells = copy.numEmptyCells;
	}
	
	/**
	 * Changes a <code>Cell</code> in the board into another <code>Cell</code> Using this method to
	 * manually set Connect Four chips in ways that are not consistent with the rules of the game
	 * can cause the board to be in an illegal state.
	 * <p>
	 * For more information about legal/illegal board states, see the <code>isLegal</code>
	 * documentation.
	 * 
	 * @param cell the cell type to be set
	 * @param row the row index
	 * @param column the column index
	 * @see #isLegal()
	 */
	public void setCellAt(Cell cell, int row, int column) {
		if (!isRowInBounds(row)) {
			throw new RowOutOfBoundsException(ROW_ERROR_MESSAGE);
		}
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}

		if (board[row][column] != Cell.EMPTY && cell == Cell.EMPTY) {
			numEmptyCells++;
		} else if (board[row][column] == Cell.EMPTY && cell != Cell.EMPTY) {
			numEmptyCells--;
		}
		
		board[row][column] = cell;
		lowestEmptyRowsDirty[column] = true;

		isConnectFourKnown = false;
	}
	
	/**
	 * Returns the <code>Cell</code> at a location in the board.
	 * 
	 * @param row the row index
	 * @param column the column index
	 * @return the <code>Cell</code> at the location
	 */
	public Cell getCellAt(int row, int column) {
		if (!isRowInBounds(row)) {
			throw new RowOutOfBoundsException(ROW_ERROR_MESSAGE);
		}
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		return board[row][column];
	}

	/**
	 * Determines if the row index is in bounds of the board.
	 * 
	 * @param row the row index
	 * @return <code>true</code> if the row index is in bounds; <code>false</code> otherwise
	 */
	public boolean isRowInBounds(int row) {
		return row >= 0 && row < numRows;
	}

	/**
	 * Determines if the column index is in bounds of the board.
	 * 
	 * @param column the column index
	 * @return <code>true</code> if the column index is in bounds; <code>false</code> otherwise
	 */
	public boolean isColumnInBounds(int column) {
		return column >= 0 && column < numColumns;
	}
	
	/**
	 * Places a chip in a column similar to how a real life chip fall into place in a real life
	 * Connect Four board. This method has no effect if the <code>Cell</code> is empty, but will
	 * return a <code>boolean</code> if a chip could be dropped depending on if the column is full
	 * or not.
	 * 
	 * @param cell the <code>Cell</code> to be dropped into the column
	 * @param column the column index
	 * @return <code>true</code> if the chip has been drop; <code>false</code> otherwise
	 */
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
			numEmptyCells--;
		}

		isConnectFourKnown = false;

		return true;
	}

	/**
	 * Performs the reverse of placing a chip in a column. Instead, it removes the topmost chip in
	 * the column and replaces it with an empty <code>Cell</code>. A <code>boolean</code> is
	 * returned depending if there was a chip to be removed from the column or not.
	 * 
	 * @param column the column index
	 * @return <code>true</code> if the column was not empty; <code>false</code> otherwise
	 */
	public boolean pickupChip(int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}

		int row = lowestEmptyRows[column];
		if (row == numRows - 1) {
			return false;
		}

		board[row + 1][column] = Cell.EMPTY;
		lowestEmptyRows[column]++;
		numEmptyCells++;

		isConnectFourKnown = false;

		return true;
	}

	/**
	 * Determines if the board is in a legal state. A board is legal if there are no non-empty cells
	 * that are floating. A The row index of a non-empty cell cannot be less than the row index of
	 * an empty cell.
	 * 
	 * @return <code>true</code> if the board is legal; <code>false</code> otherwise
	 */
	public boolean isLegal() {
		for (int c = 0; c < numColumns; c++) {
			for (int r = 0; r < numRows - 1; r++) {
				if (board[r][c] != Cell.EMPTY && board[r + 1][c] == Cell.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Determines if the <code>Board</code> object has a Connect Four match that is determined by
	 * the match length. Matches are found in the vertical, horizontal, and diagonal directions.
	 * <p>
	 * The match length can be found with the <code>getMatchLength</code> method.
	 * 
	 * @return <code>true</code> if there is a match found; <code>false</code> otherwise
	 * @see #getMatchLength()
	 */
	public boolean hasConnectFour() {
		isConnectFourKnown = (
			hasConnectFourHorizontal() || hasConnectFourVertical() ||
			hasConnectFourPositiveDiagonal() || hasConnectFourNegativeDiagonal()
		);
		return isConnectFourKnown;
	}

	/**
	 * Determines if the <code>Board</code> object has a draw state. A draw occurs when there are no
	 * empty cells remaining in the board.
	 * 
	 * @return <code>true</code> if there is a draw; <code>false</code> otherwise
	 */
	public boolean hasDraw() {
		return numEmptyCells == 0 && !((isConnectFourKnown) ? true : hasConnectFour());
	}

	/**
	 * Returns the row index of the lowest empty <code>Cell</code> of a column on a legal board such
	 * that all cells below it are non-empty. When the column is full, <code>-1</code> will be
	 * returned as the row index.
	 * <p>
	 * Undefined behavior can occur when the board is not legal. For more information about
	 * legal/illegal board states, see the <code>isLegal</code> documentation.
	 * 
	 * @param column the column index
	 * @return the row index of the lowest empty <code>Cell</code>; the value <code>-1</code> if the
	 * column is full
	 * @see #isLegal()
	 */
	public int getLowestEmptyRow(int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		if (lowestEmptyRowsDirty[column]) {
			for (int r = 0; r < numRows - 1; r++) {
				if (board[r][column] == Cell.EMPTY && board[r + 1][column] != Cell.EMPTY) {
					lowestEmptyRows[column] = r;
					lowestEmptyRowsDirty[column] = false;
					break;
				}
			}
		}
		return lowestEmptyRows[column];
	}

	/**
	 * Determines if a column in the board contains only empty <code>Cell</code> types.
	 * 
	 * @param column the column index
	 * @return <code>true</code> if the column is empty; <code>false</code> otherwise
	 */
	public boolean isColumnEmpty(int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		if (!lowestEmptyRowsDirty[column]) {
			return lowestEmptyRows[column] == numRows - 1;
		}
		for (int r = 0; r < numRows; r++) {
			if (board[r][column] != Cell.EMPTY) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines if a column in the board contains only non-empty <code>Cell</code> types.
	 * 
	 * @param column the column index.
	 * @return <code>true</code> if the column is full; <code>false</code> otherwise
	 */
	public boolean isColumnFull(int column) {
		if (!isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		if (!lowestEmptyRowsDirty[column]) {
			return lowestEmptyRows[column] == -1;
		}
		for (int r = 0; r < numRows; r++) {
			if (board[r][column] == Cell.EMPTY) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the match length required to satisfy a Connect Four match.
	 * 
	 * @return the match length
	 */
	public int getMatchLength() {
		return matchLength;
	}
	
	/**
	 * Returns the number of rows of the baord.
	 * 
	 * @return the number of rows of the board
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * Returns the number of columns in the board.
	 * 
	 * @return the number of columns in the board
	 */
	public int getNumColumns() {
		return numColumns;
	}
	
	private boolean hasConnectFourHorizontal() {
		for (int r = 0; r < numRows; r++) {
			int matchCount = 1;
			for (int c = 0; c < numColumns - 1; c++) {
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
		for (int c = 0; c < numColumns; c++) {
			int matchCount = 0;
			for (int r = 0; r < numRows - 1; r++) {
				if (board[r][c] != Cell.EMPTY && board[r][c] == board[r+1][c]) {
					matchCount++;
				} else {
					matchCount = 0;
				}
				if (matchCount == matchLength - 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourPositiveDiagonal() {
		for (int r = 0; r < numRows - matchLength + 1; r++) {
			for (int c = 0; c < numColumns - matchLength + 1; c++) {
				boolean matches = true;
				for (int i = 0; i < matchLength - 1; i++) {
					if (board[r][c] == Cell.EMPTY || board[r+i][c+i] != board[r+i+1][c+i+1]) {
						matches = false;
						break;
					}
				}
				if (matches) {
					return matches;
				}
			}
		}
		return false;
	}
	
	private boolean hasConnectFourNegativeDiagonal() {
		for (int r = matchLength - 1; r < numRows; r++) {
			for (int c = 0; c < numColumns - matchLength + 1; c++) {
				boolean matches = true;
				for (int i = 0; i < matchLength - 1; i++) {
					if (board[r][c] == Cell.EMPTY || board[r-i][c+i] != board[r-i-1][c+i+1]) {
						matches = false;
						break;
					}
				}
				if (matches) {
					return matches;
				}
			}
		}
		return false;
	}
}
