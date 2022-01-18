package connectfour.gui;

import connectfour.core.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFourPanel extends JPanel {
	private static final String PLAYER1_NAME_STR = "[Player 1] Red";
	private static final String PLAYER2_NAME_STR = "[Player 2] Yellow";
	private static final String CLOSE_STR = "Close";
	private static final String PREV_ALL_STR = "<<";
	private static final String PREV_STR = "<";
	private static final String NEXT_ALL_STR = ">>";
	private static final String NEXT_STR = ">";

	private JFrame rootWindow;

	private JPanel controlPanel;
	private JPanel playerPanel;
	private JPanel boardPanel;
	private JPanel closePanel;

	// Control panel components
	private JLabel gameStateLabel;
	private JButton prevAllButton;
	private JButton prevButton;
	private JButton nextAllButton;
	private JButton nextButton;

	// Player panel components
	private JLabel player1NameLabel;
	private JLabel player1TypeLabel;
	private JLabel player2NameLabel;
	private JLabel player2TypeLabel;

	// Board panel components
	private ConnectFourCanvas gameCanvas;
	private JLabel gameMessageLabel;

	// Close panel components
	private JButton closeButton;
	
	private MouseAdapter mouseInput;
	
	private GameController controller;
	
	public ConnectFourPanel(JFrame root, GameController gameControl) {
		super();
		
		this.rootWindow = root;
		
		// Initialize GUI elements (in panels) for this panel
		initControlPanel();
		initPlayerPanel();
		initBoardPanel();
		initStatusPanel();

		add(this.controlPanel);
		add(this.playerPanel);
		add(this.boardPanel);
		add(this.closePanel);
		
		// Panel arrangement
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		final int OUTER_PADDING = 10;
		final int INNER_PADDING = 8;
		
		layout.putConstraint(
			SpringLayout.NORTH, this.controlPanel, OUTER_PADDING, 
			SpringLayout.NORTH, this
		);
		layout.putConstraint(
			SpringLayout.WEST, this.controlPanel, OUTER_PADDING, 
			SpringLayout.WEST, this
		);
		layout.putConstraint(
			SpringLayout.EAST, this.controlPanel, -OUTER_PADDING, 
			SpringLayout.WIDTH, this
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, this.playerPanel, INNER_PADDING, 
			SpringLayout.SOUTH, this.controlPanel
		);
		layout.putConstraint(
			SpringLayout.WEST, this.playerPanel, OUTER_PADDING,
			SpringLayout.WEST, this
		);
		layout.putConstraint(
			SpringLayout.EAST, this.playerPanel, -OUTER_PADDING,
			SpringLayout.WIDTH, this
		);
		
		layout.putConstraint(
			SpringLayout.NORTH,	this.boardPanel, INNER_PADDING, 
			SpringLayout.SOUTH, this.playerPanel
		);
		layout.putConstraint(
			SpringLayout.WEST, this.boardPanel, OUTER_PADDING, 
			SpringLayout.WEST, this
		);
		layout.putConstraint(
			SpringLayout.EAST, this.boardPanel, -OUTER_PADDING, 
			SpringLayout.WIDTH, this
		);
		layout.putConstraint(
			SpringLayout.SOUTH, this.boardPanel, -OUTER_PADDING, 
			SpringLayout.NORTH, this.closePanel
		);
		
		layout.putConstraint(
			SpringLayout.WEST, this.closePanel, OUTER_PADDING, 
			SpringLayout.WEST, this
		);
		layout.putConstraint(
			SpringLayout.EAST, this.closePanel, -OUTER_PADDING,
			SpringLayout.WIDTH, this
		);
		layout.putConstraint(
			SpringLayout.SOUTH, this.closePanel, -OUTER_PADDING, 
			SpringLayout.SOUTH, this
		);
		
		// Setup for connect four game mechanics
		controller = gameControl;
		
		mouseInput = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				int column = gameCanvas.getHoverColumn();
				if (column == -1) {
					return;
				}
				
				if (controller.getGameState().isRedTurn()) {
					((Player)controller.getRedPlayer()).setNextMove(column);
				} else {
					((Player)controller.getYellowPlayer()).setNextMove(column);
				}
				
				controller.addGameState();
				controller.moveForwards();
				
				gameCanvas.updateModel(controller.getGameState().getBoard());
				gameCanvas.repaint();
				
				gameStateLabel.setText(controller.getStateString());
				gameMessageLabel.setText(controller.getMessageString());
				
				// Check if next turn is a human
				if (controller.isNextTurnHuman()) {
					if (controller.getGameState().isRedTurn()) {
						gameCanvas.setHoverColor(Cell.RED);
					} else {
						gameCanvas.setHoverColor(Cell.YELLOW);
					}
					return;
				}
				
				gameCanvas.allowHover(false);
				gameCanvas.removeMouseListener(mouseInput);
			}
		};
		
		gameCanvas.updateModel(controller.getGameState().getBoard());
		gameCanvas.repaint();
		
		gameStateLabel.setText(controller.getStateString());
		gameMessageLabel.setText(controller.getMessageString());
		
		if (controller.getCurrentState() == controller.getTotalState() &&
			controller.isNextTurnHuman()) {
			if (controller.getGameState().isRedTurn()) {
				gameCanvas.setHoverColor(Cell.RED);
			} else {
				gameCanvas.setHoverColor(Cell.YELLOW);
			}
			gameCanvas.allowHover(true);
			gameCanvas.addMouseListener(mouseInput);
		}
	}
	
	public JPanel getControlPanel() {
		return controlPanel;
	}
	
	public JPanel getPlayerPanel() {
		return playerPanel;
	}
	
	public JPanel getBoardPanel() {
		return boardPanel;
	}
	
	public JPanel getClosePanel() {
		return closePanel;
	}

	public void setPlayer1TypeText(String text) {
		player1TypeLabel.setText(text);
	}

	public void setPlayer2TypeText(String text) {
		player2TypeLabel.setText(text);
	}
	
	private void initControlPanel() {
		// Buttons for the game state history
		JPanel root = new JPanel();

		this.gameStateLabel = new JLabel();
		this.gameStateLabel.setHorizontalAlignment(JLabel.CENTER);
		root.add(this.gameStateLabel);
		
		this.prevAllButton = new JButton(PREV_ALL_STR);
		this.prevAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				controller.moveToStart();
					
				gameCanvas.updateModel(controller.getGameState().getBoard());
				gameCanvas.repaint();
				
				gameStateLabel.setText(controller.getStateString());
				gameMessageLabel.setText(controller.getMessageString());
			}
		});
		root.add(this.prevAllButton);
		
		this.prevButton = new JButton(PREV_STR);
		this.prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (controller.getCurrentState() == 0) {
					return;
				}
				
				controller.moveBackwards();
				
				gameCanvas.updateModel(controller.getGameState().getBoard());
				gameCanvas.repaint();
				
				gameStateLabel.setText(controller.getStateString());
				gameMessageLabel.setText(controller.getMessageString());
			}
		});
		root.add(this.prevButton);
		
		this.nextButton = new JButton(NEXT_STR);
		this.nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameCanvas.canHover()) {
					return;
				}
				
				if (controller.getCurrentState() == controller.getTotalState() && 
					!controller.addGameState()) {
					return;
				}
				
				controller.moveForwards();
				
				gameCanvas.updateModel(controller.getGameState().getBoard());
				gameCanvas.repaint();
				
				gameStateLabel.setText(controller.getStateString());
				gameMessageLabel.setText(controller.getMessageString());
				
				// Check if human interaction is needed to progress to the next state
				if (controller.getCurrentState() == controller.getTotalState() &&
					controller.isNextTurnHuman()) {
					if (controller.getGameState().isRedTurn()) {
						gameCanvas.setHoverColor(Cell.RED);
					} else {
						gameCanvas.setHoverColor(Cell.YELLOW);
					}
					gameCanvas.allowHover(true);
					gameCanvas.addMouseListener(mouseInput);
				}
			}
		});
		root.add(this.nextButton);
		
		this.nextAllButton = new JButton(NEXT_ALL_STR);
		this.nextAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.moveToEnd();
					
				while (controller.isNextTurnAI()) {
					controller.addGameState();
					controller.moveForwards();
				}
				
				gameCanvas.updateModel(controller.getGameState().getBoard());
				gameCanvas.repaint();
				
				gameStateLabel.setText(controller.getStateString());
				gameMessageLabel.setText(controller.getMessageString());
				
				// Check if human interaction is needed to progress to the next state
				if (controller.getCurrentState() == controller.getTotalState() &&
					controller.isNextTurnHuman()) {
					if (controller.getGameState().isRedTurn()) {
						gameCanvas.setHoverColor(Cell.RED);
					} else {
						gameCanvas.setHoverColor(Cell.YELLOW);
					}
					gameCanvas.allowHover(true);
					gameCanvas.addMouseListener(mouseInput);
				}
			}
		});
		root.add(this.nextAllButton);
		
		// Component arrangement
		SpringLayout layout = new SpringLayout();
		root.setLayout(layout);

		final int OUTER_PADDING = 0;
		final int INNER_PADDING = 8;

		layout.putConstraint(
			SpringLayout.NORTH, this.prevAllButton, OUTER_PADDING,
			SpringLayout.NORTH, root
		);
		layout.putConstraint(
			SpringLayout.SOUTH, root, OUTER_PADDING, 
			SpringLayout.SOUTH, this.prevAllButton
		);
		layout.putConstraint(
			SpringLayout.WEST, this.prevAllButton, INNER_PADDING * 2, 
			SpringLayout.WEST, root
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, this.prevButton, OUTER_PADDING, 
			SpringLayout.NORTH, root
		);
		layout.putConstraint(
			SpringLayout.SOUTH, root, OUTER_PADDING, 
			SpringLayout.SOUTH, this.prevButton
		);
		layout.putConstraint(
			SpringLayout.WEST, this.prevButton, INNER_PADDING, 
			SpringLayout.EAST, this.prevAllButton
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, this.gameStateLabel, OUTER_PADDING, 
			SpringLayout.NORTH, root
		);
		layout.putConstraint(
			SpringLayout.SOUTH, root, OUTER_PADDING,
			SpringLayout.SOUTH, this.gameStateLabel
		);
		layout.putConstraint(
			SpringLayout.WEST, this.gameStateLabel, INNER_PADDING,
			SpringLayout.EAST, this.prevButton
		);
		layout.putConstraint(
			SpringLayout.EAST, this.gameStateLabel, INNER_PADDING, 
			SpringLayout.WEST, this.nextButton
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, this.nextButton, OUTER_PADDING, 
			SpringLayout.NORTH, root
		);
		layout.putConstraint(
			SpringLayout.SOUTH, root, OUTER_PADDING, 
			SpringLayout.SOUTH, this.nextButton
		);
		layout.putConstraint(
			SpringLayout.EAST, this.nextButton, -INNER_PADDING,
			SpringLayout.WEST, this.nextAllButton
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, this.nextAllButton, OUTER_PADDING, 
			SpringLayout.NORTH, root
		);
		layout.putConstraint(
			SpringLayout.SOUTH, root, OUTER_PADDING, 
			SpringLayout.SOUTH, this.nextAllButton
		);
		layout.putConstraint(
			SpringLayout.EAST, this.nextAllButton, -INNER_PADDING * 2, 
			SpringLayout.EAST, root
		);
		
		this.controlPanel = root;
	}
	
	private void initPlayerPanel() {
		JPanel root = new JPanel();
		
		this.player1NameLabel = new JLabel(PLAYER1_NAME_STR);
		
		this.player1TypeLabel = new JLabel();
		this.player1TypeLabel.setHorizontalAlignment(JLabel.RIGHT);

		this.player2NameLabel = new JLabel(PLAYER2_NAME_STR);
		
		this.player2TypeLabel = new JLabel();
		this.player2TypeLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		// Component arrangement
		root.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;

		final int OUTER_PADDING = 0;
		final int INNER_PADDING = 8;
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(OUTER_PADDING, OUTER_PADDING, INNER_PADDING / 2, INNER_PADDING / 2);
		root.add(this.player1NameLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(INNER_PADDING / 2, OUTER_PADDING, OUTER_PADDING, INNER_PADDING / 2);
		root.add(this.player2NameLabel, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(OUTER_PADDING, INNER_PADDING / 2, INNER_PADDING / 2, OUTER_PADDING);
		root.add(this.player1TypeLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(INNER_PADDING / 2, INNER_PADDING / 2, OUTER_PADDING, OUTER_PADDING);
		root.add(this.player2TypeLabel, c);
		
		this.playerPanel = root;
	}
	
	private void initBoardPanel() {
		JPanel root = new JPanel();
		
		this.gameCanvas = new ConnectFourCanvas();
		root.add(this.gameCanvas);
		
		this.gameMessageLabel = new JLabel();
		root.add(this.gameMessageLabel);
		
		// Component arrangement
		SpringLayout layout = new SpringLayout();
		root.setLayout(layout);

		final int OUTER_PADDING = 0;
		final int INNER_PADDING = 8;
		
		layout.putConstraint(
			SpringLayout.NORTH, this.gameCanvas, OUTER_PADDING,
			SpringLayout.NORTH, root
		);
		layout.putConstraint(
			SpringLayout.WEST, this.gameCanvas, OUTER_PADDING, 
			SpringLayout.WEST, root
		);
		layout.putConstraint(
			SpringLayout.EAST, this.gameCanvas, -OUTER_PADDING, 
			SpringLayout.WIDTH, root
		);
		layout.putConstraint(
			SpringLayout.SOUTH, this.gameCanvas, -INNER_PADDING,
			SpringLayout.NORTH, this.gameMessageLabel
		);
		layout.putConstraint(
			SpringLayout.WEST, this.gameMessageLabel, OUTER_PADDING,
			SpringLayout.WEST, root
		);
		layout.putConstraint(
			SpringLayout.SOUTH, this.gameMessageLabel, -OUTER_PADDING,
			SpringLayout.SOUTH, root
		);
		
		this.boardPanel = root;
	}
	
	private void initStatusPanel() {
		// Message label to the player and close button
		JPanel root = new JPanel();

		this.closeButton = new JButton(CLOSE_STR);
		this.closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTabbedPane tabbedPane = (JTabbedPane)rootWindow.getContentPane().getComponent(0);
				tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});
		root.add(this.closeButton);
		
		// Component arrangement
		SpringLayout layout = new SpringLayout();
		root.setLayout(layout);

		final int OUTER_PADDING = 0;
		
		layout.putConstraint(
			SpringLayout.NORTH, this.closeButton, OUTER_PADDING,
			SpringLayout.NORTH, root
		);
		layout.putConstraint(
			SpringLayout.EAST, this.closeButton, -OUTER_PADDING,
			SpringLayout.EAST, root
		);
		layout.putConstraint(
			SpringLayout.SOUTH, root, OUTER_PADDING,
			SpringLayout.SOUTH, this.closeButton
		);
		
		this.closePanel = root;
	}
}
