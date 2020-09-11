package connectfour.ai.util;

import connectfour.core.GameState;

public abstract class AI {
	public AI() {
		
	}
	
	public abstract int chooseMove(GameState state);
}
