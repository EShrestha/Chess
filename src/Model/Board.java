package Model;

public class Board {
    private final int dimension = 8;
    int[][] board = new int[dimension][dimension];

    // If a file is passed in, setup board using this
    public Board(String chessCommandFiles){

    }

    // If no files are passed in, setup board using this
    public Board(){

    }
}
