package model.items;

import model.GameBoard;
import model.items.Item;

import java.awt.*;

public abstract class Food extends Item {
    public Food(Point p, GameBoard gameBoard) {
        super(p, gameBoard);
    }
    public abstract int score();
}


