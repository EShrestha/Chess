package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Rook extends Piece implements Movable{

    public Rook(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "Rook";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'R' : 'r';
        this.currentTile = tile;
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> validMoves = new LinkedList<>();

        // Up vertical
        validMoves.addAll(optimizedValidMoveGetter(board,0, 1));
        // Down vertical
        validMoves.addAll(optimizedValidMoveGetter(board,0, -1));
        // Left horizontal
        validMoves.addAll(optimizedValidMoveGetter(board,-1, 0));
        // Right horizontal
        validMoves.addAll(optimizedValidMoveGetter(board,1, 0));
        return validMoves;
    }

    @Override
    public List<Location> getValidMoves(Board board, Tile tile) {
        return null;
    }

    public List<Location> optimizedValidMoveGetter(Board board, Integer fileMultiplier, Integer rankMultiplier){
        Location currentLocation = this.getCurrentTile().getLocation();
        Map<Location, Tile> tileMap = board.getLocationTileMap();
        List<Location> validMoves = new LinkedList<>();

        for(int i = 1; i < 9 ; i++) {
            if(LocationGenerator.build(currentLocation, fileMultiplier*i, rankMultiplier*i) != null) {
                System.out.println("CONSIDERING: " + LocationGenerator.build(currentLocation, fileMultiplier*i, rankMultiplier*i));
                if(tileMap.get(LocationGenerator.build(currentLocation, fileMultiplier*i, rankMultiplier*i)).isHasPiece()) {
                    if (tileMap.get(LocationGenerator.build(currentLocation, fileMultiplier*i, rankMultiplier*i)).getCurrentPiece().getPieceColor().equals(this.pieceColor)) {
                        break;
                    } else if (tileMap.get(LocationGenerator.build(currentLocation, fileMultiplier*i, rankMultiplier*i)).getCurrentPiece().getPieceColor() != this.pieceColor) {
                        validMoves.add(LocationGenerator.build(currentLocation, fileMultiplier*i, rankMultiplier*i));
                        break;
                    }
                }else {
                    validMoves.add(LocationGenerator.build(currentLocation, fileMultiplier*i, rankMultiplier*i));
                }
            }
        }
        return validMoves;
    }

    @Override
    public void makeMove(Tile tile) {
        System.out.println("This method 'makeMove' needs to be implemented for the " + this.getName() + " class.");
    }

}
