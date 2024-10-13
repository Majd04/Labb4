package kth.model;

import java.util.Random;

public class SudokuUtilities {

    public enum SudokuLevel {EASY, MEDIUM, HARD}

    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param level The level, i.e. the difficulty, of the initial standing.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
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

        // Apply transformations to generate a random variant
        Random rand = new Random();
        boolean flipHorizontally = rand.nextBoolean();  // Randomly decide to flip horizontally
        boolean flipVertically = rand.nextBoolean();    // Randomly decide to flip vertically

        if (flipHorizontally) {
            flipBoardHorizontally(matrix);
        }
        if (flipVertically) {
            flipBoardVertically(matrix);
        }

        // Randomly swap numbers (e.g., swap all 1's with 2's)
        swapRandomNumbers(matrix);

        return matrix;
    }

    // Flip the Sudoku board horizontally
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

    // Flip the Sudoku board vertically
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

    // Swap all occurrences of two random numbers (e.g., all 1's with 2's)
    private static void swapRandomNumbers(int[][][] board) {
        Random rand = new Random();
        int num1 = rand.nextInt(9) + 1;  // Random number between 1 and 9
        int num2;
        do {
            num2 = rand.nextInt(9) + 1;  // Ensure that num2 is different from num1
        } while (num1 == num2);

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
     * Create a 3-dimensional matrix with initial values and solution in Sudoku.
     *
     * @param stringRepresentation A string of 2*81 characters, 0-9. The first 81 characters represents
     *                             the initial values, '0' representing an empty cell.
     *                             The following 81 characters represents the solution.
     * @return A 3-dimensional int matrix.
     * [row][col][0] represents the initial values, zero representing an empty cell.
     * [row][col][1] represents the solution.
     */
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " + stringRepresentation.length());

        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();

        int charIndex = 0;
        // initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] = convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        // solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] = convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }

        return values;
    }

    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }

    // Puzzle data
    private static final String easy =
            "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" + // solution values after this substring
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

    // Keep the getPuzzle methods
    public static int[][] getPuzzle(String difficulty) {
        int[][] board = new int[9][9];
        String puzzle = difficulty.substring(0, 81);  // Extract the first 81 characters for puzzle
        for (int i = 0; i < puzzle.length(); i++) {
            int row = i / 9;
            int col = i % 9;
            board[row][col] = Character.getNumericValue(puzzle.charAt(i));
        }
        return board;
    }

    private static int[][] extractInitialValues(int[][][] matrix) {
        int[][] initialBoard = new int[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                initialBoard[row][col] = matrix[row][col][0];  // Get the initial puzzle values (index 0)
            }
        }
        return initialBoard;
    }

    public static int[][] getEasyPuzzle() {
        int[][][] matrix = generateSudokuMatrix(SudokuLevel.EASY);  // This will randomize the puzzle
        return extractInitialValues(matrix);  // Extract only the initial values
    }

    public static int[][] getMediumPuzzle() {
        int[][][] matrix = generateSudokuMatrix(SudokuLevel.MEDIUM);  // This will randomize the puzzle
        return extractInitialValues(matrix);  // Extract only the initial values
    }

    public static int[][] getHardPuzzle() {
        int[][][] matrix = generateSudokuMatrix(SudokuLevel.HARD);  // This will randomize the puzzle
        return extractInitialValues(matrix);  // Extract only the initial values
    }
}


