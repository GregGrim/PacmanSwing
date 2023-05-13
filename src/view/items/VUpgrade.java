package view.items;

import model.items.Item;
import model.items.Upgrade;

import javax.swing.*;
import java.awt.*;

public class VUpgrade extends VItem {

    public VUpgrade(Item model, int r) {
        super(model, r);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        switch (((Upgrade)model).getType()) {
            case SPEED -> {
                g.setColor(Color.RED);
                g.fillOval(x,y,r/3,r/3);
            }
            case LIVES -> {
                g.setColor(Color.RED);
                g.fillOval(x,y,r/3,r/3);
            }
            case TELEPORT -> {
                g.setColor(Color.RED);
                g.fillOval(x,y,r/3,r/3);
            }
            case INVULNERABILITY -> {
                g.setColor(Color.RED);
                g.fillOval(x,y,r/3,r/3);
            }
            case DOUBLE_POINTS -> {
                g.setColor(Color.RED);
                g.fillOval(x,y,r/3,r/3);
            }
        }
    }

    @Override
    public int getIconWidth() {
        return 0;
    }

    @Override
    public int getIconHeight() {
        return 0;
    }
}
