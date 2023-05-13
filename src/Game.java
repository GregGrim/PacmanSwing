import view.StartingWindow;

import javax.swing.*;

public class Game {
    static StartingWindow startingWindow;

    private static void runApplication() {
        SwingUtilities.invokeLater(() -> startingWindow = new StartingWindow());
    }
    public static void main(String[] args) {
        runApplication();
    }
}
