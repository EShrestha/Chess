package BoardStuff;

public class Board {
    private final int dimension = 8;
    Tile[][] board = new Tile[dimension][dimension];

    // If a file is passed in, setup board using this
    public Board(String chessCommandFiles){

    }

    // If no files are passed in, setup board using this
    public Board(){
        for(int i = 0; i < board.length; i++){
            int col = 0;
            TileColor currentColor = (i % 2 == 0) ? TileColor.LIGHT : TileColor.DARK;
            for(File file : File.values()){
                Tile newTile = new Tile(currentColor, new Location(file, i));
                board[i][col] = newTile;
                currentColor = (currentColor == TileColor.DARK) ? TileColor.LIGHT : TileColor.DARK;
                col++;
            }
        }

    }
}
