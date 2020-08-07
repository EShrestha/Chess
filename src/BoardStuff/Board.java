package BoardStuff;

import Command.CommandReader;
import Model.Bishop;
import Model.Knight;
import Model.Piece;
import Model.Rook;

public class Board {
    private static final Integer DIMENSION = 8;
    Tile[][] board = new Tile[DIMENSION][DIMENSION];

    // If a file is passed in, setup board using this
    public Board(String file){
        new CommandReader().readCommandFromFile(file);
    }

    // If no files are passed in, setup board using this
    public Board(){

        for(int i = 0; i < board.length; i++){
            int col = 0;
            TileColor currentColor = (i % 2 == 0) ? TileColor.LIGHT : TileColor.DARK;
            for(File file : File.values()){
                Tile newTile = new Tile(currentColor, new Location(file, DIMENSION-i));
                board[i][col] = newTile;
                currentColor = (currentColor == TileColor.DARK) ? TileColor.LIGHT : TileColor.DARK;
                col++;
            }
        }

    }

    public void printBoard(){
        System.out.println("   +-----+-----+-----+-----+-----+-----+-----+-----+");
        for(Tile[] row : board){

            for(Tile tile : row){
                if(tile.getLocation().getFile() == File.A){
                    System.out.print(tile.getLocation().getRank()+"  ");
                }
                System.out.print("|");
                if(tile.isHasPiece()){
                    // this is where you would print the piece name, EX: | R | N | B | K | Q |...
                    System.out.println("X");
                }else{
                    System.out.print("  X  ");
                }


            }
            System.out.println("|"); // Just a line separater
            System.out.println("   +-----+-----+-----+-----+-----+-----+-----+-----+");
        }
        System.out.println("      A     B     C     D     E     F     G     H");
    }
}
