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
                allCommands.add(line);
            }
            fileReader.close();    //closes the stream and release the resources
            System.out.println("Contents of File: ");
            System.out.println(sb.toString());   //returns a string that textually represents the object
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Describe the commands in the file to console
    public void describeCommands(){
        Pattern p = Pattern.compile("^(((P|N|B|Q|K)(l|d))?([a-h]){1}(\\d{0,8}?)\\s?([a-h])?(\\d{0,8}))$");
        for(String command : allCommands){
            Matcher m = p.matcher(command);

        }
    }





}
