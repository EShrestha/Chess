package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.RankToRank;
import BoardStuff.Tile;

import java.util.List;

public abstract class Piece {
    protected String name;
    protected char shortName;
    protected PieceColor pieceColor;
    protected Tile currentTile;
    protected boolean isFirstMove = true;
    protected char shortColor;
    RankToRank rankToRank = new RankToRank();

    public Piece(PieceColor pieceColor){
        this.pieceColor = pieceColor;
    }
    public Piece(){}

    public String getName() {
        return name;
    }

    public char getShortName() {
        return shortName;
    }

    public char getShortColor(){
        return pieceColor == PieceColor.LIGHT ? 'l' : 'd';
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

    public abstract List<Location> getValidMoves(Board board);

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
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
