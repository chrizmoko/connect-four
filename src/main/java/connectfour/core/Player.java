package connectfour.core;

import connectfour.ai.util.*;

public class Player extends AbstractAI {
	private static final String NAME = "[Human Player]";
	
	private int column;
	
	public static String getName() {
		return NAME;
	}
	
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
