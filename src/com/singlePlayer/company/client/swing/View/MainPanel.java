
package com.singlePlayer.company.client.swing.View;

import com.singlePlayer.company.client.swing.Controller;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private Controller controller;
    private JPanel mainPanel;

    public MainPanel(Controller controller) {
        this.controller = controller;
        buildMainPanel();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void buildMainPanel() {

        mainPanel = new JPanel();


        mainPanel.setLayout(new BorderLayout());

        //mainPanel.add(mainGamePanel);

       // mainGamePanel.setLayout(new BorderLayout());

        mainPanel.add(controller.getTimerPanel(), BorderLayout.NORTH);


        JLayer<JPanel> jlayer = new JLayer<JPanel>(controller.getBattleFieldPanel(), controller.getHittingPanel());
        mainPanel.add(jlayer, BorderLayout.CENTER);

        mainPanel.add(controller.getCombatLogPanel().getMiddlePanel(), BorderLayout.SOUTH);

        //controller.setBattleFieldPanelMouseListener(); //поле еще не проинициализировано

    }


}