package Controller;

import BoardStuff.*;
import Model.*;
import lib.ConsoleIO;

import javax.swing.*;
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
        Map<Location, Tile> tileMap = playingBoard.getLocationTileMap();

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
                    System.out.println("MOVE ACCEPTED");


                    ////////////////TEMP BOARD CHECKS////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    Board tempBoard = createTempBoard(playingBoard); // Creating a copy board: TEMP board
                    Map<Location, Tile> TempTileMap = tempBoard.getLocationTileMap();
                    tempBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false); // Setting the move piece as already moved once
                    Piece tempTempCurrentPiece = tempBoard.board[yCurrent][xCurrent].getCurrentPiece(); // Capturing the current piece user wants to move

                    // SPECIAL MOVES OF KING
                    if(tempBoard.board[yCurrent][xCurrent].getCurrentPiece().getClass().getSimpleName().equals(King.class.getSimpleName())){
                        if(color == 'l'){
                            Board.TEMPlightKingsTile = tempBoard.board[yMoveTo][xMoveTo];
                        }else if (color == 'd') {
                            Board.TEMPdarkKingsTile = tempBoard.board[yMoveTo][xMoveTo];
                        }else {
                            Board.TEMPdarkKingsTile = Board.darkKingsTile;
                            Board.TEMPlightKingsTile = Board.lightKingsTile;
                        }
                    }

                    // SPECIAL MOVES OF PAWN
                    if(tempBoard.board[yCurrent][xCurrent].getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())){
                        if(Math.abs((tempBoard.board[yMoveTo][xMoveTo].getLocation().getRank() - tempBoard.board[yCurrent][xCurrent].getLocation().getRank() )) == 2) {
                            Location locationLeft = LocationGenerator.build(tempBoard.board[yMoveTo][xMoveTo].getLocation(), -1, 0);
                            Location locationRight = LocationGenerator.build(tempBoard.board[yMoveTo][xMoveTo].getLocation(), 1, 0);
                            Piece pieceLeft;
                            Piece pieceRight;
                            // For a pawn on the left
                            if (TempTileMap.get(locationLeft) != null && TempTileMap.get(locationLeft).isHasPiece() && TempTileMap.get(locationLeft).getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())) {
                                pieceLeft = TempTileMap.get(locationLeft).getCurrentPiece();
                                TempTileMap.get(locationLeft).getCurrentPiece().setCanEnPassant(true);
                                TempTileMap.get(locationLeft).getCurrentPiece().setEnPassantEnabledOnMove(movesMade);
                                TempTileMap.get(locationLeft).getCurrentPiece().setEnPassantTile(tempBoard.board[yMoveTo][xMoveTo]);
                                System.out.println("CAN ENPASSANT: " + pieceLeft);
                            }
                            // For a pawn on the right
                            if (TempTileMap.get(locationRight) != null && TempTileMap.get(locationRight).isHasPiece() && TempTileMap.get(locationRight).getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())) {
                                pieceRight = TempTileMap.get(locationRight).getCurrentPiece();
                                TempTileMap.get(locationRight).getCurrentPiece().setCanEnPassant(true);
                                TempTileMap.get(locationRight).getCurrentPiece().setEnPassantEnabledOnMove(movesMade);
                                TempTileMap.get(locationRight).getCurrentPiece().setEnPassantTile(tempBoard.board[yMoveTo][xMoveTo]);
                                System.out.println("CAN ENPASSANT: " + pieceRight);
                            }
                        }

                        if(tempBoard.board[yMoveTo][xMoveTo].getLocation().getRank() == 8 && color == 'l'){
                            System.out.println("LIGHT gets to promote pawn here.");
                        }
                        if(tempBoard.board[yMoveTo][xMoveTo].getLocation().getRank() == 1 && color == 'd'){
                            System.out.println("DARK gets to promote pawn here");
                        }

                    } // End of PAWN checks

                    tempBoard.board[yCurrent][xCurrent].resetTile(); // Resetting the current tile so it is a blank tile
                    tempBoard.board[yMoveTo][xMoveTo].setCurrentPiece(tempTempCurrentPiece); // Moving the captured piece to the move to tile
                    tempBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setCurrentTile(tempBoard.board[yMoveTo][xMoveTo]); // Adding the tiles location to the piece

                    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if(!new King().checkForCheck(tempBoard, color == 'l'? Board.TEMPlightKingsTile : Board.TEMPdarkKingsTile)) {
                        // Making a copy of the piece user wants to move then resetting that tile the piece was on
                        Piece tempCurrentPiece = playingBoard.board[yCurrent][xCurrent].getCurrentPiece();

                        // Marking the piece as already moved once
                        playingBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);
                        // If the piece that moved is the king then their tile location is updated as well in the Board class
                        if(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getClass().getSimpleName().equals(King.class.getSimpleName())){
                            if(color == 'l'){
                                Board.lightKingsTile = playingBoard.board[yMoveTo][xMoveTo];
                            }else {
                                Board.darkKingsTile = playingBoard.board[yMoveTo][xMoveTo];
                            }

                            if(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().isFirstMove()
                            && (Math.abs(playingBoard.board[yMoveTo][xMoveTo].getLocation().getFile().ordinal() - playingBoard.board[yCurrent][xCurrent].getLocation().getFile().ordinal()) == 2)){

                                // Means user wants to castle to the left side of the board
                                if(playingBoard.board[yMoveTo][xMoveTo].getLocation().getFile().ordinal() - playingBoard.board[yCurrent][xCurrent].getLocation().getFile().ordinal() < 0){
                                   if(color == 'l') {
                                       // Check if d1 is not in check and is empty and c1 is empty and b1 is empty
                                       if(!playingBoard.board[yCurrent][(xCurrent - 1)].isHasPiece() && !playingBoard.board[yCurrent][(xCurrent - 2)].isHasPiece() && !playingBoard.board[yCurrent][(xCurrent - 3)].isHasPiece()){
                                            // if test passes check if rook on a1 isFirstMove
                                           //if() {
                                               // if that test passes only move the rook from a1 to d1
                                               //if(){ }
                                           //}
                                   }
                                   }else{
                                       // Check if d8 is not in check and is empty and c8 is empty and b8 is empty
                                            // if piece on a8 is a rook && if rook on a8 isFirstMove
                                                // if that test passes only move the rook from a8 to d8
                                                    playingBoard.board[0][0].resetTile();
                                                    playingBoard.board[0][3].setCurrentPiece(new Rook(color == 'l'? PieceColor.LIGHT : PieceColor.DARK, playingBoard.board[0][3]));

                                   }

                                }
                                // Means user wants to castle to the right side of the board
                                if(playingBoard.board[yMoveTo][xMoveTo].getLocation().getFile().ordinal() - playingBoard.board[yCurrent][xCurrent].getLocation().getFile().ordinal() < 0){
                                    if(color == 'l') {
                                        // Check if f1 is not in check and g1 is empty
                                    }else {
                                        //Check if f8 is not in check and g8 is empty
                                    }

                                }


                            }



                        }

                        // SPECIAL MOVES OF PAWN
                        if(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())){
                                if(Math.abs((playingBoard.board[yMoveTo][xMoveTo].getLocation().getRank() - playingBoard.board[yCurrent][xCurrent].getLocation().getRank() )) == 2) {
                                    Location locationLeft = LocationGenerator.build(playingBoard.board[yMoveTo][xMoveTo].getLocation(), -1, 0);
                                    Location locationRight = LocationGenerator.build(playingBoard.board[yMoveTo][xMoveTo].getLocation(), 1, 0);
                                    Piece pieceLeft;
                                    Piece pieceRight;
                                    // For a pawn on the left
                                    if (tileMap.get(locationLeft) != null && tileMap.get(locationLeft).isHasPiece() && tileMap.get(locationLeft).getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())) {
                                        pieceLeft = tileMap.get(locationLeft).getCurrentPiece();
                                        tileMap.get(locationLeft).getCurrentPiece().setCanEnPassant(true);
                                        tileMap.get(locationLeft).getCurrentPiece().setEnPassantEnabledOnMove(movesMade);
                                        tileMap.get(locationLeft).getCurrentPiece().setEnPassantTile(playingBoard.board[yMoveTo][xMoveTo]);
                                        System.out.println("CAN ENPASSANT: " + pieceLeft);
                                    }
                                    // For a pawn on the right
                                    if (tileMap.get(locationRight) != null && tileMap.get(locationRight).isHasPiece() && tileMap.get(locationRight).getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())) {
                                        pieceRight = tileMap.get(locationRight).getCurrentPiece();
                                        tileMap.get(locationRight).getCurrentPiece().setCanEnPassant(true);
                                        tileMap.get(locationRight).getCurrentPiece().setEnPassantEnabledOnMove(movesMade);
                                        tileMap.get(locationRight).getCurrentPiece().setEnPassantTile(playingBoard.board[yMoveTo][xMoveTo]);
                                        System.out.println("CAN ENPASSANT: " + pieceRight);
                                    }
                                }

                            if((tempBoard.board[yMoveTo][xMoveTo].getLocation().getRank() == 8 && color == 'l') || (tempBoard.board[yMoveTo][xMoveTo].getLocation().getRank() == 1 && color == 'd')) {
                                boolean notValid;
                                do {
                                        String s = ConsoleIO.promptForString("What piece would you like to promote this pawn to? ");
                                        if (s.equals("Queen")) {
                                            tempCurrentPiece = new Queen(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, tempTempCurrentPiece.getCurrentTile());
                                            notValid = false;
                                        } else if (s.equals("Rook")) {
                                            tempCurrentPiece = new Rook(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, tempTempCurrentPiece.getCurrentTile());
                                            notValid = false;
                                        } else if (s.equals("Knight")) {
                                            tempCurrentPiece = new Knight(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, tempTempCurrentPiece.getCurrentTile());
                                            notValid = false;
                                        } else if (s.equals("Bishop")) {
                                            tempCurrentPiece = new Bishop(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, tempTempCurrentPiece.getCurrentTile());
                                            notValid = false;
                                        } else {
                                            notValid = true;
                                        }

                                }while (notValid) ;
                            }

                        } // End of PAWN checks






                        // Adding what piece was captured if there was a piece to be captured
                        if (playingBoard.board[yMoveTo][xMoveTo].isHasPiece()) {
                            Piece tempMoveToPiece = playingBoard.board[yMoveTo][xMoveTo].getCurrentPiece();
                            if (color == 'l') {
                                lightPlayer.addCapturedPiece(tempMoveToPiece);
                            } else {
                                darkPlayer.addCapturedPiece(tempMoveToPiece);
                            }

                        }else if(tempCurrentPiece.getClass().getSimpleName().equals(Pawn.class.getSimpleName())
                                && !playingBoard.board[yMoveTo][xMoveTo].isHasPiece()
                                && playingBoard.board[yCurrent][xCurrent].getLocation().getFile() != playingBoard.board[yMoveTo][xMoveTo].getLocation().getFile()
                                && playingBoard.board[yMoveTo][xMoveTo].getLocation().getFile() == playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile().getLocation().getFile()){
                            if (color == 'l') {
                                lightPlayer.addCapturedPiece(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile().getCurrentPiece());
                            } else {
                                darkPlayer.addCapturedPiece(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile().getCurrentPiece());
                            }
                            playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile().resetTile();
                        }

                        playingBoard.board[yCurrent][xCurrent].resetTile();
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
