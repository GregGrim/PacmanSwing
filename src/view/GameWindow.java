package view;


import controller.CompoundShortcut;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import static java.lang.Thread.sleep;

public class GameWindow extends JDialog{
    GameController gameController;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private GameTable gameTable;
    private JPanel infoPanel;
    private JLabel scoreLabel;
    private JLabel livesLabel;
    private JLabel timeLabel;
    private JTextField textField;
    private boolean isRunning = true;
    // repaints info panel
    private final Thread panelRepainter = new Thread(()->{
        while(isRunning) {
            try {
                sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateStats();
        }
    });
    // scalability listener
    private final ComponentListener componentListener = new ComponentListener() {
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
    private final ActionListener okButListener = actionEvent -> {
        if(textField.getText().length()>20) {
            JOptionPane.showMessageDialog(this, "Name is too long(max 20 chars)",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if(!textField.getText().equals("")){
        gameController.saveScore(textField.getText());
        dispose();
        } else JOptionPane.showMessageDialog(this, "Please enter your name :)",
                "Error", JOptionPane.ERROR_MESSAGE);
    };
    // frame of main game window
    public GameWindow(JFrame parentFrame,int rowNum,int colNum) {
        super(parentFrame, "Game", true);
        setPreferredSize(new Dimension(400,400));
        addComponentListener(componentListener);
        setBackground(Color.BLACK);
        setLocation(0, 0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createGameField(rowNum,colNum);
        Dimension dimension = new Dimension(gameController.getCellSize() * colNum,
                gameController.getCellSize() * rowNum + 40);
        setPreferredSize(dimension);
        addKeyListener(new CompoundShortcut(this));
        setFocusable(true);
        setVisible(true);
    }
    // shutting down all threads and disposes game window
    @Override
    public void dispose() {
        super.dispose();
        gameTable.stop();
        gameController.stopGame();

    }
    // function to be called in first round
    public void createGameField(int rowNum,int colNum) {
        gameController = new GameController(rowNum,colNum);
        createGameFieldComponents();
    }
    // function to be called for restart
    public void createGameField(int rowNum,int colNum, int score, int time,int lives) {
        gameController = new GameController(rowNum,colNum,score,time,lives);
        createGameFieldComponents();
    }
// restarts game when board is over
    public void restartGame() {
        stop();
        createGameField(gameController.getRowNum(),gameController.getColNum(),
                gameController.getScore(),gameController.getTimeValue(),gameController.getLives());
    }
    private void stop() {
        isRunning=false;
        gameTable.stop();
        try{
            panelRepainter.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        gameController.stopGame();
        remove(gameTable);
        remove(infoPanel);
    }
    public void showGameOver() {
        stop();
        createGameOverWindow();
    }
    // creates panel and game table for game
    public void createGameFieldComponents() {
        scoreLabel=new JLabel("Score: "+gameController.getScore());
        scoreLabel.setForeground(Color.YELLOW);

        livesLabel=new JLabel("Lives: "+gameController.getLives());
        livesLabel.setForeground(Color.YELLOW);

        timeLabel=new JLabel("Time: "+gameController.getTime());
        timeLabel.setForeground(Color.YELLOW);

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
        add(infoPanel, BorderLayout.NORTH);
        gameTable =new GameTable(gameController,this);
        add(gameTable);
        pack();
        isRunning=true;
        requestFocus();
        addKeyListener(gameController.getKeyListener());
        setFocusable(true);
    }
    // window appears when pacman has no more lives
    public void createGameOverWindow() {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        getContentPane().setBackground(Color.BLACK);
        getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));

        gbc.insets=new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.VERTICAL;
        JLabel label1 = new JLabel("Game Over");
        label1.setForeground(Color.RED);
        JLabel label2 = new JLabel("Your Score: "+gameController.getScore());
        label2.setForeground(Color.YELLOW);
        JLabel label3 = new JLabel("Enter your name and press \"OK\" to continue");
        label3.setForeground(Color.YELLOW);
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
        button.setBackground(Color.YELLOW);
        button.addActionListener(okButListener);
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(button,gbc);
        pack();
        setVisible(true);
    }
    public void updateStats() {
        timeLabel.setText("Time: " + gameController.getTime());
        scoreLabel.setText("Score: " + gameController.getScore());
        livesLabel.setText("Lives: " + gameController.getLives());
        infoPanel.repaint();
    }
}


