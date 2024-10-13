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
    private final int[][] board;
    private final int[][] boardSolution;
    private final int[][] initialBoard;

    /**
     * Constructs a new {@code SudokuBoard} with the given puzzle and solution boards.
     * The initial state of the puzzle is stored for later reset.
     *
     * @param board the initial puzzle board.
     * @param boardSolution the solution board for the puzzle.
     */
    public SudokuBoard(int[][] board, int[][] boardSolution) {
        this.board = board;
        this.boardSolution = boardSolution;

        // Create a copy of the initial board to reset later
        initialBoard = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                initialBoard[row][col] = board[row][col];
            }
        }
    }

    /**
     * Resets the board to its initial state.
     */
    public void getInitialBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = initialBoard[row][col];
            }
        }
    }

    /**
     * Sets the value of a specific cell on the current board.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @param val the value to set in the cell.
     */
    public void setCellVal(int row, int col, int val) {
        board[row][col] = val;
    }

    /**
     * Returns the value of a specific cell on the current board.
     *
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @return the value of the specified cell.
     */
    public int getCellVal(int row, int col) {
        return board[row][col];
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
                if (board[row][col] != boardSolution[row][col]) {
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
                int currentVal = board[row][col];
                if (currentVal != 0) {
                    int correctVal = getSolutionVal(row, col);
                    if (currentVal != correctVal) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if all cells on the current board are filled with non-zero values.
     *
     * @return {@code true} if all cells are filled, {@code false} otherwise.
     */
    public boolean allCellsFilled() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints the current state of the puzzle board and the solution board.
     * Used for debugging and inspection purposes.
     */
    public void printBoard() {
        System.out.println("Current Board:");
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col] + " ");
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
