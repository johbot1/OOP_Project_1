package states;

/**
 * Difficulty Panel
 * 
 * @author John Botonakis
 * @author Created with some very loose help from ChatGPT
 * @date 11-8-2023
 * @desc This DifficultyPanel class is a panel containing buttons for users to select different difficulty levels for the Sudoku game. 
 *       It allows users to choose the difficulty level by clicking on the buttons, and once a level is selected, it starts the game accordingly. 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import helpers.PanelInterface;
import main.SudokuPanel;

public class DifficultyPanel extends JPanel implements PanelInterface {
    // Unique identifier for the class serialization
    private static final long serialVersionUID = 1L;

    // Buttons for different difficulty levels
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JButton tedButton;
//    private JButton testButton;
    private int difficulty;

    // Reference to the main frame
    private JFrame frame;

    // Background image for the panel
    private Image backgroundImage;

    // Constructor for DifficultyPanel, initializes the panel
    public DifficultyPanel(JFrame frame) {
        this.frame = frame; // Assign the provided frame to the local frame variable
        setPreferredSize(new Dimension(600, 600)); // Set preferred panel size
        backgroundImage = new ImageIcon(getClass().getResource("/Difficulty.png")).getImage(); // Load background image

        setLayout(new BorderLayout()); // Set the layout for the panel as BorderLayout

        // Create buttons for different difficulty levels and add them to the panel
        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the bottom of the DifficultyPanel
    }

    // Override the paintComponent method to draw the background image
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass's paintComponent method

        // Draw the background image if it's available
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    // Method to create the panel containing buttons for different difficulty levels
    @Override
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(); // Create a panel to hold the buttons

        // Create buttons for different difficulty levels and set their respective
        // actions
        easyButton = new JButton("Easy");
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 20;
                changePanel(); // Start the game with 50 empty spaces
            }
        });

        mediumButton = new JButton("Medium");
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 36;
                changePanel(); // Start the game with 50 empty spaces
            }
        });

        hardButton = new JButton("Hard");
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 50;
                changePanel(); // Start the game with 50 empty spaces
            }
        });

        tedButton = new JButton("Ted");
        tedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 119;
                changePanel(); // Start the game and good luck.
            }
        });

        // Test Button used to debug win screen transition
        // Also used to make me feel smart by solving it in 1 step.
//        testButton = new JButton("Test");
//        testButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                difficulty = 1;
//                changePanel(); // Debugging.
//            }
//        });

        // Add the buttons for different difficulty levels to the button panel
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(tedButton);
//        buttonPanel.add(testButton);

        return buttonPanel; // Return the panel containing the difficulty buttons
    }

    // Method to start the game based on the selected difficulty
    @Override
    public void changePanel() {
        frame.setContentPane(new SudokuPanel(frame, difficulty)); // Start the Sudoku game with the given difficulty
        frame.revalidate(); // Refresh the frame to display the new content

    }

}
