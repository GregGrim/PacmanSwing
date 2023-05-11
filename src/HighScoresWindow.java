import controller.CompoundShortcut;
import controller.GameScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class HighScoresWindow extends JDialog {
    private JList scores;
    private List<GameScore> gameScoreList;
    private JButton backButton = new JButton("Back to Menu");
    private GridBagConstraints gbc = new GridBagConstraints();
    private ActionListener backButListener = e -> dispose();
    public  HighScoresWindow(JFrame parentFrame, Point location) {
        super(parentFrame, "High Scores", true);
        setLocation(location);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(300,300));
        setResizable(false);
        GridBagLayout layout = new GridBagLayout();

        dispatchScores();
        gameScoreList=sortedScores();
        scores = new JList(gameScoreList.toArray());
        scores.addKeyListener(new CompoundShortcut(this));
        scores.setSize(new Dimension(100,100));

        backButton.addActionListener(backButListener);
        backButton.setFocusable(false);
        JScrollPane listScroller = new JScrollPane(scores);
        listScroller.setWheelScrollingEnabled(true);
        listScroller.setVisible(true);


        setLayout(layout);
        gbc.insets=new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(backButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(listScroller,gbc);
        setVisible(true);
    }

    public void dispatchScores() {
        try {
            FileInputStream fis = new FileInputStream("gameScores.txt");
            boolean flag = true;
            gameScoreList= new ArrayList<>();
            while (flag&&fis.available() > 0) {
                ObjectInputStream input = new ObjectInputStream(fis);
                    GameScore gameScore = (GameScore) input.readObject();
                    if (gameScore != null) {
                        gameScoreList.add(gameScore);
                    } else {
                        flag = false;
                        fis.close();
                        input.close();
                    }
                }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<GameScore> sortedScores() {
        return gameScoreList.stream().sorted((a,b)->b.getScore()-a.getScore()).toList();
    }
}
