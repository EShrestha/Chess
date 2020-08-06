package Controller;

import Command.CommandReader;

public class AppControl {

    public void run(){
        new CommandReader().readCommandFromFile("chessCommands"); // .txt extension is implied

    }
}
