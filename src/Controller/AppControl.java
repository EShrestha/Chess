package Controller;

import BoardStuff.Board;
import Command.CommandReader;
import Model.*;
import lib.ConsoleIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppControl {


    //Board activeBoard;

    public void run(){
        System.out.println("Welcome to chess");
        menuControl();
        //new CommandReader().readCommandFromFile("chessCommands"); // .txt extension is implied
        //new Board().printBoard();
        System.out.println("Thank you for playing!");

    }

    private void menuControl() {
        int exit = -1;
        do {
            switch (promptMainmenu()) {
                case 1:
                    Board activeFileBoard = new Board(ConsoleIO.promptForString("Enter file name (no extensions): "));
                    new Game().playGame(activeFileBoard);
                    break;
                case 2:
                    Board activeBlankBoard = new Board(true);
                    new Game().playGame(activeBlankBoard);
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 0:
                    exit = 0;
                    break;

                default:
                    System.out.println("Woah, how did we get here?");
            }
        } while (exit != 0);
    }

    //Prompts the user with a menu
    private int promptMainmenu() {
        int selection = -1;
        int min = 0;
        int max = 4;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Choose one of the following options:\n")
                .append("\t1) Read from a file\n")
                .append("\t2) New Game\n")
                .append("\t3) BLANK\n")
                .append("\t4) BLANK\n")
                .append("\n\t0) Exit \n\n")
                .append("Enter the selection number: ");
        selection = ConsoleIO.promptForInt(sb.toString(), min, max);
        return selection;
    }
}
