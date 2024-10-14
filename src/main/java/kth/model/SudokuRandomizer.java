package kth.model;

import java.util.Random;

/**
 * The {@code SudokuRandomizer} class provides methods to apply random transformations
 * to a Sudoku puzzle, such as flipping the board and swapping numbers. These transformations
 * modify the puzzle while keeping it valid.
 *
 * @author Majd & Marvin
 * @version 1.0
 */
public class SudokuRandomizer {

    private static final int GRID_SIZE = 9;

    /**
     * Randomly flips the board horizontally or vertically and swaps two random numbers.
     *
     * @param matrix The 3D matrix representing the Sudoku puzzle and solution.
     */
    public static void randomizeBoard(int[][][] matrix) {
        Random rand = new Random();

        boolean flipHorizontally = rand.nextBoolean();
        boolean flipVertically = rand.nextBoolean();

        if (flipHorizontally) {
            flipBoardHorizontally(matrix);
        }
        if (flipVertically) {
            flipBoardVertically(matrix);
        }

        swapRandomNumbers(matrix);
    }

    /**
     * Flips the Sudoku board horizontally, reversing the order of columns in each row.
     * This transformation affects both the puzzle and its solution.
     *
     * @param board The 3D matrix representing the Sudoku puzzle and solution.
     *              The matrix will be modified in place.
     */
    private static void flipBoardHorizontally(int[][][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE / 2; col++) {
                for (int z = 0; z < 2; z++) {
                    int temp = board[row][col][z];
                    board[row][col][z] = board[row][GRID_SIZE - 1 - col][z];
                    board[row][GRID_SIZE - 1 - col][z] = temp;
                }
            }
        }
    }

    /**
     * Flips the Sudoku board vertically, reversing the order of rows.
     * This transformation affects both the puzzle and its solution.
     *
     * @param board The 3D matrix representing the Sudoku puzzle and solution.
     *              The matrix will be modified in place.
     */
    private static void flipBoardVertically(int[][][] board) {
        for (int row = 0; row < GRID_SIZE / 2; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                for (int z = 0; z < 2; z++) {
                    int temp = board[row][col][z];
                    board[row][col][z] = board[GRID_SIZE - 1 - row][col][z];
                    board[GRID_SIZE - 1 - row][col][z] = temp;
                }
            }
        }
    }

    /**
     * Swaps all occurrences of two randomly selected numbers throughout the Sudoku board.
     * This random transformation ensures that the puzzle remains valid while changing its appearance.
     *
     * @param board The 3D matrix representing the Sudoku puzzle and solution.
     *              The numbers in the matrix will be swapped in place.
     */
    private static void swapRandomNumbers(int[][][] board) {
        Random rand = new Random();
        int num1 = rand.nextInt(9) + 1;  // Random number between 1 and 9
        int num2;
        do {
            num2 = rand.nextInt(9) + 1;
        } while (num1 == num2);  // Ensure num1 and num2 are different

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                for (int z = 0; z < 2; z++) {
                    if (board[row][col][z] == num1) {
                        board[row][col][z] = num2;
                    } else if (board[row][col][z] == num2) {
                        board[row][col][z] = num1;
                    }
                }
            }
        }
    }
}
