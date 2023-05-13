package model.items;

import model.GameBoard;

import java.awt.*;
// food that allows pacman to eat monsters
public class SuperFood extends Food {
    public SuperFood(Point p, GameBoard gameBoard) {
        super(p, gameBoard);
    }

    @Override
    public int score() {
        return 200;
    }
}
