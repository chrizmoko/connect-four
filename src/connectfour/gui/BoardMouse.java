package connectfour.gui;

import connectfour.core.*;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public class BoardMouse extends MouseInputAdapter {
	private BoardCanvas canvas;
	private boolean isPolling;
	private int lastCol;
	
	public BoardMouse(BoardCanvas c) {
		super();
		
		canvas = c;
		isPolling = false;
		lastCol = -1;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int col = canvas.getMousePositionColumn();
		if (col != -1) {
			lastCol = col;
			isPolling = false;
		}
	}
	
	public void hoverChip(Cell cell) {
		if (isPolling) {
			canvas.hoverChipOverColumn(cell);
		}
	}
	
	public int getRecentColumn() {
		int result = lastCol;
		lastCol = -1;
		return result;
	}

	public void allowPolling(boolean poll) {
		isPolling = poll;
	}
	
	public boolean isPolling() {
		return isPolling;
	}
}
