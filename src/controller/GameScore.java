package controller;

import java.io.Serial;
import java.io.Serializable;

public class GameScore implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int score;
    private String playerName;
    public GameScore() {
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    @Override
    public String toString() {
        return playerName+" :    "+score;
    }
}
