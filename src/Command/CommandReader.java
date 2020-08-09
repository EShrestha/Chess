package Command;

import BoardStuff.*;
import Model.*;
import lib.ConsoleIO;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandReader {

    RankToRank rankToRank = new RankToRank();
    ArrayList<String> allCommands = new ArrayList<>();
    boolean describeToConsole;
    Board b = new Board(false);



    public Tile[][] readCommandFromFile(String fileName) {
        allCommands.clear();
        try {
            File file = new File(fileName+".txt");    //creates a new file instance
            FileReader fileReader = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fileReader);  //creates a buffering character input stream
            StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")){
                    allCommands.add(line);
                }
            }
            fileReader.close();    //closes the stream and release the resources
        } catch (IOException e) { e.printStackTrace();}
        // After adding each command to the allCommands array we call describeCommands which will print what each command did to the console.
        describeCommands();
        return b.board;
    }

    //Describe the commands in the file to console
    public void describeCommands(){
        Pattern p = Pattern.compile("^((P|R|B|N|Q|K)?(l|d)?(?:([a-h])([1-8]))?\\s?([a-h])([1-8]))$");
        String colorStr;
        PieceColor color;
        for(String command : allCommands){
            //Group 2,3,6,7 for ?l?? or ?d??    EX: Pld4 or Qdh8
            //Group 4,5,6,7 for ?? ??           EX: c3 c4
            Matcher m = p.matcher(command);
            m.find();
            Enum file = Enum.valueOf(BoardStuff.File.class,m.group(6).toUpperCase());
            Integer y = file.ordinal(); // 0-7
            Integer x = rankToRank.getRank(Integer.parseInt(m.group(7))- 1); // 0-7
            System.out.println(m.group());

            try {
                // if xlxx type of command was passed in
                if ((!m.group(2).isEmpty()) || (m.group(2) != null)) {
                    color = m.group(3).equals("l") ? PieceColor.LIGHT : PieceColor.DARK;
                    // Sets color based on if l or d was in the command
                    colorStr = m.group(3).equals("l") ? "Light" : "Dark";
                   // Prints to console what the command did
                    if(describeToConsole){ System.out.println(colorStr + " colored " + m.group(2) + " placed on " + m.group(6).toUpperCase() + "" + m.group(7));}
                    Piece piece = m.group(2).equals("P") ? new Pawn(color) : m.group(2).equals("R") ? new Rook(color) : m.group(2).equals("B") ? new Bishop(color) : m.group(2).equals("N") ? new Knight(color) : m.group(2).equals("Q") ? new Queen(color) : m.group(2).equals("K") ? new King(color) : null;


                    //Setting the pieces current tile that it is on
                    piece.setCurrentTile(b.board[x][y]);
                    //Setting the piece
                    b.board[x][y].setCurrentPiece(piece);
                }
            }catch(NullPointerException e){

                // if move to File and Rank are amongst the list of valid moves the do the following
                Piece tempPiece = b.board[rankToRank.getRank(Integer.parseInt(m.group(5))- 1)][Enum.valueOf(BoardStuff.File.class,m.group(4).toUpperCase()).ordinal()].getCurrentPiece();
                b.board[rankToRank.getRank(Integer.parseInt(m.group(5))- 1)][Enum.valueOf(BoardStuff.File.class,m.group(4).toUpperCase()).ordinal()].resetTile();
                b.board[rankToRank.getRank(Integer.parseInt(m.group(7))- 1)][Enum.valueOf(BoardStuff.File.class,m.group(6).toUpperCase()).ordinal()].resetTile();
                b.board[rankToRank.getRank(Integer.parseInt(m.group(7))- 1)][Enum.valueOf(BoardStuff.File.class,m.group(6).toUpperCase()).ordinal()].setCurrentPiece(tempPiece);
                if(describeToConsole){ System.out.println("Piece at "+m.group(4).toUpperCase()+""+m.group(5)+" moved to "+m.group(6).toUpperCase()+""+m.group(7));}
                //System.out.println(board[7][0].getCurrentPiece().getName());

            }
        }
    }

}
