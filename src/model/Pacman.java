package model;

import java.awt.*;

import static java.lang.Thread.sleep;

public class Pacman extends Character{

    private int mouthOpened = 0;
    private int score;
    private boolean mouthDirection = true;
    public Pacman (GameBoard gameBoard) {
        super(new Point(1,1), Direction.RIGHT, gameBoard,300);
        this.score=0;
        new Thread(()->{
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
        }).start();
    }

    @Override
    public void move() {
        super.move();
        Food food = gameBoard.getFoodMap().get(new Point(point.x,point.y));
        if(food!=null) {
            gameBoard.getFoodMap().remove(new Point(point.x,point.y));
            gameBoard.getAllItems().remove(food);
            score+=food.score();
        }
    }

    public int getMouthOpened() {
        return mouthOpened;
    }
    public int getScore() {
        return score;
    }
}
