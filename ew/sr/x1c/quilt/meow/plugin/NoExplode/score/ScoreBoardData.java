package ew.sr.x1c.quilt.meow.plugin.NoExplode.score;

public class ScoreBoardData implements Comparable<ScoreBoardData> {

    private final String name;
    private final int score;

    public ScoreBoardData(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoreBoardData other) {
        if (score != other.score) {
            return ((Integer) other.score).compareTo(score);
        }
        return name.compareTo(other.name);
    }
}
