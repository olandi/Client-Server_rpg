package com.company.BattleView;

import com.company.Hero.Hero;

import javax.swing.*;
import java.awt.*;

public class Main1 {

    public static Battle battle ;
    public static JFrame battleFrame = new JFrame();

    public static void mainn(Hero hero, Hero targetHero) {
        EventQueue.invokeLater(() ->
        {
            battle = new Battle(hero,targetHero);

            battleFrame = new JFrame("Battle battleFrame");
            battleFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            battleFrame.setSize(800, 600);
            battleFrame.setResizable(false);

            battleFrame.setLayout(new BorderLayout());

            battleFrame.add(battle, BorderLayout.CENTER);

            battleFrame.setVisible(true);
        });
    }
}