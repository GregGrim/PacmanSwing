package view;

import model.Item;

import java.awt.*;

public class VSimpleFood extends VItem{

    public VSimpleFood(Item model, int r) {
        super(model, r);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillOval(x,y,r/5,r/5);
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
