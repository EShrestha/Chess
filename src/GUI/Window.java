package GUI;

import BoardStuff.*;
import Controller.Game;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Window extends JFrame {

    private Container contents;

    private String commandLeft = "";
    private int commandLeftRank = -1;
    private int commandLeftFile = -1;
    private String commandRight = "";
    private RankToRank rankToRank = new RankToRank();
    public ArrayList<String> allCommands = new ArrayList<>();
    private static final File[] files = File.values();

    private Board playingBoard;
    Player lightPlayer = new Player('l');
    List<Icon> lightCaps = new LinkedList<>();
    Player darkPlayer = new Player('d');
    List<Icon> darkCaps = new LinkedList<>();
    public int movesMade = 0;
    public static ArrayList<String> saveGame = new ArrayList<>();
    boolean usingFile = false;
    boolean onceInvalidFileAlert = false;
    // Menu Bar
    final JMenuBar menuBar = createMenuBar();
    final JMenu darkMenu = new JMenu();
    final JMenu fileMenu = new JMenu("LIGHT Player To Make First Move");
    final JMenu lightMenu = new JMenu();
    final int CAP_ICON = 25;


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


    public Window(String fileName, boolean useFile) {

        super("CHESS");
        playingBoard = new Board(true);
        contents = getContentPane();
        contents.setLayout(new GridLayout(8, 8));

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Close/Resign", "Never mind"};
                int PromptResult = JOptionPane.showOptionDialog(null, "What would you like to do?", "Leaving so soon?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    String saveBtns[] = {"Yes", "No"};
                    int saveOrNot = JOptionPane.showOptionDialog(null, "Would you like to save game?", "Before you go...", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, saveBtns, saveBtns[1]);
                    if (saveOrNot == JOptionPane.YES_OPTION) {
                        if (Game.movesMade > 0) {
                            if (saveGame()) {
                                Game.movesMade = 0;
                                reset();
                                dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "To low of moves to save.", "You trying to waste storage?", 2);
                            Game.movesMade = 0;
                            reset();
                            dispose();
                        }
                    } else if (saveOrNot == JOptionPane.NO_OPTION) {
                        dispose();
                        reset();
                        Game.movesMade = 0;
                    }
                } else if (PromptResult == JOptionPane.NO_OPTION) {
                    // Cancel button, exits prompt only
                }
            }
        });


        //Create event handlers:
        ButtonHandler buttonHandler = new ButtonHandler();

        // Create and add board components:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new JButton();
                tiles[i][j].setBackground(((i + j) % 2 != 0) ? Color.gray : Color.white);
                contents.add(tiles[i][j]);
                //tiles[i][j].addActionListener(buttonHandler);
                tiles[i][j].addMouseListener(new customMouseListener());
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
        setSize(800, 825); // extra 25 pixels to account for the jmenubar at the top
        setResizable(false);
        setLocationRelativeTo(null); // Centers window
        setVisible(true);

        setJMenuBar(menuBar);
        menuBar.add(darkMenu);
        menuBar.add(fileMenu);
        menuBar.add(lightMenu);

        if (useFile) {
            usingFile = true;
            readCommandFromFile(fileName);
        }
    }

    private JMenuBar createMenuBar(){
        final JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new GridBagLayout());
        menuBar.setPreferredSize(new Dimension(800,25));
        menuBar.setBackground(Color.white);
        menuBar.setForeground(Color.black);
        return  menuBar;
    }

    private boolean saveGame() {
        String fileName = "Chess_" + java.time.LocalDate.now() + ".txt";
        try {

            java.io.File myObj = new java.io.File("src/SavedGames/"+fileName);
            if (myObj.createNewFile()) {

            } else {
                JOptionPane.showMessageDialog(this, "Try again.", "File seems to already exist.", 2);
                return false;
            }
        } catch (IOException e) {
            // Can alert that an error occurred here
            return false;
        }

        try {
            FileWriter fileWriter = new FileWriter("src/SavedGames/"+fileName);
            for (String c : saveGame) {
                fileWriter.write(c + "\n");
            }
            fileWriter.close();
            // Can alert that the game saved here
        } catch (IOException e) {
            // Can alert that an error occurred here
            return false;
        }

        return true;
    }



    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {


            Object source = e.getSource();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (source == tiles[i][j]) {
                        int i1 = i;
                        int j1 = j;


                        if (playingBoard.board[i][j].isHasPiece() || !commandLeft.trim().equals("")) {
                            if (commandLeft.trim().equals("")) {
                                commandLeft = (files[j].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(i).toString()) + 1));
                                commandLeftRank = i;
                                commandLeftFile = j;

                            } else if (commandRight.trim().equals("")) {
                                if (!(files[j].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(i).toString()) + 1)).equals(commandLeft)) {

                                    commandRight = (files[j].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(i).toString()) + 1));
                                    //processClick((commandLeft + " " + commandRight), i, j);
                                    makeValidMove(commandLeft + " " + commandRight);
                                }

                                //System.out.println("COMMAND: " + commandLeft+" "+commandRight);
                                commandLeft = "";
                                commandLeftRank = -1;
                                commandLeftFile = -1;
                                commandRight = "";
                            }
                        }

                        return;

                    }
                }
            }
        }

    }

    Color tempFirstClickColor;
    Color tempSecondClickColor;
    int tempIFirstClick;
    int tempJFirstClick;
    int tempISecondClick;
    int tempJSecondClick;
    public class customMouseListener implements MouseListener {
        int clickedi = -1;
        int clickedj = -1;

        int tempbtnx = -1;
        int tempbtny = -1;
        public void mouseClicked(MouseEvent e) {
            Object source = e.getSource();


            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (source == tiles[i][j]) {

                        clickedi = i;
                        clickedj = j;
                        break;

                    }
                }
            }

            if (e.getButton() == MouseEvent.BUTTON1) { // left click


                if (playingBoard.board[clickedi][clickedj].isHasPiece() || !commandLeft.trim().equals("")) {
                    if (commandLeft.trim().equals("")) {
                        commandLeft = (files[clickedj].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(clickedi).toString()) + 1));
                        commandLeftRank = clickedi;
                        commandLeftFile = clickedj;
                        tempFirstClickColor = tiles[clickedi][clickedj].getBackground();
                        tempIFirstClick = clickedi;
                        tempJFirstClick = clickedj;
                        tiles[clickedi][clickedj].setBackground(Color.PINK);

                    } else if (commandRight.trim().equals("")) {
                        if (!(files[clickedj].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(clickedi).toString()) + 1)).equals(commandLeft)) {

                            commandRight = (files[clickedj].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(clickedi).toString()) + 1));
                            tempSecondClickColor = tiles[clickedi][clickedj].getBackground();
                            tempISecondClick = clickedi;
                            tempJSecondClick = clickedj;
                            tiles[clickedi][clickedj].setBackground(Color.ORANGE);
                            //processClick((commandLeft + " " + commandRight), i, j);
                            makeValidMove(commandLeft + " " + commandRight);
                        }

                        //System.out.println("COMMAND: " + commandLeft+" "+commandRight);
                        commandLeft = "";
                        commandLeftRank = -1;
                        commandLeftFile = -1;
                        commandRight = "";
                        tiles[tempIFirstClick][tempJFirstClick].setBackground(tempFirstClickColor);
                        tiles[tempISecondClick][tempJSecondClick].setBackground(tempSecondClickColor);

                    }

                }
                // do stuff
            }
            if (e.getButton() == MouseEvent.BUTTON3) { //right click



                if ((clickedi != -1 && clickedj != -1) && playingBoard.board[clickedi][clickedj].isHasPiece()) {
                    if(playingBoard.board[clickedi][clickedj].getCurrentPiece().getPieceColor() == (Game.movesMade%2==0? PieceColor.LIGHT : PieceColor.DARK)) {
                        // Getting a list of valid locations for the current piece
                        List<Location> validLocations = playingBoard.board[clickedi][clickedj].getCurrentPiece().getValidMoves(playingBoard);
                        JPopupMenu popupMenu = new JPopupMenu("TITLE HERE");

                        for (Location l : validLocations) {
                            JMenuItem x = new JMenuItem("    "+l.getFile().toString().toLowerCase() + "" + l.getRank()+"    ");

                            int tempRank = (rankToRank.getRank(clickedi)) + 1;
                            x.addActionListener(new menuActionListener(files[clickedj].toString().toLowerCase() +  tempRank) );
                            popupMenu.add(x);
                        }

                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        //popupMenu.setSize(1000, 200);
                        popupMenu.setVisible(true);
                    }

                }

            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    class menuActionListener implements ActionListener {
        String temp = "";
        public menuActionListener(String cL){
           temp = cL;
        }
        public void actionPerformed(ActionEvent e) {
            makeValidMove(temp+" "+e.getActionCommand().trim());
        }
    }



    public boolean makeValidMove(String command) {
        char color = Game.movesMade % 2 == 0 ? 'l' : 'd';
        Pattern p = Pattern.compile("^([a-h])(\\d)\\s([a-h])(\\d)$");
        Matcher m = p.matcher(command);
        Map<Location, Tile> tileMap = playingBoard.getLocationTileMap();


        if(!m.matches()){
            return false;
        }

            // Getting x and y of the piece user wants ot move and the x and y of where user want to move piece to
            Integer xCurrent = Enum.valueOf(File.class, m.group(1).toUpperCase()).ordinal();
            Integer yCurrent = rankToRank.getRank(Integer.parseInt(m.group(2)) - 1);
            Integer xMoveTo = Enum.valueOf(File.class, m.group(3).toUpperCase()).ordinal();
            Integer yMoveTo = rankToRank.getRank(Integer.parseInt(m.group(4)) - 1);


            if (playingBoard.board[yCurrent][xCurrent].isHasPiece()) {
                // Getting a list of valid locations for the current piece
                List<Location> validLocations = playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getValidMoves(playingBoard);
                System.out.println("VALID LOCATIONS: " + validLocations);

                // Checking if the piece color user wants to move is actually the players color (l/d) and the move they want to make is in the valid locations list
                if (color == playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getShortColor()
                        && validLocations.contains(playingBoard.board[yMoveTo][xMoveTo].getLocation())) {


                    ////////////////TEMP BOARD CHECKS////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    Board tempBoard = createTempBoard(playingBoard); // Creating a copy board: TEMP board
                    Map<Location, Tile> TempTileMap = tempBoard.getLocationTileMap();
                    tempBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false); // Setting the move piece as already moved once
                    Piece tempTempCurrentPiece = tempBoard.board[yCurrent][xCurrent].getCurrentPiece(); // Capturing the current piece user wants to move


                    // Saving current piece information
                    // Making a copy of the piece user wants to move then resetting that tile the piece was on
                    Piece tempCurrentPiece = playingBoard.board[yCurrent][xCurrent].getCurrentPiece();
                    Icon tempPieceIcon = tiles[yCurrent][xCurrent].getIcon();

                    // SPECIAL MOVES OF KING in TEMPBOARD
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

                    // SPECIAL MOVES OF PAWN in TEMPBOARD
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
                                TempTileMap.get(locationLeft).getCurrentPiece().setEnPassantEnabledOnMove(Game.movesMade);
                                TempTileMap.get(locationLeft).getCurrentPiece().setEnPassantTile(tempBoard.board[yMoveTo][xMoveTo]);
                            }
                            // For a pawn on the right
                            if (TempTileMap.get(locationRight) != null && TempTileMap.get(locationRight).isHasPiece() && TempTileMap.get(locationRight).getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())) {
                                pieceRight = TempTileMap.get(locationRight).getCurrentPiece();
                                TempTileMap.get(locationRight).getCurrentPiece().setCanEnPassant(true);
                                TempTileMap.get(locationRight).getCurrentPiece().setEnPassantEnabledOnMove(Game.movesMade);
                                TempTileMap.get(locationRight).getCurrentPiece().setEnPassantTile(tempBoard.board[yMoveTo][xMoveTo]);
                            }
                        }

                        if((tempBoard.board[yMoveTo][xMoveTo].getLocation().getRank() == 8 && color == 'l') || (tempBoard.board[yMoveTo][xMoveTo].getLocation().getRank() == 1 && color == 'd')) {
                            boolean notValid = true;
                            Image image = (color == 'l' ? wPawn : dPawn).getImage();
                            Image newImg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            ImageIcon finalImg = new ImageIcon(newImg);

                            menuBar.removeAll();

                            do {
                                Object[] options = {"Queen", "Rook", "Knight", "Bishop"};
                                String s = (String) JOptionPane.showInputDialog(
                                        this,
                                        "Promote Pawn To: ",
                                        "Promotion!",
                                        JOptionPane.PLAIN_MESSAGE,
                                        finalImg,
                                        options,
                                        "Queen");

                                if (s != null && s.trim().length() > 0) {
                                    if (s.equals("Queen")) {
                                        tempCurrentPiece = new Queen(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, tempTempCurrentPiece.getCurrentTile());
                                        tempPieceIcon = color == 'l' ? wQueen : dQueen;
                                        notValid = false;
                                    } else if (s.equals("Rook")) {
                                        tempCurrentPiece = new Rook(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, tempTempCurrentPiece.getCurrentTile());
                                        tempPieceIcon = color == 'l' ? wRook : dRook;
                                        notValid = false;
                                    } else if (s.equals("Knight")) {
                                        tempCurrentPiece = new Knight(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, tempTempCurrentPiece.getCurrentTile());
                                        tempPieceIcon = color == 'l' ? wKnight : dKnight;
                                        notValid = false;
                                    } else if (s.equals("Bishop")) {
                                        tempCurrentPiece = new Bishop(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, tempTempCurrentPiece.getCurrentTile());
                                        tempPieceIcon = color == 'l' ? wBishop : dBishop;
                                        notValid = false;
                                    } else {
                                        notValid = true;
                                    }
                                }


                            }while (notValid) ;
                        }
                    } // End of PAWN checks

                        tempBoard.board[yCurrent][xCurrent].resetTile(); // Resetting the current tile so it is a blank tile
                        tempBoard.board[yMoveTo][xMoveTo].setCurrentPiece(tempTempCurrentPiece); // Moving the captured piece to the move to tile
                        tempBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setCurrentTile(tempBoard.board[yMoveTo][xMoveTo]); // Adding the tiles location to the piece

                    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if(new King().checkForCheck(tempBoard, color == 'l'? Board.TEMPlightKingsTile : Board.TEMPdarkKingsTile).isEmpty()) {


                        // Marking the piece as already moved once
                        if(!playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getClass().getSimpleName().equals(King.class.getSimpleName())) {
                            playingBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);
                        }
                        // If the piece that moved is the king then their tile location is updated as well in the Board class
                        if(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getClass().getSimpleName().equals(King.class.getSimpleName())){
                            if(color == 'l'){
                                Board.lightKingsTile = playingBoard.board[yMoveTo][xMoveTo];

                            }else {
                                Board.darkKingsTile = playingBoard.board[yMoveTo][xMoveTo];

                            }

                            if(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().isFirstMove()
                                    && (Math.abs(playingBoard.board[yMoveTo][xMoveTo].getLocation().getFile().ordinal() - playingBoard.board[yCurrent][xCurrent].getLocation().getFile().ordinal()) == 2)){

                                System.out.println("MOVED KING 2 SPACES");
                                // Means user wants to castle to the left side of the board
                                if(playingBoard.board[yMoveTo][xMoveTo].getLocation().getFile().ordinal() - playingBoard.board[yCurrent][xCurrent].getLocation().getFile().ordinal() < 0){
                                    System.out.println("User wants to castle long");
                                    if(color == 'l') {
                                        System.out.println("Light wants to castle long");
                                        playingBoard.board[7][0].resetTile();
                                        tiles[7][0].setIcon(null);
                                        playingBoard.board[7][3].setCurrentPiece(new Rook(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, playingBoard.board[7][3]));
                                        playingBoard.board[7][3].getCurrentPiece().setFirstMove(false);
                                        tiles[7][3].setIcon(wRook);
                                        playingBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);

                                    }else{
                                        System.out.println("Dark wants to castle long");
                                        playingBoard.board[0][0].resetTile();
                                        tiles[0][0].setIcon(null);
                                        playingBoard.board[0][3].setCurrentPiece(new Rook(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, playingBoard.board[0][3]));
                                        playingBoard.board[0][3].getCurrentPiece().setFirstMove(false);
                                        tiles[0][3].setIcon(dRook);
                                        playingBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);

                                    }

                                }
                                // Means user wants to castle to the right side of the board
                                if(playingBoard.board[yMoveTo][xMoveTo].getLocation().getFile().ordinal() - playingBoard.board[yCurrent][xCurrent].getLocation().getFile().ordinal() > 0){
                                    if(color == 'l') {
                                        System.out.println("Light wants to castle short");
                                        playingBoard.board[7][7].resetTile();
                                        tiles[7][7].setIcon(null);
                                        playingBoard.board[7][5].setCurrentPiece(new Rook(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, playingBoard.board[7][5]));
                                        playingBoard.board[7][5].getCurrentPiece().setFirstMove(false);
                                        tiles[7][5].setIcon(wRook);
                                        playingBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);
                                    }else {
                                        System.out.println("Dark wants to castle short");
                                        playingBoard.board[0][7].resetTile();
                                        tiles[0][7].setIcon(null);
                                        playingBoard.board[0][5].setCurrentPiece(new Rook(color == 'l' ? PieceColor.LIGHT : PieceColor.DARK, playingBoard.board[0][5]));
                                        playingBoard.board[0][5].getCurrentPiece().setFirstMove(false);
                                        tiles[0][5].setIcon(dRook);
                                        playingBoard.board[yCurrent][xCurrent].getCurrentPiece().setFirstMove(false);
                                    }

                                }
                            }
                        }// END OF KING SPECIAL MOVES

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
                                    tileMap.get(locationLeft).getCurrentPiece().setEnPassantEnabledOnMove(Game.movesMade);
                                    tileMap.get(locationLeft).getCurrentPiece().setEnPassantTile(playingBoard.board[yMoveTo][xMoveTo]);
                                }
                                // For a pawn on the right
                                if (tileMap.get(locationRight) != null && tileMap.get(locationRight).isHasPiece() && tileMap.get(locationRight).getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())) {
                                    pieceRight = tileMap.get(locationRight).getCurrentPiece();
                                    tileMap.get(locationRight).getCurrentPiece().setCanEnPassant(true);
                                    tileMap.get(locationRight).getCurrentPiece().setEnPassantEnabledOnMove(Game.movesMade);
                                    tileMap.get(locationRight).getCurrentPiece().setEnPassantTile(playingBoard.board[yMoveTo][xMoveTo]);
                                }
                            }

                            if(playingBoard.board[yMoveTo][xMoveTo].getLocation().getRank() == 8 && color == 'l'){
                                System.out.println("LIGHT gets to promote pawn here.");
                            }
                            if(playingBoard.board[yMoveTo][xMoveTo].getLocation().getRank() == 1 && color == 'd'){
                                System.out.println("DARK gets to promote pawn here");
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
                            tiles[rankToRank.getRank(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile().getLocation().getRank() - 1)][playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile().getLocation().getFile().ordinal()].setIcon(null);
                            System.out.println("TILE: " + playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile());
                            System.out.println("RANK: " + rankToRank.getRank(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile().getLocation().getRank() - 1));
                            System.out.println("FILE: " + playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getEnPassantTile().getLocation().getFile().ordinal());

                        }

//                        if(playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getClass().getSimpleName().equals(King.class.getSimpleName())){
//                            if(color == 'l'){
//                                Board.lightKingMoved = true;
//                            }else{
//                                Board.darktKingMoved = true;
//                            }
//                        }

                        playingBoard.board[yCurrent][xCurrent].resetTile();
                        tiles[yCurrent][xCurrent].setIcon(null);
                        playingBoard.board[yMoveTo][xMoveTo].setCurrentPiece(tempCurrentPiece);
                        playingBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setFirstMove(false);
                        tiles[yMoveTo][xMoveTo].setIcon(tempPieceIcon);

                        //Setting what tile the piece is on for the piece that just moved
                        playingBoard.board[yMoveTo][xMoveTo].getCurrentPiece().setCurrentTile(playingBoard.board[yMoveTo][xMoveTo]);

                        // After move made do these
                        saveGame.add(command);
                        Game.movesMade++;
                        //String turn = getCapturedPieces(darkPlayer) + "    |    " + "TURN: " + (Game.movesMade % 2 == 0 ? "LIGHT" : "DARK") + "    |    PREVIOUS MOVE: " + tempCurrentPiece.getClass().getSimpleName().toUpperCase() + " " + command.toUpperCase() + "    |    " + getCapturedPieces(lightPlayer);
                        String turn = ("TURN: " + (Game.movesMade % 2 == 0 ? "LIGHT" : "DARK") + "  |  PREVIOUS MOVE: " + tempCurrentPiece.getClass().getSimpleName().toUpperCase() + " " + command.toUpperCase());
                        //Back ground color changes
                        updateColorAndText(turn);
                        isCheckMate(tempBoard, color);
                        return true;

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
                    if(!onceInvalidFileAlert) {
                        if (usingFile) {
                            JOptionPane.showMessageDialog(this, "INVALID COMMAND READ FROM FILE", "Maybe turn it off and on?", 2);
                            Game.movesMade++;
                            onceInvalidFileAlert = true;
                        }
                    }
                    return false;
                }

            }else {
                if(!onceInvalidFileAlert) {
                    if (usingFile) {
                        JOptionPane.showMessageDialog(this, "INVALID COMMAND READ FROM FILE", "Maybe turn it off and on?", JOptionPane.WARNING_MESSAGE);
                        Game.movesMade++;
                        onceInvalidFileAlert = true;
                    }
                }

                return false;
            }

        return false;
    }

    public void isCheckMate(Board tempBoard, char color){
        System.out.println("IN HERE");
        Map<Location, Tile> tileMap = tempBoard.getLocationTileMap();

        if(color == 'd' && Board.TEMPlightKingsTile.getCurrentPiece().getValidMoves(tempBoard).isEmpty()) {
            System.out.println("A");
            // Light king has no valid moves left and had a piece attacking it
            if (!new King().checkForCheck(tempBoard, color == 'd' ? Board.TEMPlightKingsTile : Board.TEMPdarkKingsTile).isEmpty()) {
                System.out.println("B");
                Location firstAttackingPiece = new King().checkForCheck(tempBoard, color == 'd' ? Board.TEMPlightKingsTile : Board.TEMPdarkKingsTile).get(0);
                if(new King().checkForCheck(tempBoard,tileMap.get(firstAttackingPiece)).isEmpty()){
                   // If any tile between the kings tile and the attacking piece tile can be reached by a light color piece
                    //if(){

                    System.out.println("DARK WINS BY CHECKMATE");
                    JOptionPane.showMessageDialog(this, "DARK win by checkmate", "GAME OVER!",2);
                    reset();
                    this.dispose();
                    //}

                }

            }
        }
        if(color == 'l' && Board.TEMPdarkKingsTile.getCurrentPiece().getValidMoves(tempBoard).isEmpty()) {
            System.out.println("D");
            // Dark king has no valid moves left and has a piece attacking it
            if (!new King().checkForCheck(tempBoard, color == 'l' ? Board.TEMPdarkKingsTile : Board.TEMPlightKingsTile).isEmpty()) {
                System.out.println("E");
                Location firstAttackingPiece = new King().checkForCheck(tempBoard, color == 'l' ? Board.TEMPdarkKingsTile : Board.TEMPlightKingsTile).get(0);
                if(new King().checkForCheck(tempBoard,tileMap.get(firstAttackingPiece)).isEmpty()){
                    System.out.println("LIGHT WINS BY CHECKMATE!");
                    JOptionPane.showMessageDialog(this, "LIGHT win by checkmate", "GAME OVER!",2);
                    reset();
                    this.dispose();
                }
            }
        }
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
        Piece tempPiece = null;
        PieceColor tempColor;
        Map<Location, Tile> tileMap = originalBoard.getLocationTileMap();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j< 8; j++){

                tempBoard.board[i][j].setTileColor(originalBoard.board[i][j].getTileColor());
                tempBoard.board[i][j].setLocation(originalBoard.board[i][j].getLocation());
                if(originalBoard.board[i][j].isHasPiece()){
                    tempColor = originalBoard.board[i][j].getCurrentPiece().getPieceColor();
                    if(originalBoard.board[i][j].getCurrentPiece().getClass().getSimpleName().equals(Pawn.class.getSimpleName())){
                        tempPiece = new Pawn(tempColor, tempBoard.board[i][j]);
                        tempPiece.setFirstMove(originalBoard.board[i][j].getCurrentPiece().isFirstMove());
                    }
                    if(originalBoard.board[i][j].getCurrentPiece().getClass().getSimpleName().equals(Rook.class.getSimpleName())){
                        tempPiece = new Rook(tempColor, tempBoard.board[i][j]);
                        tempPiece.setFirstMove(originalBoard.board[i][j].getCurrentPiece().isFirstMove());
                    }
                    if(originalBoard.board[i][j].getCurrentPiece().getClass().getSimpleName().equals(Knight.class.getSimpleName())){
                        tempPiece = new Knight(tempColor, tempBoard.board[i][j]);
                        tempPiece.setFirstMove(originalBoard.board[i][j].getCurrentPiece().isFirstMove());
                    }
                    if(originalBoard.board[i][j].getCurrentPiece().getClass().getSimpleName().equals(Bishop.class.getSimpleName())){
                        tempPiece = new Bishop(tempColor, tempBoard.board[i][j]);
                        tempPiece.setFirstMove(originalBoard.board[i][j].getCurrentPiece().isFirstMove());
                    }
                    if(originalBoard.board[i][j].getCurrentPiece().getClass().getSimpleName().equals(Queen.class.getSimpleName())){
                        tempPiece = new Queen(tempColor, tempBoard.board[i][j]);
                        tempPiece.setFirstMove(originalBoard.board[i][j].getCurrentPiece().isFirstMove());
                    }
                    if(originalBoard.board[i][j].getCurrentPiece().getClass().getSimpleName().equals(King.class.getSimpleName())){
                        tempPiece = new King(tempColor, tempBoard.board[i][j]);
                        tempPiece.setFirstMove(originalBoard.board[i][j].getCurrentPiece().isFirstMove());
                    }

                    tempBoard.board[i][j].setCurrentPiece(tempPiece);
                }


            }
        }
        return tempBoard;
    }


    final int iconWidth = 20;
    final int iconHeight = 20;
    public void updateColorAndText(String textToSet){
        int pawns = 0;
        int rooks = 0;
        int knights = 0;
        int bishops = 0;
        int queens = 0;
        menuBar.removeAll();
        if(Game.movesMade % 2 == 0){
            menuBar.setBackground(Color.WHITE);
            fileMenu.setForeground(Color.GRAY);
        }else {
            menuBar.setBackground(Color.GRAY);
            fileMenu.setForeground(Color.WHITE);
        }

        if(true) {
            System.out.println("IN Y");
            for (Piece p : lightPlayer.getCapturedPieces()) {
                System.out.println("IN Z");
                if (p.getClass().getSimpleName().equals("Pawn")) {
                    pawns++;
                }
                if (p.getClass().getSimpleName().equals("Rook")) {
                    rooks++;
                }
                if (p.getClass().getSimpleName().equals("Queen")) {
                    queens++;
                }
                if (p.getClass().getSimpleName().equals("Knight")) {
                    knights++;
                }
                if (p.getClass().getSimpleName().equals("Bishop")) {
                    bishops++;
                }
            }

            Image wP = dPawn.getImage();
            Image ia = wP.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu a = new JMenu();
            a.setIcon(new ImageIcon(ia));
            if (pawns > 0) {
                a.setText(pawns + "x");
            }else{
                a.setText("   ");
            }
            menuBar.add(a);

            Image wB = dBishop.getImage();
            Image ib = wB.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu b = new JMenu();
            b.setIcon(new ImageIcon(ib));
            if (bishops > 0) {
                b.setText(bishops + "x");
            }else{
                b.setText("   ");
            }
            menuBar.add(b);

            Image wK = dKnight.getImage();
            Image ik = wK.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu c = new JMenu();
            c.setIcon(new ImageIcon(ik));
            if (knights > 0) {
                c.setText(knights + "x");
            }else{
                c.setText("   ");
            }
            menuBar.add(c);

            Image wR = dRook.getImage();
            Image ir = wR.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu d = new JMenu();
            d.setIcon(new ImageIcon(ir));
            if (rooks > 0) {
                d.setText(rooks + "x");
            }else{
                d.setText("   ");
            }
            menuBar.add(d);

            Image wQ = dQueen.getImage();
            Image iq = wQ.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu e = new JMenu();
            e.setIcon(new ImageIcon(iq));
            if (queens > 0) {
                e.setText(queens + "x");
            }else{
                e.setText("   ");
            }
            menuBar.add(e);
        }



        pawns = 0;
        rooks = 0;
        knights = 0;
        bishops = 0;
        queens = 0;
        fileMenu.setText(textToSet);
        menuBar.add(fileMenu);

        if(true){
            System.out.println("IN Y");
            for(Piece p : darkPlayer.getCapturedPieces()){
                System.out.println("IN Z");
                if(p.getClass().getSimpleName().equals("Pawn")){pawns++ ;}
                if(p.getClass().getSimpleName().equals("Rook")){rooks++ ;}
                if(p.getClass().getSimpleName().equals("Queen")){queens++ ;}
                if(p.getClass().getSimpleName().equals("Knight")){knights++ ;}
                if(p.getClass().getSimpleName().equals("Bishop")){bishops++ ;}
            }


            Image wQ = wQueen.getImage();
            Image iq = wQ.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu e = new JMenu();
            e.setIcon(new ImageIcon(iq));
            if(queens > 0){e.setText(queens+"x");}else{
                e.setText("   ");
            }
            menuBar.add(e);

            Image wR = wRook.getImage();
            Image ir = wR.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu d = new JMenu();
            d.setIcon(new ImageIcon(ir));
            if(rooks > 0){d.setText(rooks+"x");}else{
                d.setText("   ");
            }
            menuBar.add(d);

            Image wK = wKnight.getImage();
            Image ik = wK.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu c = new JMenu();
            c.setIcon(new ImageIcon(ik));
            if(knights > 0){c.setText(knights+"x");}else{
                c.setText("   ");
            }
            menuBar.add(c);

            Image wB = wBishop.getImage();
            Image ib = wB.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu b = new JMenu();
            b.setIcon(new ImageIcon(ib));
            if(bishops > 0){b.setText(bishops+"x");}else{
                b.setText("   ");
            }
            menuBar.add(b);



            Image wP = wPawn.getImage();
            Image ia = wP.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            JMenu a = new JMenu();
            a.setIcon(new ImageIcon(ia));
            if(pawns > 0){a.setText(pawns+"x");}else{
                a.setText("   ");
            }
            menuBar.add(a);


        }
    }


    public void readCommandFromFile(String fileName) {
        allCommands.clear();
        try {
            java.io.File file = new java.io.File("src/SavedGames/"+fileName + ".txt");    //creates a new file instance
            FileReader fileReader = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fileReader);  //creates a buffering character input stream
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    allCommands.add(line);
                }
            }
            fileReader.close();    //closes the stream and release the resources
        } catch (IOException e) {
            e.printStackTrace();
        }
        // After adding each command to the allCommands array we call describeCommands which will print what each command did to the console.
        for (String c : allCommands) {
            makeValidMove(c);
            saveGame.add(c);
        }
        usingFile = false;

    }

    public void reset(){
        Game.movesMade = 0;
        lightPlayer.getCapturedPieces().clear();
        darkPlayer.getCapturedPieces().clear();
        saveGame.clear();
        allCommands.clear();
    }


}
