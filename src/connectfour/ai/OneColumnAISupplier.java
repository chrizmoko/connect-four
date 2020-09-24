package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.ai.util.AISupplier;

public class OneColumnAISupplier implements AISupplier {
	@Override
	public AbstractAI get() {
		return new OneColumnAI();
	}
	
	@Override
	public String getAIName() {
		return "First Column Only AI";
	}
	
	@Override
	public String getAIDescription() {
		return "Only drops chips into the first column of the board.";
	}
}
