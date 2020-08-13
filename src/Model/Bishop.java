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
                if(tileMap.get(LocationGenerator.build(currentLocation, -i, i)).isHasPiece()) {
                    if (tileMap.get(LocationGenerator.build(currentLocation, -i, i)).getCurrentPiece().getPieceColor().equals(this.pieceColor)) {
                        break;
                    } else if (tileMap.get(LocationGenerator.build(currentLocation, -i, i)).getCurrentPiece().getPieceColor() != this.pieceColor) {
                        validMoves.add(LocationGenerator.build(currentLocation, -i, i));
                        break;
                    }
                }else {
                    validMoves.add(LocationGenerator.build(currentLocation, -i, i));
                }
            }
        }
        // Top right diagonal
        for(int i = 1; i < 8 ; i++) {
            if(LocationGenerator.build(currentLocation, i, i) != null) {
                if(tileMap.get(LocationGenerator.build(currentLocation, i, i)).isHasPiece()) {
                    if (tileMap.get(LocationGenerator.build(currentLocation, i, i)).getCurrentPiece().getPieceColor().equals(this.pieceColor)) {
                        break;
                    } else if (tileMap.get(LocationGenerator.build(currentLocation, i, i)).getCurrentPiece().getPieceColor() != this.pieceColor) {
                        validMoves.add(LocationGenerator.build(currentLocation, i, i));
                        break;
                    }
                }else {
                    validMoves.add(LocationGenerator.build(currentLocation, i, i));
                }
            }
        }
        // Bottom left diagonal
        for(int i = 1; i < 8 ; i++) {
            if(LocationGenerator.build(currentLocation, -i, -i) != null) {
                if(tileMap.get(LocationGenerator.build(currentLocation, -i, -i)).isHasPiece()) {
                    if (tileMap.get(LocationGenerator.build(currentLocation, -i, -i)).getCurrentPiece().getPieceColor().equals(this.pieceColor)) {
                        break;
                    } else if (tileMap.get(LocationGenerator.build(currentLocation, -i, -i)).getCurrentPiece().getPieceColor() != this.pieceColor) {
                        validMoves.add(LocationGenerator.build(currentLocation, -i, -i));
                        break;
                    }
                }else {
                    validMoves.add(LocationGenerator.build(currentLocation, -i, -i));
                }
            }
        }
        // Bottom right diagonal
        for(int i = 1; i < 8 ; i++) {
            if(LocationGenerator.build(currentLocation, i, -i) != null) {
                if(tileMap.get(LocationGenerator.build(currentLocation, i, -i)).isHasPiece()) {
                    if (tileMap.get(LocationGenerator.build(currentLocation, i, -i)).getCurrentPiece().getPieceColor().equals(this.pieceColor)) {
                        break;
                    } else if (tileMap.get(LocationGenerator.build(currentLocation, i, -i)).getCurrentPiece().getPieceColor() != this.pieceColor) {
                        validMoves.add(LocationGenerator.build(currentLocation, i, -i));
                        break;
                    }
                }else {
                    validMoves.add(LocationGenerator.build(currentLocation, i, -i));
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
