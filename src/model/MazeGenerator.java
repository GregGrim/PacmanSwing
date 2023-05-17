package model;


import java.util.*;
import java.util.List;
public class MazeGenerator {
    private final List<Cell> cells = new ArrayList<>();
    private List<Cell> toVisit;
    private Cell currentCell;

    public MazeGenerator(int x, int y) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cells.add(new Cell(i,j));
            }
        }
        currentCell = cells.stream().filter(c -> c.equals(new Cell(1, 1))).toList().get(0);

        toVisit = cells.stream().filter(c-> getCell(c.x+1,c.y)!=null
                &&(getCell(c.x-1,c.y)!=null)
                &&getCell(c.x,c.y+1)!=null
                &&getCell(c.x,c.y-1)!=null
                &&getCell(c.x+1,c.y).isWall
                &&getCell(c.x-1,c.y).isWall
                &&getCell(c.x,c.y+1).isWall
                &&getCell(c.x,c.y-1).isWall).toList();

        while (!toVisit.stream().allMatch(Cell::isVisited)) {
            createPath();
            toVisit = cells.stream().filter(c-> c.x%2!=0&&c.y%2!=0&&!c.visited).toList();
        }
        checkerboardDeadEnds(x,y);
    }
    private void createPath() {
        currentCell.setVisited(true);
        currentCell.setWall(false);
        List<Cell> neighs = currentCell.getUnvisitedNeighbours();
        if(neighs.size()>0) {
            Cell nextCell = neighs.get((int) (Math.random() * neighs.size()));
            if(currentCell.x!=nextCell.x) {
                getCell((currentCell.x+nextCell.x)/2, currentCell.y).setWall(false);
            } else {
                getCell(currentCell.x, (currentCell.y+nextCell.y)/2).setWall(false);
            }
            currentCell = nextCell;

        } else if(getUnvisitedCells().size()>0){
            currentCell = getVisitedCells().get((int) (Math.random() * getVisitedCells().size()));
        }
    }
    public class Cell {
        private final int x;
        private final int y;
        private boolean isWall;
        private boolean visited;
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.isWall = true;
            this.visited=false;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Cell otherCell)) return false;
            return (this.x == otherCell.x && this.y == otherCell.y);
        }

        public List<Cell> getUnvisitedNeighbours () {
            List<Cell> neighbours = new ArrayList<>();
            neighbours.add(getCell(x-2,y));
            neighbours.add(getCell(x+2,y));
            neighbours.add(getCell(x,y+2));
            neighbours.add(getCell(x,y-2));
            neighbours.removeIf(Objects::isNull);
            return neighbours.stream().filter(cell->!cell.visited).toList();
        }

        public void setWall(boolean wall) {
            isWall = wall;
        }

        public boolean isWall() {
            return isWall;
        }

        public boolean isVisited() {
            return visited;
        }
        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "("+x+", "+y+")";
        }
    }
    public Cell getCell(int x, int y) {

        List<Cell> cell = cells.stream().filter(c->c.x==x&&c.y==y).toList();
        return cell.size()>0?cell.get(0):null;
    }
    public List<Cell> getUnvisitedCells() {
        return toVisit.stream().filter(c->!c.visited).toList();
    }
    public List<Cell> getVisitedCells() {
        return cells.stream().filter(c->c.visited).toList();
    }
    public void draw() {
        for (int i = 1; i <= cells.size(); i++) {
            System.out.print(cells.get(i-1).isWall?"X ":"  ");
            if(i%11==0) {
                System.out.println();
            }
        }
    }
    public void checkerboardDeadEnds(int x, int y) {    //finds and removes all dead ends in maze
        for (int i = 1; i < x; i+=2) {
            for (int j = 1; j < y; j+=2) {
                if (    getCell(i-1, j).isWall//remove deadend in south dir
                        && getCell(i+1, j).isWall
                        && getCell(i, j+1).isWall) {
                    if(j + 1 != y-1) {
                        getCell(i,j+1).setWall(false);
                    } else {
                        if(i-1!=0) {
                            getCell(i-1,j).setWall(false);
                        } else {
                            getCell(i+1,j).setWall(false);
                        }
                    }

                }
                if (    getCell(i-1, j).isWall//remove deadend in north dir
                        && getCell(i+1, j).isWall
                        && getCell(i, j-1).isWall) {
                    if(j - 1 !=0) {
                        getCell(i,j-1).setWall(false);
                    } else {
                        if(i-1!=0) {
                            getCell(i-1,j).setWall(false);
                        } else {
                            getCell(i+1,j).setWall(false);
                        }
                    }

                }
                if (    getCell(i-1, j).isWall//remove deadend in west dir
                        && getCell(i, j-1).isWall
                        && getCell(i, j+1).isWall) {
                    if(i - 1 != 0) {
                        getCell(i-1,j).setWall(false);
                    } else {
                        if(j-1!=0) {
                            getCell(i,j-1).setWall(false);
                        } else {
                            getCell(i,j+1).setWall(false);
                        }
                    }

                }
                if (getCell(i, j-1).isWall//remove deadend in east dir
                        && getCell(i+1, j).isWall
                        && getCell(i, j+1).isWall) {
                    if(i + 1 != x-1) {
                        getCell(i+1,j).setWall(false);
                    } else {
                        if(j-1!=0) {
                            getCell(i,j-1).setWall(false);
                        } else {
                            getCell(i,j+1).setWall(false);
                        }
                    }
                }
            }
        }
    }

    public List<Cell> getCells() {
        return cells;
    }
        public static void main(String[] args) {
        MazeGenerator mazeGenerator = new MazeGenerator(11,11);
        mazeGenerator.draw();
    }
}