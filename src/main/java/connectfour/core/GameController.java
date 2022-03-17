package connectfour.core;

import java.util.Arrays;
import java.util.ArrayList;

public class GameController {
	// Index 0 of the ArrayList means first turn made indicating turn number 1, thus turn number 0
	// indicates the first turn (the initial Board/GameState)
	private ArrayList<Integer> columnMoveHistory;
	private int columnMoveIndex;
	private Board board;
	private Player[] players;
	private boolean isCompletedByConnectFour;
	private boolean isCompletedByDraw;

	public GameController(Board initialBoard, Player[] initialPlayers) {
		columnMoveHistory = new ArrayList<>();
		columnMoveIndex = -1;
		board = new Board(initialBoard);
		players = Arrays.copyOf(initialPlayers, initialPlayers.length);
		isCompletedByConnectFour = board.hasConnectFour();
		isCompletedByDraw = board.hasDraw();
	}
	
	 /**
	  * Executes a new turn starting from the most recent turn. If the game is completed, then no
	  * action occurs.
	  *
	  * @throws ConnectFourException thrown when an invalid move is made by the
	  * <code>AbstractAI</code>
	  */
	public void moveForward() throws ConnectFourException {
		if (isCompletedByConnectFour || isCompletedByDraw) {
			return;
		}

		int latestTurnNumber = columnMoveHistory.size();
		moveBoardStateToTurn(latestTurnNumber);

		GameState nextGameState = new GameState(board, players, latestTurnNumber);
		Player player = getPlayerAtTurn(latestTurnNumber);

		int move = player.getAI().chooseMove(nextGameState);
		board.dropChip(player.getCell(), move);

		columnMoveHistory.add(move);
		columnMoveIndex++;

		isCompletedByConnectFour = board.hasConnectFour();
		isCompletedByDraw = board.hasDraw();
	}

	/**
	 * Removes all the turns made after a turn, and executes a new turn.
	 * 
	 * @param turnNumber the turn number to start from
	 * @throws ConnectFourException thrown when an invalid move is made by the
	 * <code>AbstractAI</code>
	 */
	public void moveForwardFrom(int turnNumber) throws ConnectFourException {
		moveBoardStateToTurn(turnNumber);
		for (int i = columnMoveHistory.size() - 1; i >= turnNumber; i--) {
			columnMoveHistory.remove(i);
		}
		moveForward();
	}

	/**
	 * Returns the player whose turn it is.
	 * 
	 * @param stateIndex the index of the game's history
	 * @return the <code>Player</code> object of the current player
	 */
	public Player getPlayerAtTurn(int turnNumber) {
		if (turnNumber > columnMoveHistory.size()) {
			throw makeInvalidTurnException(turnNumber, columnMoveHistory.size());
		}
		return players[turnNumber % players.length];
	}

	/**
	 * Determines if the game history has ended in a completed state.
	 * 
	 * @return <code>true</code> if the game has completed; <code>false</code> otherwise
	 */
	public boolean isGameCompleted() {
		return isCompletedByConnectFour || isCompletedByDraw;
	}

	/**
	 * Returns a copy of the <code>GameState</code> object at that position in the game's history.
	 * 
	 * @param stateIndex the index of the game's history
	 * @return a copy of the <code>GameState</code> object at the index
	 */
	public GameState getStateAtTurn(int turnNumber) {
		if (turnNumber > columnMoveHistory.size()) {
			throw makeInvalidTurnException(turnNumber, columnMoveHistory.size());
		}
		moveBoardStateToTurn(turnNumber);
		return new GameState(new Board(board), players, turnNumber);
	}

	/**
	 * Returns the number of turns made so far.
	 * 
	 * @return the number of turns made
	 */
	public int getTotalTurns() {
		return columnMoveHistory.size();
	}
	
	/**
	 * Returns a <code>String</code> object corresponding to a certain game state in the game's
	 * history. The state <code>String</code> describes the current state out of the total states
	 * created in the game so far.
	 * 
	 * @param turnNumber the turn number
	 * @return a <code>String</code> object describing the current state of the game
	 */
	public String getStateStringAtTurn(int turnNumber) {
		if (turnNumber > columnMoveHistory.size()) {
			throw makeInvalidTurnException(turnNumber, columnMoveHistory.size());
		}
		return "Turn " + turnNumber + " out of " + columnMoveHistory.size();
	}
	
	/**
	 * Returns a <code>String</code> object corresponding to a certain game state in the game's
	 * history. The message <code>String</code> provides users on whose turn it is, and other status
	 * information about the current game.
	 * 
	 * @param turnNumber the turn number
	 * @return a <code>String</code> object that provides information about the current state of the
	 * game.
	 */
	public String getMessageStringAtTurn(int turnNumber) {
		if (turnNumber > columnMoveHistory.size()) {
			throw makeInvalidTurnException(turnNumber, columnMoveHistory.size());
		}

		if (turnNumber == columnMoveHistory.size()) {
			if (isCompletedByConnectFour) {
				Player winner = players[(turnNumber - 1) % players.length];
				return winner.getCell() + " has won the game!";
			}
			if (isCompletedByDraw) {
				return "The game resulted in a draw.";
			}
		}

		int i = turnNumber % players.length;
		return "[Player " + (i + 1) + "] " + players[i].getCell() + "'s turn to make a move.";
	}

	private void moveBoardStateToTurn(int turnNumber) {
		// turnNumber = 0 <==> columnMoveIndex = -1 (initial Board/GameState)
		if (turnNumber == columnMoveIndex + 1) {
			return;
		}
		while (turnNumber > columnMoveIndex + 1) {
			Cell chip = getPlayerAtTurn(columnMoveIndex + 1).getCell();
			board.dropChip(chip, columnMoveHistory.get(columnMoveIndex + 1));
			columnMoveIndex++;
		}
		while (turnNumber < columnMoveIndex + 1) {
			board.pickupChip(columnMoveHistory.get(columnMoveIndex));
			columnMoveIndex--;
		}
	}

	private static ConnectFourRuntimeException makeInvalidTurnException(int turn, int totalTurns) {
		return new ConnectFourRuntimeException(
			"Turn " + turn + " is beyond the " + totalTurns + " turns made",
			new IndexOutOfBoundsException()
		);
	}
}
