package com.myDrafts.differentExamples.other;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class TextSlider {

  static JPanel middlePanel;
  static JTextArea display;
  static JScrollPane scroll;

  public static void main(String args[]) {
    JFrame frame = new JFrame("Text Slider");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    /*
    final JTextField textField = new JTextField();

    JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    BoundedRangeModel brm = textField.getHorizontalVisibility();
    scrollBar.setModel(brm);
    panel.add(textField);
    panel.add(scrollBar);

    final CombatLogPanel ts = new CombatLogPanel();
    textField.addActionListener(e -> System.out.println("Text: " + textField.getText()));
    */

    middlePanel=new JPanel();
    middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));

    // create the middle panel components

    display = new JTextArea(7, 40);
    display.setEditable(false); // set textArea non-editable
    display.setLineWrap(true);



    scroll = new JScrollPane(display);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    //Add Textarea in to middle panel
    middlePanel.add(scroll);

    display.append("gehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffff" +
            "gehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffff" +
            "gehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffff" +
            "gehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffff" +
            "gehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffffgehhdhdffffffffffffffffffffffffffffff");

    frame.add(middlePanel, BorderLayout.NORTH);
    frame.setSize(300, 100);
    frame.setVisible(true);
    frame.pack();
  }
}