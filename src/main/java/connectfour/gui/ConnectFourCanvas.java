package connectfour.gui;

import connectfour.core.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFourCanvas extends JComponent {
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
	
	private MouseMotionListener mouseInput;
	
	private Color hoverColor;
	private int hoverColumn;
	private boolean hoverAllowed;
	
	public ConnectFourCanvas() {
		super();
		
		boardModel = null;
		
		// Add MouseListener from the canvas side since you have access to the all the stuff in
		// that scope. Only keep MouseMotionListeners that are related to hovering to this class.
		mouseInput = new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent event) {
				int column = -1;
				int positionX = event.getX() - offsetX;
				
				if (positionX > 0 && positionX < virtualWidth) {
					column = positionX / virtualCellSize;
				}
				
				if (column != hoverColumn) {
					hoverColumn = column;
					repaint();
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent event) {
				mouseDragged(event);
			}
		};
	}
	
	public void updateModel(Board board) {
		boardModel = board;
		virtualRows = board.getNumRows() + 1;
		virtualCols = board.getNumColumns();
	}
	
	public void setHoverColor(Cell cellColor) {
		switch (cellColor) {
			case RED:
				hoverColor = RED_PLAYER_COLOR;
				break;
			case YELLOW:
				hoverColor = YELLOW_PLAYER_COLOR;
				break;
			default:
				hoverColor = BACKGROUND_COLOR;
				break;
		}
	}
	
	public int getHoverColumn() {
		if (!hoverAllowed) {
			hoverColumn = -1;
		}
		return hoverColumn;
	}
	
	public void allowHover(boolean canHover) {
		if (!hoverAllowed && canHover) {
			addMouseMotionListener(mouseInput);
		} else {
			removeMouseMotionListener(mouseInput);
		}
		hoverAllowed = canHover;
	}
	
	public boolean canHover() {
		return hoverAllowed;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Draw board if a model is given
		if (boardModel != null) {	
			updateDimensions();
			
			int adjustedOffsetY = offsetY + virtualCellSize;
			int adjustedVirtualHeight = virtualHeight - virtualCellSize;
			int adjustedCellSize = (int)(virtualCellSize * CELL_RATIO);
			int cellOffset = (virtualCellSize - adjustedCellSize) / 2;
			
			g.setColor(BOARD_COLOR);
			g.fillRect(offsetX, adjustedOffsetY, virtualWidth, adjustedVirtualHeight);
			
			// Paint the cells from the board model
			for (int r = 0; r < boardModel.getNumRows(); r++) {
				for (int c = 0; c < boardModel.getNumColumns(); c++) {
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
			
			// Paint hovering chip if there is one
			if (hoverAllowed && hoverColumn != -1) {
				g.setColor(BACKGROUND_COLOR);
				g.fillRect(offsetX, offsetY, virtualWidth, virtualCellSize);
				
				int positionX = (offsetX + cellOffset) + (hoverColumn * virtualCellSize);
				int positionY = offsetY + cellOffset;
				
				g.setColor(hoverColor);
				g.fillOval(positionX, positionY, adjustedCellSize, adjustedCellSize);
			}
		}
	}
	
	private void updateDimensions() {
		// Size of the board is clamped by the smaller dimension
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