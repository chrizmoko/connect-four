package connectfour;

import connectfour.ai.util.*;
import connectfour.gui.*;

public class ConnectFour {
	private PrimaryWindow window;
	
	public void run() throws Exception {
		AISupplierLoader.addPackageName("connectfour.ai");
		AISupplierLoader.findClasses();
		for (AISupplier supp : AISupplierLoader.getSupplierInstances()) {
			AIFactory.registerSupplier(supp.getAIName(), supp);
		}
		
		window = new PrimaryWindow("Connect Four", 640, 480);
		window.setVisible(true);
	}
}
