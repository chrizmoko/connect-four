package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.GameState;

public class HumanPlayer extends AbstractAI {
	private int column;
	
	public void setNextMove(int col) {
		column = col;
	}
	
	public int getNextMove() {
		return column;
	}
	
	@Override
	public int chooseMove(GameState state) {		
		return column;
	}
}

