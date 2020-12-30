package connectfour.core;

public class GameState {
	private Board board;
	private Cell currentColor;
	private boolean gameOver;
	private int numRed, numYellow, turns;
	
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
	
	public boolean isGameDraw() {
		return gameOver && (numRed + numYellow == board.getRows() * board.getColumns());
	}
	
	public Cell getWinner() throws ConnectFourException {
		if (!gameOver) {
			throw new ConnectFourException("The game is in progress, there is no winner yet.");
		}
		if (numRed + numYellow == board.getRows() * board.getColumns()) {
			throw new ConnectFourException("The game completed in a draw, there is no winner.");
		}
		return currentColor;
	}
	
	public boolean isValidMove(int col) {
		return board.getCellAt(0, col) == Cell.EMPTY;
	}
	
	public void makeMove(int col) throws ConnectFourException {
		if (gameOver) {
			throw new ConnectFourException("The game has completed, cannot make further moves.");
		}
		if (col < 0 || col >= board.getColumns()) {
			throw new IllegalArgumentException("Column value is out of board boundaries.");
		}
		
		// Select chip color
		Cell chip = (currentColor == Cell.RED) ? Cell.RED : Cell.YELLOW;
		
		// Drop chip into the given column if possible
		if (!board.dropChip(chip, col)) {
			throw new ConnectFourException("Cannot drop chip into full column.");
		}
		
		// Check if the game has completed (either with a connect four or a draw)
		if (board.hasConnectFour() || numRed + numYellow == board.getRows() * board.getColumns()) {
			gameOver = true;
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
