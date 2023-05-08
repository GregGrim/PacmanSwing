package view;

import controller.KeyController;
import model.GameBoard;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GameTable extends JTable {
    private GameBoard gameBoard;
    private GameTableModel gameTableModel;
    private boolean isRunning = true;
    private GameWindow gameWindow;
    private Runnable painter = () -> {
        try {
            while(isRunning) {
                repaint();
                if(gameBoard.boardOver()) {
                    sleep(1500);
                    gameWindow.restartGame();
                }
                if(gameBoard.gameOver()) {
                    gameWindow.showGameOver();
                }
                sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };


    public GameTable (GameBoard gameBoard) {
        super();
        gameTableModel=new GameTableModel(gameBoard);
        setModel(gameTableModel);
        this.gameBoard = gameBoard;
        setCellSize(cellSize());
        setBackground(Color.BLACK);
        setCellSelectionEnabled(false);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0,0));
        setDefaultRenderer(VCharacter.class, (table, value, isSelected, hasFocus, row, column) -> (Component) value);

        addKeyListener(new KeyController(gameBoard));
    }
    public Runnable getPainter(GameWindow gameWindow) {
        this.gameWindow=gameWindow;
        return painter;
    }
    public void stop () {
        isRunning=false;
    }
    public int cellSize() {
        return gameTableModel.getCellSize();
    }
    public void setCellSize(int newCellSize) {
        gameTableModel.setCellSize(newCellSize);
        setRowHeight(newCellSize);
        for (int i = 0; i < gameBoard.getColNum(); i++){
            getColumnModel().getColumn(i).setPreferredWidth(newCellSize);
        }
    }

}
