package Controller;

import BoardStuff.Board;
import BoardStuff.RankToRank;
import Model.Player;
import lib.ConsoleIO;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    RankToRank rankToRank = new RankToRank();
    Player light = new Player('l');
    Player dark = new Player('d');
    int movesMade = 0;
    Board playingBoard;


    public Game(){
    }

    public void playGame(Board activeBoard){
        playingBoard = activeBoard;
        boolean notGameOver = true;

        do{
            playingBoard.printBoard();


            if(movesMade %2 == 0){ // Lights turn

                ConsoleIO.promptForString("Turn Light, input format a1 a2");

                movesMade++;
            }else if(movesMade % 2 == 1) { // Darks turn

                System.out.println(movesMade+": Black");
                movesMade++;

            }

        }while (notGameOver);

    }

    public void makeValidMove(String stringToTest, char color){
        // Group 1 2 3 4 ----> a 1  a 2
        Pattern p = Pattern.compile("^([a-h])(\\d)\\s([a-h])(\\d)$");
        Matcher m = p.matcher(stringToTest);
        String move;
        while (!m.matches()){
            move = ConsoleIO.promptForString("Enter piece coordinate and move to coordinate (a1 a2): ");
            m = p.matcher(move);
        }

//        if(color == 'l' && playingBoard.board[m.group(4)]){
//            playingBoard.board[0][0].getCurrentPiece();
//
//        }

    }
}
