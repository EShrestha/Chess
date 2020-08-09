package BoardStuff;

import java.util.HashMap;

public class RankToRank {
    private HashMap<Integer, Integer> rankToRank = new HashMap<>(); // user input a1 get turned into rank 0,6 because [][] start 0,0 at the top left corner

    public RankToRank(){
        rankToRank.put(0,7);
        rankToRank.put(1,6);
        rankToRank.put(2,5);
        rankToRank.put(3,4);
        rankToRank.put(4,3);
        rankToRank.put(5,2);
        rankToRank.put(6,1);
        rankToRank.put(7,0);
    }

    public Integer getRank(Integer rank) {
        return rankToRank.get(rank);
    }
}
