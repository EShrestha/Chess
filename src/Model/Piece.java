package Model;

import BoardStuff.Tile;

public abstract class Piece {
    protected String name;
    protected char shortName;
    protected PieceColor pieceColor;
    protected Tile currentTile;

    public Piece(PieceColor pieceColor){
        this.pieceColor = pieceColor;
    }

    public String getName() {
        return name;
    }

    public char getShortName() {
        return shortName;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "name='" + name + '\'' +
                ", pieceColor=" + pieceColor +
                ", currentTile=" + currentTile +
                '}';
    }
}
