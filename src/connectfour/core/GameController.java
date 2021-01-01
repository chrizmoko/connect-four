package connectfour.core;

import connectfour.ai.util.*;
import java.util.*;

public class GameController {
	private static final String MESSAGE_RED_TURN = "[Player 1] Red's turn to make a move.";
	private static final String MESSAGE_YELLOW_TURN = "[Player 2] Yellow's turn to make a move.";
	private static final String MESSAGE_GAME_RED_WIN = "[Player 1] Red wins the game!";
	private static final String MESSAGE_GAME_YELLOW_WIN = "[Player 2] Yello wins the game!";
	private static final String MESSAGE_GAME_DRAW = "The game is a draw -- there is no winner.";
	private static final String MESSAGE_GAME_ERROR = "An error occurred -- the game is halted.";
	
	// Helper struct for associating UI messages with the current game state
	private class GameStateData {
		public GameState gameState;
		public String message;
		public boolean validity;
		
		public GameStateData(GameState gs, String msg, boolean valid) {
			gameState = gs;
			message = msg;
			validity = valid;
		}
	}

	private ArrayList<GameStateData> history;
	private int currentState;
	private int totalState;

	private AbstractAI player1;
	private AbstractAI player2;
	
	public GameController(AbstractAI p1, AbstractAI p2) {
		history = new ArrayList<>();
		history.add(new GameStateData(new GameState(), MESSAGE_RED_TURN, true));
		
		currentState = 0;
		totalState = 0;
		
		player1 = p1;
		player2 = p2;
	}
	
	public boolean addGameState() {
		// Do not add anymore states if the game is already completed
		if (!history.get(totalState).validity) {
			return false;
		}
		
		GameState gameState = history.get(totalState).gameState.copy();
		String message = null;
		boolean validity = true;
		
		try {
			if (gameState.isRedTurn()) {
				gameState.makeMove(player1.chooseMove(gameState.copy()));
				message = MESSAGE_YELLOW_TURN;
			} else {
				gameState.makeMove(player2.chooseMove(gameState.copy()));
				message = MESSAGE_RED_TURN;
			}
		} catch (ConnectFourException | IllegalArgumentException e) {
			message = MESSAGE_GAME_ERROR;
			validity = false;
		}
		
		if (gameState.isGameOver()) {
			try {
				if (gameState.getWinner() == Cell.RED) {
					message = MESSAGE_GAME_RED_WIN;
				} else {
					message = MESSAGE_GAME_YELLOW_WIN;
				}
			} catch (ConnectFourException e) {
				// GameState.getWinner() throws an exception for an uncompleted game or for a
				// completed game that reulsted in a draw. This is a bit of a "dirty" way to use
				// a catch statement (as an else statement), but I have no idea what I would do
				// with an empty catch statement if I decided to pre-check for a draw game.
				message = MESSAGE_GAME_DRAW;
			}
			validity = false;
		}
		
		GameStateData data = new GameStateData(gameState, message, validity);
		history.add(data);
		
		totalState++;
		
		return true;
	}
	
	public GameState getGameState() {
		return history.get(currentState).gameState;
	}
	
	public String getMessageString() {
		return history.get(currentState).message;
	}
	
	public void moveToStart() {
		currentState = 0;
	}
	
	public void moveToEnd() {
		currentState = totalState;
		
		// This could also mean fast forward the game
		if (history.get(totalState).validity) {
			while (addGameState());
			currentState = totalState;
		}
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
}
