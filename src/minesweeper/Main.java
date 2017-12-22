package minesweeper;

import javax.swing.*;


public class Main {

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
    }
      MineSweeper mineSweeper = new MineSweeper(16,30,99,3); //hop, on lance le jeux en expert
  }
}