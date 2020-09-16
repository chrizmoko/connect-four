package connectfour;

import connectfour.ai.util.*;
import connectfour.core.*;
import connectfour.gui.*;

public class Driver 
{
	public static void main(String[] args)
	{
		String dir = System.getProperty("user.dir") + "\\src\\connectfour\\ai";
		System.out.println(dir);
		
		/*
		String[] aiNames = new String[4];
		aiNames[0] = "(Human)";
		aiNames[3] = "An extremely long string that takes up space.";
		AISupplier[] instances = new AISupplier[2];
		try {
			AISupplierLoader.setSearchDirectoryPath(dir);
			AISupplierLoader.findClasses();
			int i = 0;
			for (AISupplier supp : AISupplierLoader.getSupplierInstances()) {
				System.out.println(supp.getAIName());
				aiNames[i+1] = supp.getAIName();
				instances[i] = supp;
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GameState gs = new GameState();
		
		Display d = new Display(gs);
		d.getActionPanel().setPlayerAIOptions(aiNames);
		d.setVisible(true);
		*/
		
		ConnectFour c4 = new ConnectFour();
		c4.run();
		
		//System.out.println("End");
		
	//	Game control = new Game();
	//	control.run();
	}
}
