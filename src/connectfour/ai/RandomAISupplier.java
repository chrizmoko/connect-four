package connectfour.ai;

public class RandomAISupplier implements AISupplier {
	public AI get() {
		return new RandomAI();
	}
}
