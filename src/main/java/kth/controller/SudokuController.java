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
}
