package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.GameState;

public class RandomAI extends AbstractAI {
	@Override
	public int chooseMove(GameState state) {
		int col;
		do {
			col = (int)(Math.random() * state.getBoard().getNumColumns());
		} while (!state.isValidMove(col));
		return col;
	}
}
