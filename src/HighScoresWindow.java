import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HighScoresWindow extends JDialog {
    private JButton backButton = new JButton("Back to Menu");
    private ActionListener backButListener = e -> dispose();
    public  HighScoresWindow(JFrame parentFrame, Point location) {
        super(parentFrame, "High Scores", true);
        setLocation(location);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,300);
        setResizable(false);
        setLayout(new FlowLayout());
        backButton.addActionListener(backButListener);
        add(backButton);
        setVisible(true);
        //TODO scores
    }
}
