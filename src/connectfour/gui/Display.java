package connectfour.gui;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;

import connectfour.ai.util.AISupplier;
import connectfour.core.*;

public class Display {
	private static Dimension initSize = new Dimension(640, 480);
	
	private JFrame frame;
	private BoardPanel visuals;
	private ActionPanel actions;
	
	public Display(Board b) {
		// JFrame modifications
		frame = new JFrame("Connect Four");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setPreferredSize(initSize);
		frame.setMinimumSize(initSize);
		
		Border border = BorderFactory.createEmptyBorder(8, 8, 8, 8);
		
		// Connect four board panel modifications
		Dimension panelSize = new Dimension((int)(initSize.width * 0.4), initSize.height);
		
		visuals = new BoardPanel(b);
		visuals.setPreferredSize(panelSize);
		visuals.setMinimumSize(panelSize);
		visuals.setBorder(border);

		// Action panel modifications
		actions = new ActionPanel();
		actions.setPreferredSize(panelSize);
		actions.setMinimumSize(panelSize);
		actions.setBorder(border);
		
		// Add connect four and action panel to split panes
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, visuals, actions);
		split.setContinuousLayout(true);
		split.setResizeWeight(1.0);
		
		frame.add(split);
		frame.pack();
		
		split.setDividerLocation(0.5);
	}
	
	public void setVisible(boolean state) {
		frame.setVisible(state);
	}
	
	public BoardPanel getBoardPanel() {
		return visuals;
	}
	
	public ActionPanel getActionPanel() {
		return actions;
	}
}
