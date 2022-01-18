package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.Board;
import connectfour.core.Cell;
import connectfour.core.GameState;

public class BlockerAI extends AbstractAI {
	// Strategy of the "Blocker AI"
	//
	// The point of the AI is to only prevent the opponent from ever making a valid connect 4 (this
	// also works for any connect x value) in any direction. This AI does not care about winning,
	// it only cares about preventing the opponent from winning.
	//
	// For any direction that a connect 4 can be made (horizontal, vertical, positive diagonal,
	// negative diagonal), and for every turn, the AI will only pay attention to any connect 4
	// combination made from opponent and empty cells ignoring those with the player's cells. The 
	// more opponent cells in the connect 4 combination, the more weight the empty cells of that 
	// combiniation will be allotted. There has to be at least one empty cell, otherwise the game
	// would be over and this would be a moot point.
	//
	// Essentially, the AI will ideally drop a chip into an empty position that has the most
	// weight allotted to it given that making such a move does not makes it too easy for the
	// opponent gain a connect 4 above it.
	
	@Override
	public int chooseMove(GameState state) {
		Board board = state.getBoard();
		int[][] ratings = new int[board.getNumRows()][board.getNumColumns()];
		
		// Get ratings in all directions
		rateHorizontal(state, ratings);
		rateVertical(state, ratings);
		ratePositiveDiagonal(state, ratings);
		rateNegativeDiagonal(state, ratings);
		
		// Find the column with the highest rating
		int decision = 0;
		for (int i = 0; i < board.getNumColumns(); i++) {
			if (board.getLowestEmptyRow(i) != -1) {
				decision = i;
				break;
			}
		}

		int maxRating = ratings[board.getLowestEmptyRow(decision)][decision];
		for (int i = decision; i < board.getNumColumns(); i++) {
			if (board.getLowestEmptyRow(i) > -1) {
				int currentRating = ratings[board.getLowestEmptyRow(i)][i];
				int aboveRating = 0;
				if (board.getLowestEmptyRow(i) - 1 > -1) {
					aboveRating = ratings[board.getLowestEmptyRow(i)-1][i];
				}
				
				if (currentRating > maxRating && currentRating >= aboveRating) {
					maxRating = ratings[board.getLowestEmptyRow(i)][i];
					decision = i;
				}
			}
		}
		
		return decision;
	}
	
	private int weightAdjustment(int weight) {
		return weight * weight;
	}
	
	private void rateHorizontal(GameState state, int[][] output) {
		Board board = state.getBoard();
		Cell player = (state.isRedTurn()) ? Cell.RED : Cell.YELLOW;
		Cell opponent = (state.isRedTurn()) ? Cell.YELLOW : Cell.RED;
		
		for (int r = 0; r < board.getNumRows(); r++) {
			for (int c = 0; c < board.getNumColumns() - board.getMatchLength() + 1; c++) {
				boolean[] empty = new boolean[board.getMatchLength()];
				int weight = 0;
				
				for (int i = 0; i < board.getMatchLength(); i++) {
					if (board.getCellAt(r, c + i) == player) {
						weight = 0;
						break;
					} else if (board.getCellAt(r, c + i) == opponent) {
						weight++;
					} else {
						empty[i] = true;
					}
				}
				
				if (weight > 0) {
					weight = weightAdjustment(weight);
					for (int i = 0; i < board.getMatchLength(); i++) {
						if (empty[i]) {
							output[r][c + i] += weight;
						}
					}
				}
			}
		}
	}
	
	private void rateVertical(GameState state, int[][] output) {
		Board board = state.getBoard();
		Cell player = (state.isRedTurn()) ? Cell.RED : Cell.YELLOW;
		Cell opponent = (state.isRedTurn()) ? Cell.YELLOW : Cell.RED;
		
		for (int c = 0; c < board.getNumColumns(); c++) {
			for (int r = 0; r < board.getNumRows() - board.getMatchLength() + 1; r++) {
				boolean[] empty = new boolean[board.getMatchLength()];
				int weight = 0;
				
				for (int i = 0; i < board.getMatchLength(); i++) {
					if (board.getCellAt(r + i, c) == player) {
						weight = 0;
						break;
					} else if (board.getCellAt(r + i, c) == opponent) {
						weight++;
					} else {
						empty[i] = true;
					}
				}
				
				if (weight > 0) {
					weight = weightAdjustment(weight);
					for (int i = 0; i < board.getMatchLength(); i++) {
						if (empty[i]) {
							output[r + i][c] += weight;
						}
					}
				}
			}
		}
	}
	
	private void ratePositiveDiagonal(GameState state, int[][] output) {
		Board board = state.getBoard();
		Cell player = (state.isRedTurn()) ? Cell.RED : Cell.YELLOW;
		Cell opponent = (state.isRedTurn()) ? Cell.YELLOW : Cell.RED;
		
		for (int r = 0; r < board.getNumRows() - board.getMatchLength() + 1; r++) {
			for (int c = 0; c < board.getNumColumns() - board.getMatchLength() + 1; c++) {
				boolean[] empty = new boolean[board.getMatchLength()];
				int weight = 0;
				
				for (int i = 0; i < board.getMatchLength(); i++) {
					if (board.getCellAt(r + i, c + i) == player) {
						weight = 0;
						break;
					} else if (board.getCellAt(r + i, c + i) == opponent) {
						weight++;
					} else {
						empty[i] = true;
					}
				}
				
				if (weight > 0) {
					weight = weightAdjustment(weight);
					for (int i = 0; i < board.getMatchLength(); i++) {
						if (empty[i]) {
							output[r + i][c + i] += weight;
						}
					}
				}
			}
		}
	}
	
	private void rateNegativeDiagonal(GameState state, int[][] output) {
		Board board = state.getBoard();
		Cell player = (state.isRedTurn()) ? Cell.RED : Cell.YELLOW;
		Cell opponent = (state.isRedTurn()) ? Cell.YELLOW : Cell.RED;
		
		for (int r = 0; r < board.getNumRows() - board.getMatchLength() + 1; r++) {
			for (int c = board.getMatchLength() - 1; c < board.getNumColumns(); c++) {
				boolean[] empty = new boolean[board.getMatchLength()];
				int weight = 0;
				
				for (int i = 0; i < board.getMatchLength(); i++) {
					if (board.getCellAt(r + i, c - i) == player) {
						weight = 0;
						break;
					} else if (board.getCellAt(r + i, c - i) == opponent) {
						weight++;
					} else {
						empty[i] = true;
					}
				}
				
				if (weight > 0) {
					weight = weightAdjustment(weight);
					for (int i = 0; i < board.getMatchLength(); i++) {
						if (empty[i]) {
							output[r + i][c - i] += weight;
						}
					}
				}
			}
		}
	}
}
