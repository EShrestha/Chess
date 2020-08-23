package GUI;

import BoardStuff.*;
import Command.CommandReader;
import Controller.Game;
import Model.*;
import lib.ConsoleIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
    Player darkPlayer = new Player('d');
    public int movesMade = 0;
    public static ArrayList<String> saveGame = new ArrayList<>();
    boolean usingFile = false;
    boolean onceInvalidFileAlert = false;

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

        super("El Chess");
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
                        if (movesMade > 0) {
                            if (saveGame()) {
                                dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "To low of moves to save.", "You trying to waste storage?", 2);
                            dispose();
                        }
                    } else if (saveOrNot == JOptionPane.NO_OPTION) {
                        dispose();
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

        // Menu Bar
        final JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        if (useFile) {
            usingFile = true;
            readCommandFromFile(fileName);
        }
    }

    private JMenuBar createMenuBar(){
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFinalMenu());
        menuBar.setLayout(new GridBagLayout());
        menuBar.setPreferredSize(new Dimension(800,25));
        menuBar.setBackground(colorBlack);
        return  menuBar;
    }

    private JMenu createFinalMenu(){
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem titleScreen = new JMenuItem("Title Screen");
        titleScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
            }
        });

        fileMenu.add(titleScreen);
        return fileMenu;
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

    public class customMouseListener implements MouseListener {
        int clickedi = -1;
        int clickedj = -1;
        Color tempColor = Color.RED;
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

                    } else if (commandRight.trim().equals("")) {
                        if (!(files[clickedj].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(clickedi).toString()) + 1)).equals(commandLeft)) {

                            commandRight = (files[clickedj].toString().toLowerCase() + "" + (Integer.parseInt(rankToRank.getRank(clickedi).toString()) + 1));
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
                // do stuff
            }
            if (e.getButton() == MouseEvent.BUTTON3) { //right click



                if ((clickedi != -1 && clickedj != -1) && playingBoard.board[clickedi][clickedj].isHasPiece()) {
                    if(playingBoard.board[clickedi][clickedj].getCurrentPiece().getPieceColor() == (movesMade%2==0? PieceColor.LIGHT : PieceColor.DARK)) {
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
        char color = movesMade % 2 == 0 ? 'l' : 'd';
        // Group 1 2 3 4 ----> a 1  a 2
        Pattern p = Pattern.compile("^([a-h])(\\d)\\s([a-h])(\\d)$");
        Matcher m = p.matcher(command);
        if (!m.matches()) {
            return false;
        }


        // Getting x and y of the piece user wants ot move and the x and y of where user want to move piece to
        int xCurrent = Enum.valueOf(BoardStuff.File.class, m.group(1).toUpperCase()).ordinal();
        int yCurrent = rankToRank.getRank(Integer.parseInt(m.group(2)) - 1);
        int xMoveTo = Enum.valueOf(BoardStuff.File.class, m.group(3).toUpperCase()).ordinal();
        int yMoveTo = rankToRank.getRank(Integer.parseInt(m.group(4)) - 1);


        if (playingBoard.board[yCurrent][xCurrent].isHasPiece()) {
            // Getting a list of valid locations for the current piece
            List<Location> validLocations = playingBoard.board[yCurrent][xCurrent].getCurrentPiece().getValidMoves(playingBoard);

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

                Icon tempIcon = tiles[yCurrent][xCurrent].getIcon();
                tiles[yCurrent][xCurrent].setIcon(null);
                tiles[yMoveTo][xMoveTo].setIcon(tempIcon);

                saveGame.add(command);

                movesMade++;
                return true;
            } else {

                if(!onceInvalidFileAlert) {
                    if (usingFile) {
                        JOptionPane.showMessageDialog(this, "INVALID COMMAND READ FROM FILE", "Maybe turn it off and on?", 2);
                        movesMade++;
                        onceInvalidFileAlert = true;
                    }
                }
                return false;
            }

        } else {
            if(!onceInvalidFileAlert) {
                if (usingFile) {
                    JOptionPane.showMessageDialog(this, "INVALID COMMAND READ FROM FILE", "Maybe turn it off and on?", JOptionPane.WARNING_MESSAGE);
                    movesMade++;
                    onceInvalidFileAlert = true;
                }
            }

            return false;
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


}
