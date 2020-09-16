package connectfour.gui;

import java.awt.*;
import javax.swing.*;
import connectfour.core.*;

public class BoardCanvas extends JComponent {
	private static final long serialVersionUID = 1L;

	private Board board;
	private Color background, red, yellow, boardTop, boardBottom;
	private double boardXPad, boardYPad, chipPad;
	
	// Dimensions of the board relative to the component dimension
	private int boardWidth, boardHeight;
	private int boardUnit, boardXOffset, boardYOffset;
	
	public BoardCanvas(Board b) {
		super();
		
		board = b;
		
		background = new Color(0, 0, 0);
		red = new Color(255, 0, 0);
		yellow = new Color(255, 255, 0);
		boardTop = new Color(90, 160, 255);
		boardBottom = new Color(0, 90, 190);
		
		boardXPad = 0.05;
		boardYPad = 0.05;
		
		chipPad = 0.175;
	}
	
	public void setBackgroundColor(int r, int g, int b) {
		background = new Color(r, g, b);
	}
	
	public Color getBackgroundColor() {
		return background;
	}
	
	public void setRedChipColor(int r, int g, int b) {
		red = new Color(r, g, b);
	}
	
	public Color getRedChipColor() {
		return red;
	}
	
	public void setYellowChipColor(int r, int g, int b) {
		yellow = new Color(r, g, b);
	}
	
	public Color getYellowChipColor() {
		return yellow;
	}
	
	public int getMousePositionColumn() {
		calculateBoardDimension();
		Point p = getMousePosition();
		if (p == null || p.x < boardXOffset || p.x >= boardXOffset + boardWidth) {
			return -1;
		}
		return (p.x - boardXOffset) / boardUnit;
	}
	
	public void hoverChipOverColumn(Cell cell) {
		int col = getMousePositionColumn();
		if (col != -1) {
			Graphics g = getGraphics();
			// Paint other positions in the row besides the cell itself with background
			g.setColor(background);
			g.fillRect(boardXOffset, boardYOffset, boardUnit * col, boardUnit);
			g.fillRect((boardUnit * (col + 1)) + boardXOffset, boardYOffset, (board.getColumns() - col) * boardUnit, boardUnit);
			
			// Paint the chip itself
			paintBoardCell(g, cell, 0, col);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		// Update the dimensions of the board relative to component dimension
		calculateBoardDimension();
		
		// Draw background
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Draw connect four board
		paintBoardBase(g, boardUnit, boardXOffset, boardYOffset + boardUnit);
		
		// Draw cells of the connect four board
		for (int r = 1; r < board.getRows() + 1; r++) {
			for (int c = 0; c < board.getColumns(); c++) {
				paintBoardCell(g, board.getCellAt(r - 1, c), r, c);
			}
		}
	}
	
	private void calculateBoardDimension() {
		// Calculate the number of units per board cell
		double boardHWRatio = (board.getRows() + 1) / (double)board.getColumns();
		double canvasHWRatio = getHeight() / (double)getWidth();

		if (boardHWRatio < canvasHWRatio) {
			// Units are based off the canvas width implying the canvas is tall
			boardXOffset = (int)(boardXPad * getWidth());
			boardUnit = (getWidth() - 2 * boardXOffset) / board.getColumns();
			boardYOffset = (getHeight() - (boardUnit * (board.getRows() + 1))) / 2;
		} else {
			// Unit are based off the canvas height implying the canvas is wide
			boardYOffset = (int)(boardYPad * getHeight());
			boardUnit = (getHeight() - 2 * boardYOffset) / (board.getRows() + 1);
			boardXOffset = (getWidth() - (boardUnit * board.getColumns())) / 2;
		}
		
		// Calculate height and width of the board
		boardWidth = boardUnit * board.getColumns();
		boardHeight = boardUnit * board.getRows();
	}
	
	private Color cellToColor(Cell cell) {
		switch(cell) {
		case Red:
			return red;
		case Yellow:
			return yellow;
		default:
			return background;
		}
	}
	
	private void paintBoardCell(Graphics g, Cell cell, int gridRow, int gridCol) {
		calculateBoardDimension();
		
		int xPos = (gridCol * boardUnit) + boardXOffset;
		int yPos = (gridRow * boardUnit) + boardYOffset;
		int offset = (int)(boardUnit * (chipPad / 2));
		int size = (int)(boardUnit * (1 - chipPad));
		
		g.setColor(cellToColor(cell));
		g.fillOval(xPos + offset, yPos + offset, size, size);
	}
	
	private void paintBoardBase(Graphics g, int unit, int xPos, int yPos) {
		int deltaR = Math.abs(boardTop.getRed() - boardBottom.getRed());
		int deltaG = Math.abs(boardTop.getGreen() - boardBottom.getGreen());
		int deltaB = Math.abs(boardTop.getBlue() - boardBottom.getBlue());
		
		for (int y = yPos; y < yPos + boardHeight; y++) {
			// Calculate gradient color
			double factor = (double)(y - yPos) / boardHeight;
			int rChannel = boardTop.getRed() - (int)(deltaR * factor);
			int gChannel = boardTop.getGreen() - (int)(deltaG * factor);
			int bChannel = boardTop.getBlue() - (int)(deltaB * factor);
			
			g.setColor(new Color(rChannel, gChannel, bChannel));
			g.fillRect(xPos, y, boardWidth, 1);
		}
	}
}
