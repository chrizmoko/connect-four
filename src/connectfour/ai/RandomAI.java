package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.GameState;

public class RandomAI extends AbstractAI {
	@Override
	public int chooseMove(final GameState state) {
		return (int)(Math.random() * state.getBoard().getRows());
	}
}
