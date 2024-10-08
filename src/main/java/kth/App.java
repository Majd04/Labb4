package kth;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kth.model.*;
import kth.view.BoardPane;
import kth.controller.SudokuController;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Skapa modellen
        SudokuBoard sudokuBoard = new SudokuBoard();

        // Välj en svårighetsgrad (easy, medium, hard)
        String difficulty = "easy";  // Här kan du till exempel fråga användaren

        // Skapa vyn och ange svårighetsgraden
        BoardPane boardPane = new BoardPane(difficulty);

        // Skapa kontrollern och länka modellen och vyn
        SudokuController controller = new SudokuController(sudokuBoard, boardPane);
        boardPane.setController(controller);

        // 3. Lägg till knapparna till vänster
        VBox leftButtons = boardPane.createLeftButtons(controller);

        // 4. Skapa en menyrad (MenuBar) överst
        MenuBar menuBar = boardPane.createMenuBar();

        // Skapa layout och scen
        BorderPane root = new BorderPane();
        root.setTop(menuBar);  // Lägg menyraden överst
        root.setLeft(leftButtons);  // Lägg knapparna till vänster
        root.setCenter(boardPane);  // Lägg brädet i mitten
        root.setRight(boardPane.getControlPanel());  // Lägg kontrollpanelen till höger

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();
    }
    //testar git push

    public static void main(String[] args) {
        launch(args);  // Startar JavaFX-applikationen
    }
}





