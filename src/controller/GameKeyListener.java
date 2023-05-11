package controller;

import model.GameBoard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static model.items.Character.Direction.*;
import static model.items.Character.Direction.RIGHT;

public class GameKeyListener implements KeyListener {
    private final GameBoard gameBoard;

    public GameKeyListener(GameBoard gameBoard) {
        this.gameBoard=gameBoard;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w' : if(gameBoard.getPacman().getDirection()!=UP)gameBoard.getPacman().setDirection(UP);
                break;
            case 's' : if(gameBoard.getPacman().getDirection()!=DOWN)gameBoard.getPacman().setDirection(DOWN);
                break;
            case 'a' : if(gameBoard.getPacman().getDirection()!=LEFT)gameBoard.getPacman().setDirection(LEFT);
                break;
            case 'd' : if(gameBoard.getPacman().getDirection()!=RIGHT)gameBoard.getPacman().setDirection(RIGHT);
                break;
        }
//        System.out.println(e.getKeyChar());
    }
    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

}
