package controller;

import model.GameBoard;
import model.items.Item;
import view.items.VItem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class GameController {
   private GameBoard gameBoard;
   private final int rowNum;
   private final int colNum;
   private int cellSize;
   private int time;
   private boolean isRunning=true;

   // thread to count time in game
   private final Thread timeThread = new Thread(() -> {
      while(isRunning){
         try {
            sleep(1000);
            time++;
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   });
   // constructor to be called in first round
   public GameController (int rowNum, int colNum) {
      this.rowNum=rowNum;
      this.colNum=colNum;
      this.cellSize=400/ Math.max(rowNum,colNum);
      this.time=0;
      createGame();
      timeThread.start();
   }
   // constructor to be called when restarting board
   public GameController (int rowNum, int colNum, int score, int time,int lives) {
      this.rowNum=rowNum;
      this.colNum=colNum;
      createGame();
      this.time=time;
      setScore(score);
      setLives(lives);
      timeThread.start();
   }
   // reflective function that creates views of item models using abstract factory
   public VItem newItem(Item model, int r) {
      try {
         Class<?> clazz = Class.forName("view.items.V" +
                 model.getClass().getName().replace("model.items.", ""));
         Constructor<?> contor = clazz.getConstructor(Item.class, int.class);
         return (VItem) contor.newInstance(model, r);
      }catch (Exception e){
         e.printStackTrace();
      }
      return null;
   }
   // creates model of the game
   public void createGame() {
      gameBoard = new GameBoard(rowNum,colNum);
   }
   // stops the game
   public void stopGame() {
      gameBoard.stop();
      isRunning=false;
      try {
         timeThread.join();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
   public int getRowNum() {
      return rowNum;
   }

   public int getColNum() {
      return colNum;
   }

   public int getCellSize() {
      return cellSize;
   }

   public void setCellSize(int cellSize) {
      this.cellSize = cellSize;
   }
   public TableModel getGameTableModel() {
      return new AbstractTableModel() {
         @Override
         public int getRowCount() {
            return getRowNum();
         }
         @Override
         public int getColumnCount() {
            return getColNum();
         }
         @Override
         public Object getValueAt(int rowIndex, int columnIndex) {
            List<Item> items = new ArrayList<>(gameBoard.getAllItems());
            for (Item item : items) {
               if(item.getPoint().equals(new Point(columnIndex,rowIndex))) return newItem(item, cellSize);
            }
            return null;
         }
         @Override
         public Class<?> getColumnClass(int columnIndex) {
            return Icon.class;
         }
      };
   }
   public KeyListener getKeyListener() {
      return new GameKeyListener(gameBoard);
   }

   public void setScore(int score) {
      gameBoard.getPacman().setScore(score);
   }
   public int getScore() {
     return  gameBoard.getPacman().getScore();
   }
   public int getLives() {
      return gameBoard.getPacman().getLives();
   }
   public void setLives(int lives) {
      gameBoard.getPacman().setLives(lives);
   }
   // returns time in Time Format
   public String getTime() {
      int minutes=time/60;
      int seconds=time%60;
      return String.format("%02d:%02d", minutes, seconds);
   }
   public int getTimeValue() {
      return time;
   }

   // writes game score to the file
   public void saveScore(String playerName) {
      GameScore gameScore = new GameScore();
      gameScore.setPlayerName(playerName);
      gameScore.setScore(getScore());
      try {
         FileOutputStream fout = new FileOutputStream("gameScores.txt",true);
         ObjectOutputStream oos = new ObjectOutputStream(fout);
         oos.writeObject(gameScore);
         fout.close();
         oos.close();
      }catch (IOException e) {
         e.printStackTrace();
      }
   }
   public boolean boardOver() {
      return gameBoard.getFoodMap().isEmpty();
   }

   public boolean gameOver() {
      return gameBoard.getPacman().getLives()<=0;
   }

   public GameBoard getGameBoard() {
      return gameBoard;
   }
}
