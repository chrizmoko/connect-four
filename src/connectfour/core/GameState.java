package connectfour.core;

public class GameState {
	private Board board;
	private Cell currentColor;
	
	private boolean gameOver;
	
	private int numRed;
	private int numYellow; 
	private int turns;
	
	public GameState() {
		board = new Board();
		currentColor = Cell.RED;
		gameOver = false;
		numRed = 0;
		numYellow = 0;
		turns = 1;
	}
	
	public GameState(Board b) {
		board = b;
		currentColor = Cell.RED;
		gameOver = false;
		numRed = 0;
		numYellow = 0;
		turns = 1;
	}
	
	public GameState(GameState copy) {
		board = new Board(copy.board);
		currentColor = copy.currentColor;
		gameOver = copy.gameOver;
		numRed = copy.numRed;
		numYellow = copy.numYellow;
		turns = copy.turns;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getTurns() {
		return turns;
	}
	
	public boolean isRedTurn() {
		return currentColor == Cell.RED;
	}
	
	public boolean isYellowTurn() {
		return currentColor == Cell.YELLOW;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public Cell getWinner() throws ConnectFourException {
		if (!gameOver) {
			throw new ConnectFourException("The game is in progress, there is no winner yet.");
		}
		return currentColor;
	}
	
	public boolean isValidMove(int col) {
		if (col < 0 || col >= board.getNumColumns()) {
			return false;
		}
		return board.getCellAt(0, col) == Cell.EMPTY;
	}
	
	public void makeMove(int col) throws ConnectFourException {
		if (col < 0 || col >= board.getNumColumns()) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		if (gameOver) {
			throw new ConnectFourException("The game has completed, cannot make further moves.");
		}
		if (board.getCellAt(0, col) != Cell.EMPTY) {
			throw new ConnectFourException("Cannot drop chip into a full column.");
		}
		
		
		// Drop chip into the board
		Cell chip = (currentColor == Cell.RED) ? Cell.RED : Cell.YELLOW;
		board.dropChip(chip, col);
		
		// Check if the game has completed (by a connect four)
		if (board.hasConnectFour()) {
			gameOver = true;
			return;
		}
		
		// Check if the game has completed by a draw
		if (numRed + numYellow + 1 == board.getNumRows() * board.getNumColumns()) {
			gameOver = true;
			currentColor = Cell.EMPTY;
			return;
		}
		
		// Update game state attributes
		if (chip == Cell.RED) { 
			numRed++;
			currentColor = Cell.YELLOW;
		} else {
			numYellow++;
			currentColor = Cell.RED;
		}

		turns++;
	}
	
	public GameState copy() {
		return new GameState(this);
	}
}
