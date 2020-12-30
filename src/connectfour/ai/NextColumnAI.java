package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.GameState;

public class NextColumnAI extends AbstractAI {
	private int column = 0;
	
	@Override
	public int chooseMove(GameState state) {
		while (!state.isValidMove(column)) {
			column++;
		}
		return column;
	}
}
