package connectfour.gui;

import javax.swing.*;
import java.awt.*;
import connectfour.core.*;

public class BoardPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	public BoardPanel(Board b) {
		super();
		
		setLayout(new BorderLayout());
		add(new BoardCanvas(b), BorderLayout.CENTER);
	}
}
