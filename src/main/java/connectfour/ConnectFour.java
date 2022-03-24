package connectfour;

import connectfour.ai.util.AIFactory;
import connectfour.core.ConnectFourException;
import connectfour.gui.PrimaryWindow;

public class ConnectFour {
	private static final String WINDOW_TITLE = "Connect Four";
	private static final int WINDOW_WIDTH = 640;
	private static final int WINDOW_HEIGHT = 480;

	private PrimaryWindow window;
	
	public ConnectFour() {
		window = new PrimaryWindow(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public ConnectFour(AIFactory aiFactory) {
		this();
		window.setAiFactory(aiFactory);
	}

	public void run() throws ConnectFourException {
		window.setVisible(true);
	}
}
