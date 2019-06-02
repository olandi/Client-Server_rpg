package com.singlePlayer.company.client.swing.View;

import com.singlePlayer.company.client.swing.Controller;
import com.singlePlayer.company.server.ServerUtils;
import com.singlePlayer.company.server.Tim;

import javax.swing.*;
import java.awt.*;

public class TimerPanel extends JPanel {
    private JLabel jLabel;
    private Tim tim;
    private Timer timer;
    Controller controller;

    public TimerPanel(Tim tim, Controller controller) {
        this.tim = tim;
        this.controller = controller;

        initTimer();

        timer = new Timer(1000, i -> {

            tim.dec();

            jLabel.setText(Integer.toString(tim.getCount()));

            if (!controller.getServerUtils().isReadyToMoveHeroExist()) {
                timer.stop();
                jLabel.setText("Идет анимация боя...");
                tim.reset();
                timer.start();
            }

            if (controller.getServerUtils().isOneHeroRemain()) {
                timer.stop();
                jLabel.setText("Game over");
            }

        });
        timer.start();
    }

    public void initTimer() {
        jLabel = new JLabel();
        jLabel.setText(Integer.toString(tim.getCount()));
        jLabel.setFont(new Font("Arial", Font.BOLD, 25));
        this.add(jLabel);
    }

}
