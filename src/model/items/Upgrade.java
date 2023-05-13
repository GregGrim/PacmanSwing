package model.items;

import model.GameBoard;

import java.awt.*;

import static java.lang.Thread.sleep;
// upgrade that monster is leaving behind and can be picked up by pacman
public class Upgrade extends Item{
    // five different types of significant upgrades
    public enum Type {
        SPEED, LIVES, TELEPORT, INVULNERABILITY, DOUBLE_POINTS
    }
    private final Type type;
    public Upgrade(Point p, GameBoard gameBoard, Type type) {
        super(p, gameBoard);
        this.type=type;
    }

    public Type getType() {
        return type;
    }
    // function is called when pacman is picking up upgrade
    // implements upgrade mechanics
    public void callUpgrade() {
        switch (type) {
            case LIVES -> gameBoard.getPacman().setLives(gameBoard.getPacman().getLives()+1);
            case SPEED -> gameBoard.getPacman().setTimeToMove(gameBoard.getPacman().getTimeToMove()-50);
            case TELEPORT -> {
                gameBoard.getPacman().point.x=1;
                gameBoard.getPacman().point.y=1;
            }
            case INVULNERABILITY ->
                    new Thread(()->{
                gameBoard.getPacman().setInvulnerability(true);
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameBoard.getPacman().setInvulnerability(false);
            }).start();
            case DOUBLE_POINTS ->
                    new Thread(()->{
                gameBoard.setDoublePoints(true);
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameBoard.setDoublePoints(false);
            }).start();
        }
    }
}
