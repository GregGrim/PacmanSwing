package view;

import model.GameBoard;
import model.GameScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameWindow extends JDialog{
    private JFrame parentFrame;
    private GameBoard gameBoard;
    private GameTable gameTable;
    private JPanel infoPanel;
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private JLabel timeLabel;
    private JTextField textField;
    private Dimension dimension;

    private int rowNum;
    private int colNum;
    private ComponentListener componentListener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            int newHeight = gameTable.getHeight();
            int newWidth = gameTable.getWidth();
            int xBound = getWidth()-newWidth;
            int yBound = getHeight()-newHeight;
            int newCellSize=Math.min(newHeight/rowNum,newWidth/colNum);
            newHeight=newCellSize*rowNum;
            newWidth=newCellSize*colNum;
            gameTable.setCellSize(newCellSize);
            Rectangle r = getBounds();
            setBounds(r.x, r.y, newWidth+xBound, newHeight+yBound);
        }
        @Override
        public void componentMoved(ComponentEvent e) {}
        @Override
        public void componentShown(ComponentEvent e) {}
        @Override
        public void componentHidden(ComponentEvent e) {}
    };
    private ActionListener okButListener = actionEvent -> {
        GameScore gameScore = new GameScore();
        gameScore.setPlayerName(textField.getText());
        gameScore.setScore(gameBoard.getPacman().getScore());
        try {
            FileOutputStream fout = new FileOutputStream("gameScores.txt",true);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(gameScore);
            fout.close();
            oos.close();
            dispose();
        }catch (IOException e) {
            e.printStackTrace();
        }
    };

    private static ExecutorService exec = Executors.newCachedThreadPool();

    public GameWindow(JFrame parentFrame,int rowNum,int colNum) {
        super(parentFrame, "Game", true);
        this.parentFrame=parentFrame;
        addComponentListener(componentListener);
        setBackground(Color.BLACK);
        this.rowNum=rowNum;
        this.colNum=colNum;
        setLocation(0, 0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createGame();
        dimension = new Dimension(gameTable.cellSize() * colNum,gameTable.cellSize() * rowNum+40);
        setPreferredSize(dimension);
        setVisible(true);
    }

    @Override
    public void dispose() {
        gameTable.stop();
        gameBoard.stop();
        super.dispose();
    }
    public void createGame() {
        gameBoard = new GameBoard(rowNum,colNum);
        gameTable =new GameTable(gameBoard);
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(getWidth(),20));
        infoPanel.add(new JLabel("Score: "));
        infoPanel.add(scoreLabel=new JLabel());
        infoPanel.add(new JLabel("Lives: "));
        infoPanel.add(livesLabel=new JLabel());
        infoPanel.add(new JLabel("Time: "));
        infoPanel.add(timeLabel=new JLabel());
        infoPanel.setVisible(true);
        add(infoPanel);
        add(gameTable);
        pack();
        exec.execute(gameTable.getPainter(this));
    }
    public void restartGame() {
        gameTable.stop();
        gameBoard.stop();
        remove(gameTable);
        remove(infoPanel);
        createGame();
    }
    public void showGameOver() {
        gameTable.stop();
        gameBoard.stop();
        remove(gameTable);
        remove(infoPanel);
        repaint();
        JPanel panel = new JPanel();
        JLabel label = new JLabel("<html>Game Over<br>Your Score: "+gameBoard.getPacman().getScore()
                +"<br>Enter your name and press \"OK\" to continue</html>");
        label.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
        panel.add(label);
        textField = new JTextField(30);
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


