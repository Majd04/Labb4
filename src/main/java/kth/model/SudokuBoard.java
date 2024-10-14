package kth.model;

import java.io.Serializable;

/**
 * The {@code SudokuBoard} class represents the state of the Sudoku puzzle,
 * including both the current board and the solution board. It provides methods
 * to manipulate and check the state of the board, such as verifying whether
 * the puzzle is solved or resetting to its initial state.
 *
 * @author Majd & Marvin
 * @version 1.0
 */
public class SudokuBoard implements Serializable {
    private final SudokuCell[][] board;
    private final int[][] boardSolution;
    private final SudokuCell[][] initialBoard;

    /**
     * Constructs a new {@code SudokuBoard} with the given puzzle and solution boards.
     * The initial state of the puzzle is stored for later reset.
     *
     * @param board the initial puzzle board as a 2D array of integers.
     * @param boardSolution the solution board for the puzzle.
     */
    public SudokuBoard(int[][] board, int[][] boardSolution) {
        this.boardSolution = boardSolution;

        // Initialize the current board and the initial board with SudokuCell objects
        this.board = new SudokuCell[9][9];
        this.initialBoard = new SudokuCell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                boolean editable = board[row][col] == 0; // If the value is 0, it's editable
                this.board[row][col] = new SudokuCell(board[row][col], editable);
                this.initialBoard[row][col] = new SudokuCell(board[row][col], editable);
            }
        }
    }

    /**
     * Resets the board to its initial state.
     */
    public void resetToInitialBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col].setValue(initialBoard[row][col].getValue());
                board[row][col].setEditable(initialBoard[row][col].isEditable());
            }
        }
    }

    /**
     * Returns the initial state of the board as a 2D array of integers.
     * This method is useful when you need to get the initial puzzle state without resetting the board.
     *
     * @return a 2D array representing the initial state of the board.
     */
    public int[][] getInitialBoard() {
        int[][] initialBoardValues = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                initialBoardValues[row][col] = initialBoard[row][col].getValue();
            }
        }
        return initialBoardValues;
    }

    /**
     * Sets the value of a specific cell on the current board.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @param val the value to set in the cell.
     */
    public void setCellVal(int row, int col, int val) {
        board[row][col].setValue(val);
    }

    /**
     * Returns the value of a specific cell on the current board.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the value of the specified cell.
     */
    public int getCellVal(int row, int col) {
        return board[row][col].getValue();
    }

    /**
     * Checks if the current board matches the solution, indicating whether
     * the puzzle is solved.
     *
     * @return {@code true} if the current board matches the solution, {@code false} otherwise.
     */
    public boolean isSolved() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].getValue() != boardSolution[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the current board contains correct values for all filled cells
     * without verifying the entire solution.
     *
     * @return {@code true} if all filled cells have the correct values, {@code false} otherwise.
     */
    public boolean checkPartialSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int currentVal = board[row][col].getValue();  // Get the value from the current board
                if (currentVal != 0) {  // Only check non-empty cells
                    int correctVal = getSolutionVal(row, col);  // Get the correct solution value
                    if (currentVal != correctVal) {
                        return false;  // Return false if any filled value is incorrect
                    }
                }
            }
        }
        return true;  // Return true if all filled values are correct
    }

    /**
     * Checks if all cells on the current board are filled with non-zero values.
     *
     * @return {@code true} if all cells are filled, {@code false} otherwise.
     */
    public boolean allCellsFilled() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].getValue() == 0) {  // Check for empty cells
                    return false;  // Return false if there is at least one empty cell
                }
            }
        }
        return true;  // All cells are filled
    }

    /**
     * Prints the current state of the puzzle board and the solution board.
     * Used for debugging and inspection purposes.
     */
    public void printBoard() {
        System.out.println("Current Board:");
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col].getValue() + " ");
            }
            System.out.println();
        }

        System.out.println("Solution Board:");
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(boardSolution[row][col] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns the correct solution value for a specific cell.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the solution value for the specified cell.
     */
    public int getSolutionVal(int row, int col) {
        return boardSolution[row][col];
    }

    /**
     * Sets the solution value for a specific cell.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @param val the solution value to set.
     */
    public void setSolutionVal(int row, int col, int val) {
        boardSolution[row][col] = val;
    }
}
