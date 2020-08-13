package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.Tile;

import java.util.List;

public interface Movable {

    List<Location> getValidMoves(Board board);
    List<Location> getValidMoves(Board board, Tile tile);
}
