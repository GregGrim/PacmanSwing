package model;

import java.io.Serializable;

public class GameScore implements Serializable {
    private int score;
    private String playerName;
    public GameScore(String playerName) {
        this.playerName=playerName;
        this.score=0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
