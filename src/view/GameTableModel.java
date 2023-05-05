package view;
import model.GameBoard;
import model.Item;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class GameTableModel extends AbstractTableModel{
    private int cellSize;
    private GameBoard gameBoard;
    private List<VItem> vItems = new ArrayList<>();
    public GameTableModel(GameBoard gameBoard) {
        this.gameBoard=gameBoard;
        this.cellSize=800/ gameBoard.getRowNum();
    }
    @Override
    public int getRowCount() {
        return gameBoard.getRowNum();
    }
    @Override
    public int getColumnCount() {
        return gameBoard.getRowNum();
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        for (Item model: gameBoard.getAllItems()) {
            if(model.getPoint().equals(new Point(columnIndex,rowIndex))) return VItem.newItem(model, cellSize);
        }
        return null;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Icon.class;
    }

}
