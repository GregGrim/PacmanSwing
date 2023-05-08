package model;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameBoard {
    private List<Monster> monsters = new ArrayList<>();
    private Map<Point,Block> blocks = new HashMap();
    private Pacman pacman;
    private List<Item> allItems = new ArrayList<>();
    private Map<Point,Food> foodMap = new HashMap<>();
    private int rowNum;
    private int colNum;
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
        checkandremoveDeadEnds();
        blocks.remove(new Point(0,3)); // adding tunnels
        blocks.remove(new Point(colNum-1,3));
        blocks.remove(new Point(0,rowNum-4));
        blocks.remove(new Point(colNum-1,rowNum-4));
        allItems.add(pacman);
        allItems.addAll(monsters);
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
        monsters.forEach(Character::stop);
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
    public void addWallsToModel() {
        MazeGenerator mazeGenerator = new MazeGenerator((colNum)/2, (rowNum)/2);
        for (int i = 0; i < colNum; i++) { // adding all walls to the model
            for (int j = 0; j < rowNum; j++) {
                if(mazeGenerator.getGrid()[i*2][j]=='X') {
                    Block block = new Block(new Point(i, j), this);
                    blocks.put(block.point, block);
                }
            }
        }
    }
    public void checkandremoveDeadEnds() {
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                Point point = new Point(i,j);
                if (blocks.get(new Point(point.x - 1, point.y)) != null //remove deadend in south dir
                        && blocks.get(new Point(point.x + 1, point.y)) != null
                        && blocks.get(new Point(point.x, point.y + 1)) != null
                        && point.y + 1 != rowNum - 1) {
                    blocks.remove(new Point(point.x, point.y + 1));
                    //            if(point.y+1==rowNum) blocks.remove(new Point(point.x,0));
                }
                if (blocks.get(new Point(point.x - 1, point.y)) != null //remove deadend in north dir
                        && blocks.get(new Point(point.x + 1, point.y)) != null
                        && blocks.get(new Point(point.x, point.y - 1)) != null
                        && point.y - 1 != 0) {
                    blocks.remove(new Point(point.x, point.y - 1));
                    //            if(point.y-1==0) blocks.remove(new Point(point.x,rowNum));
                }
                if (blocks.get(new Point(point.x - 1, point.y)) != null //remove deadend in west dir
                        && blocks.get(new Point(point.x, point.y - 1)) != null
                        && blocks.get(new Point(point.x, point.y + 1)) != null
                        && point.x - 1 != 0) {
                    blocks.remove(new Point(point.x - 1, point.y));
                    //            if(point.x-1==0) blocks.remove(new Point(rowNum,point.y));
                }
                if (blocks.get(new Point(point.x + 1, point.y)) != null //remove deadend in east dir
                        && blocks.get(new Point(point.x, point.y - 1)) != null
                        && blocks.get(new Point(point.x, point.y + 1)) != null
                        && point.x + 1 != colNum - 1) {
                    blocks.remove(new Point(point.x + 1, point.y));
                    //            if(point.x+1==rowNum) blocks.remove(new Point(0,point.y));
                }
            }
        }
    }



    public boolean boardOver() {
        return foodMap.isEmpty();
    }
    public boolean gameOver() {
        return pacman.getLives()<=0;
    }
}
