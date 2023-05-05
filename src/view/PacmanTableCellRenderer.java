package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class PacmanTableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
             boolean hasFocus, int row, int column)
    {
        Component item = super.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);
        return item;
    }
}
