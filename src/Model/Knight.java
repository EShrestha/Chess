package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.Tile;

import java.util.List;

public class Knight extends Piece implements Movable {
    public Knight(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Knight";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'N' : 'n';
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
