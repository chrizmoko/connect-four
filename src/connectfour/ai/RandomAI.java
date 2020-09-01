package connectfour.ai;

import connectfour.core.GameState;

public class RandomAI extends AI {
	public RandomAI() {
		super();
	}
	
	public int chooseMove(GameState state) {
		return (int)(Math.random() * state.getBoard().getRows());
	}
}
