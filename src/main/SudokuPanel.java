/**
 * SudokuPanel
 * x
 * @author John Botonakis
 * @author Created with some very loose help from ChatGPT
 * @date 11-8-2023
 * @desc The SudokuPanel class is an extension of JPanel and is used to create a graphical interface for a Sudoku game. 
 *       It includes methods to handle actions within the Sudoku grid, such as checking entered values, 
 *       solving the puzzle, resetting the grid, and creating different visual elements. 
 */
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;

import helpers.SudokuGenerator;
import states.MenuPanel;
import states.WinnerPanel;

public class SudokuPanel extends JPanel {
    private static final long serialVersionUID = 1L;// ??

    private int[][] completedBoard = new int[9][9]; // 9x9 array representing the completed game board
    private List<Integer> solvedBoard; // List of integers representing the initial solved board

    private static JLabel selectedCell; // Tracks the selected cell

    private SudokuGenerator generator; // Instance of the SudokuGenerator class for generating Sudoku boards
    private JFrame frame; // Frame for the Sudoku game
    private JPanel sudokuGrid; // Panel for the Sudoku grid

    private Timer gameLoop; // Timer for the game loop

    private final MatteBorder outsideBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK); // Border for
                                                                                                        public SudokuPanel(JFrame frame, int emptySpaces) {
        // Set up the frame and initial preferences
        this.frame = frame;
        setPreferredSize(new Dimension(600, 600));

        // Set the layout of the Sudoku panel
        setLayout(new BorderLayout());

        generator = new SudokuGenerator();

        // Create the Sudoku grid and add it to the panel
        sudokuGrid = createSudokuGrid();
        add(sudokuGrid, BorderLayout.CENTER);

        // Create the panel containing buttons and add it to the bottom of the panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Generate a solved Sudoku and fill the grid with empty spaces
        solvedBoard = generator.generateSolvedSudoku();
        fillSudokuGrid(sudokuGrid, emptySpaces);
        // Create a timer for the game loop
        gameLoop = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameIsCompleted()) {
                    gameLoop.stop();
                    frame.setContentPane(new WinnerPanel(frame));
                    frame.revalidate();
                }

            }

        });
        gameLoop.start(); // Start the game loop
    }

    /**
     * Checks whether the Sudoku game is completed by inspecting all cells in the
     * grid.
     * 
     * @return true if all cells are filled and not enabled for input, indicating
     *         the game is completed.
     */
    private boolean gameIsCompleted() {
        for (Component component : sudokuGrid.getComponents()) {
            if (component instanceof JLabel) {
                JLabel cell = (JLabel) component;
                if (cell.getText().isEmpty() && cell.isEnabled()) {
                    // If the cell is enabled for input or it's empty, the game is not completed
                    return false;
                }
            }
        }
        // If all cells are filled and not enabled for input, the game is completed
        return true;
    }

    /**
     * Updates the text of the specified JLabel and refreshes its appearance.
     * 
     * @param cell The JLabel to be updated.
     * @param text The new text to set for the JLabel.
     */
    private void updateCell(JLabel cell, String text) {
        // Set the text of the JLabel to the new text
        cell.setText(text);

        // Repaints the cell to reflect the text change
        cell.repaint();

        // Refresh the cell to ensure proper layout after text change
        cell.revalidate();

        // Request the repaint of the parent container to update cell changes
        cell.getParent().repaint();

        // Refresh the parent container to update its layout due to cell changes
        cell.getParent().revalidate();
    }

    /**
     * Creates a JPanel representing the Sudoku grid. The grid consists of 81 JLabel
     * cells arranged in a 9x9 layout. Additionally, it adds a numberButtonsPanel
     * for interaction.
     *
     * @return JPanel representing the Sudoku grid.
     */
    private JPanel createSudokuGrid() {
        // Create the main panel to hold the Sudoku grid
        JPanel sudokuGrid = new JPanel();
        sudokuGrid.setLayout(new GridLayout(9, 9)); // Set a 9x9 grid layout

        // Populate the grid with individual cells
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JLabel cell = new JLabel(); // Create a new cell
                // Configure the cell - attaching mouse click listener
                addMouseClickListener(cell, sudokuGrid);
                sudokuGrid.add(cell); // Add the cell to the grid
            }
        }

        // Create a panel containing number buttons and add it to the east of the grid
        JPanel numberButtonsPanel = createNumberButtonsPanel(sudokuGrid);
        add(numberButtonsPanel, BorderLayout.EAST);

        return sudokuGrid; // Return the constructed Sudoku grid
    }

    /**
     * Make a completed Sudoku board, and keep a copy of it for later. After.
     * randomly assign holes in the board for input. The amount of holes depend on
     * the difficulty selected.
     *
     * @param sudokuGrid  The JPanel / Sudoku grid.
     * @param emptySpaces The number of empty cells to be generated randomly in the
     *                    grid.
     */
    private void fillSudokuGrid(JPanel sudokuGrid, int emptySpaces) {
        int index = 0;

        // Save a copy of the solved board for later reference
        if (!solvedBoard.isEmpty()) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    completedBoard[row][col] = solvedBoard.get(index++);
                }
            }
        }

        // Fill the grid with the solved Sudoku
        if (!solvedBoard.isEmpty()) {
            if (sudokuGrid != null) {
                for (Component component : sudokuGrid.getComponents()) {
                    if (component instanceof JLabel) {
                        JLabel cell = (JLabel) component;
                        cell.setText(String.valueOf(solvedBoard.remove(0)));
                        cell.setPreferredSize(new Dimension(50, 50));
                        cell.setHorizontalAlignment(JLabel.CENTER);
                        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        cell.setFont(new Font("Arial", Font.PLAIN, 20));
                        cell.setEnabled(false); // Lock the initial solved cells
                    }
                }
            } else {
                System.out.println("Sudoku grid is not initialized.");
                return;
            }
        } else {
            System.out.println("Sudoku could not be generated.");
            return;
        }

        // Randomly remove numbers to create empty spaces in the grid
        if (emptySpaces > 0) {
            Random random = new Random();
            for (int i = 0; i < emptySpaces; i++) {
                int randomIndex = random.nextInt(81); // 81 is the total number of cells (9x9 grid)
                Component component = sudokuGrid.getComponent(randomIndex);
                if (component instanceof JLabel) {
                    JLabel cell = (JLabel) component;
                    cell.setText(""); // Make the cell empty
                    cell.setEnabled(true); // Enable the empty cells for player input
                }
            }
        }
    }

    /**
     * Creates a panel for number buttons to input values into the Sudoku grid.
     *
     * @param sudokuGrid The JPanel representing the Sudoku grid.
     * @return The panel containing number buttons.
     */
    private JPanel createNumberButtonsPanel(JPanel sudokuGrid) {
        JPanel numbersPanel = new JPanel(new GridLayout(9, 1));

        for (int i = 1; i <= 9; i++) {
            int currentValue = i;
            JButton numberButton = new JButton(String.valueOf(currentValue));

            // ActionListener for each number button
            numberButton.addActionListener(e -> {
                // Checks if a cell is selected and enabled for editing
                if (selectedCell != null && selectedCell.isEnabled()) {
                    // Set the value of the selected cell to the number button's value
                    selectedCell.setText(String.valueOf(currentValue));
                    // Update the cell with the new value
                    updateCell(selectedCell, (String.valueOf(currentValue)));
                    // Clear any highlights on the Sudoku grid
                    clearAllHighlights(sudokuGrid);
                    // Check the value entered in the selected cell
                    checkEnteredValue(selectedCell);
                    // Refresh the grid layout
                    sudokuGrid.revalidate();
                }
            });
            numbersPanel.add(numberButton);
        }
        return numbersPanel;
    }

    /**
     * Checks the validity of the entered value in a Sudoku cell for duplicates in
     * rows, columns, and 3x3 subgrid sections.
     *
     * @param cell The JLabel representing the cell being checked.
     */
    private void checkEnteredValue(JLabel cell) {
        if (!cell.getText().isEmpty()) {
            int cellIndex = sudokuGrid.getComponentZOrder(cell);
            int cellRow = cellIndex / 9;
            int cellColumn = cellIndex % 9;

            int cellSquareRow = cellRow / 3;
            int cellSquareColumn = cellColumn / 3;

            int cellValue = Integer.parseInt(cell.getText());
            boolean foundDuplicate = false;

            // Check for duplicates in rows and columns
            for (int i = 0; i < 9; i++) {
                if (i != cellColumn && completedBoard[cellRow][i] == cellValue) {
                    foundDuplicate = true;
                    break;
                }
                if (i != cellRow && completedBoard[i][cellColumn] == cellValue) {
                    foundDuplicate = true;
                    break;
                }
            }

            // Check for duplicates within the 3x3 subgrid
            for (int row = cellSquareRow * 3; row < cellSquareRow * 3 + 3; row++) {
                for (int col = cellSquareColumn * 3; col < cellSquareColumn * 3 + 3; col++) {
                    if (row != cellRow && col != cellColumn && completedBoard[row][col] == cellValue) {
                        foundDuplicate = true;
                        break;
                    }
                }
            }

            // Change the font color based on whether a duplicate was found or not
            if (foundDuplicate) {
                // Incorrect guess, set font color to red
                cell.setForeground(Color.RED);
                updateCell(cell, String.valueOf(cellValue));
            } else {
                // Correct guess, revert the font color
                cell.setForeground(Color.BLACK); // Set it to the default font color
            }
        }
    }

    /**
     * Adds a mouse click listener to every enabled cell in the Sudoku grid to
     * handle selection.
     *
     * @param cell       The JLabel representing the cell.
     * @param sudokuGrid The JPanel containing the Sudoku grid.
     */
    private void addMouseClickListener(JLabel cell, JPanel sudokuGrid) {
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    clearAllHighlights(sudokuGrid);
                    highlightRowAndColumn(sudokuGrid, cell);
                    selectedCell = cell; // Update the selected cell
                }
            }
        });
    }

    private final MatteBorder insideHighlightBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED);
    private void clearAllHighlights(JPanel sudokuGrid) {
        for (Component component : sudokuGrid.getComponents()) {
            ((JLabel) component).setBorder(outsideBorder);
        }
    }

    private void highlightRowAndColumn(JPanel sudokuGrid, JLabel cell) {
        int cellIndex = sudokuGrid.getComponentZOrder(cell);
        int cellRow = cellIndex / 9;
        int cellColumn = cellIndex % 9;
        int cellSquareRow = cellRow / 3; // Determining the 3x3 square
        int cellSquareColumn = cellColumn / 3;

        for (Component component : sudokuGrid.getComponents()) {
            JLabel currentCell = (JLabel) component;
            int currentCellIndex = sudokuGrid.getComponentZOrder(currentCell);
            int currentRow = currentCellIndex / 9;
            int currentColumn = currentCellIndex % 9;
            int currentSquareRow = currentRow / 3; // Determining the 3x3 square for the current cell
            int currentSquareColumn = currentColumn / 3;

            if (currentRow == cellRow || currentColumn == cellColumn
                    || (currentSquareRow == cellSquareRow && currentSquareColumn == cellSquareColumn)) {
                currentCell.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideHighlightBorder));
            }
        }
    }

    /**
     * Creates the button panel containing buttons for solving the puzzle,
     * resetting, and returning to the menu.
     *
     * @return JPanel containing the buttons for puzzle-solving actions.
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        // Button to solve the Sudoku puzzle
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Loop through the Sudoku grid components
                for (Component component : sudokuGrid.getComponents()) {
                    if (component instanceof JLabel) {
                        JLabel cell = (JLabel) component;
                        int cellIndex = sudokuGrid.getComponentZOrder(cell);
                        int cellRow = cellIndex / 9;
                        int cellColumn = cellIndex % 9;
                        int solvedValue = completedBoard[cellRow][cellColumn];
                        // Update cell with its solved value
                        updateCell(cell, String.valueOf(solvedValue));
                        cell.setForeground(Color.MAGENTA); // Set font color to black
                    }
                }
            }
        });

        // Button to reset the Sudoku puzzle
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Loop through the Sudoku grid components
                for (Component component : sudokuGrid.getComponents()) {
                    if (component instanceof JLabel) {
                        JLabel cell = (JLabel) component;
                        if (cell.isEnabled()) {
                            // Clear the text of the cell and set its background to default color
                            updateCell(cell, "");
                        }
                        cell.setForeground(Color.BLACK); // Reset font color
                    }
                }
            }
        });

        // Button to return to the menu
        JButton toMenu = new JButton("Menu");
        toMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to return to the menu
                frame.setContentPane(new MenuPanel(frame));
                frame.revalidate();
            }
        });

        buttonPanel.add(solveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(toMenu);

        return buttonPanel;
    }

}
