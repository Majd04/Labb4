package kth.model;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoard {
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


/*

    public boolean isSolved() {
        if (!allCellsFilled()) {
            return false; // Returnera false om spelet inte är ifyllt än
        }
        return checkRows() && checkCols() && checkBoxes();
    }

    private boolean checkRows(){
        for (int row = 0; row < 9; row++) {
            Set<Integer> seen = new HashSet<>();
            for (int col = 0; col < 9; col++) {
                int value = board[row][col];
                if (value < 1 || value > 9 || !seen.add(value)) {
                    return false; // Ogiltigt om vi ser samma siffra mer än en gång eller en ogiltig siffra
                }
            }
        }
        return true;
    }

    private boolean checkCols(){
        for (int col = 0; col < 9; col++) {
            Set<Integer> seen = new HashSet<>();
            for (int row = 0; row < 9; row++) {
                int value = board[row][col];
                if (value < 1 || value > 9 || !seen.add(value)) {
                    return false; // Ogiltigt om vi ser samma siffra mer än en gång eller en ogiltig siffra
                }
            }
        }
        return true;
    }

    private boolean checkBoxes(){
        for (int boxRow = 0; boxRow < 9; boxRow += 3) {
            for (int boxCol = 0; boxCol < 9; boxCol += 3) {
                if (!checkBox(boxRow, boxCol)) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean checkBox(int boxRow, int boxCol) {
        Set<Integer> seen = new HashSet<>();
        for (int row = boxRow; row < boxRow + 3; row++) {
            for (int col = boxCol; col < boxCol + 3; col++) {
                int value = board[row][col];
                if (value < 1 || value > 9 || !seen.add(value)) {
                    return false; // Ogiltigt om vi ser samma siffra mer än en gång eller en ogiltig siffra
                }
            }
        }
        return true;
    }
*/

    public boolean allCellsFilled() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    return false; // Det finns tomma celler
                }
            }
        }
        return true; // Alla celler är ifyllda
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
}
