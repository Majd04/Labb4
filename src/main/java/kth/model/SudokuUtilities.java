package kth.model;

import java.util.Random;

/**
 * Utility class providing various methods to generate and manipulate
 * Sudoku puzzles at different difficulty levels. The class also includes
 * operations to modify the puzzle, such as flipping the board and swapping numbers.
 *
 * @author Majd & Marvin
 * @version 1.0
 */
public class SudokuUtilities {

    public enum SudokuLevel {EASY, MEDIUM, HARD}

    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Generates a Sudoku puzzle and its corresponding solution in a 3D matrix format.
     * The generated puzzle can be modified with random transformations such as
     * flipping and swapping numbers to introduce variations.
     *
     * @param level The difficulty level of the puzzle (EASY, MEDIUM, or HARD).
     * @return A 3D integer array where:
     *         - [row][col][0] contains the initial puzzle values, with '0' representing an empty cell.
     *         - [row][col][1] contains the solved values.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        String representationString;
        switch (level) {
            case EASY: representationString = easy; break;
            case MEDIUM: representationString = medium; break;
            case HARD: representationString = hard; break;
            default: representationString = medium;
        }

        // Convert the string representation to a 3D matrix
        int[][][] matrix = convertStringToIntMatrix(representationString);

        Random rand = new Random();
        boolean flipHorizontally = rand.nextBoolean();
        boolean flipVertically = rand.nextBoolean();

        // Optionally flip the board horizontally or vertically
        if (flipHorizontally) {
            flipBoardHorizontally(matrix);
        }
        if (flipVertically) {
            flipBoardVertically(matrix);
        }

        // Randomly swap two numbers throughout the board
        swapRandomNumbers(matrix);

        return matrix;
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

    /**
     * Converts a string representation of a Sudoku puzzle and its solution into a 3D integer matrix.
     * The string contains 81 characters representing the puzzle followed by 81 characters representing the solution.
     *
     * @param stringRepresentation A string of exactly 162 characters. The first 81 characters represent
     *                             the puzzle, where '0' indicates an empty cell. The following 81 characters
     *                             represent the solution.
     * @return A 3D integer matrix where:
     *         - [row][col][0] contains the initial puzzle values (0 represents empty).
     *         - [row][col][1] contains the corresponding solution values.
     */
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("The representation length must be 162 characters.");

        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();

        int charIndex = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] = convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] = convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        return values;
    }

    /**
     * Converts a character representing a Sudoku digit ('0'-'9') to its corresponding integer value.
     *
     * @param ch The character representing a Sudoku digit.
     * @return The integer value corresponding to the character.
     * @throws IllegalArgumentException if the character is not between '0' and '9'.
     */
    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("Invalid character: " + ch);
        return ch - '0';
    }

    // String representations of predefined Sudoku puzzles and their solutions
    private static final String easy =
            "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" +
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";

    private static final String medium =
            "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";

    private static final String hard =
            "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";

    /**
     * Extracts and returns the puzzle values from a 3D Sudoku matrix.
     * Only the initial puzzle values (not the solution) are returned.
     *
     * @param matrix The 3D matrix representing the Sudoku puzzle and solution.
     * @return A 2D array containing the initial puzzle values.
     */
    private static int[][] extractInitialValues(int[][][] matrix) {
        int[][] initialBoard = new int[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                initialBoard[row][col] = matrix[row][col][0];
            }
        }
        return initialBoard;
    }

    /**
     * Retrieves a randomly generated Sudoku puzzle at the EASY difficulty level.
     *
     * @return A 2D array representing the initial values of the EASY Sudoku puzzle.
     */
    public static int[][] getEasyPuzzle() {
        int[][][] matrix = generateSudokuMatrix(SudokuLevel.EASY);
        return extractInitialValues(matrix);
    }

    /**
     * Retrieves a randomly generated Sudoku puzzle at the MEDIUM difficulty level.
     *
     * @return A 2D array representing the initial values of the MEDIUM Sudoku puzzle.
     */
    public static int[][] getMediumPuzzle() {
        int[][][] matrix = generateSudokuMatrix(SudokuLevel.MEDIUM);
        return extractInitialValues(matrix);
    }

    /**
     * Retrieves a randomly generated Sudoku puzzle at the HARD difficulty level.
     *
     * @return A 2D array representing the initial values of the HARD Sudoku puzzle.
     */
    public static int[][] getHardPuzzle() {
        int[][][] matrix = generateSudokuMatrix(SudokuLevel.HARD);
        return extractInitialValues(matrix);
    }
}


