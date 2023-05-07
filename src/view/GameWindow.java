package view;

import model.GameBoard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class GameWindow extends JDialog{
    private JFrame parentFrame;
    private GameBoard gameBoard;
    private GameTable gameTable;
    private Dimension dimension;

    private int rowNum;
    private ActionListener okButListener = e -> dispose();

    private static ExecutorService exec = Executors.newCachedThreadPool();

    public GameWindow(JFrame parentFrame,int rowNum) {
        super(parentFrame, "Game", true);
        this.parentFrame=parentFrame;
        setBackground(Color.BLACK);
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
    public void showGameOver() {
        gameTable.stop();
        gameBoard.stop();
        remove(gameTable);
        repaint();
        Border border = BorderFactory.createLineBorder(Color.YELLOW, 3);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("<html>Game Over<br>Your Score: "+gameBoard.getPacman().getScore()
                +"<br>Enter your name and press \"OK\" to continue</html>");
        label.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
        panel.add(label);
        JTextField textField = new JTextField(30);
        textField.setFont(Font.getFont(Font.SERIF));
        panel.add(textField);
        JButton button = new JButton("OK");
        button.setPreferredSize(new Dimension(90,30));
        button.setFocusable(false);
        button.addActionListener(okButListener);
        panel.add(button);
        add(panel);
        pack();
        setVisible(true);
    }
}


