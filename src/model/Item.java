package model;

import java.awt.*;

public abstract class Item {
    volatile protected Point point;
    protected GameBoard gameBoard;
    public Item(Point p, GameBoard gameBoard) {
        this.gameBoard=gameBoard;
        this.point=p;
    }
    public Point getPoint() {
        return point;
    }

}
