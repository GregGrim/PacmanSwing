package view.items;


import model.items.Character;
import model.items.Item;
import model.items.Pacman;

import java.awt.*;

public class VPacman extends VCharacter {
    public VPacman (Item pac, int r) {
        super(pac,r);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(Color.YELLOW);
        g.fillArc(x,y,r,r,
                ((Pacman)model).getMouthOpened()/2+ ((Character)model).getDirection().getValue(),
                360-((Pacman)model).getMouthOpened());
        if (((Pacman)model).isInvulnerability()) {
            g.setColor(Color.MAGENTA);
            g.drawOval(x,y,r,r);
        }
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
