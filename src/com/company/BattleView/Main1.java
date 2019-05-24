package com.company.BattleView;

import com.company.Hero.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main1 {

    public static Battle battle ;
    public static JFrame frame = new JFrame();

    public static void mainn(Hero hero, Hero targetHero) {
        EventQueue.invokeLater(() ->
        {
            battle = new Battle(hero,targetHero);

            frame = new JFrame("Battle frame");
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);

            frame.setLayout(new BorderLayout());

            frame.add(battle, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}