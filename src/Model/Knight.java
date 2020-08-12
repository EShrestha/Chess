package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Knight extends Piece implements Movable {
    public Knight(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "Knight";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'N' : 'n';
        this.currentTile = tile;
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoveTiles = new LinkedList<>();
        Location currentLocation = this.getCurrentTile().getLocation();

        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, 2)); // right one up/down 1 from currentLocation
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, 2)); // left one up/down 1 from currentLocation

        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, -2));
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, -2));

        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 2, 1));
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 2, -1));

        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -2, 1));
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -2, -1));

        System.out.println("TOTAL POSSIBLE MOVES: " + possibleMoveTiles);

        Map<Location, Tile> tileMap = board.getLocationTileMap();

        List<Location> validMoves = new LinkedList<>();

        for(Location l : possibleMoveTiles){
            if(l != null && (board.board[l.getFile().ordinal()][(l.rankToRank.getRank(l.getRank()))].isHasPiece())){
                if(this.pieceColor != board.board[l.getFile().ordinal()][(l.rankToRank.getRank(l.getRank())+1)].getCurrentPiece().getPieceColor()){
                    System.out.println("This color: " + this.pieceColor);
                    System.out.println("That color: " + board.board[l.getFile().ordinal()][(l.rankToRank.getRank(l.getRank())+1)].getCurrentPiece().getPieceColor());
                    validMoves.add(l);
                    System.out.println("ADDED: " +  board.board[l.getFile().ordinal()][(l.rankToRank.getRank(l.getRank())+1)]);
                }

            }else if (l != null && !(board.board[l.getFile().ordinal()][(l.rankToRank.getRank(l.getRank()))].isHasPiece())) {

                validMoves.add(l);
                System.out.println("ADDED2: " +  board.board[l.getFile().ordinal()][(l.rankToRank.getRank(l.getRank()))]);
            }

        }
        return validMoves;
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
