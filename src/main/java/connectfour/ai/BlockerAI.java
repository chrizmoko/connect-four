package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.core.Board;
import connectfour.core.Cell;
import connectfour.core.GameState;

import java.util.ArrayList;

/**
 * The <code>BlockerAI</code> will only play against the opponent, but not for itself. The strategy
 * of the AI is described below:
 * <p>
 * The AI looks at the current <code>Board</code> and will attempt to prevent the opponent from
 * making a Connect Four match. The AI is not interested in winning; it is only interested in
 * preventing the opponent from winning.
 * <p>
 * For every match that can be made on the board, the AI will add weights for every empty cell
 * within each possible match. Matches that contain more of the opponent's cells will have more
 * weight added to empty cells within the match, however the AI will disregard matches that contain
 * its own cell color since that match is already "blocked" by the AI. After calculating the weights
 * for every empty cell in the board, the AI will select a column with the largest weight such that
 * dropping the a chip in the column will not advantage the opponent in the next turn.
 */
public class BlockerAI extends AbstractAI {
	@Override
	public int chooseMove(GameState state) {
		Board board = state.getBoard();
		Cell color = state.getCurrentPlayer().getCell();

		int[][] weights = new int[board.getNumRows()][board.getNumColumns()];

		addHorizontalWeights(color, board, weights);
		addVerticalWeights(color, board, weights);
		addPositiveDiagonalWeights(color, board, weights);
		addNegativeDiagonalWeights(color, board, weights);

		int column = -1;
		int maxWeight = -1;

		for (int c = 0; c < board.getNumColumns(); c++) {
			if (board.isColumnFull(c)) {
				continue;
			}

			int weight = weights[board.getLowestEmptyRow(c)][c];
			int weightAbove = 0;

			if (board.isColumnInBounds(board.getLowestEmptyRow(c) - 1)) {
				weightAbove = weights[board.getLowestEmptyRow(c) - 1][c];
			}

			if (weight > maxWeight && weight >= weightAbove) {
				maxWeight = weights[board.getLowestEmptyRow(c)][c];
				column = c;
			}
		}

		return column;
	}
	
	private static int reweight(int weight) {
		return weight * weight;
	}

	private static void addHorizontalWeights(Cell player, Board board, int[][] weights) {
		for (int r = 0; r < board.getNumRows(); r++) {
			for (int c = 0; c < board.getNumColumns() - board.getMatchLength() + 1; c++) {
				ArrayList<Integer> cellsToWeigh = new ArrayList<Integer>(board.getMatchLength());
				int weight = 0;

				for (int i = 0; i < board.getMatchLength(); i++) {
					Cell cell = board.getCellAt(r, c + i);
					if (cell == player) {
						cellsToWeigh.clear();
						break;
					} else if (cell == Cell.EMPTY) {
						cellsToWeigh.add(i);
					} else {
						weight++;
					}
				}

				for (int i = 0; i < cellsToWeigh.size(); i++) {
					weights[r][c + cellsToWeigh.get(i)] += reweight(weight); 
				}
			}
		}
	}

	private static void addVerticalWeights(Cell player, Board board, int[][] weights) {
		for (int c = 0; c < board.getNumColumns(); c++) {
			for (int r = 0; r < board.getNumRows() - board.getMatchLength() + 1; r++) {
				ArrayList<Integer> cellsToWeigh = new ArrayList<Integer>(board.getMatchLength());
				int weight = 0;

				for (int i = 0; i < board.getMatchLength(); i++) {
					Cell cell = board.getCellAt(r + i, c);
					if (cell == player) {
						cellsToWeigh.clear();
						break;
					} else if (cell == Cell.EMPTY) {
						cellsToWeigh.add(i);
					} else {
						weight++;
					}
				}

				for (int i = 0; i < cellsToWeigh.size(); i++) {
					weights[r + cellsToWeigh.get(i)][c] += reweight(weight);
				}
			}
		}
	}

	private static void addPositiveDiagonalWeights(Cell player, Board board, int[][] weights) {
		for (int r = 0; r < board.getNumRows() - board.getMatchLength() + 1; r++) {
			for (int c = 0; c < board.getNumColumns() - board.getMatchLength() + 1; c++) {
				ArrayList<Integer> cellsToWeigh = new ArrayList<Integer>(board.getMatchLength());
				int weight = 0;
				
				for (int i = 0; i < board.getMatchLength(); i++) {
					Cell cell = board.getCellAt(r + i, c + i);
					if (cell == player) {
						cellsToWeigh.clear();
						break;
					} else if (cell == Cell.EMPTY) {
						cellsToWeigh.add(i);
					} else {
						weight++;
					}
				}

				for (int i = 0; i < cellsToWeigh.size(); i++) {
					weights[r + cellsToWeigh.get(i)][c + cellsToWeigh.get(i)] += reweight(weight);
				}
			}
		}
	}

	private static void addNegativeDiagonalWeights(Cell player, Board board, int[][] weights) {
		for (int r = 0; r < board.getNumRows() - board.getMatchLength() + 1; r++) {
			for (int c = board.getMatchLength() - 1; c < board.getNumColumns(); c++) {
				ArrayList<Integer> cellsToWeigh = new ArrayList<Integer>(board.getMatchLength());
				int weight = 0;

				for (int i =0; i < board.getMatchLength(); i++) {
					Cell cell = board.getCellAt(r + i, c - i);
					if (cell == player) {
						cellsToWeigh.clear();
						break;
					} else if (cell == Cell.EMPTY) {
						cellsToWeigh.add(i);
					} else {
						weight++;
					}
				}

				for (int i = 0; i < cellsToWeigh.size(); i++) {
					weights[r + cellsToWeigh.get(i)][c - cellsToWeigh.get(i)] += reweight(weight);
				}
			}
		}
	}
}
