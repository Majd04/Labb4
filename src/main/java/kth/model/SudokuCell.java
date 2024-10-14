package kth.model;

import java.io.Serializable;

/**
 * Represents a single cell in the Sudoku board. Each cell contains a value
 * and could be extended to include other attributes such as whether it is editable.
 *
 * @author Majd & Marvin
 * @version 1.0
 */
public class SudokuCell implements Serializable {
    private int value;
    private boolean editable;

    /**
     * Constructs a SudokuCell with the given value and editability.
     *
     * @param value the value of the cell (0 represents an empty cell).
     * @param editable whether the cell is editable (i.e., not part of the original puzzle).
     */
    public SudokuCell(int value, boolean editable) {
        this.value = value;
        this.editable = editable;
    }

    /**
     * Gets the value of the cell.
     *
     * @return the value of the cell (0 for empty).
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the cell.
     *
     * @param value the value to set in the cell.
     */
    public void setValue(int value) {
        if (editable) {
            this.value = value;
        }
    }

    /**
     * Returns whether the cell is editable.
     *
     * @return true if the cell is editable, false otherwise.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets the editability of the cell.
     *
     * @param editable true if the cell should be editable, false otherwise.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
