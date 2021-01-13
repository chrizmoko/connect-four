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
	private class GameStateData {
		public GameState gameState;
		public String message;
		public boolean completed;
		
		public GameStateData(GameState gs, String msg, boolean c) {
			gameState = gs;
			message = msg;
			completed = c;
		}
	}
	
	private ArrayList<GameStateData> history;
	private int currentState;
	private int totalState;

	private AbstractAI playerRed;
	private AbstractAI playerYellow;
	
	public GameController(AbstractAI player1, AbstractAI player2) {
		history = new ArrayList<>();
		history.add(new GameStateData(new GameState(), MESSAGE_RED_TURN, false));
		
		currentState = 0;
		totalState = 0;
		
		playerRed = player1;
		playerYellow = player2;
	}
	
	public boolean addGameState() {
		// Do not add anymore states if the game is already completed
		if (history.get(totalState).completed) {
			return false;
		}
		
		GameState gameState = history.get(totalState).gameState.copy();
		String message = null;
		boolean isCompleted = false;
		
		try {
			if (gameState.isRedTurn()) {
				gameState.makeMove(playerRed.chooseMove(gameState.copy()));
				message = MESSAGE_YELLOW_TURN;
			} else {
				gameState.makeMove(playerYellow.chooseMove(gameState.copy()));
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
		
		GameStateData data = new GameStateData(gameState, message, isCompleted);
		history.add(data);
		
		totalState++;
		
		return true;
	}
	
	public GameState getGameState() {
		return history.get(currentState).gameState;
	}
	
	public boolean isNextTurnAI() {
		if (!history.get(totalState).completed) {
			if (history.get(totalState).gameState.isRedTurn()) {
				return !(playerRed instanceof Player);
			} else {
				return !(playerYellow instanceof Player);
			}
		}
		return false;
	}
	
	public boolean isNextTurnHuman() {
		if (!history.get(totalState).completed) {
			if (history.get(totalState).gameState.isRedTurn()) {
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
		return history.get(currentState).message;
	}
}
