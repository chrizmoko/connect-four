package connectfour.core;

import connectfour.gui.Display;

public class Driver 
{
	public static void main(String[] args)
	{
		Board b = new Board();
		Display d = new Display(b);
	}
}
