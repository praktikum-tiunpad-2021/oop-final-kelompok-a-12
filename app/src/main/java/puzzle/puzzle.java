package puzzle;

import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class puzzle {
    private boolean solved;
    private int blankTileIndex;
    private double blankX0;
    private double blankX1;
    private double blankY0;
    private double blankY1;

    public puzzle(){
        this.solved = false;
    }

    public void swapContent(Label a, Label b){
        String x;
        String y;
        x = a.getText();
        y = b.getText();
        
        a.setText(y);
        b.setText(x);
    }

    public void tilesMovement(Pane p, Label[] l){
        p.setOnMousePressed(event -> {  
            if(event.getX()>=this.blankX0+50 && 
                event.getX()<=this.blankX1+50 &&
                event.getY()>=this.blankY0 &&
                event.getY()<=this.blankY1
                ){
            
                swapContent(l[blankTileIndex], l[blankTileIndex+1]);
                blankTileIndex = blankTileIndex+1;
                blankPositionChange('r');

            } else if(event.getX()>=this.blankX0-50 && 
                event.getX()<=this.blankX1-50 &&
                event.getY()>=this.blankY0 &&
                event.getY()<=this.blankY1
            ){
                swapContent(l[blankTileIndex], l[blankTileIndex-1]);
                blankTileIndex = blankTileIndex-1;
                blankPositionChange('l');

            } else if(event.getX()>=this.blankX0 && 
                event.getX()<=this.blankX1 &&
                event.getY()>=this.blankY0-50 &&
                event.getY()<=this.blankY1-50
            ){
                swapContent(l[blankTileIndex], l[blankTileIndex-4]);
                blankTileIndex = blankTileIndex-4;
                blankPositionChange('u');

            } else if(event.getX()>=this.blankX0 && 
                event.getX()<=this.blankX1 &&
                event.getY()>=this.blankY0+50 &&
                event.getY()<=this.blankY1+50
            ){
                swapContent(l[blankTileIndex], l[blankTileIndex+4]);
                blankTileIndex = blankTileIndex+4;
                blankPositionChange('d');
            }
        });

    }

    public void blankPositionChange(char dir){
        if (dir=='r'){
            blankX0 += 50;
            blankX1 += 50;
        } else if (dir=='l'){
            blankX0 -= 50;
            blankX1 -= 50;
        } else if (dir=='u'){
            blankY0 -= 50;
            blankY1 -= 50;
        } else if (dir=='d'){
            blankY0 += 50;
            blankY1 += 50;
        }
    }

    public void initialContent(int n, Label[] l){
        int[] arr = new int[n];

        for(int i =0; i<n ; i++){
            arr[i]= i+1;
        }

        while(solved==false){
            arr = shuffle(arr);
            solveCheck(arr);
        }

        for(int i =0; i<n ; i++){
            l[i] = new Label(Integer.toString(arr[i]));
            if(arr[i]==n){
                l[i].setText(" ");
                this.blankTileIndex = i;
            }
            l[i].setMinSize(50, 50);
            l[i].setStyle("-fx-font: 20 arial;");
            l[i].setAlignment(Pos.CENTER);
        }
    }

    public void blankPosition(Label[] l){
        blankX0 = l[blankTileIndex].getLayoutX();
        blankX1 = l[blankTileIndex].getLayoutX() + 50;
        blankY0 = l[blankTileIndex].getLayoutY();
        blankY1 = l[blankTileIndex].getLayoutY() + 50;
    }

    public void solveCheck(int[] arr){
        int inv = 0;
        for(int i =0; i<15 ; i++){
            if(arr[i]>arr[i+1]){
                inv+=1;
            }
        }
        if(inv%2 ==0){
            this.solved = true;
        }else{
            this.solved = false;
        }
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
