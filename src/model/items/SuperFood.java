package model.items;

import model.GameBoard;
import model.items.Food;

import java.awt.*;

public class SuperFood extends Food {
    public SuperFood(Point p, GameBoard gameBoard) {
        super(p, gameBoard);
    }

    @Override
    public int score() {
        return 200;
    }
}