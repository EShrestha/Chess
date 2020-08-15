package Model;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.LocationGenerator;
import BoardStuff.Tile;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static Controller.Game.movesMade;

public class King extends Piece implements Movable{
    public King(PieceColor pieceColor, Tile tile) {
        super(pieceColor);
        this.name = "King";
        this.shortName = this.pieceColor == PieceColor.LIGHT ? 'K' : 'k';
        this.currentTile = tile;
    }

    //Default
    public King(){}

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
                if(tileMap.get(l).getCurrentPiece().getPieceColor() != this.getPieceColor() && tileMap.get(l).getLocation().getFile() != this.getCurrentTile().getLocation().getFile()){
                   // if(!checkCheck(board, ))
                     //   validMoves.add(l);


                    //Implement checking to see if the move will put it in check and if it doesnt add move


                }
            }else if(l != null && !tileMap.get(l).isHasPiece()){
                if(tileMap.get(l).getLocation().getFile() == this.getCurrentTile().getLocation().getFile()){

                    //Implement checking to see if the move will put it in check and if it doesnt add move


                    validMoves.add(l);
                }
            }

        }
        return validMoves;
    }

    public Boolean[] inCheckOrCheckmate(){
        Boolean[] bools = new Boolean[4];
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

    public boolean checkForCheck(Board board, Tile tile)
    {
        Map<Location, Tile> tileMap = board.getLocationTileMap();
        List<Location> threats = new LinkedList<>();

        PieceColor color = tile.getCurrentPiece().getPieceColor();
        char thisColor = movesMade % 2 == 0 ? 'l' : 'd';
        System.out.println("CURRENT PIECE COLOR:" + color);


        Bishop b = new Bishop(color,tile);
        b.setFirstMove(false);
        List<Location> bishopPossibleMoveTiles = new LinkedList<>();
        bishopPossibleMoveTiles.addAll(b.getValidMoves(board, tile));

        for(Location l : bishopPossibleMoveTiles){

            if(l != null && tileMap.get(l).isHasPiece()){
                System.out.println("BISHOP CONSIDERING THREAT: " + l);
                if(!tileMap.get(l).getCurrentPiece().getPieceColor().equals(this.pieceColor)
                    && tileMap.get(l).getCurrentPiece().getClass().equals(Queen.class.getSimpleName()) || tileMap.get(l).getCurrentPiece().getClass().getSimpleName().equals(Bishop.class.getSimpleName())){

                    threats.add(l);
                    System.out.println("THREAT ADDED: " + l);
                }
            }
        }

        Rook r = new Rook(color, tile);
        r.setFirstMove(false);
        List<Location> rookPossibleMoveTiles = new LinkedList<>();
        rookPossibleMoveTiles.addAll(r.getValidMoves(board, tile));


        for(Location l : rookPossibleMoveTiles){

            if(l != null && tileMap.get(l).isHasPiece()){
                System.out.println("ROOK CONSIDERING THREAT: " + l);
                if(!tileMap.get(l).getCurrentPiece().getPieceColor().equals(this.pieceColor)
                        && tileMap.get(l).getCurrentPiece().getClass().getSimpleName().equals(Queen.class.getSimpleName()) || tileMap.get(l).getCurrentPiece().getClass().getSimpleName().equals(Rook.class.getSimpleName())){

                    threats.add(l);
                    System.out.println("THREAT ADDED: " + l);
                }
            }
        }

        Pawn p = new Pawn(color,tile);
        p.setFirstMove(false);
        List<Location> pawnPossibleMoveTiles = new LinkedList<>();
        pawnPossibleMoveTiles.addAll(p.getValidMoves(board));



        if(pawnPossibleMoveTiles != null && !pawnPossibleMoveTiles.isEmpty()) {
            for (Location l : pawnPossibleMoveTiles) {
                System.out.println("PAWN CONSIDERING THREATS: " + l);

                if (l != null && tileMap.get(l).isHasPiece()) {

                    if (!tileMap.get(l).getCurrentPiece().getPieceColor().equals(this.pieceColor)
                            && tileMap.get(l).getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())) {

                        threats.add(l);
                        System.out.println("THREAT ADDED: " + l);
                    }
                }
            }
        }


        Knight k = new Knight(color,tile);
        k.setFirstMove(false);
        List<Location> knightPossibleMoveTiles = new LinkedList<>();
        knightPossibleMoveTiles.addAll(k.getValidMoves(board));


        if(knightPossibleMoveTiles != null && !knightPossibleMoveTiles.isEmpty()) {
            for (Location l : knightPossibleMoveTiles) {
                System.out.println("KNIGHT CONSIDERING THREAT: " + l);
                if (l != null && tileMap.get(l).isHasPiece()) {

                    if (!tileMap.get(l).getCurrentPiece().getPieceColor().equals(this.pieceColor)
                            && tileMap.get(l).getCurrentPiece().getClass().getSimpleName().equals(Knight.class.getSimpleName())) {

                        threats.add(l);
                        System.out.println("THREAT ADDED: " + l);
                    }
                }
            }
        }

        System.out.println("FULL THREATS LIST: " + threats);
        if(!threats.isEmpty())
        {
            return true;
        }

        return false;
    }

    public boolean checkCheckmate (Board board)
    {
        boolean inCheckMate = false;
        List<Location> validMoves = this.getValidMoves(board);
        if(validMoves.isEmpty())
            inCheckMate = true;
        return inCheckMate;
    }



}
