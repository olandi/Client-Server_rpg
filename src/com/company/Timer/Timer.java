package com.company.Timer;

import javax.swing.*;
import java.awt.*;

public class Timer extends JPanel {
    JLabel jLabel;
    Tim duration;
    javax.swing.Timer timer;
    JPanel battleFieldPanel;



    public Timer(int duration, JPanel battleFieldPanel) {
        this.duration = new Tim(duration,battleFieldPanel);
        this.battleFieldPanel = battleFieldPanel;

        initTimer();

        timer = new javax.swing.Timer(1000,i -> {

            this.duration.dec();

            jLabel.setText(Integer.toString(this.duration.getCount()));

            if (!ServerUtils.isReadyToMoveHeroExist()) {
               // this.duration.setCount(1);
               // jLabel.setText(Integer.toString(this.duration.getCount()));
                //jLabel.setText(0+"");

                timer.stop();
                jLabel.setText("Идет анимация боя...");


                this.duration.reset();
                //timer.restart();
                timer.start();
            }

            if (ServerUtils.isOneHeroRemain()){timer.stop(); jLabel.setText("Game over");}

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
