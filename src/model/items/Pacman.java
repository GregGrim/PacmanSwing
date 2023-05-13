package model.items;

import model.GameBoard;
import model.items.Character;
import model.items.Food;
import model.items.Monster;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Pacman extends Character {

    private int mouthOpened = 0;
    private int score;
    private int lives;
    private boolean mouthDirection = true;
    private boolean invulnerability = false;
    private Thread pacmanThread = new Thread(()->{
        while (isRunning) {
            try {
                sleep(10);
                mouthOpened+=mouthDirection?10:-10;
                if(mouthOpened==90||mouthOpened==0) {
                    mouthDirection=!mouthDirection;
                }
            } catch (InterruptedException e) {
                isRunning = false;
            }
        }
    });
    public Pacman (GameBoard gameBoard) {
        super(new Point(1,1), Direction.RIGHT, gameBoard,300);
        this.score=0;
        this.lives=1;
        pacmanThread.start();
    }

    @Override
    public void move() {
        super.move();
        Food food = gameBoard.getFoodMap().get(new Point(point.x,point.y));
        if(food!=null) {
            gameBoard.getFoodMap().remove(new Point(point.x,point.y));
            gameBoard.getAllItems().remove(food);
            score+=gameBoard.isDoublePoints()?food.score()*2:food.score();
        }
        List<Monster> monsters = new ArrayList<>(gameBoard.getMonsters());
        for (Monster monster:
                monsters) {
            if(monster.getPoint().equals(point)) {
                if(monster.isVulnerable()&&monster.isAlive()) {
                    monster.deathProcess();
                    score+=monster.getScore();
                } else if(monster.isAlive()&&!monster.isVulnerable()&&!invulnerability){
                    deathProcess();
                    return;
                }
            }
        }
    }
    public int getMouthOpened() {
        return mouthOpened;
    }
    public int getScore() {
        return score;
    }
    public int getLives() {
        return lives;
    }
    public void deathProcess() {
        lives--;
        System.out.println(lives);
        // TODO death animation!
        point.x=1;
        point.y=1;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setInvulnerability(boolean invulnerability) {
        this.invulnerability = invulnerability;
    }
    public boolean isInvulnerability() {
        return invulnerability;
    }
    @Override
    public void stop() {
        super.stop();
        try {
            pacmanThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
