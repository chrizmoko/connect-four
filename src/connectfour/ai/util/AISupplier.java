package connectfour.ai.util;

public interface AISupplier {
	public AbstractAI get();
	public String getAIName();
}
