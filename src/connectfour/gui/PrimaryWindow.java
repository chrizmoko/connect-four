package connectfour.gui;

import java.awt.*;
import javax.swing.*;

public class PrimaryWindow {
	private JFrame window;
	private JTabbedPane tabbedPane;
	private JPanel setupPanel;
	
	public PrimaryWindow(String title, int width, int height) {
		// Create window and set defaults
		window = new JFrame(title);
		window.setPreferredSize(new Dimension(width, height));
		window.setMinimumSize(new Dimension(width, height));
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create window components
		setupPanel = new SetupPanel(window);
		
		// Create tabbed pane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Setup", setupPanel);
		window.add(tabbedPane);
		
		window.pack();
	}
	
	public void setVisible(boolean visible) {
		window.setVisible(visible);
	}
	
	public boolean isVisible() {
		return window.isVisible();
	}
}
