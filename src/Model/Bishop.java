package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Bishop extends Piece implements Movable{
    public Bishop(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "Bishop";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'B' : 'b';
        this.currentTile = tile;
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoveTiles = new LinkedList<>();
        Location currentLocation = this.getCurrentTile().getLocation();
        Map<Location, Tile> tileMap = board.getLocationTileMap();
        List<Location> validMoves = new LinkedList<>();

        // Top left diagonal
        for(int i = 1; i < 8 ; i++) {
            if(LocationGenerator.build(currentLocation, -i, i) != null) {
                possibleMoveTiles.add(LocationGenerator.build(currentLocation, -i, i)); // right one up/down 1 from currentLocation
            }
        }
        // Top right diagonal
        for(int i = 1; i < 8 ; i++) {
            possibleMoveTiles.add(LocationGenerator.build(currentLocation, i, i)); // right one up/down 1 from currentLocation
        }
        // Bottom left diagonal
        for(int i = 1; i < 8 ; i++) {
            possibleMoveTiles.add(LocationGenerator.build(currentLocation, -i, -i)); // right one up/down 1 from currentLocation
        }
        for(int i = 1; i < 8 ; i++) {
            possibleMoveTiles.add(LocationGenerator.build(currentLocation, i, -i)); // right one up/down 1 from currentLocation
        }




        // Filters out the tiles from the possibleMoveTiles that are not a valid tile


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
