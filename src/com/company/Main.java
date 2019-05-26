package com.company;

import com.company.BattleView.Battle;
import com.company.Timer.Timer;
import com.company.clientUtils.ClientUtils;
import com.company.controllerUtils.ControllerUtils;
import com.company.turnManager.TurnManager;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final int TIMER_DURATION = 40;


    public JFrame frame;
    private JPanel battleFieldPanel = new JPanel();
    private Battle battle = new Battle(battleFieldPanel);
    private GameFieldGUI gameFieldGUI = new GameFieldGUI(battleFieldPanel, battle);


    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
                new Main().initFrame()
        );
    }

    private void initFrame(){
        frame = new JFrame("W");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(true);

        frame.setLayout(new BorderLayout());

        frame.add(battleFieldPanel);

        battleFieldPanel.setLayout(new BorderLayout());

        battleFieldPanel.add(new Timer(TIMER_DURATION, battleFieldPanel), BorderLayout.NORTH);


        JLayer<JPanel> jlayer = new JLayer<JPanel>(gameFieldGUI, battle);
        battleFieldPanel.add(jlayer);

/*
        battleFieldPanel.add(gameFieldGUI, BorderLayout.CENTER);
        battleFieldPanel.add(battle, BorderLayout.CENTER);*/




        battleFieldPanel.add(GameFieldGUI.combatLog.getMiddlePanel(),BorderLayout.SOUTH);

        battleFieldPanel.addMouseListener(gameFieldGUI.getMouseListener());

        frame.setVisible(true);
    }
}
