package com.myDrafts.differentExamples.other;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class TextSlider1 {

  public static void main(String args[]) {
    JFrame frame = new JFrame("Text Slider");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    final JTextField textField = new JTextField();

    JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    BoundedRangeModel brm = textField.getHorizontalVisibility();
    scrollBar.setModel(brm);
    panel.add(textField);
    panel.add(scrollBar);

    final TextSlider1 ts = new TextSlider1();
    textField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Text: " + textField.getText());
      }
    });
    frame.add(panel, BorderLayout.NORTH);
    frame.setSize(300, 100);
    frame.setVisible(true);
  }
}