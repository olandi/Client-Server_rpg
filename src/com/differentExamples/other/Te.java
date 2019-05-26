package com.differentExamples.other;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Te {
   static JPanel middlePanel;
    static JTextArea display;
    static JScrollPane scroll;

    public static void main(String[] args) {
        middlePanel=new JPanel();
        middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));

        // create the middle panel components

        display = new JTextArea(16, 58);
        display.setEditable(false); // set textArea non-editable
        display.setLineWrap(true);
        display.setWrapStyleWord(true);

        scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //Add Textarea in to middle panel
        middlePanel.add(scroll);
    }
}
