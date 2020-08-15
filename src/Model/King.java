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

    public Boolean[] inCheckOrCheckmate(){
        Boolean[] bools = new Boolean[3];
        // [0] = check?  [1] = checkmate?  [3] = neither?

        bools[0] = true;
        bools[1] = false;
        bools[3] = false;

        return bools;
    }

    @Override
    public List<Location> getValidMoves(Board board, Tile tile) {
        return null;
    }

    public boolean checkCheck(Board board, Tile tile)
    {
        int color = Game.movesMade%2;
        Bishop b = new Bishop(this.pieceColor,tile);
        Rook r = new Rook(this.pieceColor, tile);
        Pawn p = new Pawn(this.pieceColor,tile);
        Knight k = new Knight(this.pieceColor, tile);


        return true;
    }



}
