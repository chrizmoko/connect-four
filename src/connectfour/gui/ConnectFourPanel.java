package connectfour.gui;

import connectfour.ai.util.*;
import connectfour.core.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectFourPanel extends JPanel {
	private JFrame rootWindow;
	private JPanel controlPanel;
	private JPanel playerPanel;
	private JPanel boardPanel;
	private JPanel statusPanel;
	
	private GameController controller;
	
	public ConnectFourPanel(JFrame root, String player1Name, String player2Name) {
		super();
		
		rootWindow = root;
		
		// Panels for this panel
		controlPanel = buildControlPanel();
		add(controlPanel);
		
		playerPanel = buildPlayerPanel();
		add(playerPanel);
		
		boardPanel = buildBoardPanel();
		add(boardPanel);
		
		statusPanel = buildStatusPanel();
		add(statusPanel);
		
		// Panel arrangement
		SpringLayout layout = new SpringLayout();
		int outerPadding = 10;
		int innerPadding = 8;
		
		layout.putConstraint(
			SpringLayout.NORTH, controlPanel, 
			outerPadding, 
			SpringLayout.NORTH, this
		);
		layout.putConstraint(
			SpringLayout.WEST, controlPanel,
			outerPadding, 
			SpringLayout.WEST, this
		);
		layout.putConstraint(
			SpringLayout.EAST, controlPanel,
			-outerPadding, 
			SpringLayout.WIDTH, this
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, playerPanel, 
			innerPadding, 
			SpringLayout.SOUTH, controlPanel
		);
		layout.putConstraint(
			SpringLayout.WEST, playerPanel,
			outerPadding,
			SpringLayout.WEST, this
		);
		layout.putConstraint(
			SpringLayout.EAST, playerPanel, 
			-outerPadding,
			SpringLayout.WIDTH, this
		);
		
		layout.putConstraint(
			SpringLayout.NORTH,	boardPanel,
			innerPadding, 
			SpringLayout.SOUTH, playerPanel
		);
		layout.putConstraint(
			SpringLayout.WEST, boardPanel,
			outerPadding, 
			SpringLayout.WEST, this
		);
		layout.putConstraint(
			SpringLayout.EAST, boardPanel, 
			-outerPadding, 
			SpringLayout.WIDTH, this
		);
		layout.putConstraint(
			SpringLayout.SOUTH, boardPanel,
			-outerPadding, 
			SpringLayout.NORTH, statusPanel
		);
		
		layout.putConstraint(
			SpringLayout.WEST, statusPanel, 
			outerPadding, 
			SpringLayout.WEST, this
		);
		layout.putConstraint(
			SpringLayout.EAST, statusPanel, 
			-outerPadding,
			SpringLayout.WIDTH, this
		);
		layout.putConstraint(
			SpringLayout.SOUTH, statusPanel, 
			-outerPadding, 
			SpringLayout.SOUTH, this
		);
		
		setLayout(layout);
		
		// Setup for connect four game mechanics
		AbstractAI player1;
		try {
			player1 = AIFactory.getAI(player1Name);
		} catch (AIFactoryException e) {
			player1 = new Player();
		}
		
		AbstractAI player2;
		try {
			player2 = AIFactory.getAI(player2Name);
		} catch (AIFactoryException e) {
			player2 = new Player();
		}
		
		controller = new GameController(player1, player2);
		
		ConnectFourCanvas canvas = (ConnectFourCanvas)getBoardPanel().getComponent(0);
		canvas.updateModel(controller.getGameState().getBoard());
		canvas.repaint();
		
		((JLabel)getControlPanel().getComponent(0)).setText(controller.getStateString());
		((JLabel)getStatusPanel().getComponent(0)).setText(controller.getMessageString());
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
	
	public JPanel getStatusPanel() {
		return statusPanel;
	}
	
	private JPanel buildControlPanel() {
		// Buttons for the game state history
		JPanel basePanel = new JPanel();

		JLabel stateLabel = new JLabel();
		stateLabel.setHorizontalAlignment(JLabel.CENTER);
		basePanel.add(stateLabel);
		
		JButton prevFarButton = new JButton("<<");
		prevFarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				controller.moveToStart();
				
				ConnectFourCanvas canvas = (ConnectFourCanvas)getBoardPanel().getComponent(0);
				canvas.updateModel(controller.getGameState().getBoard());
				canvas.repaint();
				
				((JLabel)getControlPanel().getComponent(0)).setText(controller.getStateString());
				((JLabel)getStatusPanel().getComponent(0)).setText(controller.getMessageString());
			}
		});
		basePanel.add(prevFarButton);
		
		JButton prevButton = new JButton("<");
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// Cannot move to a state before the initial state
				if (controller.getCurrentState() == 0) {
					return;
				}
				
				controller.moveBackwards();
				
				ConnectFourCanvas canvas = (ConnectFourCanvas)getBoardPanel().getComponent(0);
				canvas.updateModel(controller.getGameState().getBoard());
				canvas.repaint();
				
				((JLabel)getControlPanel().getComponent(0)).setText(controller.getStateString());
				((JLabel)getStatusPanel().getComponent(0)).setText(controller.getMessageString());
			}
		});
		basePanel.add(prevButton);
		
		JButton nextButton = new JButton(">");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (controller.getCurrentState() == controller.getTotalState() && 
					!controller.addGameState()) {
					return;
				}
				
				controller.moveForwards();
				
				ConnectFourCanvas canvas = (ConnectFourCanvas)getBoardPanel().getComponent(0);
				canvas.updateModel(controller.getGameState().getBoard());
				canvas.repaint();
				
				((JLabel)getControlPanel().getComponent(0)).setText(controller.getStateString());
				((JLabel)getStatusPanel().getComponent(0)).setText(controller.getMessageString());
			}
		});
		basePanel.add(nextButton);
		
		JButton nextFarButton = new JButton(">>");
		nextFarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				controller.moveToEnd();
				
				ConnectFourCanvas canvas = (ConnectFourCanvas)getBoardPanel().getComponent(0);
				canvas.updateModel(controller.getGameState().getBoard());
				canvas.repaint();
				
				((JLabel)getControlPanel().getComponent(0)).setText(controller.getStateString());
				((JLabel)getStatusPanel().getComponent(0)).setText(controller.getMessageString());
			}
		});
		basePanel.add(nextFarButton);
		
		// Component arrangement
		SpringLayout layout = new SpringLayout();
		int outerPadding = 0;
		int innerPadding = 8;

		layout.putConstraint(
			SpringLayout.NORTH, prevFarButton, 
			outerPadding,
			SpringLayout.NORTH, basePanel
		);
		layout.putConstraint(
			SpringLayout.SOUTH, basePanel,
			outerPadding, 
			SpringLayout.SOUTH, prevFarButton
		);
		layout.putConstraint(
			SpringLayout.WEST, prevFarButton, 
			innerPadding * 2, 
			SpringLayout.WEST, basePanel
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, prevButton,
			outerPadding, 
			SpringLayout.NORTH, basePanel
		);
		layout.putConstraint(
			SpringLayout.SOUTH, basePanel,
			outerPadding, 
			SpringLayout.SOUTH, prevButton
		);
		layout.putConstraint(
			SpringLayout.WEST, prevButton, 
			innerPadding, 
			SpringLayout.EAST, prevFarButton
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, stateLabel, 
			outerPadding, 
			SpringLayout.NORTH, basePanel
		);
		layout.putConstraint(
			SpringLayout.SOUTH, basePanel, 
			outerPadding,
			SpringLayout.SOUTH, stateLabel
		);
		layout.putConstraint(
			SpringLayout.WEST, stateLabel, 
			innerPadding,
			SpringLayout.EAST, prevButton
		);
		layout.putConstraint(
			SpringLayout.EAST, stateLabel, 
			innerPadding, 
			SpringLayout.WEST, nextButton
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, nextButton,
			outerPadding, 
			SpringLayout.NORTH, basePanel
		);
		layout.putConstraint(
			SpringLayout.SOUTH, basePanel, 
			outerPadding, 
			SpringLayout.SOUTH, nextButton
		);
		layout.putConstraint(
			SpringLayout.EAST, nextButton,
			-innerPadding,
			SpringLayout.WEST, nextFarButton
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, nextFarButton, 
			outerPadding, 
			SpringLayout.NORTH, basePanel
		);
		layout.putConstraint(
			SpringLayout.SOUTH, basePanel, 
			outerPadding, 
			SpringLayout.SOUTH, nextFarButton
		);
		layout.putConstraint(
			SpringLayout.EAST, nextFarButton,
			-innerPadding * 2, 
			SpringLayout.EAST, basePanel
		);
		
		basePanel.setLayout(layout);
		return basePanel;
	}
	
	private JPanel buildPlayerPanel() {
		// Labels describing the type of players playing
		JPanel basePanel = new JPanel();
		
		JLabel player1Label = new JLabel("[Player 1] Red");
		basePanel.add(player1Label);
		
		JLabel player2Label = new JLabel("[Player 2] Yellow");
		basePanel.add(player2Label);
		
		JLabel player1TypeLabel = new JLabel("[default text]");
		player1TypeLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		JLabel player2TypeLabel = new JLabel("[default text]");
		player2TypeLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		// Component arrangement
		basePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		int outerPadding = 0;
		int innerPadding = 8;
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(outerPadding, outerPadding, innerPadding / 2, innerPadding / 2);
		basePanel.add(player1Label, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(innerPadding / 2, outerPadding, outerPadding, innerPadding / 2);
		basePanel.add(player2Label, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(outerPadding, innerPadding / 2, innerPadding / 2, outerPadding);
		basePanel.add(player1TypeLabel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(innerPadding / 2, innerPadding / 2, outerPadding, outerPadding);
		basePanel.add(player2TypeLabel, c);
		
		return basePanel;
	}
	
	private JPanel buildBoardPanel() {
		JPanel basePanel = new JPanel();
		
		ConnectFourCanvas canvas = new ConnectFourCanvas();
		basePanel.add(canvas);
		
		// Component arrangement
		SpringLayout layout = new SpringLayout();
		int outerPadding = 0;
		
		layout.putConstraint(
			SpringLayout.NORTH, canvas, 
			outerPadding,
			SpringLayout.NORTH, basePanel
		);
		layout.putConstraint(
			SpringLayout.WEST, canvas,
			outerPadding, 
			SpringLayout.WEST, basePanel
		);
		layout.putConstraint(
			SpringLayout.EAST, canvas, 
			-outerPadding, 
			SpringLayout.WIDTH, basePanel
		);
		layout.putConstraint(
			SpringLayout.SOUTH, basePanel, 
			outerPadding,
			SpringLayout.SOUTH, canvas
		);
		
		basePanel.setLayout(layout);
		return basePanel;
	}
	
	private JPanel buildStatusPanel() {
		// Message label to the player and close button
		JPanel basePanel = new JPanel();
		
		JLabel statusLabel = new JLabel("[default text]");
		basePanel.add(statusLabel);
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTabbedPane tabbedPane = (JTabbedPane)rootWindow.getContentPane().getComponent(0);
				tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});
		basePanel.add(closeButton);
		
		// Component arrangement
		SpringLayout layout = new SpringLayout();
		int outerPadding = 0;
		int innerPadding = 8;
		
		layout.putConstraint(
			SpringLayout.NORTH, statusLabel,
			outerPadding,
			SpringLayout.NORTH, basePanel
		);
		layout.putConstraint(
			SpringLayout.WEST, statusLabel, 
			outerPadding,
			SpringLayout.WEST, basePanel
		);
		layout.putConstraint(
			SpringLayout.EAST, statusLabel,
			-outerPadding,
			SpringLayout.WIDTH, basePanel
		);
		
		layout.putConstraint(
			SpringLayout.NORTH, closeButton, 
			innerPadding,
			SpringLayout.SOUTH, statusLabel
		);
		layout.putConstraint(
			SpringLayout.EAST, closeButton,
			-outerPadding,
			SpringLayout.EAST, basePanel
		);
		layout.putConstraint(
			SpringLayout.SOUTH, basePanel,
			outerPadding,
			SpringLayout.SOUTH, closeButton
		);
		
		basePanel.setLayout(layout);
		return basePanel;
	}
}
