package BoardStuff;

public class LocationGenerator {
    // Creates locations based on the y(rank) and x(file) position of a piece and y and x offset that is passed in
    Board tempBoard = new Board(true);
    private static final File[] files = File.values();
    public static Location build(Location current, Integer fileOffset, Integer rankOffset){
        Integer currentFile = current.getFile().ordinal();

        //DBUG System.out.println("File: " + (currentFile + fileOffset) + " Rank: " + (current.getRank() + rankOffset));

        if(((currentFile + fileOffset) < 8 && (current.getRank() + rankOffset) < 8) &&
                ((currentFile + fileOffset) > -1 && (current.getRank() + rankOffset) > 0 )) {
            return new Location(files[currentFile + fileOffset], current.getRank() + rankOffset);
        }else{
            return null;
        }

    }
}
