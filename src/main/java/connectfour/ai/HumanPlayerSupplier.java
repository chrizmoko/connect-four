package connectfour.ai;

import connectfour.ai.util.AISupplier;
import connectfour.ai.util.AbstractAI;

public class HumanPlayerSupplier implements AISupplier {
    @Override
	public AbstractAI get() {
		return new HumanPlayer();
	}

	@Override
	public String getAIName() {
		return "[Human Player]";
	}

	@Override
	public String getAIDescription() {
		return "A human player.";
	}
}
