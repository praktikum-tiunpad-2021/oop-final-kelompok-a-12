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
        int n = 4; // besar n atau banyak sisi puzzle bisa diganti disini
                   // namun belum tersedia perubahan dengan GUI untuk user
        Label[] tiles = new Label[n*n];
        Pane pane = new Pane();
        Button reset = new Button("Reset Puzzle");
        puzzle fifPuz = new puzzle(n,tiles, pane);
        
        fifPuz.initialContent();            // mengisi angka
        pane.getChildren().addAll(tiles);   // meletakkan label tiles pada pane
        pane.getChildren().add(reset);      // menambahkan tombol reset di bawahnya
        fifPuz.setTilesPosition();          // set posisi dari tiles

        reset.setStyle("-fx-background-color: #4287f5;-fx-font: 15 arial;");
        reset.setMinWidth(n*100);           // set lebar minimal tombol reset
        reset.setMinHeight(60);             // set tinggi minimal tombol reset
        reset.setLayoutY(n*100);            // set letak koordinat y tombol reset

        fifPuz.setBlankPosition();          // set blank tile position
        fifPuz.tilesMovement();             // enable movement tile dari klik mouse
        
        // anonymouse class untuk aksi saat tombol reset ditekan
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                fifPuz.resetPuzzle(reset);
            };
        });

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("15 Puzzle");
        primaryStage.show();
    }
}
