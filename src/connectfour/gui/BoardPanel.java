package connectfour.gui;

import javax.swing.*;
import java.awt.*;
import connectfour.core.*;

public class BoardPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private BoardCanvas canvas;
	private BoardMouse mouse;
	
	public BoardPanel(Board b) {
		super();
		
		canvas = new BoardCanvas(b);
		mouse = new BoardMouse(canvas);
		
		// Board panel settings
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);
		
		addMouseListener(mouse);
	}
	
	public BoardCanvas getCanvas() {
		return canvas;
	}
	
	public int chooseColumn(Cell cell) {
		mouse.allowPolling(true);
		int col;
		do {
			mouse.hoverChip(cell);
			col = mouse.getRecentColumn();
		} while (col < 0);
		return col;
	}
}
