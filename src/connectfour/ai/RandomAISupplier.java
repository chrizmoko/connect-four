package connectfour.ai;

import connectfour.ai.util.AI;
import connectfour.ai.util.AISupplier;

public class RandomAISupplier implements AISupplier {
	public AI get() {
		return new RandomAI();
	}
	
	public String getAIName() {
		return "Random Column AI";
	}
}
