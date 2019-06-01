package com.singlePlayer.company;

import com.singlePlayer.company.client.swing.View.*;
import com.singlePlayer.company.client.swing.Controller;
import com.singlePlayer.company.server.ServerUtils;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                    Controller controller = new Controller();
                    controller.setBattleFieldPanelMouseListener();

                    Main.createAndShowGUI(controller.getMainGamePanel());


                }
        );
    }


    public static void createAndShowGUI(JPanel panel) {
        //Create and set up the window.
        JFrame frame = new JFrame("CardLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        //Create and set up the content pane.
        //  MainLayoutView demo = new MainLayoutView(controller);
        // demo.addComponentToPane(frame.getContentPane());


        frame.add(panel, BorderLayout.CENTER);

        //Display the window.
        // frame.pack();
        frame.setVisible(true);
    }


}
