package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Rook extends Piece implements Movable{

    public Rook(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "Rook";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'R' : 'r';
        this.currentTile = tile;
    }

    @Override
    public List<Location> getValidMoves(Board board) {
        List<Location> possibleMoveTiles = new LinkedList<>();
        Location currentLocation = this.getCurrentTile().getLocation();

        for (int p = 1; p < 8; p++) { // Lets Rook move up and to the right
            possibleMoveTiles.add(LocationGenerator.build(currentLocation, 0, p));
            possibleMoveTiles.add(LocationGenerator.build(currentLocation, p, 0));

            for (int n = -1; n > -8; n--) { // Lets rook move down and left
                possibleMoveTiles.add(LocationGenerator.build(currentLocation, 0, n));
                possibleMoveTiles.add(LocationGenerator.build(currentLocation, n, 0));
            }
        }

        Map<Location, Tile> tileMap = board.getLocationTileMap();

        List<Location> validMoves = new LinkedList<>();

        for(Location l : possibleMoveTiles) {
            if (l != null && tileMap.get(l).isHasPiece()) {
                if (tileMap.get(l).getCurrentPiece().getPieceColor() != this.getPieceColor()
                        && tileMap.get(l).getLocation().getFile() != this.getCurrentTile().getLocation().getFile()) {
                    validMoves.add(l);
                }
            } else if (l != null && !tileMap.get(l).isHasPiece()) {
                if (tileMap.get(l).getLocation().getFile() == this.getCurrentTile().getLocation().getFile()) {
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

    //
}
