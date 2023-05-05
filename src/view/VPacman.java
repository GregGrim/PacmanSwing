package view;


import model.Character;
import model.Item;
import model.Pacman;

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
