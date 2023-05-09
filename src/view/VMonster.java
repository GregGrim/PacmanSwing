package view;

import model.Item;
import model.Monster;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;

public class VMonster extends VCharacter {
    public VMonster (Item m, int r) {
        super(m,r);
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // monster body
        g2d.setColor(getColor(((Monster)model).getName()));
        g2d.fillArc(x, y, r, r, 0, 180); // upper half
        g2d.fillRect(x, y + r / 2, r, r / 2); // lower half
        // monster legs
        g2d.setColor(Color.BLACK);
        double legWidth = r / 10.0;
        double legHeight = r / 5.0;
        for (int i = ((Monster)model).getLegsPosition(); i < 10; i+=2) {
            double legX = x + i*legWidth;
            double legY = y + r * 0.85;
            Rectangle2D.Double leg = new Rectangle2D.Double(legX, legY, legWidth, legHeight);
            g2d.fill(leg);
        }
        g2d.setColor(Color.white);
        int eyeSize = r / 5;
        int eyeY = y + r / 4;
        g2d.fillOval(x + r / 3 - eyeSize / 2, eyeY - eyeSize / 2, eyeSize, eyeSize);
        g2d.fillOval(x + 2 * r / 3 - eyeSize / 2, eyeY - eyeSize / 2, eyeSize, eyeSize);
        g2d.setColor(Color.black);
        int pupilSize = eyeSize / 2;
        int xPupilLocation = x + r / 3 - pupilSize / 2;
        int yPupilLocation = eyeY - pupilSize / 2;
        switch (((Monster) model).getDirection()) {
            case UP -> yPupilLocation -= pupilSize/2;
            case DOWN ->yPupilLocation += pupilSize/2;
            case LEFT -> xPupilLocation -= pupilSize / 2;
            case RIGHT -> xPupilLocation += pupilSize / 2;
        }
        g2d.fillOval(xPupilLocation, yPupilLocation, pupilSize, pupilSize);
        g2d.fillOval(xPupilLocation+r / 3, yPupilLocation, pupilSize, pupilSize);
        g2d.dispose();
    }
    @Override
    public int getIconWidth() {
        return r;
    }
    @Override
    public int getIconHeight() {
        return r;
    }
    public static Color getColor(String colorName) {
        try {
            Field field = Class.forName("java.awt.Color").getField(colorName);
            return (Color) field.get(null);
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
