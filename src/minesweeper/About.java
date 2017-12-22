package minesweeper;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class About extends JDialog implements ActionListener {
  private JPanel panel = new JPanel();
  private GridBagLayout gridBagLayout = new GridBagLayout();
  private JLabel title = new JLabel();
  private JLabel us = new JLabel();
  private JLabel rules = new JLabel();
  private JLabel size = new JLabel();

  public About(Frame frame, String _title, boolean mod) {
    super(frame, _title, mod);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
    }
  }

  public About() {
    this(null, "", false);
  }
  private void jbInit() throws Exception {
    panel.setLayout(gridBagLayout);
    size.setPreferredSize(new Dimension(190, 1));
    title.setFont(new java.awt.Font("Serif", 1, 20));
    title.setForeground(SystemColor.black);
    title.setPreferredSize(new Dimension(131, 20));
    title.setText("RULES");
    rules.setForeground(SystemColor.black);
    rules.setPreferredSize(new Dimension(131, 80));
    rules.setText("<html><h2>Goal</h2> <p>Discover all boxes without explode mines !</p>");
    us.setForeground(Color.black);
    us.setPreferredSize(new Dimension(131, 50));
    us.setText("<html><p> by Alexis Ducreux<br /> & Julien Bringard</p><html>");
    getContentPane().add(panel);
    panel.add(size);
    panel.add(title,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    panel.add(rules,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    panel.add(us,      new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
  }
  
  public void actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}