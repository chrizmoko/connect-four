package connectfour.ai;

import connectfour.ai.util.AISupplier;
import connectfour.ai.util.AbstractAI;

public class BlockerAISupplier implements AISupplier {
	@Override
	public AbstractAI get() {
		return new BlockerAI();
	}

	@Override
	public String getAIName() {
		return "Blocker AI";
	}

	@Override
	public String getAIDescription() {
		return (
			"Attempts to block every possible match that the opponent makes trying its best to " +
			"not make moves that would let the opponent win."
		);
	}
}
