package view.items;

import model.items.Item;
import model.items.Upgrade;

import java.awt.*;


public class VUpgrade extends VItem {

    public VUpgrade(Item model, int r) {
        super(model, r);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        switch (((Upgrade)model).getType()) {
            case SPEED -> {
                g.setColor(Color.CYAN);
                g.fillRect(r/2-r/10,r/2-r/10-r/4,r/5, r/5);
                g.fillRect(r/2-r/10,r/2-r/10,r/5, r/5);
                g.fillRect(r/2-r/10,r/2-r/10+r/4,r/5, r/5);
            }
            case LIVES -> {
                g.setColor(Color.RED);
                g.fillRect(0,r/2-r/10,r,r/5);
                g.fillRect(r/2-r/10,0,r/5,r);
            }
            case TELEPORT -> {
                g.setColor(new Color(100,0,200));
                g.drawString("TP",x,y);
            }
            case INVULNERABILITY -> {
                g.setColor(Color.MAGENTA);
                g.drawOval(r/4,r/4,r/2,r/2);
            }
            case DOUBLE_POINTS -> {
                g.setColor(new Color(255,140,0));
                g.drawString("DP",x,y);
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
