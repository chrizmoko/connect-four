package connectfour;

import connectfour.core.*;
import connectfour.gui.*;
import java.util.Scanner;

public class Game {
	private GameState gs;
	private Display d;
	private Scanner sc;
	
	public Game() {
		gs = new GameState();
		d = new Display(gs.getBoard(), 'O', 'X', ' ');
		sc = new Scanner(System.in);
	}
	
	public void run() {
		while (!gs.isGameOver()) {
			// Announce which color's turn it is
			if (gs.isRedTurn()) {
				System.out.println("Red's turn");
			}
			if (gs.isYellowTurn()) {
				System.out.println("Yellow's turn");
			}
			
			// Display the board
			d.display();
			
			// Ask for input
			while (true) {
				try {
					System.out.print("Enter a valid column number (1-7): ");
					gs.makeMove(sc.nextInt() - 1);
					break;
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}

			// Line spacing
			System.out.println();
		}
		
		d.display();
		try {
			Cell winner = gs.getWinner();
			String result = (winner == Cell.Red) ? "Red" : "Yellow";
			System.out.println(result + " is the winner!");
		} catch (Exception e) {
		}
	}
	
}
