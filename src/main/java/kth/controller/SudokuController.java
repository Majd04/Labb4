package kth.controller;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kth.io.SudokuFileIO;
import kth.model.SudokuBoard;
import kth.model.SudokuUtilities;
import kth.view.BoardPane;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Controller class responsible for managing the interaction between the
 * Sudoku board model and the user interface (view).
 * It handles user input, updates the model, and reflects changes in the UI.
 *
 * @author Majd & Marvin
 * @version 1.0
 */
public class SudokuController {
    private SudokuBoard sudokuBoard;
    private final BoardPane boardPane;
    private int selectedNumber = 0;
    private final Random random = new Random();
    private int selectedRow = -1;
    private int selectedCol = -1;
    private final SudokuUtilities.SudokuLevel currentLevel;

    /**
     * Constructs a SudokuController with the specified board, view, and difficulty level.
     * Initializes the puzzle and solution based on the provided difficulty.
     *
     * @param board the SudokuBoard model
     * @param view the BoardPane UI component
     * @param level the difficulty level of the puzzle
     */
    public SudokuController(SudokuBoard board, BoardPane view, SudokuUtilities.SudokuLevel level) {
        int[][][] puzzleAndSolution = SudokuUtilities.generateSudokuMatrix(level);
        int[][] puzzle = new int[9][9];
        int[][] solution = new int[9][9];
        this.currentLevel = level;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                puzzle[row][col] = puzzleAndSolution[row][col][0];
                solution[row][col] = puzzleAndSolution[row][col][1];
            }
        }

        this.sudokuBoard = new SudokuBoard(puzzle, solution);
        this.boardPane = view;
    }

    /**
     * Sets the difficulty level of the Sudoku puzzle.
     * Generates a new puzzle and solution for the selected difficulty level.
     *
     * @param newDifficulty the new difficulty level
     */
    public void setDifficulty(SudokuUtilities.SudokuLevel newDifficulty) {
        int[][][] puzzleAndSolution = SudokuUtilities.generateSudokuMatrix(newDifficulty);
        int[][] newPuzzle = new int[9][9];
        int[][] newSolution = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                newPuzzle[row][col] = puzzleAndSolution[row][col][0];
                newSolution[row][col] = puzzleAndSolution[row][col][1];
            }
        }

        this.sudokuBoard = new SudokuBoard(newPuzzle, newSolution);
        this.boardPane.initializeBoard(newPuzzle);
    }

    /**
     * Returns the current difficulty level.
     *
     * @return the current difficulty level
     */
    public SudokuUtilities.SudokuLevel getDifficulty() {
        return currentLevel;
    }

    /**
     * Sets the currently selected number to be placed in a Sudoku cell.
     *
     * @param number the number selected by the user
     */
    public void setSelectedNumber(int number) {
        this.selectedNumber = number;
    }

    /**
     * Returns the currently selected number to be placed in a Sudoku cell.
     *
     * @return the selected number
     */
    public int getSelectedNumber() {
        return selectedNumber;
    }

    /**
     * Handles the action when a Sudoku cell is clicked by the user.
     * If a number is selected, it is placed in the cell at the specified row and column.
     *
     * @param row the row index of the clicked cell
     * @param col the column index of the clicked cell
     */
    public void handleCellClick(int row, int col) {
        selectedRow = row;
        selectedCol = col;
        if (selectedNumber != 0) {
            sudokuBoard.setCellVal(row, col, selectedNumber);
            boardPane.updateCell(row, col, selectedNumber, false);
        }
    }

    /**
     * Clears the value in the currently selected cell on the Sudoku board.
     */
    public void clearSelectedCell() {
        if (selectedRow != -1 && selectedCol != -1) {
            sudokuBoard.setCellVal(selectedRow, selectedCol, 0);
            boardPane.updateCell(selectedRow, selectedCol, 0, false);
        }
    }

    /**
     * Returns the current Sudoku board.
     *
     * @return the SudokuBoard model
     */
    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    /**
     * Checks if the current solution is correct.
     * If the puzzle is completely filled, it checks if the solution is correct.
     * Otherwise, it checks the correctness of the partial solution.
     */
    public void checkSolution() {
        sudokuBoard.printBoard();
        if (sudokuBoard.allCellsFilled()) {
            if (sudokuBoard.isSolved()) {
                Alert solvedAlert = new Alert(Alert.AlertType.INFORMATION);
                solvedAlert.setTitle("Sudoku Solved");
                solvedAlert.setHeaderText(null);
                solvedAlert.setContentText("Congratulations! You have solved the Sudoku puzzle!");
                solvedAlert.showAndWait();
            } else {
                Alert notSolvedAlert = new Alert(Alert.AlertType.ERROR);
                notSolvedAlert.setTitle("Sudoku Not Solved");
                notSolvedAlert.setHeaderText(null);
                notSolvedAlert.setContentText("There are mistakes in the Sudoku puzzle. Please try again.");
                notSolvedAlert.showAndWait();
            }
        } else {
            if (sudokuBoard.checkPartialSolution()) {
                Alert partialCorrectAlert = new Alert(Alert.AlertType.INFORMATION);
                partialCorrectAlert.setTitle("Sudoku Progress");
                partialCorrectAlert.setHeaderText(null);
                partialCorrectAlert.setContentText("So far, everything is correct. Keep going!");
                partialCorrectAlert.showAndWait();
            } else {
                Alert partialIncorrectAlert = new Alert(Alert.AlertType.ERROR);
                partialIncorrectAlert.setTitle("Sudoku Mistakes");
                partialIncorrectAlert.setHeaderText(null);
                partialIncorrectAlert.setContentText("There are mistakes in your current solution. Please try again.");
                partialIncorrectAlert.showAndWait();
            }
        }
    }

    /**
     * Provides a hint by filling a randomly selected empty cell with the correct value.
     */
    public void giveHint() {
        int row, col;
        do {
            row = random.nextInt(9);
            col = random.nextInt(9);
        } while (sudokuBoard.getCellVal(row, col) != 0);

        int correctValue = sudokuBoard.getSolutionVal(row, col);
        sudokuBoard.setCellVal(row, col, correctValue);
        boardPane.updateCell(row, col, correctValue, true);
    }

    /**
     * Saves the current Sudoku game to a file.
     *
     * @param stage the current window (Stage) for the FileChooser dialog
     */
    public void saveGame(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Sudoku Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                SudokuFileIO.serializeToFile(sudokuBoard, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Restarts the current Sudoku game by resetting the board to its initial state.
     */
    public void restartGame() {
        sudokuBoard.getInitialBoard();
        boardPane.updateBoard(sudokuBoard);
    }

    /**
     * Loads a saved Sudoku game from a file.
     *
     * @param stage the current window (Stage) for the FileChooser dialog
     */
    public void loadGame(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Sudoku Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                sudokuBoard = SudokuFileIO.deSerializeFromFile(file);
                boardPane.updateBoard(sudokuBoard);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
