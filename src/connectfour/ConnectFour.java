package connectfour;

import connectfour.gui.*;

public class ConnectFour {
	private PrimaryWindow window;
	
	public void run() throws Exception {
		window = new PrimaryWindow("Connect Four", 640, 480);
		window.setVisible(true);
	}
}
