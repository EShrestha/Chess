package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;

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
       // possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, 2));
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, 2));
       // possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, -2));
       // possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, -2));
        //possibleMoveTiles.add(LocationGenerator.build(currentLocation, -2, 1));
      //  possibleMoveTiles.add(LocationGenerator.build(currentLocation, 2, 1));
       // possibleMoveTiles.add(LocationGenerator.build(currentLocation, -2, -1));
      //  possibleMoveTiles.add(LocationGenerator.build(currentLocation, 2, -1));
        Map<Location, Tile> tileMap = board.getLocationTileMap();

        // No clue what this does but it works
        List<Location> validMoves = possibleMoveTiles.stream()
                .filter(tileMap::containsKey)
                .collect(Collectors.toList());

        return validMoves.stream().filter((candidate) -> {
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
