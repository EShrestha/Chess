package BoardStuff;

import java.util.Objects;


public class Location {
    private final File file;
    private final Integer intFile;
    private final Integer rank;
    public RankToRank rankToRank = new RankToRank();

    public Location(File file, Integer rank){
        this.file = file;
        this.rank = rank;
        this.intFile = file.ordinal();
    }

    public File getFile() {
        return file;
    }

    public Integer getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(file, location.file) &&
                Objects.equals(rank, location.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    @Override
    public String toString() {
        return "Location{" +
                "file=" + file +
                ", rank=" + rank +
                '}';
    }
}
