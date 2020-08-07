package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.Tile;

import java.util.Collections;
import java.util.List;

public class Queen extends Piece implements Movable {

    private Movable bishop;
    private Movable rook;

    public Queen(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Queen";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'Q' : 'q';
    }

    public Queen(PieceColor pieceColor, Movable bishop, Movable rook){
        super(pieceColor);
        this.bishop = bishop;
        this.rook = rook;
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoveTiles = Collections.emptyList();
        possibleMoveTiles.addAll(bishop.getValidMoves(board, this.getCurrentTile()));
        possibleMoveTiles.addAll(rook.getValidMoves(board, this.getCurrentTile()));
        return possibleMoveTiles;
    }

    @Override
    public List<Location> getValidMoves(Board board, Tile tile) {
        return null;
    }

    @Override
    public void makeMove(Tile tile) {
        Tile currentTile = this.getCurrentTile(); // Stores what tile the piece is on currently
        this.setCurrentTile(tile);                // Sets this piece location to the passed in tile location
        currentTile.resetTile();                  // Clears the tile this piece just moved from
    }
}
