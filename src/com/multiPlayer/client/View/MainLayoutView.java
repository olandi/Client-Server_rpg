package com.multiPlayer.client.View;

import com.multiPlayer.client.MainLayoutController;

import javax.swing.*;
import java.awt.*;

public class MainLayoutView {
    private JPanel cards; //a panel that uses CardLayout
    final static String LOGIN_PANEL = "LOGIN_PANEL";
    final static String GO_BATTLE = "GO_BATTLE";
    final static String FIGHT = "FIGHT";


    private MainLayoutController controller;

    public MainLayoutView(MainLayoutController controller){
        this.controller = controller;
        initComponentToPane();
    }

    public void switchToBattle(){
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, GO_BATTLE);
    }

    public void switchToFight(){
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, FIGHT);
    }

    private void initComponentToPane() {

        JPanel loginPanel = controller.getLoginView().getLoginPanel();
        JPanel goBattlePanel = controller.getPickBattleView().getGoBattlePanel();
        JPanel fight = controller.getBattleFieldController().getMainGamePanel();


        cards = new JPanel(new CardLayout());

        cards.add(loginPanel, LOGIN_PANEL);
        cards.add(goBattlePanel, GO_BATTLE);
        cards.add(fight, FIGHT);

       // contentPane.add(loginPanel, BorderLayout.PAGE_START);
      //  contentPane.add(cards, BorderLayout.CENTER);


    }



    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CardLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);

        //Create and set up the content pane.
      //  MainLayoutView demo = new MainLayoutView(controller);
       // demo.addComponentToPane(frame.getContentPane());


        frame.add(cards,BorderLayout.CENTER);

        //Display the window.
       // frame.pack();
        frame.setVisible(true);
    }





}
