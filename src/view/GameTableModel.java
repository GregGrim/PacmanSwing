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
    public GameTableModel(GameBoard gameBoard) {
        this.gameBoard=gameBoard;
        this.cellSize=400/ Math.max(gameBoard.getRowNum(),gameBoard.getColNum());
    }
    @Override
    public int getRowCount() {
        return gameBoard.getRowNum();
    }
    @Override
    public int getColumnCount() {
        return gameBoard.getColNum();
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Item> items = new ArrayList<>(gameBoard.getAllItems());
        for (Item item : items) {
            if(item.getPoint().equals(new Point(columnIndex,rowIndex))) return VItem.newItem(item, cellSize);
        }
        return null;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Icon.class;
    }
    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }
}
