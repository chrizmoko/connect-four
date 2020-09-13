package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.ai.util.AISupplier;

public class RandomAISupplier implements AISupplier {
	public AbstractAI get() {
		return new RandomAI();
	}
	
	public String getAIName() {
		return "Random Column AI";
	}
	
	public String getAIDescription() {
		return "Randomly drops chips into any column of the board.";
	}
}
