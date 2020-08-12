package Model;

import java.util.ArrayList;

public class Player {
    // l = white, d = Black
    char color;
    // Array list of pieces the player has left and what they have captured
    private ArrayList<Piece> capturedPieces = new ArrayList<>();
    private ArrayList<Piece> currentPieces = new ArrayList<>();

    //Constructor
    public Player(char color ){
        this.color = color;
    }

    public void resetPlayer(){
        capturedPieces.clear();
        currentPieces.clear();
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public void addCapturedPiece(Piece capturedPiece) {
        this.capturedPieces.add(capturedPiece);
    }

    public ArrayList<Piece> getCurrentPieces() {
        return currentPieces;
    }

    public void setCurrentPieces(Piece currentPiece) {
        this.currentPieces.add(currentPiece);
    }
}
