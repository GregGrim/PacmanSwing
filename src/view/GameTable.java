package view;

import controller.GameController;
import view.items.VCharacter;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GameTable extends JTable {
    private GameController gameController;
    private boolean isRunning = true;
    private GameWindow gameWindow;
    private Runnable painter = () -> {
        try {
            while(isRunning) {
                repaint();
                if(gameController.boardOver()) {
                    sleep(1500);
                    gameWindow.restartGame();
                }
                if(gameController.gameOver()) {
                    gameWindow.showGameOver();
                }
                sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };
    public GameTable (GameController gameController) {
        super();
        this.gameController=gameController;
        setModel(gameController.getGameTableModel());
        setBackground(Color.BLACK);
        setCellSelectionEnabled(false);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0,0));
        setDefaultRenderer(VCharacter.class, (table, value, isSelected, hasFocus, row, column) -> (Component) value);
        addKeyListener(gameController.getKeyListener());
    }
    public Runnable getPainter(GameWindow gameWindow) {
        this.gameWindow=gameWindow;
        return painter;
    }
    public void stop () {
        isRunning=false;
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
