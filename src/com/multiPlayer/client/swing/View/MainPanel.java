
package com.multiPlayer.client.swing.View;

import com.multiPlayer.client.swing.Controller;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private Controller controller;
    //private JPanel mainPanel;

    public MainPanel(Controller controller) {
        this.controller = controller;
        buildMainPanel();
    }

    private void buildMainPanel() {
        setLayout(new BorderLayout());
        add(controller.getTimerPanel(), BorderLayout.NORTH);
        JLayer<JPanel> jlayer = new JLayer<JPanel>(controller.getBattleFieldPanel(), controller.getHittingPanel());
        add(jlayer, BorderLayout.CENTER);
        add(controller.getCombatLogPanel().getMiddlePanel(), BorderLayout.SOUTH);
    }

}