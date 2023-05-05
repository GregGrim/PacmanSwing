import view.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartingWindow extends JFrame {
    private JButton gameButton = new JButton("Start Game");
    private JButton scoresButton = new JButton("High Scores");
    private JButton exitButton = new JButton("Exit");
    private JTextField txt = new JTextField(10);
    private int rowNum;
    private GridBagConstraints gbc = new GridBagConstraints();
    private GridBagLayout layout = new GridBagLayout();

    private ActionListener gameButListener = e -> {
        try {
            rowNum = Integer.parseInt(txt.getText())/2 *2 +1;
            if(rowNum<10||rowNum>100) throw new NumberFormatException();
            SwingUtilities.invokeLater(()-> new GameWindow(this, rowNum));
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a number in range(10,100)!!!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txt.setText("");
        }
    };
    private ActionListener scoresButListener = e ->
            SwingUtilities.invokeLater(()-> new HighScoresWindow(this,getLocation()));
    private ActionListener exitButListener = e -> System.exit(0);

    public StartingWindow() {
        super("Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,300);
        setResizable(false);
        gameButton.addActionListener(gameButListener);
        scoresButton.addActionListener(scoresButListener);
        exitButton.addActionListener(exitButListener);
        gameButton.setFocusable(false);
        scoresButton.setFocusable(false);
        exitButton.setFocusable(false);
        setLayout(layout);
        gbc.insets=new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(gameButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(txt,gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(scoresButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(exitButton,gbc);
        setVisible(true);
    }
}
