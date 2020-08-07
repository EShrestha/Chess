package Controller;

import BoardStuff.Board;
import Command.CommandReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppControl {

    Board activeBoard;

    public void run(){
        System.out.println("Welcom to chess");
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
                    activeBoard = new Board(promptForString("Enter file name (no extensions): "));
                    play(activeBoard);
                    break;
                case 2:
                    activeBoard = new Board();
                    play(activeBoard);
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
        selection = promptForInt(sb.toString(), min, max);
        return selection;
    }

    //Starts the game with the given board
    private void play(Board board){
        board.printBoard();

        // Probably make a player instance or something the prompt for moves idk

    }



    private int promptForInt(String prompt, int min, int max) {
        int num = 0;
        boolean isInvalid = true;
        do {
            String input = promptForString(prompt);
            try {
                num = Integer.parseInt(input);
                isInvalid = num < min || num > max;
            } catch (NumberFormatException nfe) {
            }
            if (isInvalid) {
                System.out.println("--INPUT MUST BE VALID--\n");
            }
        } while (isInvalid);
        return num;
    }

    private static String promptForString(String prompt) {
        BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        boolean isInvalid;
        do {
            System.out.print(prompt);
            try {
                input = buffy.readLine();
            } catch (IOException ioe) {
                System.out.println("--Woah, there was a technical issue, let's try again.--");
            }
            isInvalid = input == null || input.isEmpty() || input.trim().equals("");
            if (isInvalid) {
                System.out.println("--INPUT MUST BE VALID--\n");
            }

        } while (isInvalid);
        return input;
    }
}
