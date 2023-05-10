package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameWindow extends JDialog{
    GameController gameController;
    private GridBagConstraints gbc = new GridBagConstraints();
    private GameTable gameTable;
    private JPanel infoPanel;
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private JLabel timeLabel;
    private JTextField textField;
    private Dimension dimension;
    private ComponentListener componentListener = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            int newHeight = gameTable.getHeight();
            int newWidth = gameTable.getWidth();
            int xBound = getWidth()-newWidth;
            int yBound = getHeight()-newHeight;
            int newCellSize=Math.min(newHeight/gameController.getRowNum(),newWidth/gameController.getColNum());
            newHeight=newCellSize*gameController.getRowNum();
            newWidth=newCellSize*gameController.getColNum();
            gameController.setCellSize(newCellSize);
            Rectangle r = getBounds();
            setBounds(r.x, r.y, newWidth+xBound, newHeight+yBound);
            gameTable.resizeTable(newCellSize);
        }
        @Override
        public void componentMoved(ComponentEvent e) {}
        @Override
        public void componentShown(ComponentEvent e) {}
        @Override
        public void componentHidden(ComponentEvent e) {}
    };
    private ActionListener okButListener = actionEvent -> {
        if(!textField.getText().equals("")){
        gameController.saveScore(textField.getText());
        dispose();
        } else JOptionPane.showMessageDialog(this, "Please enter your name :)",
                "Error", JOptionPane.ERROR_MESSAGE);
    };

    private static ExecutorService exec = Executors.newCachedThreadPool();

    public GameWindow(JFrame parentFrame,int rowNum,int colNum) {
        super(parentFrame, "Game", true);
        addComponentListener(componentListener);
        setBackground(Color.BLACK);
        setLocation(0, 0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createGameField(rowNum,colNum);
        dimension = new Dimension(gameController.getCellSize() * colNum,gameController.getCellSize() * rowNum+40);
        setPreferredSize(dimension);
        setVisible(true);
    }

    @Override
    public void dispose() {
        gameTable.stop();
        gameController.stopGame();
        super.dispose();
    }
    public void createGameField(int rowNum,int colNum) {
        gameController = new GameController(rowNum,colNum);
        createGameFieldComponents();
    }
    public void createGameField(int rowNum,int colNum, int score, int cellSize) {
        gameController = new GameController(rowNum,colNum,score,cellSize);
        createGameFieldComponents();
    }

    public void restartGame() {
        stop();
        createGameField(gameController.getRowNum(),gameController.getColNum(),
                gameController.getScore(),gameController.getCellSize());
    }
    private void stop() {
        gameTable.stop();
        gameController.stopGame();
        remove(gameTable);
        remove(infoPanel);
        exec.shutdown();
    }
    public void showGameOver() {
        stop();
        createGameOverWindow();
    }
    public void createGameFieldComponents() {
        gameTable =new GameTable(gameController);
        scoreLabel=new JLabel("Score: "+gameController.getScore());
        scoreLabel.setForeground(Color.WHITE);

        livesLabel=new JLabel("Lives: ");
        livesLabel.setForeground(Color.WHITE);

        timeLabel=new JLabel("Time: ");
        timeLabel.setForeground(Color.WHITE);

        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1,3));
        JPanel cellPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cellPanel.add(scoreLabel);
        cellPanel.setBackground(Color.BLACK);
        infoPanel.add(cellPanel);

        cellPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cellPanel.add(livesLabel);
        cellPanel.setBackground(Color.BLACK);
        infoPanel.add(cellPanel);

        cellPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cellPanel.add(timeLabel);
        cellPanel.setBackground(Color.BLACK);
        infoPanel.add(cellPanel);

        infoPanel.setVisible(true);
        add(infoPanel, BorderLayout.NORTH);
        add(gameTable);
        pack();
        exec.execute(gameTable.getPainter(this));
    }
    public void createGameOverWindow() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        gbc.insets=new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.VERTICAL;
        JLabel label1 = new JLabel("Game Over");
        JLabel label2 = new JLabel("Your Score: "+gameController.getScore());
        JLabel label3 = new JLabel("Enter your name and press \"OK\" to continue");
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(label1,gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        add(label2,gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        add(label3,gbc);

        textField = new JTextField(10);
        textField.setFont(Font.getFont(Font.SERIF));
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(textField,gbc);

        JButton button = new JButton("OK");
        button.setPreferredSize(new Dimension(90,30));
        button.setFocusable(false);
        button.addActionListener(okButListener);
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(button,gbc);
        pack();
        setVisible(true);
    }
}


