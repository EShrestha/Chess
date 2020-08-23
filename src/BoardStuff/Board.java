package BoardStuff;

import Command.CommandReader;
import Controller.Game;
import Model.*;

import java.util.HashMap;
import java.util.Map;

public class Board{
    public static final Integer DIMENSION = 8;
    public Tile[][] board = new Tile[DIMENSION][DIMENSION];
    private  Map<Location, Tile> locationTileMap;
    public static Tile darkKingsTile;
    public static Tile lightKingsTile;
    public static Tile TEMPdarkKingsTile;
    public static Tile TEMPlightKingsTile;

    public static Tile lightRookQueenSide;
    public static Tile lightRookKingSide;
    public static Tile darkRookQueenSide;
    public static Tile darkRookKingSide;




    public Board(){ }

    // If no files are passed in, setup board using this
    public Board(boolean setUpNewGame){
        locationTileMap = new HashMap<>();

        int tileID = 0;
        for(int i = 0; i < board.length; i++){
            int col = 0;
            TileColor currentColor = (i % 2 == 0) ? TileColor.LIGHT : TileColor.DARK;
            for(File file : File.values()){
                Tile newTile = new Tile(currentColor, new Location(file, DIMENSION-i), tileID);
                locationTileMap.put(newTile.getLocation(), newTile);
                board[i][col] = newTile;
                currentColor = (currentColor == TileColor.DARK) ? TileColor.LIGHT : TileColor.DARK;
                col++;
                tileID++;
            }

        }
        //Sets up the new game with all pieces in the right tiles
        if(setUpNewGame){setUpNewGame();}
    }

    // If a file is passed in, setup board using this
    public Board(String file){
        locationTileMap = new HashMap<>();
        board = new CommandReader(true).readCommandFromFile(file);
    }

    public Map<Location, Tile> getLocationTileMap(){
        return locationTileMap;
    }


    public void printBoard(){
        System.out.println("   +-----+-----+-----+-----+-----+-----+-----+-----+");
        for(Tile[] row : board){

            for(Tile tile : row){
                if(tile.getLocation().getFile() == File.A){
                    System.out.print(tile.getLocation().getRank()+"  ");
                }
                System.out.print("|");
                if(tile.isHasPiece() ){
                    // this is where you would print the piece name, EX: | R | N | B | K | Q |...
                    System.out.print("  " + tile.getCurrentPiece().getShortName() + "  ");

                }else{
                    System.out.print("  -  ");
                    // DEBUG - Below line is for debugging
                    //System.out.print(" "+tile.getLocation().getFile()+","+tile.getLocation().getRank()+" ");
                }
            }
            System.out.println("|");
            System.out.println("   +-----+-----+-----+-----+-----+-----+-----+-----+");
        }
        System.out.println("      A     B     C     D     E     F     G     H");

    }

    public void setUpNewGame(){
        // All pieces(DARK) of the 8th Rank
        board[0][0].setCurrentPiece(new Rook(PieceColor.DARK, board[0][0]));
        darkRookQueenSide = board[0][0];
        board[0][1].setCurrentPiece(new Knight(PieceColor.DARK, board[0][1]));
        board[0][2].setCurrentPiece(new Bishop(PieceColor.DARK, board[0][2]));
        board[0][3].setCurrentPiece(new Queen(PieceColor.DARK, board[0][3]));
        board[0][4].setCurrentPiece(new King(PieceColor.DARK, board[0][4]));
        darkKingsTile = board[0][4];
        TEMPdarkKingsTile = board[0][4];
        board[0][5].setCurrentPiece(new Bishop(PieceColor.DARK, board[0][5]));
        board[0][6].setCurrentPiece(new Knight(PieceColor.DARK, board[0][6]));
        board[0][7].setCurrentPiece(new Rook(PieceColor.DARK, board[0][7]));
        darkRookKingSide = board[0][7];
        // All pieces(DARK) of the 7th Rank (Pawns only)
        board[1][0].setCurrentPiece(new Pawn(PieceColor.DARK, board[1][0]));
        board[1][1].setCurrentPiece(new Pawn(PieceColor.DARK, board[1][1]));
        board[1][2].setCurrentPiece(new Pawn(PieceColor.DARK, board[1][2]));
        board[1][3].setCurrentPiece(new Pawn(PieceColor.DARK, board[1][3]));
        board[1][4].setCurrentPiece(new Pawn(PieceColor.DARK, board[1][4]));
        board[1][5].setCurrentPiece(new Pawn(PieceColor.DARK, board[1][5]));
        board[1][6].setCurrentPiece(new Pawn(PieceColor.DARK, board[1][6]));
        board[1][7].setCurrentPiece(new Pawn(PieceColor.DARK, board[1][7]));


        // All pieces(LIGHT) of the 8th Rank
        board[7][0].setCurrentPiece(new Rook(PieceColor.LIGHT, board[7][0]));
        lightRookQueenSide = board[7][0];
        board[7][1].setCurrentPiece(new Knight(PieceColor.LIGHT, board[7][1]));
        board[7][2].setCurrentPiece(new Bishop(PieceColor.LIGHT, board[7][2]));
        board[7][3].setCurrentPiece(new Queen(PieceColor.LIGHT, board[7][3]));
        board[7][4].setCurrentPiece(new King(PieceColor.LIGHT, board[7][4]));
        lightKingsTile = board[7][4];
        TEMPlightKingsTile = board[7][4];
        board[7][5].setCurrentPiece(new Bishop(PieceColor.LIGHT, board[7][5]));
        board[7][6].setCurrentPiece(new Knight(PieceColor.LIGHT, board[7][6]));
        board[7][7].setCurrentPiece(new Rook(PieceColor.LIGHT, board[7][7]));
        lightRookKingSide = board[7][7];
        // All pieces(LIGHT) of the 7th Rank (Pawns only)
        board[6][0].setCurrentPiece(new Pawn(PieceColor.LIGHT, board[6][0]));
        board[6][1].setCurrentPiece(new Pawn(PieceColor.LIGHT, board[6][1]));
        board[6][2].setCurrentPiece(new Pawn(PieceColor.LIGHT, board[6][2]));
        board[6][3].setCurrentPiece(new Pawn(PieceColor.LIGHT, board[6][3]));
        board[6][4].setCurrentPiece(new Pawn(PieceColor.LIGHT, board[6][4]));
        board[6][5].setCurrentPiece(new Pawn(PieceColor.LIGHT, board[6][5]));
        board[6][6].setCurrentPiece(new Pawn(PieceColor.LIGHT, board[6][6]));
        board[6][7].setCurrentPiece(new Pawn(PieceColor.LIGHT, board[6][7]));

    }

    public static boolean amiInCheck(Board originalBoard, Integer yCurrent, Integer xCurrent, Integer yMoveTo, Integer xMoveTo ){
        Board tempBoard = createTempBoard(originalBoard);
        tempBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);
        Piece tempTempCurrentPiece = tempBoard.board[yCurrent][xCurrent].getCurrentPiece();
        //if king is moving the update it in tempboard first to see if it will be in checked
        if(tempTempCurrentPiece.getClass().getSimpleName().equals(King.class.getSimpleName())){
            if(Game.movesMade % 2 == 0){
                Board.TEMPlightKingsTile = tempBoard.board[yMoveTo][xMoveTo];
            }else {
                Board.TEMPdarkKingsTile = tempBoard.board[yMoveTo][xMoveTo];
            }
        }
        tempBoard.board[yCurrent][xCurrent].resetTile();
        tempBoard.board[yMoveTo][xMoveTo].setCurrentPiece(tempTempCurrentPiece);
        //Setting what tile the piece is on for the piece that just moved
        tempBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setCurrentTile(tempBoard.board[yMoveTo][xMoveTo]);

        boolean r = new King().checkForCheck(tempBoard, (Game.movesMade % 2) == 0 ? Board.TEMPlightKingsTile : Board.TEMPdarkKingsTile);
        resetTempTiles();
        return r;
    }

    public static Board createTempBoard(Board originalBoard){
        Board tempBoard = new Board(false);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j< 8; j++){

                tempBoard.board[i][j].setTileColor(originalBoard.board[i][j].getTileColor());
                tempBoard.board[i][j].setLocation(originalBoard.board[i][j].getLocation());
                if(originalBoard.board[i][j].isHasPiece()){
                    tempBoard.board[i][j].setCurrentPiece(originalBoard.board[i][j].getCurrentPiece());
                }


            }
        }
        return tempBoard;
    }

    public static void resetTempTiles(){
        Board.TEMPlightKingsTile.setLocation(Board.lightKingsTile.getLocation());
        Board.TEMPlightKingsTile.setTileColor(Board.lightKingsTile.getTileColor());
        Board.TEMPlightKingsTile.setCurrentPiece(Board.lightKingsTile.getCurrentPiece());
        Board.TEMPlightKingsTile.setHasPiece(Board.lightKingsTile.isHasPiece());

        Board.TEMPdarkKingsTile.setLocation(Board.darkKingsTile.getLocation());
        Board.TEMPdarkKingsTile.setTileColor(Board.darkKingsTile.getTileColor());
        Board.TEMPdarkKingsTile.setCurrentPiece(Board.darkKingsTile.getCurrentPiece());
        Board.TEMPdarkKingsTile.setHasPiece(Board.darkKingsTile.isHasPiece());
    }


}
