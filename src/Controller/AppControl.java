package Controller;

import BoardStuff.Board;
import lib.ConsoleIO;

public class AppControl {


    //Board activeBoard;

    public void run() {
        System.out.println("Welcome to chess");
        menuControl();
        System.out.println("Thank you for playing!");

    }

    private void menuControl() {
        int exit = -1;
        do {
            switch (promptMainmenu()) {
                case 1:
                    Board activeFileBoard = new Board(ConsoleIO.promptForString("Enter file name (no extensions): "));
                    new Game(activeFileBoard);
                    break;
                case 2:
                    new Game();
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
        int selection;
        int min = 0;
        int max = 4;

        String sb = "\n" + "Choose one of the following options:\n" +
                "\t1) Read from a file\n" +
                "\t2) New Game\n" +
                "\t3) BLANK\n" +
                "\t4) BLANK\n" +
                "\n\t0) Exit \n\n" +
                "Enter the selection number: ";
        selection = ConsoleIO.promptForInt(sb, min, max);
        return selection;
    }
}
