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

public class SudokuController {
    private SudokuBoard sudokuBoard;
    private BoardPane boardPane;
    private int selectedNumber = 0;
    private Random random = new Random();  // For selecting random empty cells
    private int selectedRow = -1;
    private int selectedCol = -1;

/*    public SudokuController(SudokuBoard board, BoardPane view) {
        this.sudokuBoard = board;
        this.boardPane = view;
    }*/

    public SudokuController(SudokuBoard board, BoardPane view, SudokuUtilities.SudokuLevel level) {
        // Använd SudokuUtilities för att generera pussel och lösning
        int[][][] puzzleAndSolution = SudokuUtilities.generateSudokuMatrix(level);
        int[][] puzzle = new int[9][9];
        int[][] solution = new int[9][9];

        // Dela upp i pussel och lösning
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                puzzle[row][col] = puzzleAndSolution[row][col][0];  // Olösta pusslet
                solution[row][col] = puzzleAndSolution[row][col][1];  // Lösning
            }
        }

        this.sudokuBoard = new SudokuBoard(puzzle, solution);
        this.boardPane = view;
    }

    // Set the currently selected number
    public void setSelectedNumber(int number) {
        this.selectedNumber = number;
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    // Handle click on a Sudoku cell (row, col)
    public void handleCellClick(int row, int col) {
        selectedRow = row;
        selectedCol = col;
        if (selectedNumber != 0) {
            sudokuBoard.setCellVal(row, col, selectedNumber);  // Update model
            boardPane.updateCell(row, col, selectedNumber, false);    // Update view
        }
    }

    // Metod för att rensa den valda cellen
    public void clearSelectedCell() {
        if (selectedRow != -1 && selectedCol != -1) {
            // Rensa cellen i logiken (modell)
            sudokuBoard.setCellVal(selectedRow, selectedCol, 0);  // Sätt värdet till 0 (tom cell)

            // Uppdatera UI för att spegla ändringen
            boardPane.updateCell(selectedRow, selectedCol, 0, false);  // Uppdatera grafiken i cellen

            // Töm den valda cellen (logik och UI)
            System.out.println("Cleared cell at [" + selectedRow + "][" + selectedCol + "]");
        } else {
            System.out.println("No cell selected to clear.");
        }
    }

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    // Metod för att kontrollera om lösningen är korrekt
    // Check the solution (full or partial)
    public void checkSolution() {
        // Check if the board is fully filled
        if (sudokuBoard.allCellsFilled()) {
            // If the board is fully filled, perform a full solution check
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

                sudokuBoard.printBoard();  // Print the current and solution boards for debugging
            }
        } else {
            // If the board is not fully filled, perform a partial solution check
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

                sudokuBoard.printBoard();  // Print the current and solution boards for debugging
            }
        }
    }

    public void giveHint() {
        int row, col;

        // Find a random empty cell (i.e., one that has a 0 value)
        do {
            row = random.nextInt(9);  // Random row index (0-8)
            col = random.nextInt(9);  // Random column index (0-8)
        } while (sudokuBoard.getCellVal(row, col) != 0);  // Keep looking until we find an empty cell

        // Get the correct value from the solution
        int correctValue = sudokuBoard.getSolutionVal(row, col);

        // Update the current board with the correct value
        sudokuBoard.setCellVal(row, col, correctValue);  // Update model (logic board)
        boardPane.updateCell(row, col, correctValue, true);    // Update view (UI)

    }

    public void saveGame(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Sudoku Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                SudokuFileIO.serializeToFile(sudokuBoard, file);
            } catch (IOException e) {
                e.printStackTrace();  // Hantera fel
            }
        }
    }

    public void loadGame(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Sudoku Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                sudokuBoard = SudokuFileIO.deSerializeFromFile(file);
                boardPane.updateBoard(sudokuBoard);  // Uppdatera vyn med laddat bräde
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();  // Hantera fel
            }
        }
    }
}
