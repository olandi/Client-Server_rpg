package com.company.Timer;

import javax.swing.*;
import java.awt.*;

public class Timer extends JPanel {
    JLabel jLabel;
    Tim duration;
    javax.swing.Timer timer;

    public Timer(int duration) {
        this.duration = new Tim(duration);

        initTimer();

        timer = new javax.swing.Timer(1000,i -> {

            this.duration.dec();

            jLabel.setText(Integer.toString(this.duration.getCount()));
        } );
        timer.start();
    }

    public void initTimer(){
        jLabel = new JLabel();
        jLabel.setText(Integer.toString(duration.getCount()));
        jLabel.setFont(new Font("Arial",Font.BOLD,25));
        this.add(jLabel);

    }

}
