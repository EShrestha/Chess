package Controller;

import BoardStuff.*;
import Model.King;
import Model.Pawn;
import Model.Piece;
import Model.Player;
import lib.ConsoleIO;

import java.util.ArrayList;
import java.util.HashMap;
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
    Board playingBoardFile;
    Board playingBoard = new Board(true);


    public Game() {
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
            System.out.println(getCapturedPieces(darkPlayer));
            playingBoard.printBoard();
            System.out.println(getCapturedPieces(lightPlayer));

        }while (notGameOver);


    }

    public Game(Board activeBoard) {
        playingBoardFile = activeBoard;
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
            System.out.println(getCapturedPieces(darkPlayer));
            playingBoard.printBoard();
            System.out.println(getCapturedPieces(lightPlayer));

        }while (notGameOver);

    }

    public void makeValidMove(char color) {
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
            Integer xCurrent = Enum.valueOf(File.class, m.group(1).toUpperCase()).ordinal();
            Integer yCurrent = rankToRank.getRank(Integer.parseInt(m.group(2)) - 1);
            Integer xMoveTo = Enum.valueOf(File.class, m.group(3).toUpperCase()).ordinal();
            Integer yMoveTo = rankToRank.getRank(Integer.parseInt(m.group(4)) - 1);


            if (yCurrent != null && yMoveTo != null
            && playingBoard.board[yCurrent][xCurrent].isHasPiece()) {
                // Getting a list of valid locations for the current piece
                List<Location> validLocations = playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getValidMoves(playingBoard);
                System.out.println("VALID MOVES: " + validLocations);

                // Checking if the piece color user wants to move is actually the players color (l/d) and the move they want to make is in the valid locations list
                if (color == playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getShortColor()
                        && validLocations.contains(playingBoard.board[yMoveTo][xMoveTo].getLocation())) {


                    ////////////////////////////////// Check for check
                    Board tempBoard = createTempBoard(playingBoard);
                    tempBoard.printBoard();
                    tempBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);
                    Piece tempTempCurrentPiece = tempBoard.board[yCurrent][xCurrent].getCurrentPiece();
                    tempBoard.board[yCurrent][xCurrent].resetTile();
                    tempBoard.board[yMoveTo][xMoveTo].setCurrentPiece(tempTempCurrentPiece);
                    //Setting what tile the piece is on for the piece that just moved
                    tempBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setCurrentTile(tempBoard.board[yMoveTo][xMoveTo]);
                    ////////////////////////////////////
                    if(!new King().checkForCheck(tempBoard, color == 'l'? Board.lightKingsTile : Board.darkKingsTile)) {

                        System.out.println("TEMP: " + tempBoard.board[yCurrent][xCurrent].toString());
                        System.out.println("PLAYING: " + playingBoard.board[yCurrent][xCurrent].toString());


                        // Marking the piece as already moved once
                        playingBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);
                        if(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getClass().getSimpleName().equals(King.class.getSimpleName())){
                            if(color == 'l'){
                                Board.lightKingsTile = playingBoard.board[yMoveTo][xMoveTo];
                            }else {
                                Board.darkKingsTile = playingBoard.board[yMoveTo][xMoveTo];
                            }
                        }

                        // Making a copy of the piece user wants to move then resetting that tile the piece was on
                        Piece tempCurrentPiece = playingBoard.board[yCurrent][xCurrent].getCurrentPiece();
                        playingBoard.board[yCurrent][xCurrent].resetTile();

                        // Adding what piece was captured if there was a piece to be captured
                        if (playingBoard.board[yMoveTo][xMoveTo].isHasPiece()) {
                            Piece tempMoveToPiece = playingBoard.board[yMoveTo][xMoveTo].getCurrentPiece();
                            if (color == 'l') {
                                lightPlayer.addCapturedPiece(tempMoveToPiece);
                            } else {
                                darkPlayer.addCapturedPiece(tempMoveToPiece);
                            }

                        }

                        playingBoard.board[yMoveTo][xMoveTo].setCurrentPiece(tempCurrentPiece);
                        //Setting what tile the piece is on for the piece that just moved
                        playingBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setCurrentTile(playingBoard.board[yMoveTo][xMoveTo]);

                        System.out.println("TILE MOVED: " + Math.abs((playingBoard.board[yMoveTo][xMoveTo].getLocation().getRank() - playingBoard.board[yCurrent][xCurrent].getLocation().getRank() )));
                        validMoveMade = true;
                        move = "X";

                    }else {
                        System.out.println("****INVALID**** PUTS YOU IN CHECK");
                        System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
                        Board.TEMPlightKingsTile.setLocation(Board.lightKingsTile.getLocation());
                        Board.TEMPlightKingsTile.setTileColor(Board.lightKingsTile.getTileColor());
                        Board.TEMPlightKingsTile.setCurrentPiece(Board.lightKingsTile.getCurrentPiece());
                        Board.TEMPlightKingsTile.setHasPiece(Board.lightKingsTile.isHasPiece());

                        Board.TEMPdarkKingsTile.setLocation(Board.darkKingsTile.getLocation());
                        Board.TEMPdarkKingsTile.setTileColor(Board.darkKingsTile.getTileColor());
                        Board.TEMPdarkKingsTile.setCurrentPiece(Board.darkKingsTile.getCurrentPiece());
                        Board.TEMPdarkKingsTile.setHasPiece(Board.darkKingsTile.isHasPiece());
                    }
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

    public String getCapturedPieces(Player player){
        String capPieces = "[";
        for (Piece p : player.getCapturedPieces()){
            capPieces += (p.getShortName() + " ");
        }
        capPieces += "]";
        return capPieces;
    }

    public Board createTempBoard(Board originalBoard){
        Board tempBoard = new Board(false);
        Map<Location, Tile> tileMap = originalBoard.getLocationTileMap();

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


}
