package com.company.Timer;

import com.company.heroActions.HeroAction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;


public class Timer extends JPanel {

    JLabel jLabel;
    Tim duration;
    javax.swing.Timer timer;

    public static Deque<HeroAction> actions = new ArrayDeque<>();

    public Timer(int duration) {
        this.duration = new Tim(duration);

        initTimer();

        timer = new javax.swing.Timer(1000,i -> {
           // jLabel.setText(duration);

           // System.out.println(this.duration.getCount());
            this.duration.dec();

            jLabel.setText(Integer.toString(this.duration.getCount()));

           // repaint();

        } );
        timer.start();

    }

    public void initTimer(){
        jLabel = new JLabel();
        jLabel.setText(Integer.toString(duration.getCount()));
        jLabel.setFont(new Font("Arial",Font.BOLD,25));
        this.add(jLabel);



    }

    public void method() {


    }
}
