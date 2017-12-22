package minesweeper;

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class MineSweeper
    extends JFrame
    implements MouseListener, WindowListener, ActionListener {
  private JPanel menuPanel = new JPanel();
  private JPanel gamePanel = new JPanel();
  private GridBagLayout layoutGamePanel = new GridBagLayout();
  private Segment showMines = new Segment(); //affichage nombre de mines
  private JButton newGameButton = new JButton();
  private Segment showGameTimer = new Segment(); //affichage du timer écoulé
  private Border borderPanel;
  private JMenuBar menu = new JMenuBar();
  private JMenu gameMenu = new JMenu("Game");
  private JCheckBox pause = new JCheckBox("Pause");
  private JMenu help = new JMenu("?");
  private JMenuItem newGameMenu = new JMenuItem("New Game");
  JCheckBoxMenuItem easyGameMenu = new JCheckBoxMenuItem("Easy");
  JCheckBoxMenuItem mediumGameMenu = new JCheckBoxMenuItem("Medium");
  JCheckBoxMenuItem hardGameMenu = new JCheckBoxMenuItem("Hard");
  JCheckBoxMenuItem customGameMenu = new JCheckBoxMenuItem("Custom");
  private JMenuItem about = new JMenuItem("About");
  private BoxLayout layoutMenuPanel = new BoxLayout(menuPanel,
      BoxLayout.LINE_AXIS);
  private Component box2; 
  private Component box3;
  private Component box1;
  private Component box4;

  private Icon cool, oups, boum, win; //images du newGameButton

  int nFlag = 0; //nombres de drapeaux posés
  private int nMines; //nombre total de mines
  private int WIDTH; //nombre de cases selon la largeur
  private int HEIGHT; //nombre de cases selon la hauteur
  private int nBoxes; //nombre de cases non découvertes restantes
  GameGrid[][] game; //tableau de la grille de jeu (cases)
  private String mines; //contient la répartition des mines
  private int[][] selectedBoxes = new int[8][2]; //repère les cases sélectionnées
  private GameTimer timer = new GameTimer(showGameTimer); 
  private int LVL;

  //lvl == 1 -> Débutant
  //lvl == 2 -> Intermédiaire
  //lvl == 3 -> Expert
  //lvl == 4 -> Personnalisé
  public MineSweeper(int _height, int _width, int _mines, int _lvl) {
    HEIGHT = _height;
    WIDTH = _width;
    nBoxes = HEIGHT * WIDTH;
    nMines = _mines;
    LVL = _lvl;
    game = new GameGrid[HEIGHT][WIDTH];

    URL location;
    location = java.lang.ClassLoader.getSystemResource("Images/cool.gif");
    cool = new ImageIcon(location);
    location = java.lang.ClassLoader.getSystemResource("Images/oups.gif");
    oups = new ImageIcon(location);
    location = java.lang.ClassLoader.getSystemResource("Images/boum.gif");
    boum = new ImageIcon(location);
    location = java.lang.ClassLoader.getSystemResource("Images/win.gif");
    win = new ImageIcon(location);

    //création des cases
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        game[i][j] = new GameGrid();
      }
    }

    //séléction du niveau
    if (_lvl == 1) easyGameMenu.setSelected(true);
    if (_lvl == 2) mediumGameMenu.setSelected(true);
    if (_lvl == 3) hardGameMenu.setSelected(true);
    if (_lvl == 4) customGameMenu.setSelected(true);

    //initialisation
    newGame();

    try {
      jbInit();
      this.setVisible(true);
      newGameButton.requestFocus();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //initialise le jeu
  public void newGame() {
    timer.cancel(); //Timer à 0
    newGameButton.setIcon(cool);
    nFlag = 0;
    nBoxes = HEIGHT * WIDTH;
    showMines.setValue(nMines);
    showGameTimer.setValue(0);
    gamePanel.setVisible(true);
    pause.setSelected(false);

    //Génération des mines
    mines = "";
    for (int i = 0; i < nMines; i++) mines = mines + "1";
    while (mines.length() < HEIGHT * WIDTH) {
      int i = (int) (Math.random() * (mines.length() + 1));
      mines = mines.substring(0, i) + "0" + mines.substring(i);
    }

    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        game[i][j].reset();
        game[i][j].removeMouseListener(this);
        game[i][j].addMouseListener(this); 
        if (mines.charAt(i * WIDTH + j) == '1') {
          game[i][j].setMine(true);
        }
      }
    }
    repaint();

    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        if (!game[i][j].isMine()) {
          int n = 0;
          try {
            if (game[i - 1][j - 1].isMine()) n++;
          }
          catch (java.lang.ArrayIndexOutOfBoundsException e) {}
          try {
            if (game[i - 1][j].isMine()) n++;
          }
          catch (java.lang.ArrayIndexOutOfBoundsException e) {}
          try {
            if (game[i - 1][j + 1].isMine()) n++;
          }
          catch (java.lang.ArrayIndexOutOfBoundsException e) {}
          try {
            if (game[i][j - 1].isMine()) n++;
          }
          catch (java.lang.ArrayIndexOutOfBoundsException e) {}
          try {
            if (game[i][j + 1].isMine()) n++;
          }
          catch (java.lang.ArrayIndexOutOfBoundsException e) {}
          try {
            if (game[i + 1][j - 1].isMine()) n++;
          }
          catch (java.lang.ArrayIndexOutOfBoundsException e) {}
          try {
            if (game[i + 1][j].isMine()) n++;
          }
          catch (java.lang.ArrayIndexOutOfBoundsException e) {}
          try {
            if (game[i + 1][j + 1].isMine()) n++;
          }
          catch (java.lang.ArrayIndexOutOfBoundsException e) {}
          game[i][j].setNumber(n);
        }
      }
    }
  }

  private void jbInit() throws Exception {
    borderPanel = BorderFactory.createEtchedBorder(Color.white,
        new Color(156, 156, 156));
    box2 = Box.createGlue();
    box3 = Box.createGlue();
    box1 = Box.createHorizontalStrut(8);
    box1.setSize(5, 50);
    box4 = Box.createHorizontalStrut(8);
    box4.setSize(5, 50);

    this.addWindowListener(this);

    int sizeX = WIDTH * 16 + 20;
    int sizeY = HEIGHT * 16 + 20;
    if (sizeX < 160) sizeX = 150;

    this.setSize(sizeX + 6, sizeY + 50 + 23 + 25);
    this.setTitle("MineSweeper");
    this.setResizable(false);

    //MENU
    gameMenu.setMnemonic('G');
    newGameMenu.addActionListener(this);
    newGameMenu.setMnemonic('N');
    easyGameMenu.addActionListener(this);
    easyGameMenu.setMnemonic('1');
    mediumGameMenu.addActionListener(this);
    mediumGameMenu.setMnemonic('2');
    hardGameMenu.addActionListener(this);
    hardGameMenu.setMnemonic('3');
    customGameMenu.addActionListener(this);
    customGameMenu.setMnemonic('C');
    gameMenu.add(newGameMenu);
    gameMenu.add(new JSeparator());
    gameMenu.add(easyGameMenu);
    gameMenu.add(mediumGameMenu);
    gameMenu.add(hardGameMenu);
    gameMenu.add(customGameMenu);
    menu.setBorderPainted(false);
    menu.add(gameMenu);
    pause.setMnemonic('P');
    pause.setOpaque(false);
    pause.setFocusPainted(false);
    pause.addActionListener(this);
    menu.add(pause);
    help.setMnemonic('?');
    about.addActionListener(this);
    about.setMnemonic('A');
    help.add(about);
    menu.add(help);
    this.setJMenuBar(menu);

    showMines.setMaximumSize(new Dimension(49, 27));
    showGameTimer.setMaximumSize(new Dimension(49, 27));
    newGameButton.setMaximumSize(new Dimension(25, 25));
    newGameButton.setMinimumSize(new Dimension(25, 25));
    menuPanel.setBorder(borderPanel);
    menuPanel.setPreferredSize(new Dimension(450, 50));
    menuPanel.setLayout(layoutMenuPanel);
    gamePanel.setBorder(borderPanel);
    gamePanel.setPreferredSize(new Dimension(sizeX, sizeY));
    gamePanel.setLayout(layoutGamePanel);
    showMines.setValue(nMines);
    showGameTimer.setValue(0);
    newGameButton.setPreferredSize(new Dimension(25, 25));
    newGameButton.setFocusPainted(false);
    newGameButton.setIcon(cool);
    newGameButton.setMargin(new Insets(0, 0, 0, 0));
    newGameButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newGameButton_actionPerformed(e);
      }
    });
    menuPanel.add(box1, null);
    menuPanel.add(showMines, null);
    menuPanel.add(box2, null);
    menuPanel.add(newGameButton, null);
    menuPanel.add(box3, null);
    menuPanel.add(showGameTimer, null);
    menuPanel.add(box4, null);
    this.getContentPane().add(menuPanel, BorderLayout.NORTH);
    this.getContentPane().add(gamePanel, BorderLayout.CENTER);


    GameDisplay gd = new GameDisplay(this.getGraphicsConfiguration());

    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        game[i][j].setGameDisplay(gd);
        gamePanel.add(game[i][j], new GridBagConstraints(j, i, 1, 1, 0.0, 0.0
            , GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
      }
    }
  }


  public int[] caseClic(int x, int y) {
    int OFFSETX = (int) game[0][0].getX() + 3;
    int OFFSETY = (int) game[0][0].getY() + 22;
    int posx = -1, posy = -1;
    if (x - OFFSETX >= 0) posx = (x - OFFSETX) / 16;
    if (posx >= WIDTH) posx = -1;
    if (y - OFFSETY >= 0 && posx != -1) posy = (y - OFFSETY) / 16;
    if (posy >= HEIGHT) posy = -1;
    if (posy == -1) posx = -1;
    int[] retour = {
        posx, posy};
    return retour;
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    try {
      int x = (int) ( (JPanel) e.getSource()).getLocation().getX() + e.getX() + 3;
      int y = (int) ( (JPanel) e.getSource()).getLocation().getY() + e.getY() + 22;
      int[] pos = caseClic(x, y); //coordonnées de la case enfoncée
      newGameButton.setIcon(oups);

      if (e.getButton() == e.BUTTON3 && pos[1] != -1 && pos[0] != -1) {
        int temp = game[pos[1]][pos[0]].getCondition();
        switch (temp) {
          case 0:
            game[pos[1]][pos[0]].setCondition(2);
            nFlag++;
            showMines.setValue(nMines - nFlag);
            break;
          case 2: 
            game[pos[1]][pos[0]].setCondition(3);
            nFlag--;
            showMines.setValue(nMines - nFlag);
            break;
          case 3: 
            game[pos[1]][pos[0]].setCondition(0);
            break;
        }
        game[pos[1]][pos[0]].repaint();
      }

      y = pos[1];
      x = pos[0];
      if (e.getButton() == e.BUTTON1 && x != -1 && y != -1 && game[y][x].getCondition() == 1 && game[y][x].getNumber() != 0) {
        for (int i = 0; i < 7; i++) {
          for (int j = 0; j < 2; j++) {
            selectedBoxes[i][j] = -1;
          }
        }
        try {
          if (game[y - 1][x - 1].getCondition() == 0) {
            game[y - 1][x - 1].setSelected(true);
            selectedBoxes[0][0] = y - 1;
            selectedBoxes[0][1] = x - 1;
          }
        }
        catch (Exception exc) {}
        try {
          if (game[y - 1][x].getCondition() == 0) {
            game[y - 1][x].setSelected(true);
            selectedBoxes[1][0] = y - 1;
            selectedBoxes[1][1] = x;
          }
        }
        catch (Exception exc) {}
        try {
          if (game[y - 1][x + 1].getCondition() == 0) {
            game[y - 1][x + 1].setSelected(true);
            selectedBoxes[2][0] = y - 1;
            selectedBoxes[2][1] = x + 1;
          }
        }
        catch (Exception exc) {}
        try {
          if (game[y][x - 1].getCondition() == 0) {
            game[y][x - 1].setSelected(true);
            selectedBoxes[3][0] = y;
            selectedBoxes[3][1] = x - 1;
          }
        }
        catch (Exception exc) {}
        try {
          if (game[y][x + 1].getCondition() == 0) {
            game[y][x + 1].setSelected(true);
            selectedBoxes[4][0] = y;
            selectedBoxes[4][1] = x + 1;
          }
        }
        catch (Exception exc) {}
        try {
          if (game[y + 1][x - 1].getCondition() == 0) {
            game[y + 1][x - 1].setSelected(true);
            selectedBoxes[5][0] = y + 1;
            selectedBoxes[5][1] = x - 1;
          }
        }
        catch (Exception exc) {}
        try {
          if (game[y + 1][x].getCondition() == 0) {
            game[y + 1][x].setSelected(true);
            selectedBoxes[6][0] = y + 1;
            selectedBoxes[6][1] = x;
          }
        }
        catch (Exception exc) {}
        try {
          if (game[y + 1][x + 1].getCondition() == 0) {
            game[y + 1][x + 1].setSelected(true);
            selectedBoxes[7][0] = y + 1;
            selectedBoxes[7][1] = x + 1;
          }
        }
        catch (Exception exc) {}
      }
    }
    catch (java.lang.ClassCastException ex) {}
  }

  public void mouseReleased(MouseEvent e) {

    if (nBoxes == HEIGHT * WIDTH && e.getButton() == e.BUTTON1) {
      timer.cancel();
      timer = new GameTimer(showGameTimer);
      timer.start();
    }

    try {
      int x = (int) ( (JPanel) e.getSource()).getLocation().getX() + e.getX() + 3;
      int y = (int) ( (JPanel) e.getSource()).getLocation().getY() + e.getY() + 22;
      int[] pos = caseClic(x, y);
      newGameButton.setIcon(cool);
      if (pos[0] != -1 && pos[1] != -1) {
        y = pos[1];
        x = pos[0];
        if (e.getButton() == e.BUTTON1) {
          discover(y, x);
          repaint();
        }
        game[y][x].setSelected(false);
        try {
          game[selectedBoxes[0][0]][selectedBoxes[0][1]].setSelected(false);
        }
        catch (Exception exc) {}
        try {
          game[selectedBoxes[1][0]][selectedBoxes[1][1]].setSelected(false);
        }
        catch (Exception exc) {}
        try {
          game[selectedBoxes[2][0]][selectedBoxes[2][1]].setSelected(false);
        }
        catch (Exception exc) {}
        try {
          game[selectedBoxes[3][0]][selectedBoxes[3][1]].setSelected(false);
        }
        catch (Exception exc) {}
        try {
          game[selectedBoxes[4][0]][selectedBoxes[4][1]].setSelected(false);
        }
        catch (Exception exc) {}
        try {
          game[selectedBoxes[5][0]][selectedBoxes[5][1]].setSelected(false);
        }
        catch (Exception exc) {}
        try {
          game[selectedBoxes[6][0]][selectedBoxes[6][1]].setSelected(false);
        }
        catch (Exception exc) {}
        try {
          game[selectedBoxes[7][0]][selectedBoxes[7][1]].setSelected(false);
        }
        catch (Exception exc) {}
      }
    }
    catch (java.lang.ClassCastException ex) {}
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  void newGameButton_actionPerformed(ActionEvent e) {
    if (!pause.isSelected()) newGame();
  }

  public void discover(int y, int x) {
    if ( (game[y][x].getCondition() == 0 || game[y][x].getCondition() == 3) && !game[y][x].isMine()) {
      nBoxes--; 
      game[y][x].setCondition(1); 
      if (game[y][x].getNumber() == 0) { 
        discoverPart1(x - 1, y - 1);
        discoverPart1(x - 1, y);
        discoverPart1(x - 1, y + 1);
        discoverPart1(x, y - 1);
        discoverPart1(x, y + 1);
        discoverPart1(x + 1, y - 1);
        discoverPart1(x + 1, y);
        discoverPart1(x + 1, y + 1);
      }
    }

    else if (game[y][x].getCondition() == 1 && game[y][x].getNumber() != 0) {
      int n = 0; 
      if (discoverPart2(x - 1, y - 1)) n++;
      if (discoverPart2(x - 1, y)) n++;
      if (discoverPart2(x - 1, y + 1)) n++;
      if (discoverPart2(x, y - 1)) n++;
      if (discoverPart2(x, y + 1)) n++;
      if (discoverPart2(x + 1, y - 1)) n++;
      if (discoverPart2(x + 1, y)) n++;
      if (discoverPart2(x + 1, y + 1)) n++;

      if (n == game[y][x].getNumber()) {
        if (discoverPart3(x - 1, y - 1)) discover(y - 1, x - 1);
        if (discoverPart3(x - 1, y)) discover(y, x - 1);
        if (discoverPart3(x - 1, y + 1)) discover(y + 1, x - 1);
        if (discoverPart3(x, y - 1)) discover(y - 1, x);
        if (discoverPart3(x, y + 1)) discover(y + 1, x);
        if (discoverPart3(x + 1, y - 1)) discover(y - 1, x + 1);
        if (discoverPart3(x + 1, y)) discover(y, x + 1);
        if (discoverPart3(x + 1, y + 1)) discover(y + 1, x + 1);
      }
    }

    
    else if ( (game[y][x].getCondition() == 0 || game[y][x].getCondition() == 3) && game[y][x].isMine()) {
      timer.cancel(); 
      game[y][x].setCondition(4); 
      newGameButton.setIcon(boum);
      for (int i = 0; i < HEIGHT; i++) {
        for (int j = 0; j < WIDTH; j++) {
          game[i][j].removeMouseListener(this);
          game[i][j].setBlocked(true);
          if (! (y == i && x == j) && mines.charAt(i * WIDTH + j) == '1' && game[i][j].getCondition() != 2)
            game[i][j].setCondition(5); 
        }
      }
      for (int i = 0; i < HEIGHT; i++) {
        for (int j = 0; j < WIDTH; j++) {
          if (game[i][j].getCondition() == 2 && !game[i][j].isMine()) 
              game[i][j].setCondition(6);
        }
      }
    }

    if (nBoxes == nMines && !game[0][0].isBlocked()) {
      timer.cancel();
      showMines.setValue(0);
      newGameButton.setIcon(win);
      for (int i = 0; i < HEIGHT; i++) {
        for (int j = 0; j < WIDTH; j++) {
          game[i][j].removeMouseListener(this); 
          game[i][j].setBlocked(true);
          if (game[i][j].isMine()) 
              game[i][j].setCondition(2);
        }
      }
    }
  }

 
  public void discoverPart1(int x, int y) {
    if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {
      if (game[y][x].getCondition() == 0 && game[y][x].getNumber() != 0) {
        game[y][x].setCondition(1);
        nBoxes--;
      }
      if (game[y][x].getCondition() == 0 && game[y][x].getNumber() == 0)
        discover(y, x); 
    }
  }

  public boolean discoverPart2(int x, int y) {
    if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {
      if (game[y][x].getCondition() == 2)
        return true;
    }
    return false;
  }


  public boolean discoverPart3(int x, int y) {
    if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {
      if (game[y][x].getCondition() == 0 || game[y][x].getCondition() == 3)
        return true;
    }
    return false;
  }

  public void windowOpened(WindowEvent e) {
  }

  public void windowClosing(WindowEvent e) {
    timer.stop();
    System.exit(0);
  }

  public void windowClosed(WindowEvent e) {
  }

  public void windowIconified(WindowEvent e) {
    try {
      timer.suspend();
    } 
    catch (Exception esc) {}
  }

  public void windowDeiconified(WindowEvent e) {
    try {
      timer.resume();
    }
    catch (Exception esc) {}
  }

  public void windowActivated(WindowEvent e) {
  }

  public void windowDeactivated(WindowEvent e) {
  }


  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == newGameMenu) newGame();
    else if (e.getSource() == easyGameMenu && LVL != 1) {
      this.dispose(); 
      System.gc();
      if (LVL == 1) easyGameMenu.setSelected(true);
      MineSweeper minesweeper = new MineSweeper(8, 8, 10, 1); 
    }
    else if (e.getSource() == easyGameMenu && !easyGameMenu.isSelected())
      easyGameMenu.setSelected(true);
    else if (e.getSource() == mediumGameMenu && LVL != 2) {
      this.dispose();
      System.gc();
      if (LVL == 2) mediumGameMenu.setSelected(true);
      MineSweeper minesweeper = new MineSweeper(16, 16, 40, 2);
    }
    else if (e.getSource() == mediumGameMenu &&
             !mediumGameMenu.isSelected()) mediumGameMenu.setSelected(true);
    else if (e.getSource() == hardGameMenu && LVL != 3) {
      this.dispose(); // on détruit la fenetre
      System.gc();
      if (LVL == 3) hardGameMenu.setSelected(true);
      MineSweeper minesweeper = new MineSweeper(16, 30, 99, 3);
    }
    else if (e.getSource() == hardGameMenu && LVL != 4) hardGameMenu.setSelected(true);
    else if (e.getSource() == customGameMenu) {
      if (LVL == 4) customGameMenu.setSelected(true);
      else customGameMenu.setSelected(false);
      Custom perso = new Custom(this, "Parameter", true, HEIGHT,
                                            WIDTH, nMines);
      perso.setLocation( (int)this.getLocation().getX() + 20,
                        (int)this.getLocation().getY() + 20);
      perso.setVisible(true);
    }
    else if (e.getSource() == pause) {
      if (pause.isSelected()) {
        gamePanel.setVisible(false);
        timer.suspend();
      }
      else {
        gamePanel.setVisible(true);
        timer.resume();
      }
    }
    else if (e.getSource() == about) {
      About app = new About(this, "Minesweeper", true);
      app.setLocation( (int)this.getLocation().getX() + 20,
                      (int)this.getLocation().getY() + 20);
      app.setVisible(true);
    }
  }
}
