package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.Tile;

import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece implements Movable {

    private Movable bishop;
    private Movable rook;

    public Queen(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "Queen";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'Q' : 'q';
        this.currentTile = tile;
    }

    public Queen(PieceColor pieceColor, Movable bishop, Movable rook){
        super(pieceColor);
        this.bishop = bishop;
        this.rook = rook;
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoveTiles = new LinkedList<>();
        Bishop b = new Bishop(this.pieceColor,this.getCurrentTile());
        Rook r = new Rook(this.pieceColor, this.getCurrentTile());
        possibleMoveTiles.addAll(b.getValidMoves(board, this.getCurrentTile()));
        possibleMoveTiles.addAll(r.getValidMoves(board, this.getCurrentTile()));
        return possibleMoveTiles;
    }

    @Override
    public List<Location> getValidMoves(Board board, Tile tile) {
        return null;
    }


}
