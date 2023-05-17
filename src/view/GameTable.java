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
                gameWindow.updateStats();
                if(gameController.boardOver()) {
                    SwingUtilities.invokeLater(()->gameWindow.restartGame());
                    sleep(1500);
                }
                if(gameController.gameOver()) {
                    SwingUtilities.invokeLater(()->gameWindow.showGameOver());
                }
                sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    public GameTable (GameController gameController, GameWindow gameWindow) {
        super();
        this.gameWindow=gameWindow;
        this.gameController=gameController;
        setModel(gameController.getGameTableModel());
        setBackground(Color.BLACK);
        setCellSelectionEnabled(false);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0,0));
        setFocusable(false);
        addKeyListener(gameController.getKeyListener());
        painter.start();
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

    @Override
    public void repaint(Rectangle r) {
        super.repaint(r);
    }
}
