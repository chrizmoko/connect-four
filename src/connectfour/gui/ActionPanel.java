package connectfour.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import connectfour.ai.util.AISupplier;

import java.awt.*;

public class ActionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel buttonPanel, playerPanel;
	private JButton startButton, endButton;
	private JLabel label1, label2;
	private JComboBox<String> playerList1, playerList2;
	
	public ActionPanel() {
		super();
		
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(2, 1));
		
		// Panel for game managing buttons
		buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Game Control"));
		buttonPanel.setLayout(new FlowLayout());
		
		startButton = new JButton("New Game");
		startButton.setAlignmentX(CENTER_ALIGNMENT);
		startButton.setToolTipText("Create a new Connect Four game.");
		buttonPanel.add(startButton);
		
		endButton = new JButton("End Game");
		endButton.setAlignmentX(CENTER_ALIGNMENT);
		endButton.setToolTipText("End the current Connect Four game.");
		buttonPanel.add(endButton);
		
		add(buttonPanel);
		
		// Panel for player selection
		playerPanel = new JPanel();
		playerPanel.setBorder(BorderFactory.createTitledBorder("Set Players"));
		GroupLayout playerLayout = new GroupLayout(playerPanel);
		playerLayout.setAutoCreateGaps(true);
		playerLayout.setAutoCreateContainerGaps(true);
		playerPanel.setLayout(playerLayout);
		
		label1 = new JLabel("Player 1:");
		label2 = new JLabel("Player 2:");
		
		playerList1 = new JComboBox<>();
		playerList1.setMaximumRowCount(5);
		playerList1.setToolTipText("Choose whether Player 1 should be played by a human or an AI.");
		
		playerList2 = new JComboBox<>();
		playerList2.setMaximumRowCount(5);
		playerList2.setToolTipText("Choose whether Player 2 should be played by a human or an AI.");
		
		GroupLayout.SequentialGroup hGroup = playerLayout.createSequentialGroup();
		hGroup.addGroup(playerLayout.createParallelGroup().
				addComponent(label1).
				addComponent(label2));
		hGroup.addGroup(playerLayout.createParallelGroup().
				addComponent(playerList1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE).
				addComponent(playerList2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE));
		playerLayout.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = playerLayout.createSequentialGroup();
		vGroup.addGroup(playerLayout.createParallelGroup(Alignment.BASELINE).
				addComponent(label1).
				addComponent(playerList1));
		vGroup.addGroup(playerLayout.createParallelGroup(Alignment.BASELINE).
				addComponent(label2).
				addComponent(playerList2));
		playerLayout.setVerticalGroup(vGroup);
		
		add(playerPanel);
	}
	
	public void setPlayerAIOptions(String[] aiNames) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(aiNames);
		playerList1.setModel(model);
		playerList2.setModel(model);
	}
	
	public String getPlayerOneAI() {
		return (String)playerList1.getSelectedItem();
	}
	
	public String getPlayerTwoAI() {
		return (String)playerList2.getSelectedItem();
	}
}
