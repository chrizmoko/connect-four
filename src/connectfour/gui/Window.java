package connectfour.gui;

import javax.swing.*;
import java.awt.*;
import connectfour.core.*;

public class Window {
	private Board board;
	private JFrame frame;
	
	public Window(Board b) {
		board = b;
		
		frame = new JFrame("Connect Four");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		
		Visual visual = new Visual(board);
		//JPanel actions = new JPanel();
		
		JButton actions = new JButton("actions");
		
		frame.getContentPane().add(actions, BorderLayout.EAST);
		frame.getContentPane().add(visual);
	}
	
	public void visible(boolean state) {
		frame.setVisible(state);
	}
	
	public void update() {
		
	}
}
