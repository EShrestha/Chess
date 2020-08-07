package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pawn extends Piece implements Movable{

    private boolean isFirstMove = true;

    public Pawn(PieceColor pieceColor) {
        super(pieceColor);
        this.name = "Pawn";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'P' : 'p';
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoveTiles = Collections.emptyList();
        Location currentLocation = this.getCurrentTile().getLocation();
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 0, 2)); // 0= same file, 2 = can move up 2 rank from current location
        // If it's the first move
        if(isFirstMove){
            possibleMoveTiles.add(LocationGenerator.build(currentLocation, 0, 1)); // 0 = same file, 1 = can move up 1 rank from current location
            return possibleMoveTiles;
        }

        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, 1)); // right one up 1 from curretntLocation
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, 1)); // left one up 1 from currentLocation
        Map<Location, Tile> tileMap = board.getLocationTileMap();

        // No clue what this does but it works
        List<Location> validMoves = possibleMoveTiles.stream()
                .filter(tileMap::containsKey)
                .collect(Collectors.toList());

        return validMoves.stream().filter((candidate) ->{
            if(candidate.getFile().equals(this.getCurrentTile().getLocation().getFile()) && tileMap.get(candidate).isHasPiece()){
                return false;
            }

            return !tileMap.get(candidate).getCurrentPiece().pieceColor.equals(this.getPieceColor()); // if the canidate tile does not have same piece color add it to the list
        }).collect(Collectors.toList());


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
