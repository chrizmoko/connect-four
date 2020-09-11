package connectfour.ai;

import connectfour.ai.util.AI;
import connectfour.core.GameState;

public class RandomAI extends AI {
	public RandomAI() {
		super();
	}
	
	public int chooseMove(final GameState state) {
		return (int)(Math.random() * state.getBoard().getRows());
	}
}
