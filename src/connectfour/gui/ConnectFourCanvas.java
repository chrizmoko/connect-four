package connectfour.gui;

import connectfour.core.*;
import java.awt.*;
import javax.swing.*;

class ConnectFourCanvas extends JComponent {
	private static final Color RED_PLAYER_COLOR = new Color(255, 0, 0);
	private static final Color YELLOW_PLAYER_COLOR = new Color(255, 255, 0);
	private static final Color BOARD_COLOR = new Color(0, 0, 255);
	private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
	
	private static final double PAD_RATIO = 0.1;
	private static final double CELL_RATIO = 0.8;
	
	private Board boardModel;
	
	private int virtualRows;
	private int virtualCols;
	private int virtualWidth;
	private int virtualHeight;
	private int virtualCellSize;
	
	private int offsetX;
	private int offsetY;
	
	public void updateModel(Board board) {
		boardModel = board;
		
		virtualRows = board.getRows() + 1;
		virtualCols = board.getColumns();
	}
	
	@Override
	public void paint(Graphics g) {
		updateValues();
		
		int adjustedOffsetY = offsetY + virtualCellSize;
		int adjustedVirtualHeight = virtualHeight - virtualCellSize;
		int adjustedCellSize = (int)(virtualCellSize * CELL_RATIO);
		int cellOffset = (virtualCellSize - adjustedCellSize) / 2;
		
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(BOARD_COLOR);
		g.fillRect(offsetX, adjustedOffsetY, virtualWidth, adjustedVirtualHeight);
		
		// Paint the cells from the board model
		for (int r = 0; r < boardModel.getRows(); r++) {
			for (int c = 0; c < boardModel.getColumns(); c++) {
				switch (boardModel.getCellAt(r, c)) {
				case RED:
					g.setColor(RED_PLAYER_COLOR);
					break;
				case YELLOW:
					g.setColor(YELLOW_PLAYER_COLOR);
					break;
				default:
					g.setColor(BACKGROUND_COLOR);
					break;
				}
				
				int positionX = (offsetX + cellOffset) + (virtualCellSize * c);
				int positionY = (adjustedOffsetY + cellOffset) + (virtualCellSize * r);
				g.fillOval(positionX, positionY, adjustedCellSize, adjustedCellSize);
			}
		}
	}
	
	private void updateValues() {
		// The resizability of the board is dependent on the smaller dimension
		if (getWidth() < getHeight()) {
			offsetX = (int)(getWidth() * PAD_RATIO) / 2;
			virtualWidth = getWidth() - (offsetX * 2);
			virtualCellSize = virtualWidth / virtualCols;
			virtualHeight = virtualCellSize * virtualRows;
			offsetY = (getHeight() - virtualHeight) / 2;
		} else {
			offsetY = (int)(getHeight() * PAD_RATIO) / 2;
			virtualHeight = getHeight() - (offsetY * 2);
			virtualCellSize = virtualHeight / virtualRows;
			virtualWidth = virtualCellSize * virtualCols;
			offsetX = (getWidth() - virtualWidth) / 2;
		}
	}
}