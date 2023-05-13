package model.items;

import model.GameBoard;
import model.items.Character;

import java.awt.*;

import static java.lang.Thread.sleep;

public class Monster extends Character {
    private String name;
    private boolean vulnerable;
    private boolean alive;
    private int score;
    private int looping;
    private int legsPosition = 0;
    private Thread monsterThread = new Thread(()->{
        while (isRunning) {
            try {
                sleep(150);
                if(legsPosition==0) legsPosition++;
                else legsPosition--;
            } catch (InterruptedException e) {
                isRunning = false;
            }
        }
    });
    private Thread createUpgrade = new Thread(()->{
        while (isRunning) {
            try {
                sleep(5000);
                if(Math.random()<0.25) gameBoard.createUpgrade(point.x,point.y);
            } catch (InterruptedException ignored) {
            }
        }
    });

    public Monster(String name, GameBoard gameBoard) {
        super(new Point(gameBoard.getColNum()-2,gameBoard.getRowNum()-2), getRandomDirection(), gameBoard,400);
        this.name=name;
        this.score=250;
        this.vulnerable=false;
        this.alive=true;
        this.looping=0;
        monsterThread.start();
        createUpgrade.start();
    }
    private static Direction getRandomDirection() {
        switch ((int) (Math.random() * 4)) {
            case 0 -> {
                return Direction.UP;
            }
            case 1 -> {
                return Direction.DOWN;
            }
            case 2 -> {
                return Direction.LEFT;
            }
            case 3 -> {
                return Direction.RIGHT;
            }
        }
        return null;
    }

    @Override
    public void move() {
        int x = point.x;
        int y = point.y;
        super.move();

        int distX = gameBoard.getPacman().point.x - x;
        int distY = gameBoard.getPacman().point.y - y;
        if (distX > 0) {
            if (distY > 0) {
                if (distX > distY) {
                    direction = Direction.RIGHT;
                } else direction = Direction.DOWN;
            } else {
                if (distX > -distY) {
                    direction = Direction.RIGHT;
                } else direction = Direction.UP;
            }
        } else {
            if (distY > 0) {
                if (-distX > distY) {
                    direction = Direction.LEFT;
                } else direction = Direction.DOWN;
            } else {
                if (-distX > -distY) {
                    direction = Direction.LEFT;
                } else direction = Direction.UP;
            }
        }
        if(point.y==y&&point.x==x) { // checks if monsters stays in wall direction
            if(looping++>3) {
                looping=0;
                direction=getRandomDirection();
            }
        } else {
            looping=0;
        }

    }

    public String getName() {
        return name;
    }

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getScore() {
        return score;
    }

    public void deathProcess() {
        setAlive(false);
    }

    public int getLegsPosition() {
        return legsPosition;
    }
    @Override
    public void stop() {
        super.stop();
        try {
            monsterThread.join();
            createUpgrade.interrupt();
            createUpgrade.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
