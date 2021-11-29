/*
 * Main Class
 */
package puzzle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage)throws Exception{
        int n = 4;
        Label[] tiles = new Label[n*n];
        Pane pane = new Pane();
        Button reset = new Button("Reset Puzzle");
        puzzle fifPuz = new puzzle(n,tiles);
        
        fifPuz.initialContent(n*n);
        pane.getChildren().addAll(tiles);
        pane.getChildren().add(reset);
        fifPuz.setTilesPosition(n);

        reset.setMinWidth(200);
        reset.setLayoutY(200);

        fifPuz.setBlankPosition();
        fifPuz.tilesMovement(pane);
        
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                fifPuz.resetPuzzle(reset, pane);
            };
        });

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("15 Puzzle");
        primaryStage.show();
    }
}
