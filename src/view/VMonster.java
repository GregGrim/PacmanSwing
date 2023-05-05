package view;



import model.Item;
import model.Monster;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class VMonster extends VCharacter {
    public VMonster (Item m, int r) {
        super(m,r);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(getColor(((Monster)model).getName()));
        g.fillOval(x,y,r,r);
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
