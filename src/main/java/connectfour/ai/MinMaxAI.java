package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.GameState;
import connectfour.core.Board;
import connectfour.core.Cell;
import connectfour.core.ConnectFourException;
import connectfour.core.ConnectFourRuntimeException;

public class MinMaxAI extends AbstractAI {
	private int depth;
	
	public MinMaxAI(int depth) {
		if (depth < 0) {
			throw new IllegalArgumentException("Depth cannot be a negative number.");
		}
		this.depth = depth;
	}
	
	@Override
	public int chooseMove(GameState state) {
		// Initial decision (for comparison) for a valid column
		GameState copy = new GameState(state);
		int decision = 0;
		if (copy.getBoard().isColumnFull(decision)) {
			decision++;
		}
		copy.makeMove(decision);
		
		// From the result of the initial comparison value, find the best move
		int maxValue = minmax(copy, depth, true);
		for (int i = decision + 1; i < state.getBoard().getNumColumns(); i++) {
			copy = new GameState(state);
			
			try {
				copy.makeMove(i);
			} catch (ConnectFourRuntimeException e) {
				continue;
			}
			
			int tempValue = minmax(copy, depth, true);
			if (tempValue > maxValue) {
				maxValue = tempValue;
				decision = i;
			}
		}
		
		return decision;
	}
	
	private int minmax(GameState state, int depth, boolean maximize) {
		if (depth == 0 || state.isGameCompleted()) {
			// Calculate the heuristic of the current board
			Cell player = (state.getCurrentPlayer().getCell() == Cell.RED) ? Cell.RED : Cell.YELLOW;
			Cell opponent = (state.getCurrentPlayer().getCell() == Cell.RED) ? Cell.YELLOW : Cell.RED;
			
			return heuristic(state.getBoard(), player, opponent);
		}
		
		if (maximize) {
			// Maximize
			int maxValue = -1;
			for (int c = 0; c < state.getBoard().getNumColumns(); c++) {
				GameState copy = new GameState(state);
				
				try {
					copy.makeMove(c);
				} catch (ConnectFourRuntimeException e) {
					continue;
				}
				
				int tempValue = minmax(copy, depth - 1, false);
				if (tempValue > maxValue) {
					maxValue = tempValue;
				}
			}
			return maxValue;
		} else {
			// Minimize
			int minValue = 1;
			for (int c = 0; c < state.getBoard().getNumColumns(); c++) {
				GameState copy = new GameState(state);
				
				try {
					copy.makeMove(c);
				} catch (ConnectFourRuntimeException e) {
					continue;
				}
				
				int tempValue = minmax(copy, depth - 1, true);
				if (tempValue < minValue) {
					minValue = tempValue;
				}
			}
			return minValue;
		}
	}
	
	private int heuristic(Board board, Cell player, Cell opponent) {
		// TODO: Figure out a good heuristic to evaluate the connect four board
		
		return -1;
	}
}
