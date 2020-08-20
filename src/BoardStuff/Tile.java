package BoardStuff;

import Model.Piece;

public class Tile {
    private TileColor tileColor;
    private Location location;
    private boolean hasPiece;
    private Piece currentPiece;
    public Integer tileID;

    public Tile(TileColor tileColor, Location location, Integer tileID) {
        this.tileColor = tileColor;
        this.location = location;
        this.hasPiece = false;
        this.tileID = tileID;
    }

    public void resetTile(){
        this.hasPiece = false;
        this.currentPiece = null;
    }

    public TileColor getTileColor() {
        return tileColor;
    }

    public void setTileColor(TileColor color){
        this.tileColor = color;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isHasPiece() {
        return hasPiece;
    }

    public void setHasPiece(boolean hasPiece) {
        this.hasPiece = hasPiece;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
        this.setHasPiece(true);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileColor=" + tileColor +
                ", location=" + location +
                ", hasPiece=" + hasPiece +
                '}';
    }
}
