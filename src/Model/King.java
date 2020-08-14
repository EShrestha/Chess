package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;
import Controller.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class King extends Piece implements Movable{
    public King(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "King";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'K' : 'k';
        this.currentTile = tile;
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoveTiles = new LinkedList<>();
        Location currentLocation = this.getCurrentTile().getLocation();

        // If it's the first move
        //if(this.isFirstMove()){
            //maybe implement castling here
        //}

        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, 0)); // right one up/down 1 from currentLocation
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, 0)); // left one up/down 1 from currentLocation
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 0, 1));
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 0, -1));
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, -1)); // right one up/down 1 from currentLocation
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, 1)); // left one up/down 1 from currentLocation
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, 1, 1));
        possibleMoveTiles.add(LocationGenerator.build(currentLocation, -1, -1));

        Map<Location, Tile> tileMap = board.getLocationTileMap();

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

    public boolean checkCheck(Board board, Tile tile)
    {
       // int color = Game.getMovesMade()
        return true;
    }



}
