package model;
import model.items.*;


import java.awt.*;
import java.util.*;
import java.util.List;

// main model class
public class GameBoard {
    private final List<Monster> monsters = new ArrayList<>();
    private final Map<Point, Block> blocks = new HashMap<>();
    private final Pacman pacman;
    private final List<Item> allItems = new ArrayList<>();
    private final Map<Point, Food> foodMap = new HashMap<>();
    private final int rowNum;
    private final int colNum;
    private boolean doublePoints = false;
    public GameBoard (int rowNum, int colNum) {
        this.rowNum=rowNum;
        this.colNum=colNum;
        pacman = new Pacman(this);
        new Thread(pacman.getRunner()).start();
        monsters.add(new Monster("PINK",this));
        monsters.add(new Monster("BLUE",this));
        monsters.add(new Monster("GREEN",this));
        monsters.add(new Monster("RED",this));
        addWallsToModel();
        checkerboardDeadEnds();
        blocks.remove(new Point(0,3)); // adding tunnels
        blocks.remove(new Point(colNum-1,3));
        blocks.remove(new Point(0,rowNum-4));
        blocks.remove(new Point(colNum-1,rowNum-4));
        allItems.add(pacman);
        allItems.addAll(monsters);
        // fills empty space with food
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                Point point = new Point(i,j) ;
                if(blocks.get(point)==null) {
                    if(Math.random()<0.03) foodMap.put(point,new SuperFood(point,this));
                    else foodMap.put(point,new SimpleFood(point,this));
                }
            }
        }
        allItems.addAll(foodMap.values());
        allItems.addAll(blocks.values());
        monsters.forEach(m->new Thread(m.getRunner()).start());
    }
    public List<Monster> getMonsters() {
        return monsters;
    }
    public Pacman getPacman() {
        return pacman;
    }
    public int getRowNum() {
        return rowNum;
    }
    public int getColNum() {
        return colNum;
    }
    public void stop() {
        pacman.stop();
        monsters.forEach(Monster::stop);
    }

    public void setDoublePoints(boolean doublePoints) {
        this.doublePoints = doublePoints;
    }
    public boolean isDoublePoints() {
        return doublePoints;
    }
    // adds upgrade to the game field
    public synchronized void createUpgrade(int x, int y) {
        allItems.removeIf(item -> (new Point(x,y)).equals(item.getPoint())&&Food.class.isAssignableFrom(item.getClass()));
        switch ((int) (Math.random()*5)) {
            case 0-> allItems.add(new Upgrade(new Point(x,y),this, Upgrade.Type.SPEED));
            case 1-> allItems.add(new Upgrade(new Point(x,y),this, Upgrade.Type.LIVES));
            case 2-> allItems.add(new Upgrade(new Point(x,y),this, Upgrade.Type.TELEPORT));
            case 3-> allItems.add(new Upgrade(new Point(x,y),this, Upgrade.Type.INVULNERABILITY));
            case 4-> allItems.add(new Upgrade(new Point(x,y),this, Upgrade.Type.DOUBLE_POINTS));
        }
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    public Map<Point,Block> getBlocks() {
        return blocks;
    }

    public Map<Point, Food> getFoodMap() {
        return foodMap;
    }
    //creates maze and adds walls to model
    public void addWallsToModel() {
        MazeGenerator mazeGenerator = new MazeGenerator((colNum)/2, (rowNum)/2);
        for (int i = 0; i < colNum; i++) { // adding all walls to the model
            for (int j = 0; j < rowNum; j++) {
                if(mazeGenerator.getGrid()[i*2][j]=='X') {
                    Block block = new Block(new Point(i, j), this);
                    blocks.put(block.getPoint(), block);
                }
            }
        }
    }
    //finds and removes all dead ends in maze
    public void checkerboardDeadEnds() {
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                Point point = new Point(i,j);
                if (blocks.get(new Point(point.x - 1, point.y)) != null //remove deadend in south dir
                        && blocks.get(new Point(point.x + 1, point.y)) != null
                        && blocks.get(new Point(point.x, point.y + 1)) != null
                        && point.y + 1 != rowNum - 1) {
                    blocks.remove(new Point(point.x, point.y + 1));
                }
                if (blocks.get(new Point(point.x - 1, point.y)) != null //remove deadend in north dir
                        && blocks.get(new Point(point.x + 1, point.y)) != null
                        && blocks.get(new Point(point.x, point.y - 1)) != null
                        && point.y - 1 != 0) {
                    blocks.remove(new Point(point.x, point.y - 1));
                }
                if (blocks.get(new Point(point.x - 1, point.y)) != null //remove deadend in west dir
                        && blocks.get(new Point(point.x, point.y - 1)) != null
                        && blocks.get(new Point(point.x, point.y + 1)) != null
                        && point.x - 1 != 0) {
                    blocks.remove(new Point(point.x - 1, point.y));
                }
                if (blocks.get(new Point(point.x + 1, point.y)) != null //remove deadend in east dir
                        && blocks.get(new Point(point.x, point.y - 1)) != null
                        && blocks.get(new Point(point.x, point.y + 1)) != null
                        && point.x + 1 != colNum - 1) {
                    blocks.remove(new Point(point.x + 1, point.y));
                }
            }
        }
    }
}
