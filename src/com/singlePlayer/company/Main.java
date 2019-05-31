package com.singlePlayer.company;

import com.singlePlayer.company.client.SwingView.*;
import com.singlePlayer.company.controller.Controller;
import com.singlePlayer.company.server.ServerUtils;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final int TIMER_DURATION = 40;


    private JFrame frame;
    private MainPanel mainGamePanel;
    private HittingPanel hittingPanel;
    private BattleFieldPanel battleFieldPanel;
    private TimerPanel timerPanel;
    private CombatLogPanel combatLogPanel;


    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
                new Main().initFrame()
        );
    }

    private void initFrame() {
        initFrameComponents();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(true);

        frame.setLayout(new BorderLayout());

        frame.add(mainGamePanel);

        mainGamePanel.setLayout(new BorderLayout());

        mainGamePanel.add(timerPanel, BorderLayout.NORTH);


        JLayer<JPanel> jlayer = new JLayer<JPanel>(battleFieldPanel, hittingPanel);
        mainGamePanel.add(jlayer, BorderLayout.CENTER);


        mainGamePanel.add(combatLogPanel.getMiddlePanel(), BorderLayout.SOUTH);

        mainGamePanel.addMouseListener(battleFieldPanel.getMouseListener());

        frame.setVisible(true);
    }


    private void initFrameComponents() {
        frame = new JFrame("W");
        mainGamePanel = new MainPanel();
        battleFieldPanel = new BattleFieldPanel(mainGamePanel);
        hittingPanel = new HittingPanel(mainGamePanel);
        timerPanel = new TimerPanel(ServerUtils.getTimer(TIMER_DURATION));
        combatLogPanel = new CombatLogPanel();


        Controller.setMainGamePanel(mainGamePanel);
        Controller.setBattleFieldPanel(battleFieldPanel);
        Controller.setHittingPanel(hittingPanel);
        Controller.setTimerPanel(timerPanel);
        Controller.setCombatLogPanel(combatLogPanel);

    }

}
