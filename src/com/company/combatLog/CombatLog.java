package com.company.combatLog;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CombatLog extends JPanel {

  private static JPanel middlePanel;
  private static JTextArea display;
  private static JScrollPane scroll;

  public JPanel getMiddlePanel() {
    return middlePanel;
  }

  public void appendText(String text){
    display.append(text);
  }





  public CombatLog() {

    middlePanel=new JPanel();
    middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Combat Log"));

    // create the middle panel components
    display = new JTextArea(7, 40);
    display.setEditable(false); // set textArea non-editable
    display.setLineWrap(true);


    scroll = new JScrollPane(display);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    //Add Textarea in to middle panel
    middlePanel.add(scroll);

  }
}