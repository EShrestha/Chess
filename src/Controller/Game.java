package Controller;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.RankToRank;
import Model.Pawn;
import Model.Piece;
import Model.Player;
import lib.ConsoleIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    RankToRank rankToRank = new RankToRank();
    Player light = new Player('l');
    Player dark = new Player('d');
    int movesMade = 0;
    boolean lightResign = false;
    boolean darkResign = false;
    Board playingBoard;


    public Game(){
    }

    public void playGame(Board activeBoard){
        playingBoard = activeBoard;
        boolean notGameOver = true;
        playingBoard.printBoard();

        do{

            if(movesMade % 2 == 0){ // Lights turn

                System.out.print("LIGHT -> ");
                    makeValidMove('l');

                movesMade++;

            }else if(movesMade % 2 == 1) { // Darks turn

                System.out.print("DARK -> ");
                makeValidMove('d');

                movesMade++;

            }
            playingBoard.printBoard();

        }while (notGameOver);

    }

    public void makeValidMove(char color){
        // Group 1 2 3 4 ----> a 1  a 2
        Pattern p = Pattern.compile("^([a-h])(\\d)\\s([a-h])(\\d)$");
        Matcher m = p.matcher("");
        Boolean validMoveMade = false;

        do {
            String move;
            while (!m.matches()) {
                m = p.matcher(ConsoleIO.promptForString("Enter piece coordinate and move to coordinate (a1 a2): "));
                if (!m.matches()) {
                    System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
                }
            }

            // Getting x and y of the piece user wants ot move and the x and y of where user want to move piece to
            Integer xCurrent = Enum.valueOf(BoardStuff.File.class, m.group(1).toUpperCase()).ordinal();
            Integer yCurrent = rankToRank.getRank(Integer.parseInt(m.group(2)) - 1);
            Integer xMoveTo = Enum.valueOf(BoardStuff.File.class, m.group(3).toUpperCase()).ordinal();
            Integer yMoveTo = rankToRank.getRank(Integer.parseInt(m.group(4)) - 1);


            if (playingBoard.board[yCurrent][xCurrent].isHasPiece()) {
                //
                List<Location> validLocations = playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getValidMoves(playingBoard);
                System.out.println(validLocations);

                if (color == playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getShortColor()
                        && validLocations.contains(playingBoard.board[yMoveTo][xMoveTo].getLocation())) {

                    Piece tempPiece = playingBoard.board[yCurrent][xCurrent].getCurrentPiece();
                    playingBoard.board[yCurrent][xCurrent].resetTile();

                    playingBoard.board[yMoveTo][xMoveTo].setCurrentPiece(tempPiece);
                    playingBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setCurrentTile(playingBoard.board[yMoveTo][xMoveTo]);

                    validMoveMade = true;
                    move = "X";
                } else {
                    System.out.println("***INVALID MOVE***");
                    m = p.matcher("");
                    System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
                }

            }else {
                System.out.println("***INVALID MOVE***");
                m = p.matcher("");
                System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
            }

        }while (!validMoveMade) ;


    }
}
