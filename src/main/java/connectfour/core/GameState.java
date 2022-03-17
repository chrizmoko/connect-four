package connectfour.core;

import java.util.Arrays;
import java.util.HashSet;

/**
 * The <code>GameState</code> manages a state of the Connect Four game. A state consists of the
 * board and who's turn it is in relation to it.
 */
public class GameState {
	private static final String DUPLICATE_CELL_ERROR_MESSAGE = "Duplicate found in array.";
	private static final String EMPTY_CELL_ERROR_MESSAGE = "Empty Cell cannot be a player.";
	private static final String COLUMN_ERROR_MESSAGE = "Column value is out of board boundaries.";
	private static final String COLUMN_FULL_ERROR_MESSAGE = "Cannot drop chip in full column.";

	private Board board;
	private Player[] players;
	private Player currentPlayer; 
	private boolean gameCompleted;
	private int turnNumber;
	
	/**
	 * Creates the initial <code>GameState</code> of a Connect Four Game with custom starting
	 * properties. A <code>Board</code> object will serve as the starting state of the game state's
	 * board and will take an array of unique, non-empty <code>Cell</code> enums that will represent
	 * the players. The first <code>Cell</code> of the player array will be the starting player. The
	 * turn number of this <code>GameState</code> can also be set.
	 * 
	 * @param baseBoard the <code>Board</code> object to be used
	 * @param basePlayers an array of unique <code>Cell</code> enums
	 * @param baseTurnNumber the turn that the <code>GameState</code> begins on
	 * @see Board
	 * @see Cell
	 */
	public GameState(Board baseBoard, Player[] basePlayers, int baseTurnNumber) {
		HashSet<Cell> uniqueCells = new HashSet<>(basePlayers.length);
		for (Player p : basePlayers) {
			if (p.getCell() == Cell.EMPTY) {
				throw new IllegalArgumentException(EMPTY_CELL_ERROR_MESSAGE);
			}
			if (uniqueCells.contains(p.getCell())) {
				throw new IllegalArgumentException(DUPLICATE_CELL_ERROR_MESSAGE);
			}
			uniqueCells.add(p.getCell());
		}

		board = new Board(baseBoard);
		players = Arrays.copyOf(basePlayers, basePlayers.length);
		currentPlayer = players[turnNumber % players.length];
		gameCompleted = board.hasConnectFour() || board.hasDraw();
		turnNumber = baseTurnNumber;
	}
	
	/**
	 * Creates a deep copy of a <code>GameState</code> object.
	 * 
	 * @param copy the <code>GameState</code> object to be copied
	 */
	public GameState(GameState copy) {
		board = new Board(copy.board);
		players = copy.players; // Cannot be accessed from outside, so ok to copy reference
		currentPlayer = copy.currentPlayer;
		gameCompleted = copy.gameCompleted;
		turnNumber = copy.turnNumber;
	}
	
	/**
	 * Returns a reference to the <code>Board</code> object of the game state.
	 * 
	 * @return a reference to the <code>Board</code> object
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the turn number of the game. The turn number starts on a zero-index.
	 * 
	 * @return the turn number
	 */
	public int getTurnNumber() {
		return turnNumber;
	}
	
	 /**
	  * Returns the <code>Player</code> object of the player whose turn it is to make the current
	  * move. If the game is completed, the current player will be the winner if there is a connect
	  * four match. For a draw, a <code>null</code> value will be returned.
	  *
	  * @return the <code>Player</code> object of the current player or the winner;
	  * otherwise <code>null</code> if there is no winner
	  */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	 /**
	  * Returns an array of <code>Player</code> objects of all players in the Connect Four game.
	  *
	  * @return a <code>Player</code> array
	  */
	public Player[] getAllPlayers() {
		return Arrays.copyOf(players, players.length);
	}
	
	/**
	 * Determines if the game state has come to a completion. The game has completed if either a
	 * connect four match or a draw has occured.
	 * 
	 * @return <code>true</code> if the game is over; <code>false</code> otherwise
	 */
	public boolean isGameCompleted() {
		return gameCompleted;
	}
	
	/**
	 * Determines if the placing a chip in the column will be a valid move. A valid move requires
	 * the column index to be in bounds of the <code>Board</code> and the column to have at least an
	 * empty cell.
	 * 
	 * @param column the column index of the board
	 * @return <code>true</code> if the move is valid; <code>false</code> otherwise
	 * @see Board
	 */
	public boolean isValidMove(int column) {
		if (!board.isColumnInBounds(column)) {
			return false;
		}
		return !board.isColumnFull(column);
	}
	
	/**
	 * Places the current player's chip in a column transitioning the current game state to the next
	 * game state.
	 * 
	 * @param column the column index
	 * @see #isValidMove()
	 * @see #isGameCompleted()
	 */
	public void makeMove(int column) {
		if (!board.isColumnInBounds(column)) {
			throw new ColumnOutOfBoundsException(COLUMN_ERROR_MESSAGE);
		}
		if (board.isColumnFull(column)) {
			throw new ConnectFourRuntimeException(COLUMN_FULL_ERROR_MESSAGE);
		}
		
		if (gameCompleted) {
			return;
		}

		board.dropChip(currentPlayer.getCell(), column);
		
		if (board.hasConnectFour()) {
			gameCompleted = true;
			return;
		}
		if (board.hasDraw()) {
			gameCompleted = true;
			currentPlayer = null;
			return;
		}

		turnNumber++;
		currentPlayer = players[turnNumber % players.length];
	}
}
