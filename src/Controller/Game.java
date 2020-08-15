package Controller;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.RankToRank;
import BoardStuff.Tile;
import Model.King;
import Model.Piece;
import Model.Player;
import lib.ConsoleIO;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    RankToRank rankToRank = new RankToRank();
    Player lightPlayer = new Player('l');
    Player darkPlayer = new Player('d');
    public static int movesMade = 0;
    boolean lightResign = false;
    boolean darkResign = false;
    Tile darkKingTile;
    Tile lightKingTile;
    boolean notGameOver = true;
    Board playingBoard;


    public Game(){
    }
    public int getMovesMade()
    {
        return movesMade;
    }

    public void playGame(Board activeBoard){
        playingBoard = activeBoard;
        playingBoard.printBoard();

        do{

            if(movesMade % 2 == 0){ // Lights turn

                System.out.print("LIGHT -> ");
                    makeValidMove('l');

                movesMade++;

            }else if(movesMade % 2 == 1) { // Dark turn

                System.out.print("DARK -> ");
                makeValidMove('d');

                movesMade++;

            }

            System.out.println();
            refreshBoard();

        }while (notGameOver);

    }

    public void makeValidMove(char color){
        // Group 1 2 3 4 ----> a 1  a 2
        Pattern p = Pattern.compile("^([a-h])(\\d)\\s([a-h])(\\d)$");
        Matcher m = p.matcher("");
        boolean validMoveMade = false;


        do {

            while (!m.matches()) {
                m = p.matcher(ConsoleIO.promptForString("Enter piece coordinate and move to coordinate (a1 a2): "));
                if (!m.matches()) {
                    System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
                }
            }

            // Getting x and y of the piece user wants ot move and the x and y of where user want to move piece to
            int xCurrent = Enum.valueOf(BoardStuff.File.class, m.group(1).toUpperCase()).ordinal();
            int yCurrent = rankToRank.getRank(Integer.parseInt(m.group(2)) - 1);
            int xMoveTo = Enum.valueOf(BoardStuff.File.class, m.group(3).toUpperCase()).ordinal();
            int yMoveTo = rankToRank.getRank(Integer.parseInt(m.group(4)) - 1);


            if (playingBoard.board[yCurrent][xCurrent].isHasPiece()) {
                // Getting a list of valid locations for the current piece
                List<Location> validLocations = playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getValidMoves(playingBoard);
                System.out.println("ACTUAL VALID MOVES: " + validLocations);

                // Checking if the piece color user wants to move is actually the players color (l/d) and the move they want to make is in the valid locations list
                if (color == playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getShortColor()
                        && validLocations.contains(playingBoard.board[yMoveTo][xMoveTo].getLocation())) {

                    // Probably check for check/checkmate here

                    // Marking the piece as already moved once
                    playingBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);

                    // Making a copy of the piece user wants to move then resetting that tile the piece was on
                    Piece tempCurrentPiece = playingBoard.board[yCurrent][xCurrent].getCurrentPiece();
                    playingBoard.board[yCurrent][xCurrent].resetTile();

                    // Adding what piece was captured if there was a piece to be captured
                    if(playingBoard.board[yMoveTo][xMoveTo].isHasPiece()) {
                        Piece tempMoveToPiece = playingBoard.board[yMoveTo][xMoveTo].getCurrentPiece();
                        if(color == 'l'){ lightPlayer.addCapturedPiece(tempMoveToPiece);
                        }else { darkPlayer.addCapturedPiece(tempMoveToPiece); }

                    }

                    playingBoard.board[yMoveTo][xMoveTo].setCurrentPiece(tempCurrentPiece);
                    //Setting what tile the piece is on for the piece that just moved
                    playingBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setCurrentTile(playingBoard.board[yMoveTo][xMoveTo]);

                    validMoveMade = true;


                } else {
                    System.out.println("***INVALID MOVE***");
                    System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
                }

            }else {
                System.out.println("***INVALID MOVE***");
                System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
            }
            // Clearing user input so user can input again
            m = p.matcher("");
        }while (!validMoveMade) ;

    }


    public void refreshBoard(){
        System.out.print("CAPTURED PIECES: ");
        for(Piece dP : darkPlayer.getCapturedPieces()){
            System.out.print(dP.getShortName() + " ");
        }
        System.out.println("\n");
        playingBoard.printBoard();
        System.out.print("CAPTURED PIECES: ");
        for(Piece lP : lightPlayer.getCapturedPieces()){
            System.out.print(lP.getShortName() + " ");
        }
        System.out.println("\n");
    }


}
