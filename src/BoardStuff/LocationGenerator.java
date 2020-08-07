package BoardStuff;

public class LocationGenerator {
    // Creates locations based on the y(rank) and x(file) position of a piece and y and x offset that is passed in
    private static final File[] files = File.values();
    public static Location build(Location current, Integer fileOffset, Integer rankOffset){
        Integer currentFile = current.getFile().ordinal();
        return new Location(files[currentFile + fileOffset], current.getRank() + rankOffset);

    }
}
