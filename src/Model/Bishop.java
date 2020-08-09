package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.Tile;

import java.util.List;

public class Bishop extends Piece implements Movable{
    public Bishop(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "Bishop";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'B' : 'b';
        this.currentTile = tile;
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        System.out.println("This method 'getValidMoves' needs to be implemented for the " + this.getName() + " class.");
        return null;
    }

    @Override
    public List<Location> getValidMoves(Board board, Tile tile) {
        return null;
    }

    @Override
    public void makeMove(Tile tile) {
        System.out.println("This method 'makeMove' needs to be implemented for the " + this.getName() + " class.");
    }
}
