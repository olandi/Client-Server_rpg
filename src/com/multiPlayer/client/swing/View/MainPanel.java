
package com.multiPlayer.client.swing.View;

import com.multiPlayer.client.swing.BattleFieldController;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private BattleFieldController battleFieldController;
    //private JPanel mainPanel;

    public MainPanel(BattleFieldController battleFieldController) {
        this.battleFieldController = battleFieldController;
        buildMainPanel();
    }

    private void buildMainPanel() {
        setLayout(new BorderLayout());
        add(battleFieldController.getTimerPanel(), BorderLayout.NORTH);
        JLayer<JPanel> jlayer = new JLayer<JPanel>(battleFieldController.getBattleFieldPanel(), battleFieldController.getHittingPanel());
        add(jlayer, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        BoxLayout boxLayout = new BoxLayout(bottom, BoxLayout.X_AXIS);
        bottom.setLayout(boxLayout);

        bottom.add(battleFieldController.getHeroInfoPanel().getHeroInfoPanel());
        bottom.add(battleFieldController.getCombatLogPanel().getMiddlePanel());

        add(bottom, BorderLayout.SOUTH);
    }


}