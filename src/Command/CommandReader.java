package Command;

import BoardStuff.Board;
import BoardStuff.Location;
import BoardStuff.Tile;
import BoardStuff.TileColor;
import Model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandReader {

    ArrayList<Integer> fileNum = new ArrayList<>();
    ArrayList<Integer> rankNum = new ArrayList<>();
    ArrayList<String> allCommands = new ArrayList<>();
    Tile[][] board = new Tile[8][8];

    public Tile[][] readCommandFromFile(String fileName) {
        allCommands.clear();
        try {
            File file = new File(fileName+".txt");    //creates a new file instance
            FileReader fileReader = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fileReader);  //creates a buffering character input stream
            StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while ((line = br.readLine()) != null) {
                //sb.append(line);      //appends line to string buffer
                //sb.append("\n");     //line feed
                if (!line.toString().startsWith("#")){
                    allCommands.add(line);
                }
            }
            fileReader.close();    //closes the stream and release the resources
        } catch (IOException e) {
            e.printStackTrace();
        }
        // After adding each command to the command we call describeCommands which will print what each command did in th econsole.
        makeBlankBoard(board.length);
        describeCommands();
        return board;
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
            Integer x = Integer.parseInt(m.group(7))-1; // 0-7
            System.out.println(m.group());

            try {
                // if xlxx type of command was passed in
                if ((!m.group(2).isEmpty()) || (m.group(2) != null)) {
                    color = m.group(3).equals("l") ? PieceColor.LIGHT : PieceColor.DARK;
                    // Sets color based on if l or d was in the command
                    colorStr = m.group(3).equals("l") ? "Light" : "Dark";
                   // Prints to console what the command did
                    System.out.println(colorStr + " colored " + m.group(2) + " placed on " + m.group(6).toUpperCase() + "" + m.group(7));

                    Piece piece;


                        //Checks for type of piece
                       if( m.group(2).equals("P")) { // PAWN
                           piece = new Pawn(color);
                           piece.setCurrentTile(board[x][y]);
                           board[x][y].setCurrentPiece(piece);

                       }else if( m.group(2).equals("R")) { // Rook
                           piece = new Rook(color);
                           piece.setCurrentTile(board[x][y]);
                           board[x][y].setCurrentPiece(piece);

                       }else if( m.group(2).equals("B")) { // Bishop
                           piece = new Bishop(color);
                           piece.setCurrentTile(board[x][y]);
                           board[x][y].setCurrentPiece(piece);

                       }else if( m.group(2).equals("N")) { // Knight
                           piece = new Knight(color);
                           piece.setCurrentTile(board[x][y]);
                           board[x][y].setCurrentPiece(piece);

                       }else if( m.group(2).equals("Q")) { // Queen
                           piece = new Queen(color);
                           piece.setCurrentTile(board[x][y]);
                           board[x][y].setCurrentPiece(piece);

                       }else if( m.group(2).equals("K")) { // King
                           piece = new King(color);
                           piece.setCurrentTile(board[x][y]);
                           board[x][y].setCurrentPiece(piece);

                       }
            }
            }catch(NullPointerException e){
                // Prints to console what each piece did
                System.out.println("Piece at "+m.group(4).toUpperCase()+""+m.group(5)+" moved to "+m.group(6).toUpperCase()+""+m.group(7));

            }

        }
    }

    public void makeBlankBoard(int DIMENSION){
        for(int i = 0; i < board.length; i++){
            int col = 0;
            TileColor currentColor = (i % 2 == 0) ? TileColor.LIGHT : TileColor.DARK;
            for(BoardStuff.File file : BoardStuff.File.values()){
                Tile newTile = new Tile(currentColor, new Location(file, DIMENSION-i));
                //locationTileMap.put(newTile.getLocation(), newTile);
                board[i][col] = newTile;
                currentColor = (currentColor == TileColor.DARK) ? TileColor.LIGHT : TileColor.DARK;
                col++;
            }
        }

    }





}
