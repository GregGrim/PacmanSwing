package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GameTable extends JTable {
    private GameController gameController;
    private boolean isRunning = true;
    private GameWindow gameWindow;
    private final Thread painter = new Thread(() -> {
        try {
            while(isRunning) {
                repaint();
                if(gameController.boardOver()) {
                    gameWindow.restartGame();
                    sleep(1500);
                }
                if(gameController.gameOver()) {
                    gameWindow.showGameOver();
                }
                sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    public GameTable (GameController gameController) {
        super();
        this.gameController=gameController;
        setModel(gameController.getGameTableModel());
        setBackground(Color.BLACK);
        setCellSelectionEnabled(false);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0,0));
        setFocusable(false);
        addKeyListener(gameController.getKeyListener());
    }
    public Runnable getPainter(GameWindow gameWindow) {
        this.gameWindow=gameWindow;
        return painter;
    }
    public void stop () {
        isRunning=false;
        try {
            painter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resizeTable(int newCellSize) {
        setRowHeight(newCellSize);
        for (int i = 0; i < gameController.getColNum(); i++) {
            getColumnModel().getColumn(i).setPreferredWidth(newCellSize);
            getColumnModel().getColumn(i).setMaxWidth(newCellSize);
            getColumnModel().getColumn(i).setMinWidth(newCellSize);
        }
    }
}
