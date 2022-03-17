package connectfour.core;

import connectfour.ai.util.AbstractAI;
import connectfour.ai.HumanPlayer;


/**
 * A struct class that encapsulates a player in the Connect Four game. A player contains the
 * <code>AbstractAI</code> it is managed by, and a <code>Cell</code> that represents the player on
 * the Connect Four <code>Board</code> object.
 */
public class Player {
	private final AbstractAI ai;
	private final Cell cell;

	/**
	 * Creates a <code>Player</code> object.
	 * 
	 * @param ai the <code>AbstractAI</code> object to control the player
	 * @param cell the <code>Cell</code> to represent the player
	 */
	public Player(AbstractAI ai, Cell cell) {
		this.ai = ai;
		this.cell = cell;
	}

	/**
	 * Returns the <code>AbstractAI</code> that controls the player.
	 * 
	 * @return the <code>AbstractAI</code> object
	 */
	public AbstractAI getAI() {
		return ai;
	}

	/**
	 * Returns the <code>Cell</code> used to represent the player on the Connect Four board.
	 * 
	 * @return the <code>Cell</code>
	 */
	public Cell getCell() {
		return cell;
	}

	/**
	 * Determines if the player is controlled by an AI. A <code>Player</code> object is considered
	 * to be controlled by an AI (in a general sense), if the <code>AbstractAI</code> class of the
	 * <code>Player</code> object does not extend the <code>HumanPlayer</code> class.
	 * 
	 * @return <code>true</code> if the player is an AI; <code>false</code> otherwise
	 */
	public boolean isComputerPlayer() {
		return !(ai instanceof HumanPlayer);
	}

	/**
	 * Determines if the player is controlled by a human user. A <code>Player</code> object is
	 * considered to be human-controlled if the <code>Player</code> object's controller field is an
	 * instance of the <code>HumanPlayer</code> class.
	 * 
	 * @return <code>true</code> if the player is human-controlled; <code>false</code> otherwise
	 */
	public boolean isHumanPlayer() {
		return ai instanceof HumanPlayer;
	}
}
