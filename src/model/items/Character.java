package model.items;

import model.GameBoard;
import model.items.Item;

import java.awt.*;

import static java.lang.Thread.sleep;

public abstract class Character extends Item {
    protected boolean isRunning=true;
    private int timeToMove;

    public enum Direction {
        UP(90), DOWN(270), LEFT(180), RIGHT(0);
        private final int value;
        Direction(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    protected Direction direction;
    private Thread runner = new Thread(() -> {
        try {
            while(isRunning) {
                move();
                sleep(timeToMove);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    public Character (Point p, Direction d, GameBoard gameBoard, int timeToMove) {
        super(p, gameBoard);
        this.timeToMove=timeToMove;
        direction=d;
    }
    public Direction getDirection() {
        return direction;
    }
    public synchronized void move() {
        switch (direction) {
            case UP : {
                if(gameBoard.getBlocks().get(new Point(point.x, point.y - 1)) == null) {
                        if(point.y>0) {
                            point.y-=1;
                        } else point.y=gameBoard.getRowNum()-1;
                    }
                }
                break;
            case DOWN : {
                if(gameBoard.getBlocks().get(new Point(point.x, point.y + 1)) == null) {
                    if(point.y<gameBoard.getRowNum()-1) {
                        point.y+=1;
                    } else point.y=0;
                }
            }
                break;
            case LEFT : {
                if(gameBoard.getBlocks().get(new Point(point.x-1, point.y)) == null) {
                    if(point.x>0) {
                        point.x-=1;
                    } else point.x=gameBoard.getColNum()-1;
                }
            }
                break;
            case RIGHT : {

                if(gameBoard.getBlocks().get(new Point(point.x+1, point.y)) == null) {
                    if(point.x<gameBoard.getColNum()-1) {
                        point.x+=1;
                    } else point.x=0;
                }
            }
            break;
        }

    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void stop() {
        isRunning=false;
        try {
            runner.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Runnable getRunner() {
        return runner;
    }

    public void setTimeToMove(int timeToMove) {
        this.timeToMove = timeToMove;
    }

    public int getTimeToMove() {
        return timeToMove;
    }
}
