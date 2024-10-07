package kth.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import kth.controller.SudokuController;
import kth.model.SudokuUtilities;

public class BoardPane extends GridPane {
    private Label[][] numberTiles = new Label[9][9]; // UI elements
    private SudokuController controller;
    private VBox controlPanel;

    public BoardPane(String difficulty) {
        initializeBoard(difficulty);  // Set up the board UI
        controlPanel = initializeControlPanel();  // Create the control panel with buttons 1-9 and clear button
    }

    // Allow setting the controller from outside
    public void setController(SudokuController controller) {
        this.controller = controller;
    }

    // Skapa rutnätet med svårighetsgraden från SudokuUtilities
    private void initializeBoard(String difficulty) {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        // Välj utgångsläget baserat på svårighetsgraden
        int[][] puzzle = null;
        switch (difficulty) {
            case "easy":
                puzzle = SudokuUtilities.getEasyPuzzle();
                break;
            case "medium":
                puzzle = SudokuUtilities.getMediumPuzzle();
                break;
            case "hard":
                puzzle = SudokuUtilities.getHardPuzzle();
                break;
        }

        // Skapa rutnätet baserat på det valda utgångsläget
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = puzzle[row][col];
                Label tile = new Label(value == 0 ? "" : String.valueOf(value));
                tile.setPrefSize(50, 50);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                final int finalRow = row;
                final int finalCol = col;

                if (value != 0) {
                    tile.setStyle("-fx-font-weight: bold; -fx-background-color: lightgray;");
                    tile.setMouseTransparent(true);  // Gör rutan oåtkomlig
                } else {
                    // Event handler för redigerbara rutor
                    tile.setOnMouseClicked(event -> {
                        if (controller.getSelectedNumber() != 0) {
                            tile.setText(String.valueOf(controller.getSelectedNumber()));
                            controller.handleCellClick(finalRow, finalCol);
                        }
                    });
                }

                numberTiles[row][col] = tile;
                this.add(tile, col, row);  // Lägg till tile i GridPane
            }
        }
    }

    // Return control panel for external usage
    public VBox getControlPanel() {
        return controlPanel;
    }

    // Create the control panel with number buttons and clear button
    private VBox initializeControlPanel() {
        VBox panel = new VBox(10);  // Vertical layout with spacing
        for (int i = 1; i <= 9; i++) {
            Button numButton = new Button(String.valueOf(i));
            numButton.setPrefSize(50, 50);  // Button size
            int finalI = i;
            numButton.setOnAction(event -> {
                controller.setSelectedNumber(finalI);  // Set the selected number in the controller
            });
            panel.getChildren().add(numButton);
        }

        // Clear button to reset the cell
        Button clearButton = new Button("C");
        clearButton.setPrefSize(50, 50);
        clearButton.setOnAction(event -> controller.setSelectedNumber(0));  // Clear the selection
        panel.getChildren().add(clearButton);

        return panel;
    }

    // Update the cell at (row, col) with a value from the model
    public void updateCell(int row, int col, int value) {
        if (value != 0) {
            numberTiles[row][col].setText(String.valueOf(value));
        } else {
            numberTiles[row][col].setText("");
        }
    }
}
