package view;

import model.Character;
import model.Item;

import java.awt.*;

public class VBlock extends VItem {

    public VBlock(Item model, int r) {
        super(model,r);
    }
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillRect(0,0,getIconWidth(),getIconHeight());
    }
    @Override
    public int getIconWidth() {
        return r;
    }

    @Override
    public int getIconHeight() {
        return r;
    }
}
