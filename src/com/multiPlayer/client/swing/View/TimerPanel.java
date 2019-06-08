package com.multiPlayer.client.swing.View;

import com.multiPlayer.client.swing.BattleFieldController;

import javax.swing.*;
import java.awt.*;

public class TimerPanel extends JPanel {

    private JLabel jLabel;
    private BattleFieldController battleFieldController;

    public TimerPanel(BattleFieldController battleFieldController) {
        this.battleFieldController = battleFieldController;
        jLabel = new JLabel();
        jLabel.setText("Start Battle");
        jLabel.setFont(new Font("Arial", Font.BOLD, 25));
        this.add(jLabel);
    }

    public JLabel getjLabel() {
        return jLabel;
    }




}
