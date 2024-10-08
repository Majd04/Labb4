package kth.controller;

import javafx.scene.control.Alert;
import kth.model.SudokuBoard;
import kth.view.BoardPane;

public class SudokuController {
    private SudokuBoard sudokuBoard;
    private BoardPane boardPane;
    private int selectedNumber = 0;

    public SudokuController(SudokuBoard board, BoardPane view) {
        this.sudokuBoard = board;
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
        if (selectedNumber != 0) {
            sudokuBoard.setCellVal(row, col, selectedNumber);  // Update model
            boardPane.updateCell(row, col, selectedNumber);    // Update view
        }
    }

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    // Metod för att kontrollera om lösningen är korrekt
    public void checkSolution() {
        // Kontrollera om Sudoku-brädet är löst
        if (sudokuBoard.isSolved()) {
            // Visa ett popup-meddelande som säger att spelet är löst
            Alert solvedAlert = new Alert(Alert.AlertType.INFORMATION);
            solvedAlert.setTitle("Sudoku Solved");
            solvedAlert.setHeaderText(null);
            solvedAlert.setContentText("Congratulations! You have solved the Sudoku puzzle!");
            solvedAlert.showAndWait();
        } else {
            // Visa ett popup-meddelande som säger att lösningen är felaktig
            Alert notSolvedAlert = new Alert(Alert.AlertType.ERROR);
            notSolvedAlert.setTitle("Sudoku Not Solved");
            notSolvedAlert.setHeaderText(null);
            notSolvedAlert.setContentText("There are mistakes in the Sudoku puzzle. Please try again.");
            notSolvedAlert.showAndWait();
        }
    }

    // Metod för att ge en ledtråd till användaren
    public void giveHint() {
        // Här kan du implementera logiken för att ge en ledtråd
        System.out.println("Giving a hint...");
        // Uppdatera brädet med ledtråden
    }
}
