package model;

import java.awt.*;

public abstract class Food extends Item{
    public Food(Point p, GameBoard gameBoard) {
        super(p, gameBoard);
    }
    public abstract int score();
}

