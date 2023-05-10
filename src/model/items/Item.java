package model.items;

import model.GameBoard;

import java.awt.*;

public abstract class Item {
    protected Point point;
    protected GameBoard gameBoard;
    public Item(Point p, GameBoard gameBoard) {
        this.gameBoard=gameBoard;
        this.point=p;
    }
    public Point getPoint() {
        return point;
    }

}
