package com.multiPlayer.client.View;

import com.multiPlayer.client.ClientListener;
import com.multiPlayer.client.MainLayoutController;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;


public class LoginView {

    private JTextField login;
    private JButton button;
    private String clientName;
    private JPanel loginPanel;
    private MainLayoutController controller;

    public LoginView(MainLayoutController controller) {
        initLoginGui();
        this.controller = controller;
    }


    public JPanel getLoginPanel() {
        return loginPanel;
    }

    private void initLoginGui() {
        clientName = "Player" + ThreadLocalRandom.current().nextInt(1, 5000);

        loginPanel = new JPanel(null);

        login = new JTextField(clientName, 30);
        login.setEditable(true);

        button = new JButton("Connect to server");

        button.addActionListener(actionEvent -> {
            controller.createPlayer(login.getText());
            new ClientListener(controller).start();
        });

        JLabel loginLabel = new JLabel("Player name");
        loginLabel.setFont(new Font("Arial", Font.BOLD,18));
        login.setFont(new Font("Arial", Font.PLAIN,16));

        loginLabel.setBounds(300,250,200,50);
        login.setBounds(300,300,200,30);
        button.setBounds(310,400,180,40);


        loginPanel.add(loginLabel);
        loginPanel.add(login);
        loginPanel.add(button);

    }


}