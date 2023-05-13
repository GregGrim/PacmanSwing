package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartingWindow extends JFrame {
    private final JTextField rows = new JTextField(10);
    private final JTextField columns = new JTextField(10);
    private int rowNum;
    private int colNum;

    private final ActionListener gameButListener = e -> {
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
    private final ActionListener scoresButListener = e ->
            SwingUtilities.invokeLater(()-> new HighScoresWindow(this,getLocation()));
    private final ActionListener exitButListener = e -> System.exit(0);

    public StartingWindow() {
        super("Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,300);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));
        JButton gameButton = new JButton("Start Game");
        gameButton.setBackground(Color.YELLOW);
        gameButton.addActionListener(gameButListener);
        JButton scoresButton = new JButton("High Scores");
        scoresButton.setBackground(Color.YELLOW);
        scoresButton.addActionListener(scoresButListener);
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.YELLOW);
        exitButton.addActionListener(exitButListener);
        gameButton.setFocusable(false);
        scoresButton.setFocusable(false);
        exitButton.setFocusable(false);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(gameButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        add(rows, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel rows = new JLabel("Rows:");
        rows.setForeground(Color.YELLOW);
        add(rows, gbc);


        gbc.gridx = 2;
        gbc.gridy = 3;
        add(columns, gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        JLabel cols = new JLabel("Columns:");
        cols.setForeground(Color.YELLOW);
        add(cols, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        add(scoresButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(exitButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(new JLabel("s27157"), gbc);

        setVisible(true);
    }
}
