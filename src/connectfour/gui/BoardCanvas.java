package connectfour.gui;

import java.awt.*;
import javax.swing.*;

import connectfour.core.*;

public class BoardCanvas extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private Board board;
	private Color bgColor, rColor, yColor;
	private float xPadMin, yPadMin;
	
	public BoardCanvas(Board b) {
		super();
		
		board = b;
		
		bgColor = new Color(0, 0, 0);
		rColor = new Color(255, 0, 0);
		yColor = new Color(255, 255, 0);
		
		xPadMin = 0.05f;
		yPadMin = 0.05f;
	}
	
	public Point getCurrentMousePosition() {
		// TODO: Needs to be implemented
		return null;
	}
	
	@Override
	public void paint(Graphics g) {
		//System.out.println(getWidth() + " " + getHeight() + " " + Color.BLACK);
		
		// Calculate the number of pixels per cell
		double boardHWRatio = (double)(board.getRows() + 1) / board.getColumns();
		double canvasHWRatio = (double)getHeight() / getWidth();
		int unit, xOffset, yOffset;
		if (boardHWRatio < canvasHWRatio) {
			// Units are based off the canvas width implying the canvas is tall
			xOffset = (int)(xPadMin * getWidth());
			unit = (getWidth() - 2 * xOffset) / board.getColumns();
			yOffset = (getHeight() - (unit * (board.getRows() + 1))) / 2;
		} else {
			// Unit are based off the canvas height implying the canvas is wide
			yOffset = (int)(yPadMin * getHeight());
			unit = (getHeight() - 2 * yOffset) / (board.getRows() + 1);
			xOffset = (getWidth() - (unit * board.getColumns())) / 2;
		}
		
		// Draw background
		g.setColor(bgColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Draw cells of the board
		for (int r = 1; r < board.getRows() + 1; r++) {
			for (int c = 0; c < board.getColumns(); c++) {
				g.setColor(rColor);
				g.fillOval(c * unit + xOffset, r * unit + yOffset, unit, unit);
			}
		}
	}
}
