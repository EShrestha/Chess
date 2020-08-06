package Command;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandReader {

    ArrayList<Integer> fileNum = new ArrayList<>();
    ArrayList<Integer> rankNum = new ArrayList<>();
    ArrayList<String> allCommands = new ArrayList<>();

    public void readCommandFromFile(String fileName) {
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
        describeCommands();
    }

    //Describe the commands in the file to console
    public void describeCommands(){
        Pattern p = Pattern.compile("^((P|R|N|B|Q|K)?(l|d)?(?:([a-h])([1-8]))?\\s?([a-h])([1-8]))$");
        String color;
        for(String command : allCommands){
            //Group 2,3,6,7 for ?l?? or ?d??    EX: Pld4 or Qdh8
            //Group 4,5,6,7 for ?? ??           EX: c3 c4
            Matcher m = p.matcher(command);
            m.find();
            try {
                if ((!m.group(2).isEmpty()) || (m.group(2) != null)) {
                    color = m.group(3).equals("l") ? "Light" : "Dark";
                    System.out.println(color + " colored " + m.group(2) + " placed on " + m.group(6).toUpperCase() + "" + m.group(7));
                    // Would call a new method here to actually place that piece
                }
            }catch(NullPointerException e){
                System.out.println("Piece at "+m.group(4).toUpperCase()+""+m.group(5)+" moved to "+m.group(6).toUpperCase()+""+m.group(7));
                // Would call a new method here to validate the move and check for checks
            }

        }
    }





}