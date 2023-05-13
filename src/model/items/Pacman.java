package model.items;

import model.GameBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Pacman extends Character {

    private int mouthOpened = 0;
    private int score;
    private int lives;
    private boolean mouthDirection = true;
    private boolean invulnerability = false;
    private Monster onMonster;
    private final Thread checkingMonster = new Thread(()->{
        while(isRunning) {
            if (onMonster==null) {
                List<Monster> monsters = new ArrayList<>(gameBoard.getMonsters());
                for (Monster monster :
                        monsters) {
                    if (monster.getPoint().equals(point)) {
                        onMonster = monster;
                        checkMonster();
                    }
                }
            }
            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    private final Thread pacmanThread = new Thread(()->{
        while (isRunning) {
            try {
                sleep(10);
                mouthOpened+=mouthDirection?10:-10;
                if(mouthOpened==90||mouthOpened==0) {
                    mouthDirection=!mouthDirection;
                }
            } catch (InterruptedException e) {
                isRunning = false;
            }
        }
    });
    public Pacman (GameBoard gameBoard) {
        super(new Point(1,1), Direction.RIGHT, gameBoard,300);
        this.score=0;
        this.lives=1;
        this.onMonster=null;
        pacmanThread.start();
        checkingMonster.start();
    }

    @Override
    public void move() {
        super.move();
        checkFood();
        checkUpgrade();
    }

    public int getMouthOpened() {
        return mouthOpened;
    }
    public int getScore() {
        return score;
    }
    public int getLives() {
        return lives;
    }
    public void deathProcess() {
        lives--;
        point.x=1;
        point.y=1;
    }
    public void checkFood() {
        Food food = gameBoard.getFoodMap().get(new Point(point.x,point.y));
        if(food!=null) {
            score+=gameBoard.isDoublePoints()?food.score()*2:food.score();
            if(SuperFood.class.isAssignableFrom(food.getClass())){
                new Thread(()->{
                    gameBoard.getMonsters().forEach(m->m.setVulnerable(true));
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gameBoard.getMonsters().forEach(m->m.setVulnerable(false));
                }).start();
            }
            gameBoard.getFoodMap().remove(new Point(point.x,point.y));
            gameBoard.getAllItems().remove(food);
        }
    }
    public void checkMonster() {
        if(onMonster!=null) {
            if (onMonster.isVulnerable() && onMonster.isAlive()) {
                onMonster.deathProcess();
                score += onMonster.getScore();
            } else if (onMonster.isAlive() && !onMonster.isVulnerable() && !invulnerability) {
                deathProcess();
            }
        }
        onMonster=null;
    }
    public void checkUpgrade() {
        List<Item> items = new ArrayList<>(gameBoard.getAllItems());
        Object[] upgradesArray = items
                .stream().filter(item->item.getPoint().equals(new Point(point.x, point.y))
                        &&item instanceof Upgrade).toArray();
        if(upgradesArray.length!=0) {
            for (Object o : upgradesArray) {
                ((Upgrade) o).callUpgrade();
                gameBoard.getAllItems().remove((Upgrade) o);
            }
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setInvulnerability(boolean invulnerability) {
        this.invulnerability = invulnerability;
    }
    public boolean isInvulnerability() {
        return invulnerability;
    }
    @Override
    public void stop() {
        super.stop();
        try {
            pacmanThread.join();
            checkingMonster.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
