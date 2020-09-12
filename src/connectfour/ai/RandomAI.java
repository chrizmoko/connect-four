package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.GameState;

public class RandomAI extends AbstractAI {
	public RandomAI() {
		super();
	}
	
	public int chooseMove(final GameState state) {
		return (int)(Math.random() * state.getBoard().getRows());
	}
}
