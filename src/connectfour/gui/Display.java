package connectfour.gui;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

import connectfour.ai.util.AISupplier;
import connectfour.core.*;

public class Display {
	private static Dimension initSize = new Dimension(640, 480);
	
	private JFrame frame;
	private BoardPanel visual;
	private ActionPanel actions;
	
	public Display(Board board, String[] aiNames) {
		// JFrame modifications
		frame = new JFrame("Connect Four");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setMinimumSize(initSize);
		
		Border border = BorderFactory.createEmptyBorder(8, 8, 8, 8);
		//Border border = BorderFactory.createLineBorder(Color.RED, 4);
		
		// Connect four board panel modifications
		visual = new BoardPanel(board);
		visual.setMinimumSize(new Dimension((int)(initSize.width * 0.3f), initSize.height));
		visual.setBorder(border);

		// Action panel modifications
		actions = new ActionPanel(aiNames);
		actions.setMinimumSize(new Dimension((int)(initSize.width * 0.3f), initSize.height));
		actions.setBorder(border);
		
		// Add connect four and action panel to split panes
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, visual, actions);
		split.setContinuousLayout(true);
		split.setResizeWeight(1.0);
		
		frame.add(split);
		frame.pack();
		
		split.setDividerLocation(0.6);
	}
	
	public void setVisible(boolean state) {
		frame.setVisible(state);
	}
	
	public String getPlayerOneAIName() {
		return actions.getList1Selection();
	}
	
	public String getPlayerTwoAIName() {
		// TODO: Needs to be implemented
		return actions.getList2Selection();
	}
}
