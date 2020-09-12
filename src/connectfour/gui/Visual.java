package connectfour.gui;

import java.awt.*;
import connectfour.core.*;

public class Visual extends Canvas {
	private Board board;
	private float xPadMin, yPadMin;
	
	public Visual(Board b) {
		super();
		
		board = b;
		xPadMin = 0.08f;
		yPadMin = 0.08f;
		
		setBackground(Color.BLACK);
		System.out.println(getBackground());
		System.out.println(getWidth() + " " + getHeight());
	}
	
	@Override
	public void paint(Graphics g) {
		// Calculate the number of pixels per cell
		float boardHWRatio = board.getRows() / board.getColumns();
		float canvasHWRatio = getHeight() / getWidth();
		int unit, xOffset, yOffset;
		if (boardHWRatio < canvasHWRatio) {
			// Units are based off the canvas width implying the canvas is tall
			xOffset = (int)(xPadMin * getWidth());
			unit = (getWidth() - 2 * xOffset) / board.getColumns();
			yOffset = (getHeight() - (unit * board.getRows())) / 2;
		} else {
			// Unit are based off the canvas height implying the canvas is wide
			yOffset = (int)(yPadMin * getHeight());
			unit = (getHeight() - 2 * yOffset) / board.getRows();
			xOffset = (getWidth() - (unit * board.getColumns())) / 2;
		}
		
		// Draw the cells of the board
		for (int r = 0; r < board.getRows(); r++) {
			for (int c = 0; c < board.getColumns(); c++) {
				g.setColor(Color.RED);
				g.fillOval(c * unit + xOffset, r * unit + yOffset, unit, unit);
			}
		}
	}
}
