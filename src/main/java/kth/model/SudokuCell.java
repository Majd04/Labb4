package kth.model;

public class SudokuCell {
    private int value;
    private boolean editable;

    public SudokuCell(int value, boolean editable) {
        this.value = value;
        this.editable = editable;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (editable) {
            this.value = value;
        } else {
            throw new IllegalStateException("Cell is not editable");
        }
    }

    public boolean isEditable() {
        return editable;
    }


}
