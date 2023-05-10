package model.items;

import model.GameBoard;
import model.items.Food;

import java.awt.*;

public class SimpleFood extends Food {

    public SimpleFood(Point p, GameBoard gameBoard) {
        super(p, gameBoard);
    }

    @Override
    public int score() {
        return 5;
    }

}
