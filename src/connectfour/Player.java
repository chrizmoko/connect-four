package connectfour;

import connectfour.core.*;
import connectfour.ai.util.*;
import connectfour.gui.*;

public class Player {
	private Display display;
	private AbstractAI computer;
	private Cell color;
	
	public Player(Cell c, Display d) {
		display = d;
		computer = null;
		color = c;
	}
	
	public Player(Cell c, Display d, AbstractAI ai) {
		display = d;
		computer = ai;
		color = c;
	}
	
	public void doMove(GameState gs) throws ConnectFourException {
		if (computer != null) {
			gs.makeMove(computer.chooseMove(gs));
		} else {
			while (true) {
				try {
					int col = display.getBoardPanel().chooseColumn(color);
					gs.makeMove(col);
					break;
				} catch(ConnectFourException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isHuman() {
		return computer == null;
	}
	
	public boolean isComputer() {
		return computer != null;
	}
}
