package connectfour.gui;

import connectfour.ai.util.*;
import java.awt.event.*;
import javax.swing.*;


public class SetupPanel extends JPanel {
	private JFrame rootWindow;
	private JPanel playerPanel;
	
	private int gamesCounter;
	
	public SetupPanel(JFrame root) {
		super();
		
		rootWindow = root;
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		playerPanel = buildPlayerPanel();
		add(playerPanel);
		
		setLayout(layout);
		
		gamesCounter = 1;
	}
	
	public JPanel getPlayerPanel() {
		return playerPanel;
	}
	
	private JPanel buildPlayerPanel() {
		// Combo boxes and labels to describe the panel
		JPanel basePanel = new JPanel();
		String[] aiNames = AIFactory.getRegisteredNames();
		
		JLabel instructionLabel = new JLabel(
			"To play a game, choose who will play as red and as yellow."
		);
		add(instructionLabel);
		
		JLabel player1Label = new JLabel("[Player 1] Red");
		basePanel.add(player1Label);
		
		JComboBox<String> player1Combo = new JComboBox<>(aiNames);
		basePanel.add(player1Combo);
		
		JLabel player2Label = new JLabel("[Player 2] Yellow");
		basePanel.add(player2Label);
		
		JComboBox<String> player2Combo = new JComboBox<>(aiNames);
		basePanel.add(player2Combo);
		
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTabbedPane tabbedPane = (JTabbedPane)rootWindow.getContentPane().getComponent(0);
				ConnectFourPanel panel = new ConnectFourPanel(rootWindow);
				
				// Grab the selected AI names from the combo boxes
				String player1Select = (String)player1Combo.getSelectedItem();
				((JLabel)panel.getPlayerPanel().getComponent(2)).setText(player1Select);
				
				String player2Select = (String)player2Combo.getSelectedItem();
				((JLabel)panel.getPlayerPanel().getComponent(3)).setText(player2Select);
				
				tabbedPane.add("Game " + gamesCounter, panel);
				gamesCounter++;
			}
		});
		basePanel.add(startButton);
		
		// Group layout options
		GroupLayout layout = new GroupLayout(basePanel);
		int outerPadding = 10;
		int innerPaddingX = 8;
		int innerPaddingY = 8;
		
		// Horizontal arrangement
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGap(outerPadding)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(instructionLabel, GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(innerPaddingX * 3)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(player1Label)
						.addComponent(player2Label)
					)
					.addGap(innerPaddingX)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
						.addComponent(player1Combo)
						.addComponent(player2Combo)
					)
					.addGap(innerPaddingX)
					.addComponent(startButton)
				)
			)
			.addGap(outerPadding)
		);
		
		// Vertical arrangement
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGap(outerPadding)
			.addComponent(instructionLabel)
			.addGap(innerPaddingY * 2)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false)
				.addComponent(player1Label)
				.addComponent(player1Combo)
			)
			.addGap(innerPaddingY)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false)
				.addComponent(player2Label)
				.addComponent(player2Combo)
			)
			.addGap(innerPaddingY * 2)
			.addComponent(startButton)
			.addGap(outerPadding)
		);
		
		basePanel.setLayout(layout);
		return basePanel;
	}
}
