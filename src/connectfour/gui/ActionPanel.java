package connectfour.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import connectfour.ai.util.AISupplier;

import java.awt.*;

public class ActionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JButton gameButton;
	JPanel select;
	JLabel label1, label2;
	JComboBox<String> aiList1, aiList2;
	
	public ActionPanel(String[] aiNames) {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		gameButton = new JButton("New Game");
		gameButton.setAlignmentX(CENTER_ALIGNMENT);
		add(gameButton);
		
		// Player selection
		select = new JPanel();
		select.setBorder(BorderFactory.createTitledBorder("Set Players"));
		GroupLayout selectLayout = new GroupLayout(select);
		selectLayout.setAutoCreateGaps(true);
		selectLayout.setAutoCreateContainerGaps(true);
		select.setLayout(selectLayout);
		
		label1 = new JLabel("Player 1:");
		label2 = new JLabel("Player 2:");
		
		aiList1 = new JComboBox<>(aiNames);
		aiList1.setMaximumRowCount(5);
		aiList1.setPopupVisible(false);
		
		aiList2 = new JComboBox<>(aiNames);
		aiList2.setMaximumRowCount(5);
		
		GroupLayout.SequentialGroup hGroup = selectLayout.createSequentialGroup();
		hGroup.addGroup(selectLayout.createParallelGroup().
				addComponent(label1).
				addComponent(label2));
		hGroup.addGroup(selectLayout.createParallelGroup().
				addComponent(aiList1).
				addComponent(aiList2));
		selectLayout.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = selectLayout.createSequentialGroup();
		vGroup.addGroup(selectLayout.createParallelGroup(Alignment.BASELINE).
				addComponent(label1).
				addComponent(aiList1));
		vGroup.addGroup(selectLayout.createParallelGroup(Alignment.BASELINE).
				addComponent(label2).
				addComponent(aiList2));
		selectLayout.setVerticalGroup(vGroup);
		
		add(select);
		
		aiList1.setPrototypeDisplayValue(aiNames[0]);
		System.out.println(aiList1.getPrototypeDisplayValue());
	}
	
	@Override
	public void paint(Graphics g) {
		System.out.println(getWidth() + " " + getHeight());
		super.paint(g);
	}
	
	public String getList1Selection() {
		return (String)aiList1.getSelectedItem();
	}
	
	public String getList2Selection() {
		return (String)aiList2.getSelectedItem();
	}
}
