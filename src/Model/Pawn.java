package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Pawn extends Piece implements Movable{

    public Pawn(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "Pawn";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'P' : 'p';
        this.currentTile = tile;
    }


    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoveTiles = new LinkedList<>();
        Location currentLocation = this.getCurrentTile().getLocation();
        Map<Location, Tile> tileMap = board.getLocationTileMap();

        Integer rankOffset = this.pieceColor == PieceColor.LIGHT ? 1 : -1;
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 0, rankOffset)); // 0= same file, 2 = can move up 2 rank from current location

        // If it's the first move
        if(this.isFirstMove() && !tileMap.get(LocationGenerator.build(currentLocation, 0, rankOffset)).isHasPiece()){
                possibleMoveTiles.add(LocationGenerator.build(currentLocation, 0, 2 * rankOffset)); // 0 = same file, 1 = can move up 1 rank from current location
        }

        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, rankOffset)); // right one up/down 1 from currentLocation
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, rankOffset)); // left one up/down 1 from currentLocation



        // Filters out the tiles from the possibleMoveTiles that are not a valid tile
        List<Location> validMoves = new LinkedList<>();

        for(Location l : possibleMoveTiles){
            if(l != null && tileMap.get(l).isHasPiece()){
                if(tileMap.get(l).getCurrentPiece().getPieceColor() != this.getPieceColor()
                && tileMap.get(l).getLocation().getFile() != this.getCurrentTile().getLocation().getFile()){
                    validMoves.add(l);
                }
            }else if(l != null && !tileMap.get(l).isHasPiece()){
                if(tileMap.get(l).getLocation().getFile() == this.getCurrentTile().getLocation().getFile()){
                    validMoves.add(l);
                }
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
