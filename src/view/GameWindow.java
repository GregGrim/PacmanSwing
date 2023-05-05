package view;

import model.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class GameWindow extends JDialog{
    private GameBoard gameBoard;
    private GameTable gameTable;
    private Dimension dimension;

    private boolean isRunning = true;
    private int rowNum;

    private static ExecutorService exec = Executors.newCachedThreadPool();

    public GameWindow(JFrame parentFrame,int rowNum) {
        super(parentFrame, "Game", true);
        this.rowNum=rowNum;
        setLocation(0, 0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dimension = new Dimension(800/rowNum * rowNum,800/rowNum * rowNum+40);
        setPreferredSize(dimension);
        setResizable(false);
        createGame();
        setVisible(true);
    }

    @Override
    public void dispose() {
        gameTable.stop();
        gameBoard.stop();
        super.dispose();
    }
    public void createGame() {
        gameBoard = new GameBoard(rowNum);
        gameTable =new GameTable(gameBoard);
        add(gameTable);
        pack();
        exec.execute(gameTable.getPainter(this));
    }
    public void restartGame() {
        gameTable.stop();
        gameBoard.stop();
        remove(gameTable);
        createGame();
    }
}


