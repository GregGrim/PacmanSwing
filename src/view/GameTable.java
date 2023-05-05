package view;

import controller.KeyController;
import model.GameBoard;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GameTable extends JTable {
    GameBoard gameBoard;
    private boolean isRunning = true;
    private GameWindow gameWindow;
    private Runnable painter = () -> {
        try {
            while(isRunning) {
                repaint();
                if(gameBoard.boardOver()) {
                    sleep(2000);
                    gameWindow.restartGame();
                }
                sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };


    public GameTable (GameBoard gameBoard) {
        super(new GameTableModel(gameBoard));
        this.gameBoard = gameBoard;
        int cellSize=800/ gameBoard.getRowNum();
        setBackground(Color.BLACK);
        setCellSelectionEnabled(false);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0,0));
        setDefaultRenderer(VCharacter.class, (table, value, isSelected, hasFocus, row, column) -> (Component) value);
        setRowHeight(cellSize);
        for (int i = 0; i < getColumnCount(); i++){
            getColumnModel().getColumn(i).setPreferredWidth(cellSize);
        }
        addKeyListener(new KeyController(gameBoard));
    }
    public Runnable getPainter(GameWindow gameWindow) {
        this.gameWindow=gameWindow;
        return painter;
    }
    public void stop () {
        isRunning=false;
    }

}
