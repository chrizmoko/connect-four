package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.ai.util.AISupplier;

public class RandomAISupplier implements AISupplier {
	@Override
	public AbstractAI get() {
		return new RandomAI();
	}
	
	@Override
	public String getAIName() {
		return "Random Column AI";
	}
	
	@Override
	public String getAIDescription() {
		return "Randomly drops chips into any empty column of the board.";
	}
}
