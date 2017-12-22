package minesweeper;

import java.awt.image.*;
import java.awt.*;

public class GameDisplay{

  public static VolatileImage flag, about, aboutSelect, mine, explosion, error;
  public static VolatileImage[] number = new VolatileImage[9];
  private final Color[] color = new Color[8];

  public static Color top = new Color(214, 208, 200); //couleur du "top" des cases

  public GameDisplay(GraphicsConfiguration gd) {
    //Les color des numbers
    color[0] = new Color(0, 0, 255);
    color[1] = new Color(0, 128, 0);
    color[2] = new Color(255, 0, 0);
    color[3] = new Color(0, 0, 128);
    color[4] = new Color(128, 0, 0);
    color[5] = new Color(0, 128, 128);
    color[6] = new Color(128, 0, 128);
    color[7] = new Color(0, 0, 0);

    Graphics2D g;

    //numbers
    for (int i = 0; i <= 8; i++) {
      number[i] = gd.createCompatibleVolatileImage(16, 16);
      g = (Graphics2D) number[i].getGraphics();
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON);
      g.setStroke(new BasicStroke(1.2f));
      g.setFont(new java.awt.Font("Monospaced", 1, 15));
      g.setColor(new Color(249, 249, 247));
      g.fillRect(0, 0, 16, 16);
      if (i != 0) g.setColor(color[i - 1]);
      if (i != 0) g.drawString("" + i, 4, 12);
      g.setColor(Color.lightGray);
      g.drawLine(0, 0, 0, 15);
      g.drawLine(0, 0, 15, 0);
      g.dispose();
    }

    //flag
    flag = gd.createCompatibleVolatileImage(16, 16);
    g = (Graphics2D) flag.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
    g.setStroke(new BasicStroke(1.2f));
    g.setColor(top);
    g.fillRect(0, 0, 16, 16);
    g.setColor(Color.white);
    g.drawLine(0, 0, 0, 15);
    g.drawLine(0, 0, 15, 0);
    g.setColor(Color.black);
    g.drawLine(9, 12, 9, 4);
    g.drawLine(10, 12, 10, 4);
    g.drawLine(8, 12, 11, 12);
    g.drawLine(7, 13, 12, 13);
    g.setColor(Color.red);
    int[] x = {
        9, 4, 4, 9};
    int[] y = {
        5, 5, 6, 8};
    g.fillPolygon(x, y, 4);
    g.dispose();

    //error
    error = gd.createCompatibleVolatileImage(16, 16);
    g = (Graphics2D) error.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
    g.setStroke(new BasicStroke(1.2f));
    g.setColor(top);
    g.fillRect(0, 0, 16, 16);
    g.setColor(Color.white);
    g.drawLine(0, 0, 0, 15);
    g.drawLine(0, 0, 15, 0);
    g.setColor(Color.black);
    g.drawLine(9, 12, 9, 4);
    g.drawLine(10, 12, 10, 4);
    g.drawLine(8, 12, 11, 12);
    g.setColor(Color.red);
    g.fillPolygon(x, y, 4);
    g.setColor(Color.red);
    g.drawLine(3, 3, 12, 12);
    g.drawLine(3, 12, 12, 3);
    g.dispose();

    about = gd.createCompatibleVolatileImage(16, 16);
    g = (Graphics2D) about.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
    g.setStroke(new BasicStroke(1.2f));
    g.setColor(top);
    g.fillRect(0, 0, 16, 16);
    g.setColor(Color.white);
    g.drawLine(0, 0, 0, 15);
    g.drawLine(0, 0, 15, 0);
    g.setFont(new java.awt.Font("Dialog", 1, 13));
    g.setColor(Color.blue);
    g.drawString("?", 4, 13);
    g.dispose();

    aboutSelect = gd.createCompatibleVolatileImage(16, 16);
    g = (Graphics2D) aboutSelect.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
    g.setStroke(new BasicStroke(1.2f));
    g.setColor(top);
    g.fillRect(0, 0, 16, 16);
    g.setColor(Color.gray);
    g.drawLine(0, 0, 0, 15);
    g.drawLine(0, 0, 15, 0);
    g.setFont(new java.awt.Font("Dialog", 1, 12));
    g.setColor(Color.blue);
    g.drawString("?", 5, 12);
    g.dispose();

    //mine
    mine = gd.createCompatibleVolatileImage(16, 16);
    g = (Graphics2D) mine.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
    g.setStroke(new BasicStroke(1.2f));
    g.setColor(Color.lightGray);
    g.drawLine(0, 0, 0, 15);
    g.drawLine(0, 0, 15, 0);
    g.setColor(Color.black);
    g.fillRect(5, 5, 5, 5);
    g.drawLine(3, 7, 11, 7);
    g.drawLine(7, 3, 7, 11);
    g.setColor(new Color(128, 128, 128));
    g.drawLine(2, 7, 2, 7);
    g.drawLine(4, 6, 4, 6);
    g.drawLine(4, 8, 4, 8);
    g.drawLine(4, 4, 4, 4);
    g.drawLine(4, 10, 4, 10);
    g.drawLine(6, 4, 6, 4);
    g.drawLine(6, 10, 6, 10);
    g.drawLine(7, 2, 7, 2);
    g.drawLine(7, 12, 7, 12);
    g.drawLine(8, 4, 8, 4);
    g.drawLine(8, 10, 8, 10);
    g.drawLine(10, 4, 10, 4);
    g.drawLine(10, 10, 10, 10);
    g.drawLine(10, 6, 10, 6);
    g.drawLine(10, 8, 10, 8);
    g.drawLine(12, 7, 12, 7);
    g.setColor(Color.white);
    g.drawLine(6, 6, 6, 6);
    g.dispose();

    //explosion
    explosion = gd.createCompatibleVolatileImage(16, 16);
    g = (Graphics2D) explosion.getGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
    g.setStroke(new BasicStroke(1.2f));
    g.setColor(Color.red);
    g.fillRect(0, 0, 16, 16);
    g.setColor(new Color(128, 64, 64));
    g.drawLine(0, 0, 0, 15);
    g.drawLine(0, 0, 15, 0);

    g.setColor(Color.black);
    g.fillRect(5, 5, 5, 5);
    g.drawLine(3, 7, 11, 7);
    g.drawLine(7, 3, 7, 11);
    g.setColor(new Color(128, 0, 0));
    g.drawLine(2, 7, 2, 7);
    g.drawLine(4, 6, 4, 6);
    g.drawLine(4, 8, 4, 8);
    g.drawLine(4, 4, 4, 4);
    g.drawLine(4, 10, 4, 10);
    g.drawLine(6, 4, 6, 4);
    g.drawLine(6, 10, 6, 10);
    g.drawLine(7, 2, 7, 2);
    g.drawLine(7, 12, 7, 12);
    g.drawLine(8, 4, 8, 4);
    g.drawLine(8, 10, 8, 10);
    g.drawLine(10, 4, 10, 4);
    g.drawLine(10, 10, 10, 10);
    g.drawLine(10, 6, 10, 6);
    g.drawLine(10, 8, 10, 8);
    g.drawLine(12, 7, 12, 7);
    g.setColor(Color.white);
    g.drawLine(6, 6, 6, 6);
    g.dispose();
  }
}

