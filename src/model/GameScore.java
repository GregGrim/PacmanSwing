package model;

import java.io.Serializable;

public class GameScore implements Serializable {
    private int score;
    private String playerName;
    public GameScore(String playerName,int score) {
        this.playerName=playerName;
        this.score=score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
