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
}
