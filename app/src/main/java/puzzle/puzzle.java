package puzzle;

import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


public class puzzle {
    private boolean solveableStatus;
    private Label[] tiles;
    private int n;
    private int blankTileIndex;
    private double blankX0; // koordinat x paling kiri dari tile kosong
    private double blankX1; // koordinat x paling kanan dari tile kosong
    private double blankY0; // koordinat y paling kiri dari tile kosong
    private double blankY1; // koordinat y paling kanan dari tile kosong

    public puzzle(int n ,Label[] tiles){
        this.solveableStatus = false;
        this.tiles = tiles;
        this.n = n;
    }

    public void swapContent(Label a, Label b){
        String x;
        String y;
        x = a.getText();
        y = b.getText();
        
        a.setText(y);
        b.setText(x);
    }

    public void tilesMovement(Pane p){
        p.setOnMousePressed(event -> {  
            if(event.getX()>=this.blankX0+50 && 
                event.getX()<=this.blankX1+50 &&
                event.getY()>=this.blankY0 &&
                event.getY()<=this.blankY1
                ){
            
                swapContent(tiles[blankTileIndex], tiles[blankTileIndex+1]);
                blankTileIndex = blankTileIndex+1;
                blankPositionChange('r');

            } else if(event.getX()>=this.blankX0-50 && 
                event.getX()<=this.blankX1-50 &&
                event.getY()>=this.blankY0 &&
                event.getY()<=this.blankY1
            ){
                swapContent(tiles[blankTileIndex], tiles[blankTileIndex-1]);
                blankTileIndex = blankTileIndex-1;
                blankPositionChange('l');

            } else if(event.getX()>=this.blankX0 && 
                event.getX()<=this.blankX1 &&
                event.getY()>=this.blankY0-50 &&
                event.getY()<=this.blankY1-50
            ){
                swapContent(tiles[blankTileIndex], tiles[blankTileIndex-n]);
                blankTileIndex = blankTileIndex-n;
                blankPositionChange('u');

            } else if(event.getX()>=this.blankX0 && 
                event.getX()<=this.blankX1 &&
                event.getY()>=this.blankY0+50 &&
                event.getY()<=this.blankY1+50
            ){
                swapContent(tiles[blankTileIndex], tiles[blankTileIndex+n]);
                blankTileIndex = blankTileIndex+n;
                blankPositionChange('d');
            }
        });

    }

    public void blankPositionChange(char direction){
        if (direction=='r'){
            blankX0 += 50;
            blankX1 += 50;
        } else if (direction=='l'){
            blankX0 -= 50;
            blankX1 -= 50;
        } else if (direction=='u'){
            blankY0 -= 50;
            blankY1 -= 50;
        } else if (direction=='d'){
            blankY0 += 50;
            blankY1 += 50;
        }
    }

    public void setTilesPosition(int n){
        for(int i =0; i<n*n ; i++){
            int j = i;
            int k = i;
            int m = 0;
            tiles[i].setLayoutX(50*(i%n));
            while(k>=n){
                m+=1;
                k-=n;
            }
            tiles[j].setLayoutY(50*m);
            i = j;
        }
    }

    public void resetPuzzle(Button reset, Pane pane){
        solveableStatus = false;
        pane.getChildren().removeAll(tiles);
        initialContent(n*n);
        pane.getChildren().addAll(tiles);
        setTilesPosition(n);
    }

    public void initialContent(int n){
        int[] arr = new int[n];

        for(int i =0; i<n ; i++){
            arr[i]= i+1;
        }

        while(solveableStatus==false){
            arr = shuffle(arr);
            solveCheck(arr);
        }

        for(int i =0; i<n ; i++){
            tiles[i] = new Label(Integer.toString(arr[i]));
            if(arr[i]==n){
                tiles[i].setText(" ");
                this.blankTileIndex = i;
            }
            tiles[i].setMinSize(50, 50);
            tiles[i].setStyle("-fx-font: 20 arial;");
            tiles[i].setAlignment(Pos.CENTER);
        }
    }

    public void blankPosition(){
        blankX0 = tiles[blankTileIndex].getLayoutX();
        blankX1 = tiles[blankTileIndex].getLayoutX() + 50;
        blankY0 = tiles[blankTileIndex].getLayoutY();
        blankY1 = tiles[blankTileIndex].getLayoutY() + 50;
    }

    public void solveCheck(int[] arr){
        int inversion = 0;
        for(int i =0; i<15 ; i++){
            if(arr[i]>arr[i+1]){
                inversion += 1;
            }
        }
        if(inversion%2 ==0){
            solveableStatus = true;
        }else{
            solveableStatus = false;
        }
    }

    public boolean finishedCheck(){
        for(int i = 0; i<16; i++){
            int check = Integer.parseInt(tiles[i].getText());
            if(check != i+1){
                return false;
            }
        } return true;
    }

    public int[] shuffle(int[] arr){
        Random rand = new Random();
        for(int i = 0; i < arr.length-1; i++) {
            int index = rand.nextInt(arr.length);
            int tmp = arr[i];
            arr[i] = arr[index];
            arr[index] = tmp;
        }

        return arr;
    }

}
