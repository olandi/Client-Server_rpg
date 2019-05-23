package com.company.BattleView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main1 {

    static Battle battle = new Battle();
    public static JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            frame = new JFrame("Battle frame");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);

            frame.setLayout(new BorderLayout());

            frame.add(battle, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}