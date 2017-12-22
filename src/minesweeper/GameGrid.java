package minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameGrid
    extends JPanel
    implements MouseListener {

  private int condition = 0; 
  private boolean mine = false; 
  private boolean selected = false; 
  private boolean blocked = false; 
  private int number = 0; 

  private GameDisplay gd = null; 

  public GameGrid() {
    try {
      //construction de la case
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setBackground(GameDisplay.top);
    this.setMaximumSize(new Dimension(16, 16)); 
    this.setMinimumSize(new Dimension(16, 16));
    this.addMouseListener(this);
    this.setPreferredSize(new Dimension(16, 16));
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    if (e.getModifiers() == 16 && condition != 1 && condition != 2 && !blocked) {
      selected = true;
      repaint();
    }
  }

  public void mouseReleased(MouseEvent e) {
    selected = false;
    repaint();
  }

  public void mouseEntered(MouseEvent e) {
    if (e.getModifiers() == 16 && condition != 1 && condition != 2 && !blocked) {
      selected = true;
      repaint();
    }
  }

  public void mouseExited(MouseEvent e) {
    selected = false;
    repaint();
  }

  public boolean isMine() {
    return mine;
  }

  public int getCondition() {
    return condition;
  }

  public void setCondition(int condition) {
    this.condition = condition;
  }

  public void setMine(boolean mine) {
    this.mine = mine;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    this.paintComponent(this.getGraphics());
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (gd != null) {
      if (!selected) {
        if (condition == 0) { 
          g.setColor(Color.white); 
          g.drawLine(0, 0, 0, 15);
          g.drawLine(0, 0, 15, 0);
        }
        else if (condition == 1) g.drawImage(gd.number[number], 0, 0, null);
        else if (condition == 2) g.drawImage(gd.flag, 0, 0, null);
        else if (condition == 6) g.drawImage(gd.error, 0, 0, null);
        else if (condition == 3) g.drawImage(gd.about, 0, 0, null);
        else if (condition == 4) g.drawImage(gd.explosion, 0, 0, null);
        else if (condition == 5) g.drawImage(gd.mine, 0, 0, null);
      }
      else { 
        if (condition == 3) g.drawImage(gd.aboutSelect, 0, 0, null); //?
        else if (condition != 1) {
          g.setColor(Color.gray); 
          g.drawLine(0, 0, 0, 15);
          g.drawLine(0, 0, 15, 0);
        }
      }
    }
    g.setColor(Color.darkGray);
    g.drawLine(0, 15, 15, 15);
    g.drawLine(15, 0, 15, 15);
    g.dispose();
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public boolean isBlocked() {
    return blocked;
  }

  public void setGameDisplay(GameDisplay gd) {
    this.gd = gd;
  }

  public void reset() {
    this.condition = 0;
    this.selected = false;
    setMine(false);
    setBlocked(false);
  }
}
