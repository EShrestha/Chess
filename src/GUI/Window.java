package GUI;

import BoardStuff.Board;
import BoardStuff.File;
import BoardStuff.Location;
import BoardStuff.RankToRank;
import Controller.Game;
import Model.Piece;
import Model.Player;
import lib.ConsoleIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Window extends JFrame
{

    private Container contents;

    private String commandLeft = "";
    private int commandLeftRank = -1;
    private int commandLeftFile = -1;
    private String commandRight = "";
    private RankToRank rankToRank = new RankToRank();
    private static final File[] files = File.values();

    private Board playingBoard = new Board(true);
    Player lightPlayer = new Player('l');
    Player darkPlayer = new Player('d');
    public int movesMade = 0;

    //Components:
    private JButton[][] tiles = new JButton[8][8];

    //Colors:
    private Color colorBlack = Color.black;

    // Current position:
    private int row = 7;
    private int col = 1;

    // Images
    private ImageIcon wPawn = new ImageIcon("src/Icons/wP.png");
    private ImageIcon wRook = new ImageIcon("src/Icons/wR.png");
    private ImageIcon wKnight = new ImageIcon("src/Icons/wN.png");
    private ImageIcon wBishop = new ImageIcon("src/Icons/wB.png");
    private ImageIcon wQueen = new ImageIcon("src/Icons/wQ.png");
    private ImageIcon wKing = new ImageIcon("src/Icons/wK.png");

    private ImageIcon dPawn = new ImageIcon("src/Icons/dP.png");
    private ImageIcon dRook = new ImageIcon("src/Icons/dR.png");
    private ImageIcon dKnight = new ImageIcon("src/Icons/dN.png");
    private ImageIcon dBishop = new ImageIcon("src/Icons/dB.png");
    private ImageIcon dQueen = new ImageIcon("src/Icons/dQ.png");
    private ImageIcon dKing = new ImageIcon("src/Icons/dK.png");



    public Window(){
        super("El Chess");

        contents = getContentPane();
        contents.setLayout(new GridLayout(8,8));

        //Create event handlers:
        ButtonHandler buttonHandler = new ButtonHandler();

        // Create and add board components:
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                tiles[i][j] = new JButton();
                tiles[i][j].setBackground(((i+j) % 2 != 0) ? Color.gray : Color.white);
                contents.add(tiles[i][j]);
                tiles[i][j].addActionListener(buttonHandler);
            }
        }
        // Where we set the Icon of each button
        tiles[0][0].setIcon(dRook);
        tiles[0][1].setIcon(dKnight);
        tiles[0][2].setIcon(dBishop);
        tiles[0][3].setIcon(dQueen);
        tiles[0][4].setIcon(dKing);
        tiles[0][5].setIcon(dBishop);
        tiles[0][6].setIcon(dKnight);
        tiles[0][7].setIcon(dRook);

        tiles[1][0].setIcon(dPawn);
        tiles[1][1].setIcon(dPawn);
        tiles[1][2].setIcon(dPawn);
        tiles[1][3].setIcon(dPawn);
        tiles[1][4].setIcon(dPawn);
        tiles[1][5].setIcon(dPawn);
        tiles[1][6].setIcon(dPawn);
        tiles[1][7].setIcon(dPawn);

        tiles[7][0].setIcon(wRook);
        tiles[7][1].setIcon(wKnight);
        tiles[7][2].setIcon(wBishop);
        tiles[7][3].setIcon(wQueen);
        tiles[7][4].setIcon(wKing);
        tiles[7][5].setIcon(wBishop);
        tiles[7][6].setIcon(wKnight);
        tiles[7][7].setIcon(wRook);

        tiles[6][0].setIcon(wPawn);
        tiles[6][1].setIcon(wPawn);
        tiles[6][2].setIcon(wPawn);
        tiles[6][3].setIcon(wPawn);
        tiles[6][4].setIcon(wPawn);
        tiles[6][5].setIcon(wPawn);
        tiles[6][6].setIcon(wPawn);
        tiles[6][7].setIcon(wPawn);



        // Size and display window
        setSize(800, 800);
        setResizable(false);
        setLocationRelativeTo(null); // Centers window
        setVisible(true);
    }


    private boolean isValidMove(int i, int j){
        return false;
    }

    private void processClick(String command, int imoveTo, int jMoveTo){

        if(makeValidMove(command)){
            Icon tempIcon = tiles[commandLeftRank][commandLeftFile].getIcon();
            tiles[commandLeftRank][commandLeftFile].setIcon(null);
            tiles[imoveTo][jMoveTo].setIcon(tempIcon);
            return;
        }
    }


    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    if(source == tiles[i][j])
                    {
                        int i1 = i;
                        int j1 = j;



                            if (commandLeft.trim().equals("")) {
                                commandLeft = (files[j].toString().toLowerCase()+""+(Integer.parseInt(rankToRank.getRank(i).toString()) + 1));
                                commandLeftRank = i;
                                commandLeftFile = j;

                            }else if(commandRight.trim().equals("")) {
                                if (!(files[j].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(i).toString()) + 1)).equals(commandLeft)){

                                    commandRight = (files[j].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(i).toString()) + 1));
                                    processClick((commandLeft+" "+commandRight), i, j);
                                }

                                //System.out.println("COMMAND: " + commandLeft+" "+commandRight);
                                System.out.println("RANK1: " + i1 + " FILE1: " + j1);
                                System.out.println("RANK2: " + i + " FILE2: " + j);
                                commandLeft = "";
                                commandLeftRank = -1;
                                commandLeftFile = -1;
                                commandRight = "";
                            }

                            return;

                    }
                }
            }
        }
    }


    public boolean makeValidMove(String command){
        char color = movesMade % 2 == 0 ? 'l' : 'd';
        // Group 1 2 3 4 ----> a 1  a 2
        Pattern p = Pattern.compile("^([a-h])(\\d)\\s([a-h])(\\d)$");
        Matcher m = p.matcher(command);
        if(!m.matches()){return false;}


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

                    movesMade++;
                    return true;
                } else {
                    System.out.println("***INVALID MOVE***");
                    System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
                    return false;
                }

            }else {
                System.out.println("***INVALID MOVE***");
                System.out.print(color == 'l' ? "LIGHT -> " : "DARK -> ");
                return false;
            }

    }





}
