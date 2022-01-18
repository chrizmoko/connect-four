package connectfour.gui;

import connectfour.core.*;
import connectfour.ai.util.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PrimaryWindow {
	private static final String SETUP_STR = "Setup";
	private static final String SETUP_INSTRUCTION_STR = (
		"To play a game, choose who will play as red and as yellow."
	);
	private static final String SETUP_PLAYER1_STR = "[Player 1] Red";
	private static final String SETUP_PLAYER2_STR = "[Player 2] Yellow";
	private static final String SETUP_START_GAME_STR = "Start Game";
	private static final String GAME_PREFIX_STR = "Game ";

	private JFrame window;
	private JTabbedPane tabbedPane;
	private JPanel setupPanel;

	// Setup panel components
	private JLabel instructionLabel;
	private JLabel player1NameLabel;
	private JLabel player2NameLabel;
	private JComboBox<String> player1NameComboBox;
	private JComboBox<String> player2NameComboBox;
	private JButton startButton;

	private AIFactory aiFactory;
	private int numGames;
	
	public PrimaryWindow(String title, int width, int height) {
		final Dimension windowDimension = new Dimension(width, height);

		// Create window and set defaults
		this.window = new JFrame(title);
		this.window.setPreferredSize(windowDimension);
		this.window.setMinimumSize(windowDimension);
		this.window.setLocationRelativeTo(null);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create window components
		initSetupPanel();
		setAiFactory(AIFactory.getDefaultFactory());

		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.tabbedPane.addTab(SETUP_STR, this.setupPanel);
		this.window.add(tabbedPane);
		
		this.window.pack();
	}
	
	public void setVisible(boolean visible) {
		this.window.setVisible(visible);
	}
	
	public boolean isVisible() {
		return this.window.isVisible();
	}

	public void setAiFactory(AIFactory factory) {
		Vector<String> playerNames = new Vector<>(factory.getRegisteredNames());
		playerNames.insertElementAt(Player.getName(), 0);

		this.player1NameComboBox.setModel(new DefaultComboBoxModel<String>(playerNames));
		this.player2NameComboBox.setModel(new DefaultComboBoxModel<String>(playerNames));

		this.aiFactory = factory;
	}

	public AIFactory getAiFactory() {
		return aiFactory;
	}

	private void initSetupPanel() {
		JPanel root = new JPanel();

		this.instructionLabel = new JLabel(SETUP_INSTRUCTION_STR);
		root.add(this.instructionLabel);

		this.player1NameLabel = new JLabel(SETUP_PLAYER1_STR);
		root.add(this.player1NameLabel);

		this.player2NameLabel = new JLabel(SETUP_PLAYER2_STR);
		root.add(this.player2NameLabel);

		this.player1NameComboBox = new JComboBox<>();
		root.add(this.player1NameComboBox);

		this.player2NameComboBox = new JComboBox<>();
		root.add(this.player2NameComboBox);

		this.startButton = new JButton(SETUP_START_GAME_STR);
		this.startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// Grab the selected player names for the game
				String player1 = (String)player1NameComboBox.getSelectedItem();
				String player2 = (String)player2NameComboBox.getSelectedItem();

				// Create game controller and connect four game panel
				AbstractAI playerRed;
				try {
					playerRed = aiFactory.getAI(player1);
				} catch (AIFactoryException e) {
					playerRed = new Player();
				}

				AbstractAI playerYellow;
				try {
					playerYellow = aiFactory.getAI(player2);
				} catch (AIFactoryException e) {
					playerYellow = new Player();
				}

				GameController controller = new GameController(playerRed, playerYellow);
				ConnectFourPanel connectFourPanel = new ConnectFourPanel(window, controller);

				connectFourPanel.setPlayer1TypeText(player1);
				connectFourPanel.setPlayer2TypeText(player2);

				// Create a new tab for the game
				tabbedPane.add(GAME_PREFIX_STR + numGames, connectFourPanel);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				numGames++;
			}
		});
		root.add(this.startButton);

		GroupLayout layout = new GroupLayout(root);
		root.setLayout(layout);

		final int OUTER_PADDING = 10;
		final int INNER_PADDING = 8;

		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGap(OUTER_PADDING)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(this.instructionLabel, GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(INNER_PADDING * 2)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(this.player1NameLabel)
						.addComponent(this.player2NameLabel)
					)
					.addGap(INNER_PADDING)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
						.addComponent(this.player1NameComboBox)
						.addComponent(this.player2NameComboBox)
					)
				)
				.addComponent(this.startButton, GroupLayout.Alignment.TRAILING)
			)
			.addGap(OUTER_PADDING)
		);

		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGap(OUTER_PADDING)
			.addComponent(this.instructionLabel)
			.addGap(INNER_PADDING * 2)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false)
				.addComponent(this.player1NameLabel)
				.addComponent(this.player1NameComboBox)
			)
			.addGap(INNER_PADDING)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false)
				.addComponent(this.player2NameLabel)
				.addComponent(this.player2NameComboBox)
			)
			.addGap(INNER_PADDING * 2)
			.addComponent(this.startButton)
			.addGap(OUTER_PADDING)
		);

		// Need to wrap the root in another panel to force resize of some of the components
		JPanel wrapper = new JPanel();
		BoxLayout wrapperLayout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(wrapperLayout);
		wrapper.add(root);

		this.setupPanel = wrapper;
	}
}
