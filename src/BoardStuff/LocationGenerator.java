package BoardStuff;

public class LocationGenerator {
    // Creates locations based on the y(rank) and x(file) position of a piece and y and x offset that is passed in
    static Board tempBoard = new Board(true);
    private static final File[] files = File.values();
    public static Location build(Location current, Integer fileOffset, Integer rankOffset){
        Integer currentFile = current.getFile().ordinal();
        if ( tempBoard.board[currentFile + fileOffset][current.getRank() + rankOffset] != null ) {
            return new Location(files[currentFile + fileOffset], current.getRank() + rankOffset);
        }else {return null;}

    }
}
