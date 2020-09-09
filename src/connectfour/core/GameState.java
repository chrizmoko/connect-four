package connectfour.core;

public class GameState {
	private Board board;
	private Cell currentColor;
	private boolean gameOver;
	private int numRed, numYellow, turns;
	
	public GameState() {
		board = new Board();
		currentColor = Cell.Red;
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
		return currentColor == Cell.Red;
	}
	
	public boolean isYellowTurn() {
		return currentColor == Cell.Yellow;
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
		try {
			return board.getCellAt(0, col) == Cell.Empty;
		} catch (ConnectFourException e) {
			return false;
		}
	}
	
	public void makeMove(int col) throws ConnectFourException {
		if (gameOver) {
			throw new ConnectFourException("The game has completed, cannot make further moves.");
		}
		if (col < 0 || col >= board.getColumns()) {
			throw new ConnectFourException("Column value is out of board boundaries.");
		}
		
		// Select chip color
		Cell chip = (currentColor == Cell.Red) ? Cell.Red : Cell.Yellow;
		
		// Drop chip into the given column if possible
		if (!board.dropChip(chip, col)) {
			throw new ConnectFourException("Cannot drop chip into full column.");
		}
		
		// Check for a connect four
		if (board.hasConnectFour()) {
			gameOver = true;
			return;
		}
		
		// Update game state attributes
		if (chip == Cell.Red) { 
			numRed++;
			currentColor = Cell.Yellow;
		} else {
			numYellow++;
			currentColor = Cell.Red;
		}
		turns++;
	}
	
	public GameState copy() {
		return new GameState(this);
	}
}
