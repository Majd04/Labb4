package kth.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import kth.controller.SudokuController;
import kth.model.SudokuBoard;
import kth.model.SudokuUtilities;

/**
 * The {@code BoardPane} class represents the UI for the Sudoku board, managing both
 * the visual grid and user interactions with the puzzle. This pane also initializes
 * the control panel and handles updates to the board.
 *
 * @author Majd & Marvin
 * @version 1.0
 */
public class BoardPane extends GridPane {
    private Label[][] numberTiles = new Label[9][9];
    private SudokuController controller;
    private VBox controlPanel;
    private SudokuBoard sudokuBoard;
    private int[][] puzzle;
    private int[][] initialPuzzle;

    /**
     * Constructs a new {@code BoardPane} object with a given Sudoku puzzle.
     * This constructor initializes the board and the control panel with buttons.
     *
     * @param puzzle the initial Sudoku puzzle in a 2D integer array format.
     */
    public BoardPane(int[][] puzzle) {
        this.puzzle = puzzle;
        initializeBoard(puzzle);
        controlPanel = initializeControlPanel();
    }

    /**
     * Sets the controller to be used by the {@code BoardPane}. The controller manages
     * interactions between the view and the logic of the game.
     *
     * @param controller the {@code SudokuController} instance that handles user interactions.
     */
    public void setController(SudokuController controller) {
        this.controller = controller;
    }

    /**
     * Initializes the Sudoku board by creating a grid of tiles based on the given puzzle.
     * It organizes the tiles into 3x3 sections and sets up event handlers for user input.
     *
     * @param puzzle the 2D integer array representing the Sudoku puzzle to display.
     */
    public void initializeBoard(int[][] puzzle) {
        this.getChildren().clear();
        this.initialPuzzle = puzzle;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                numberTiles[row][col] = null;
            }
        }

        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        GridPane root = new GridPane();
        root.setPadding(new Insets(35, 20, 0, 10));

        for (int sectionRow = 0; sectionRow < 3; sectionRow++) {
            for (int sectionCol = 0; sectionCol < 3; sectionCol++) {
                GridPane section = new GridPane();
                section.setStyle("-fx-border-color: black; -fx-border-width: 3px;");

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
                            tile.setMouseTransparent(true);
                        } else {
                            final int finalRow = actualRow;
                            final int finalCol = actualCol;
                            tile.setOnMouseClicked(event -> {
                                if (controller.getSelectedNumber() != 0) {
                                    tile.setText(String.valueOf(controller.getSelectedNumber()));
                                    controller.handleCellClick(finalRow, finalCol);
                                }
                            });
                        }

                        numberTiles[actualRow][actualCol] = tile;
                        section.add(tile, col, row);
                    }
                }

                root.add(section, sectionCol, sectionRow);
            }
        }

        this.getChildren().add(root);
    }

    /**
     * Returns the control panel for external use, which contains the number buttons
     * and the clear button.
     *
     * @return the control panel {@code VBox}.
     */
    public VBox getControlPanel() {
        return controlPanel;
    }

    /**
     * Initializes the control panel which contains buttons numbered 1-9 for user input,
     * as well as a clear button to reset a selected cell.
     *
     * @return the control panel {@code VBox}.
     */
    private VBox initializeControlPanel() {
        VBox panel = new VBox(10);
        for (int i = 1; i <= 9; i++) {
            Button numButton = new Button(String.valueOf(i));
            numButton.setPrefSize(50, 50);
            int finalI = i;
            numButton.setOnAction(event -> controller.setSelectedNumber(finalI));
            panel.getChildren().add(numButton);
        }

        Button clearButton = new Button("C");
        clearButton.setPrefSize(50, 50);
        clearButton.setOnAction(event -> controller.clearSelectedCell());
        panel.getChildren().add(clearButton);

        return panel;
    }

    /**
     * Creates a {@code VBox} with "Check" and "Hint" buttons, used to check the
     * solution of the puzzle or give a hint to the player.
     *
     * @param controller the {@code SudokuController} that handles the button actions.
     * @return a {@code VBox} containing the "Check" and "Hint" buttons.
     */
    public VBox createLeftButtons(SudokuController controller) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER_LEFT);

        Button checkButton = new Button("Check");
        checkButton.setOnAction(event -> controller.checkSolution());

        Button hintButton = new Button("Hint");
        hintButton.setOnAction(event -> controller.giveHint());

        vbox.getChildren().addAll(checkButton, hintButton);
        return vbox;
    }

    /**
     * Creates a {@code MenuBar} with options for file operations (load, save, exit),
     * game difficulty levels, and help instructions.
     *
     * @param stage the current window {@code Stage} for file dialogs.
     * @return the {@code MenuBar} with file, game, and help menus.
     */
    public MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem loadGame = new MenuItem("Load game");
        MenuItem saveGame = new MenuItem("Save game");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(loadGame, saveGame, exit);

        loadGame.setOnAction(event -> controller.loadGame(stage));
        saveGame.setOnAction(event -> controller.saveGame(stage));
        exit.setOnAction(event -> Platform.exit());

        Menu gameMenu = new Menu("Game");
        MenuItem newGame = new MenuItem("New game");
        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem hard = new MenuItem("Hard");

        newGame.setOnAction(event -> controller.setDifficulty(controller.getDifficulty()));
        easy.setOnAction(event -> controller.setDifficulty(SudokuUtilities.SudokuLevel.EASY));
        medium.setOnAction(event -> controller.setDifficulty(SudokuUtilities.SudokuLevel.MEDIUM));
        hard.setOnAction(event -> controller.setDifficulty(SudokuUtilities.SudokuLevel.HARD));

        gameMenu.getItems().addAll(newGame, easy, medium, hard);

        Menu helpMenu = new Menu("Help");
        MenuItem restart = new MenuItem("Restart game");
        MenuItem check = new MenuItem("Check if game is solved");
        MenuItem about = new MenuItem("About");

        about.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("How to play sudoku");
            alert.setContentText("Sudoku is a puzzle where the goal is to fill the entire " +
                    "grid with numbers 1 - 9. Each row, column, and subgrid cannot contain " +
                    "the same number more than once.");
            alert.showAndWait();
        });

        check.setOnAction(event -> controller.checkSolution());
        restart.setOnAction(event -> controller.restartGame());

        helpMenu.getItems().addAll(restart, check, about);
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);

        return menuBar;
    }

    /**
     * Updates the value of the tile at the specified row and column. This method
     * can also apply special styles to hint tiles, which lock the tile and change its appearance.
     *
     * @param row the row index of the tile to update.
     * @param col the column index of the tile to update.
     * @param value the value to place in the tile (0 for clearing the tile).
     * @param isHint whether the tile is a hint (applies special styles and locks it).
     */
    public void updateCell(int row, int col, int value, boolean isHint) {
        Platform.runLater(() -> {
            if (numberTiles[row][col] != null) {
                if (value != 0) {
                    numberTiles[row][col].setText(String.valueOf(value));

                    if (isHint) {
                        numberTiles[row][col].setDisable(true);
                        numberTiles[row][col].setStyle("-fx-font-weight: bold; -fx-background-color: lightblue;" +
                                "-fx-text-fill: black; -fx-border-color: black; -fx-alignment: center;");
                    } else {
                        numberTiles[row][col].setDisable(false);
                    }
                } else {
                    numberTiles[row][col].setText("");
                    numberTiles[row][col].setDisable(false);
                    numberTiles[row][col].setStyle("-fx-border-color: black; -fx-alignment: center;");
                }
            }
        });
    }

    /**
     * Updates the entire board by fetching the current values from the model and
     * setting them in the corresponding tiles in the view.
     *
     * @param board the {@code SudokuBoard} model representing the current game state.
     */
    public void updateBoard(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board.getCellVal(row, col);
                updateCell(row, col, value, false);
            }
        }
    }
}
