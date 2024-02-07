package helpers;

/**
 * SudokuGenerator
 * 
 * @author John Botonakis
 * @author Created with some very loose help from ChatGPT
 * @date 11-8-2023
 * @desc This SudokuGenerator class is responsible for generating a solved Sudoku board using backtracking 
 *        and providing necessary methods to manipulate the Sudoku board, implemented by inheriting from 
 *        the List<Integer> interface. 
 *       
 */
import java.util.*;

public class SudokuGenerator implements List<Integer> {
    // List to store the Sudoku board as a flat 81-cell structure
    private List<Integer> board;

    // Constructor: Initializes the board with 81 cells, all initially set to 0
    public SudokuGenerator() {
        board = new ArrayList<>(Collections.nCopies(81, 0));
    }

    // Method to generate a solved Sudoku puzzle
    public List<Integer> generateSolvedSudoku() {
        // Try to solve the Sudoku starting from cell 0
        if (solveSudoku(0)) {
            return board; // Return the solved Sudoku board
        }
        return new ArrayList<>(); // Return an empty list if no solution is found
    }

    // Recursively solves the Sudoku puzzle using backtracking
    private boolean solveSudoku(int position) {
        if (position == 81) {
            return true; // All cells are filled, Sudoku is solved
        }

        int row = position / 9;
        int col = position % 9;

        // If the cell is not empty, skip to the next cell
        if (board.get(position) != 0) {
            return solveSudoku(position + 1);
        }

        // Try numbers 1 to 9 in the current cell
        for (int num = 1; num <= 9; num++) {
            if (isSafe(row, col, num)) {
                board.set(position, num); // Place the number in the cell

                // Recursively solve the Sudoku with the new number placed
                if (solveSudoku(position + 1)) {
                    return true; // The number placed in this cell works, and the Sudoku is solved
                }

                board.set(position, 0); // Backtrack
            }
        }

        return false; // No valid number can be placed in this cell
    }

    // Checks if a number can be placed in the given cell without breaking Sudoku
    // rules
    private boolean isSafe(int row, int col, int num) {
        return !usedInRow(row, num) && !usedInCol(col, num) && !usedInBox(row - row % 3, col - col % 3, num);
    }

    // Check if the number exists in the given row
    private boolean usedInRow(int row, int num) {
        for (int i = 0; i < 9; i++) {
            if (board.get(row * 9 + i) == num) {
                return true;
            }
        }
        return false;
    }

    // Check if the number exists in the given column
    private boolean usedInCol(int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board.get(i * 9 + col) == num) {
                return true;
            }
        }
        return false;
    }

    // Check if the number exists in the 3x3 subgrid
    private boolean usedInBox(int boxStartRow, int boxStartCol, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get((i + boxStartRow) * 9 + (j + boxStartCol)) == num) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return board.size();
    }

    @Override
    public boolean isEmpty() {
        return board.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return board.contains(o);
    }

    @Override
    public Iterator<Integer> iterator() {
        return board.iterator();
    }

    @Override
    public Object[] toArray() {
        return board.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return board.toArray(a);
    }

    @Override
    public boolean add(Integer e) {
        return board.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return board.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return board.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        return board.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Integer> c) {
        return board.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return board.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return board.retainAll(c);
    }

    @Override
    public void clear() {
        board.clear();
    }

    @Override
    public Integer get(int index) {
        return board.get(index);
    }

    @Override
    public Integer set(int index, Integer element) {
        return board.set(index, element);
    }

    @Override
    public void add(int index, Integer element) {
        board.add(index, element);
    }

    @Override
    public Integer remove(int index) {
        return board.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return board.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return board.lastIndexOf(o);
    }

    @Override
    public ListIterator<Integer> listIterator() {
        return board.listIterator();
    }

    @Override
    public ListIterator<Integer> listIterator(int index) {
        return board.listIterator(index);
    }

    @Override
    public List<Integer> subList(int fromIndex, int toIndex) {
        return board.subList(fromIndex, toIndex);
    }

}
