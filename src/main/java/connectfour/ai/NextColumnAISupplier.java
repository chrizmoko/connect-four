package connectfour.ai;

import connectfour.ai.util.AbstractAI;
import connectfour.ai.util.AISupplier;

public class NextColumnAISupplier implements AISupplier {
	@Override
	public AbstractAI get() {
		return new NextColumnAI();
	}
	
	@Override
	public String getAIName() {
		return "Next Column Only AI";
	}
	
	@Override
	public String getAIDescription() {
		return "Drops chips into the same column until the column is full. From the leftmost"
			+ " column to the rightmost column, chips will be dropped into the next column that"
			+ " is empty.";
	}
}
