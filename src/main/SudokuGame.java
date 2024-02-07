/**
 * SudokuGame
 * 
 * @author John Botonakis
 * @author Created with some very loose help from ChatGPT
 * @date 11-8-2023
 * @desc This Game class is responsible for anything and everything related to
 *       running the game instance, such as displaying menus and setting the
 *       game window in the center of the screen.
 */
package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import states.MenuPanel;

/**
 * The `SudokuGame` class initializes and manages the main JFrame for the Sudoku
 * game.
 */
public class SudokuGame {
    private JFrame frame;
    private static boolean allowed = true;

    /**
     * Constructor for the SudokuGame. Initializes the main frame, positions it at
     * the center of the screen, sets its size and default close operation, and
     * displays the menu panel.
     */
    public SudokuGame() {
        if (allowed) {
            frame = new JFrame("Sudoku Game");
            frame.setSize(600, 600);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - frame.getWidth()) / 2;
            int y = (screenSize.height - frame.getHeight()) / 2;
            frame.setLocation(x, y);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            displayMenu();
            allowed = false;
        }
    }

    /**
     * Displays the menu panel in the game frame.
     */
    private void displayMenu() {
        MenuPanel menu = new MenuPanel(frame);
        frame.setContentPane(menu);
        frame.setVisible(true);
    }
}
