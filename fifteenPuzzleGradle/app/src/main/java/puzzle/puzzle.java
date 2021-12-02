package puzzle;

import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


public class puzzle {
    private Pane pane;
    private Label[] tiles;
    private Button green;
    private boolean solveableStatus;
    private int n;
    private int blankTileIndex;
    private double blankX0; // koordinat x paling kiri dari tile kosong
    private double blankX1; // koordinat x paling kanan dari tile kosong
    private double blankY0; // koordinat y paling kiri dari tile kosong
    private double blankY1; // koordinat y paling kanan dari tile kosong
    private double batasKananDanBawah;

    public puzzle(int n ,Label[] tiles, Pane pane){
        this.solveableStatus = false;
        this.green = new Button();
        this.pane = pane;
        this.tiles = tiles;
        this.n = n;
        this.batasKananDanBawah = 100 * n;
    }

    // swap isi antara dua tiles
    public void swapContent(Label a, Label b){
        String x;
        String y;
        x = a.getText();
        y = b.getText();
        
        a.setText(y);
        b.setText(x);
    }

    // logika dalam klik dari mouse beserta listener koordinat mouse
    public void tilesMovement(){
        pane.setOnMousePressed(event -> {  
            //jika tiles yang diklik berada di kanan tiles blank
            if(event.getX()>=blankX0+100 && 
                event.getX()<=blankX1+100 &&
                event.getX()<=batasKananDanBawah &&
                event.getY()>=blankY0 &&
                event.getY()<=blankY1
                ){
            
                swapContent(tiles[blankTileIndex], tiles[blankTileIndex+1]);
                blankTileIndex = blankTileIndex+1;
                blankPositionChange('r');
                finishedCheck();
            
            //jika tiles yang diklik berada di kiri tiles blank
            } else if(event.getX()>=blankX0-100 && 
                event.getX()<=blankX1-100 &&
                event.getY()>=blankY0 &&
                event.getY()<=blankY1
            ){
                swapContent(tiles[blankTileIndex], tiles[blankTileIndex-1]);
                blankTileIndex = blankTileIndex-1;
                blankPositionChange('l');
                finishedCheck();

            //jika tiles yang diklik berada di atas tiles blank
            } else if(event.getX()>=blankX0 && 
                event.getX()<=blankX1 &&
                event.getY()>=blankY0-100 &&
                event.getY()<=blankY1-100
            ){
                swapContent(tiles[blankTileIndex], tiles[blankTileIndex-n]);
                blankTileIndex = blankTileIndex-n;
                blankPositionChange('u');
                finishedCheck();

            //jika tiles yang diklik berada di bawah tiles blank
            } else if(event.getX()>=blankX0 && 
                event.getX()<=blankX1 &&
                event.getY()>=blankY0+100 &&
                event.getY()<=blankY1+100 &&
                event.getY()<=batasKananDanBawah
            ){
                swapContent(tiles[blankTileIndex], tiles[blankTileIndex+n]);
                blankTileIndex = blankTileIndex+n;
                blankPositionChange('d');
                finishedCheck();
            }
        });
    }

    // pergeseran koordinat tile blank tergantung arah pindah
    public void blankPositionChange(char direction){
        if (direction=='r'){
            blankX0 += 100;
            blankX1 += 100;
        } else if (direction=='l'){
            blankX0 -= 100;
            blankX1 -= 100;
        } else if (direction=='u'){
            blankY0 -= 100;
            blankY1 -= 100;
        } else if (direction=='d'){
            blankY0 += 100;
            blankY1 += 100;
        }
    }

    // set lokasi dari tilse yang berupa array of Label
    public void setTilesPosition(){
        for(int i =0; i<n*n ; i++){
            int j = i;
            int k = i;
            int m = 0;
            tiles[i].setLayoutX(100 * (i%n));
            while(k>=n){
                m+=1;
                k-=n;
            }
            tiles[j].setLayoutY(100 * m);
            i = j;
        }
    }

    // dijalankan ketika tombol reset ditekan
    public void resetPuzzle(Button reset){
        solveableStatus = false;
        pane.getChildren().removeAll(tiles);
        initialContent();
        pane.getChildren().addAll(tiles);
        setTilesPosition();
        setBlankPosition();
        pane.getChildren().remove(green);
    }

    // melakukan pengisian angka acak pada label tiles
    public void initialContent(){
        int[] arr = new int[n*n];

        for(int i =0; i<n*n ; i++){
            arr[i]= i+1;
        }

        // akan terus diacak sampai puzzle mungkin untuk diselesaikan
        while(solveableStatus==false){
            arr = shuffle(arr);
            solveCheck(arr);
        }

        // input angka hasil acak menjadi teks pada label
        for(int i =0; i<n*n ; i++){
            tiles[i] = new Label(Integer.toString(arr[i]));
            if(arr[i]==n*n){
                tiles[i].setText(" ");
                blankTileIndex = i;
            }
            tiles[i].setMinSize(100, 100);
            tiles[i].setStyle("-fx-font: 30 arial;");
            tiles[i].setAlignment(Pos.CENTER);
        }
    }

    // melakukan set untuk posisi tile blank
    public void setBlankPosition(){
        blankX0 = tiles[blankTileIndex].getLayoutX();
        blankX1 = tiles[blankTileIndex].getLayoutX() + 100;
        blankY0 = tiles[blankTileIndex].getLayoutY();
        blankY1 = tiles[blankTileIndex].getLayoutY() + 100;
    }

    // melakukan cek apakah angka puzzle mungkin untuk diselesaikan 
    public void solveCheck(int[] arr){
        int inversion = 0;
        int blankIndex = 0;
        int blankPos = 0;
       
        // menghitung jumlah inversi
        for(int i = n*n-1; i>0 ; i--){
            int j = i-1;
            while(j>=0){
                if(arr[i]<arr[j] && arr[i] != n*n && arr[j] != n*n){
                    inversion += 1;
                }    
                j--;
            }
        }

        // mencari posisi index pada array dimana berisi angka n*n (blank)
        for(int i =0; i<n*n ; i++){
            if(arr[i] == n*n){
                blankIndex = i;
            }
        }

        // mencari dimana posisi blank dihitung dari baris paling bawah
        int i = n*n-1;
        while(i>=-1){
            i-=n;
            if(blankIndex>i) blankPos+=1;
        }

        if(n%2 == 1){ // jika n atau sisi puzzle ganjil
            if(inversion%2 == 0){
                solveableStatus = true;
            } else{
                solveableStatus = false;
            }
        } else if(n%2 == 0){ // jika n atau sisi puzzle genap
            if(inversion%2 ==0 && blankPos%2 == 1){
                solveableStatus = true;
            } else if(inversion%2 ==1 && blankPos%2 == 0){
                solveableStatus = true;
            }else{
                solveableStatus = false;
            }
        }
    }

    // cek apakah puzzle telah terselesaikan 
    public void finishedCheck(){
        int[] arr = new int[n*n];
        
        // mengubah teks pada label (tiles) ke array 1D
        for(int i = 0; i<n*n; i++){
            int check;
            if(tiles[i].getText()!= " "){
                check = Integer.parseInt(tiles[i].getText());
            } else check = n*n;
            arr[i] = check;
        } 

        // cek apakah array berisi angka yang terurut
        int i = 0;
        while(i < n*n-1){
            if(arr[i] != i+1) break;
            if(i==n*n-2 && arr[i]== i+1) finishedView(); // angka telah terurut
            i++;
        }
    }

    // memberikan view button hijau sebagai penanda puzzle telah selesai tersusun
    public void finishedView(){
        green.setStyle("-fx-background-color: #00ff00;-fx-font: 20 arial;");
        green.setText("Selesai!");
        green.setOpacity(1.0);
        pane.getChildren().add(green);
        green.setMinHeight(n*100);
        green.setMinWidth(n*100);
        green.setLayoutX(0);
    }

    // melakukan shuffle pada array 1D
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
