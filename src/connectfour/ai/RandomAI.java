package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.GameState;
import connectfour.core.Cell;

public class RandomAI extends AbstractAI {
	@Override
	public int chooseMove(GameState state) {
		int col;
		do {
			col = (int)(Math.random() * state.getBoard().getColumns());
		} while (!state.isValidMove(col));
		return col;
	}
}
