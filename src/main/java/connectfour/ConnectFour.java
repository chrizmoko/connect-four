package connectfour;

import connectfour.gui.*;

public class ConnectFour {
	private static final String WINDOW_TITLE = "Connect Four";
	private static final int WINDOW_WIDTH = 640;
	private static final int WINDOW_HEIGHT = 480;

	private PrimaryWindow window;
	
	public ConnectFour() {
		window = new PrimaryWindow(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() throws Exception {
		window.setVisible(true);
	}
}
