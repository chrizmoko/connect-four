package connectfour;

import connectfour.ai.*;
import connectfour.ai.util.*;
import connectfour.core.*;
import connectfour.gui.*;

public class ConnectFour {
	private GameState gameState;
	private Display display;
	private Player player1, player2;
	
	
	public ConnectFour() {
		gameState = new GameState();
		display = new Display(gameState.getBoard());
		player1 = new Player(Cell.Red, display);
		player2 = new Player(Cell.Yellow, display);
	}
	
	public void run() {
		display.setVisible(true);
		
		while (!gameState.isGameOver()) {
			// Select current player
			Player currentPlayer = gameState.isRedTurn() ? player1 : player2;
			
			// Do move and update board
			try {
				currentPlayer.doMove(gameState);
				display.getBoardPanel().repaint();
			} catch (ConnectFourException e) {
				e.printStackTrace();
				return;
			}
			
			
		}
	}
}
