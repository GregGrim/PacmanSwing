package view.items;

import model.items.Item;

import java.awt.*;

public class VSuperFood extends VItem {

    public VSuperFood(Item model, int r) {
        super(model, r);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(Color.GREEN);
        g.fillOval(x,y,r/3,r/3);
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