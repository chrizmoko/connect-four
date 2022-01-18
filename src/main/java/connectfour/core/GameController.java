package connectfour.core;

import connectfour.ai.util.*;
import java.util.*;

public class GameController {
	private static final String MESSAGE_RED_TURN = "[Player 1] Red's turn to make a move.";
	private static final String MESSAGE_YELLOW_TURN = "[Player 2] Yellow's turn to make a move.";
	private static final String MESSAGE_GAME_RED_WIN = "[Player 1] Red wins the game!";
	private static final String MESSAGE_GAME_YELLOW_WIN = "[Player 2] Yellow wins the game!";
	private static final String MESSAGE_GAME_DRAW = "The game is a draw -- there is no winner.";
	private static final String MESSAGE_GAME_ERROR = "An error occurred -- the game is halted.";
	
	// Helper struct for associating UI messages with the current game state
	private class GameStateData_ {
		public GameState gameState;
		public String message;
		public boolean completed;
		
		public GameStateData_(GameState gs, String msg, boolean c) {
			gameState = gs;
			message = msg;
			completed = c;
		}
	}
	
	private ArrayList<GameStateData_> _history_;
	private ArrayList<Integer> history;
	private int currentState;
	private int totalState;
	private int completionState;

	private GameState gameState;
	private String currentMessage;

	private AbstractAI playerRed;
	private AbstractAI playerYellow;
	
	public GameController(AbstractAI player1, AbstractAI player2) {
		_history_ = new ArrayList<>();
		_history_.add(new GameStateData_(new GameState(), MESSAGE_RED_TURN, false));

		history = new ArrayList<>();
		
		currentState = 0;
		totalState = 0;
		completionState = -1;
		
		// Red always starts first
		gameState = new GameState();
		currentMessage = MESSAGE_RED_TURN;

		playerRed = player1;
		playerYellow = player2;
	}
	
	public boolean addGameState() {
		/*
		// Do not add anymore states if the game is already completed
		if (_history_.get(totalState).completed) {
			return false;
		}
		
		GameState gameState = new GameState(_history_.get(totalState).gameState);
		String message = null;
		boolean isCompleted = false;
		
		try {
			if (gameState.isRedTurn()) {
				gameState.makeMove(playerRed.chooseMove(new GameState(gameState)));
				message = MESSAGE_YELLOW_TURN;
			} else {
				gameState.makeMove(playerYellow.chooseMove(new GameState(gameState)));
				message = MESSAGE_RED_TURN;
			}
		} catch (ConnectFourException | IllegalArgumentException e) {
			message = MESSAGE_GAME_ERROR;
			e.printStackTrace();
			isCompleted = true;
		}
		
		if (gameState.isGameOver()) {
			try {
				switch (gameState.getWinner()) {
				case RED:
					message = MESSAGE_GAME_RED_WIN;
					break;
				case YELLOW:
					message = MESSAGE_GAME_YELLOW_WIN;
					break;
				default:
					message = MESSAGE_GAME_DRAW;
					break;
				}
			} catch (ConnectFourException e) {
				// Should not occur since the game is already checked for completion
			}
			isCompleted = true;
		}
		
		GameStateData_ data = new GameStateData_(gameState, message, isCompleted);
		_history_.add(data);
		
		totalState++;
		
		return true;
		*/

		// Do not continue game if game is completed
		if (completionState != -1) {
			return false;
		}

		// Player makes a move
		try {
			if (gameState.isRedTurn()) {
				gameState.makeMove(playerRed.chooseMove(new GameState(gameState)));
				currentMessage = MESSAGE_YELLOW_TURN;
			} else {
				gameState.makeMove(playerYellow.chooseMove(new GameState(gameState)));
				currentMessage = MESSAGE_RED_TURN;
			}
		} catch (ConnectFourException | IllegalArgumentException e) {
			currentMessage = MESSAGE_GAME_ERROR;
			e.printStackTrace();
		}

		return true;
	}
	
	public GameState getGameState() {
		return _history_.get(currentState).gameState;
	}
	
	public boolean isNextTurnAI() {
		if (!_history_.get(totalState).completed) {
			if (_history_.get(totalState).gameState.isRedTurn()) {
				return !(playerRed instanceof Player);
			} else {
				return !(playerYellow instanceof Player);
			}
		}
		return false;
	}
	
	public boolean isNextTurnHuman() {
		if (!_history_.get(totalState).completed) {
			if (_history_.get(totalState).gameState.isRedTurn()) {
				return playerRed instanceof Player;
			} else {
				return playerYellow instanceof Player;
			}
		}
		return false;
	}
	
	public AbstractAI getRedPlayer() {
		return playerRed;
	}
	
	public AbstractAI getYellowPlayer() {
		return playerYellow;
	}
	
	public void moveToStart() {
		currentState = 0;
	}
	
	public void moveToEnd() {
		currentState = totalState;
	}
	
	public void moveForwards() {
		if (currentState == totalState) {
			throw new IndexOutOfBoundsException();
		}
		currentState++;
	}
	
	public void moveBackwards() {
		if (currentState == 0) {
			throw new IndexOutOfBoundsException();
		}
		currentState--;
	}
	
	public int getCurrentState() {
		return currentState;
	}
	
	public int getTotalState() {
		return totalState;
	}
	
	public String getStateString() {
		return "State " + currentState + " out of " + totalState;
	}
	
	public String getMessageString() {
		/*
		return _history_.get(currentState).message;
		*/
		return currentMessage;
	}
}
