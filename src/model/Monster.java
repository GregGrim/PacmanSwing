package model;

import java.awt.*;

import static java.lang.Thread.sleep;

public class Monster extends Character {
    private String name;
    private boolean vulnerable;
    private boolean alive;
    private int score;

    public Monster(String name,GameBoard gameBoard) {
        super(new Point(gameBoard.getRowNum()/2,gameBoard.getRowNum()/2), getRandomDirection(), gameBoard,400);
        this.name=name;
        this.score=250;
        this.vulnerable=false;
        this.alive=true;
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
        if(point.y==y&&point.x==x) {
            int distX=gameBoard.getPacman().point.x-x;
            int distY=gameBoard.getPacman().point.y-y;
            if(distX>0) {
                if(distY>0) {
                    if(distX>distY) {
                        direction=Direction.RIGHT;
                    } else direction=Direction.DOWN;
                } else {
                    if(distX>-distY) {
                    direction=Direction.RIGHT;
                    } else direction=Direction.UP;
                }
            } else {
                if(distY>0) {
                    if(-distX>distY) {
                        direction=Direction.LEFT;
                    } else direction=Direction.DOWN;
                } else {
                    if(-distX>-distY) {
                        direction=Direction.LEFT;
                    } else direction=Direction.UP;
                }

            }

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
}
