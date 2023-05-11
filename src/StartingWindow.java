import controller.CompoundShortcut;
import view.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartingWindow extends JFrame {
    private JButton gameButton = new JButton("Start Game");
    private JButton scoresButton = new JButton("High Scores");
    private JButton exitButton = new JButton("Exit");
    private JTextField rows = new JTextField(10);
    private JTextField columns = new JTextField(10);
    private int rowNum;
    private int colNum;
    private GridBagConstraints gbc = new GridBagConstraints();
    private GridBagLayout layout = new GridBagLayout();

    private ActionListener gameButListener = e -> {
        try {
            rowNum = Integer.parseInt(rows.getText())/2 *2 +1;
            colNum = Integer.parseInt(columns.getText())/2 *2 +1;
            if(rowNum<10||rowNum>101) throw new NumberFormatException();
            if(colNum<10||colNum>101) throw new NumberFormatException();
            SwingUtilities.invokeLater(()-> new GameWindow(this, rowNum, colNum));
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a number in range(10,100)!",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
        add(rows,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(new JLabel("Rows:"),gbc);


        gbc.gridx = 2;
        gbc.gridy = 3;
        add(columns,gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        add(new JLabel("Columns:"),gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        add(scoresButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(exitButton,gbc);
        setVisible(true);
    }
}
