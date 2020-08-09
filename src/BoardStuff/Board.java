package BoardStuff;

import Command.CommandReader;
import Model.*;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private static final Integer DIMENSION = 8;
    public Tile[][] board = new Tile[DIMENSION][DIMENSION];
    private final Map<Location, Tile> locationTileMap;



    // If no files are passed in, setup board using this
    public Board(boolean setUpNewGame){
        locationTileMap = new HashMap<>();

        for(int i = 0; i < board.length; i++){
            int col = 0;
            TileColor currentColor = (i % 2 == 0) ? TileColor.LIGHT : TileColor.DARK;
            for(File file : File.values()){
                Tile newTile = new Tile(currentColor, new Location(file, DIMENSION-i));
                locationTileMap.put(newTile.getLocation(), newTile);
                board[i][col] = newTile;
                currentColor = (currentColor == TileColor.DARK) ? TileColor.LIGHT : TileColor.DARK;
                col++;
            }
        }
        //Setus up the new game with all pieces in the right tiles
        if(setUpNewGame){setUpNewGame();}
    }

    // If a file is passed in, setup board using this
    public Board(String file){
        locationTileMap = new HashMap<>();
        board = new CommandReader().readCommandFromFile(file);
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
                    // DEBUG - Below line is for debuging
                    //System.out.print(" "+tile.getLocation().getFile()+","+tile.getLocation().getRank()+" ");
                }
            }
            System.out.println("|"); // Just a line separater
            System.out.println("   +-----+-----+-----+-----+-----+-----+-----+-----+");
        }
        System.out.println("      A     B     C     D     E     F     G     H");

    }

    public void setUpNewGame(){
        // All pieces(DARK) of the 8th Rank
        board[0][0].setCurrentPiece(new Rook(PieceColor.DARK));
        board[0][1].setCurrentPiece(new Bishop(PieceColor.DARK));
        board[0][2].setCurrentPiece(new Knight(PieceColor.DARK));
        board[0][3].setCurrentPiece(new Queen(PieceColor.DARK));
        board[0][4].setCurrentPiece(new King(PieceColor.DARK));
        board[0][5].setCurrentPiece(new Knight(PieceColor.DARK));
        board[0][6].setCurrentPiece(new Bishop(PieceColor.DARK));
        board[0][7].setCurrentPiece(new Rook(PieceColor.DARK));
        // All pieces(DARK) of the 7th Rank (Pawns only)
        board[1][0].setCurrentPiece(new Pawn(PieceColor.DARK));
        board[1][1].setCurrentPiece(new Pawn(PieceColor.DARK));
        board[1][2].setCurrentPiece(new Pawn(PieceColor.DARK));
        board[1][3].setCurrentPiece(new Pawn(PieceColor.DARK));
        board[1][4].setCurrentPiece(new Pawn(PieceColor.DARK));
        board[1][5].setCurrentPiece(new Pawn(PieceColor.DARK));
        board[1][6].setCurrentPiece(new Pawn(PieceColor.DARK));
        board[1][7].setCurrentPiece(new Pawn(PieceColor.DARK));


        // All pieces(LIGHT) of the 8th Rank
        board[7][0].setCurrentPiece(new Rook(PieceColor.LIGHT));
        board[7][1].setCurrentPiece(new Bishop(PieceColor.LIGHT));
        board[7][2].setCurrentPiece(new Knight(PieceColor.LIGHT));
        board[7][3].setCurrentPiece(new Queen(PieceColor.LIGHT));
        board[7][4].setCurrentPiece(new King(PieceColor.LIGHT));
        board[7][5].setCurrentPiece(new Knight(PieceColor.LIGHT));
        board[7][6].setCurrentPiece(new Bishop(PieceColor.LIGHT));
        board[7][7].setCurrentPiece(new Rook(PieceColor.LIGHT));
        // All pieces(LIGHT) of the 7th Rank (Pawns only)
        board[6][0].setCurrentPiece(new Pawn(PieceColor.LIGHT));
        board[6][1].setCurrentPiece(new Pawn(PieceColor.LIGHT));
        board[6][2].setCurrentPiece(new Pawn(PieceColor.LIGHT));
        board[6][3].setCurrentPiece(new Pawn(PieceColor.LIGHT));
        board[6][4].setCurrentPiece(new Pawn(PieceColor.LIGHT));
        board[6][5].setCurrentPiece(new Pawn(PieceColor.LIGHT));
        board[6][6].setCurrentPiece(new Pawn(PieceColor.LIGHT));
        board[6][7].setCurrentPiece(new Pawn(PieceColor.LIGHT));

    }
}
