package Model;

import java.util.ArrayList;

public class Player {
    // l = white, d = Black
    char color;
    // Array list of pieces the player has left and what they have captured
    ArrayList<Piece> capturedPieces = new ArrayList<>();
    ArrayList<Piece> currentPieces = new ArrayList<>();

    //Constructor
    public Player(char color ){
        this.color = color;
    }

    public void resetPlayer(){
        capturedPieces.clear();
        currentPieces.clear();
    }



}
