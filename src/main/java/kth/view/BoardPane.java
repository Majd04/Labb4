package kth.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
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

    private void initializeBoard(String difficulty) {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        // Huvud-GridPane för hela Sudoku-brädet
        GridPane root = new GridPane();
        root.setGridLinesVisible(false);  // Vi tar bort vanliga gridlines

        root.setPadding(new Insets(35, 20, 0, 10));

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

        // Skapa 3x3-sektionerna
        for (int sectionRow = 0; sectionRow < 3; sectionRow++) {
            for (int sectionCol = 0; sectionCol < 3; sectionCol++) {

                // Skapa en separat GridPane för varje 3x3-sektion
                GridPane section = new GridPane();
                section.setStyle("-fx-border-color: black; -fx-border-width: 3px;");

                // Fyll varje 3x3-sektion med celler
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        int actualRow = sectionRow * 3 + row;
                        int actualCol = sectionCol * 3 + col;
                        int value = puzzle[actualRow][actualCol];

                        Label tile = new Label(value == 0 ? "" : String.valueOf(value));
                        tile.setPrefSize(50, 50);
                        tile.setFont(font);
                        tile.setAlignment(Pos.CENTER);
                        tile.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                        if (value != 0) {
                            tile.setStyle(tile.getStyle() + "-fx-font-weight: bold; -fx-background-color: lightgray;");
                            tile.setMouseTransparent(true);  // Gör rutan oåtkomlig
                        } else {
                            final int finalRow = actualRow;
                            final int finalCol = actualCol;

                            // Event handler för redigerbara rutor
                            tile.setOnMouseClicked(event -> {
                                if (controller.getSelectedNumber() != 0) {
                                    tile.setText(String.valueOf(controller.getSelectedNumber()));
                                    controller.handleCellClick(finalRow, finalCol);
                                }
                            });
                        }

                        // Lägg till cellen i 3x3-sektionen
                        section.add(tile, col, row);
                    }
                }

                // Lägg till 3x3-sektionen i huvud-GridPane
                root.add(section, sectionCol, sectionRow);
            }
        }

        this.getChildren().add(root);  // Lägg till hela GridPane till scenen
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

    // Metod för att skapa knapparna "Check" och "Hint" till vänster
    public VBox createLeftButtons(SudokuController controller) {
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Avstånd mellan knapparna
        vbox.setPadding(new javafx.geometry.Insets(10)); // Marginal runt knapparna

        // Centrera knapparna i VBox
        vbox.setAlignment(Pos.CENTER_LEFT);  // Centrera dem vertikalt till vänster

        Button checkButton = new Button("Check");
        checkButton.setOnAction(event -> controller.checkSolution());

        Button hintButton = new Button("Hint");
        hintButton.setOnAction(event -> controller.giveHint());

        vbox.getChildren().addAll(checkButton, hintButton); // Lägg till knappar i VBox
        return vbox;
    }

    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File-meny
        Menu fileMenu = new Menu("File");
        MenuItem loadGame = new MenuItem("Load game");
        MenuItem saveGame = new MenuItem("Save game");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(loadGame, saveGame, exit);

        // Game-meny
        Menu gameMenu = new Menu("Game");
        MenuItem newGame = new MenuItem("New game");
        MenuItem resetGame = new MenuItem("Reset game");
        gameMenu.getItems().addAll(newGame, resetGame);

        // Help-meny
        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About");
        helpMenu.getItems().add(about);

        // Lägg till menyn i menyraden
        menuBar.getMenus().add(fileMenu);

        // Här kan du lägga till fler menyer, t.ex. "Help"
        return menuBar;
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
