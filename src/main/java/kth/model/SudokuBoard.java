package kth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SudokuBoard implements Serializable {
    private int[][] board;
    private int[][] boardSolution;

    public SudokuBoard(int[][] board, int[][] boardSolution) {
        //board = new int[9][9];
        this.board = board;
        this.boardSolution = boardSolution;
    }

    public void setCellVal(int row, int col, int val) {
        board[row][col] = val;
    }

    public int getCellVal(int row, int col) {
        return board[row][col];
    }

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

    public boolean checkPartialSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int currentVal = board[row][col];  // Get the value from the current board
                if (currentVal != 0) {  // Only check non-empty cells
                    int correctVal = getSolutionVal(row, col);  // Get the correct solution value
                    if (currentVal != correctVal) {
                        System.out.println("Incorrect value at [" + row + "][" + col + "]: " +
                                "Entered = " + currentVal + ", Correct = " + correctVal);
                        return false;  // Return false if any filled value is incorrect
                    }
                }
            }
        }
        return true;  // Return true if all filled values are correct
    }


    public boolean allCellsFilled() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {  // Check for empty cells
                    return false;  // Return false if there is at least one empty cell
                }
            }
        }
        return true;  // All cells are filled
    }

    public void printBoard() {
/*        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }*/

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

    public int getSolutionVal(int row, int col) {
        int solutionVal = boardSolution[row][col];
        System.out.println("Solution value at [" + row + "][" + col + "] = " + solutionVal);
        return solutionVal;
    }
}
