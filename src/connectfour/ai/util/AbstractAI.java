package connectfour.ai.util;

import connectfour.core.GameState;

public abstract class AbstractAI {
	public AbstractAI() {
		
	}
	
	public abstract int chooseMove(GameState state);
}
