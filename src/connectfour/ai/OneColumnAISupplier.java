package connectfour.ai;

import connectfour.ai.util.AI;
import connectfour.ai.util.AISupplier;

public class OneColumnAISupplier implements AISupplier {
	public AI get() {
		return new OneColumnAI();
	}
	
	public String getAIName() {
		return "First Column Only AI";
	}
}
