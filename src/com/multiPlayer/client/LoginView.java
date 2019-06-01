package com.multiPlayer.client;

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

        // loginFrame = new JFrame();
        //  loginFrame.setSize(300, 300);

        loginPanel = new JPanel();

        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        login = new JTextField(clientName, 30);
        login.setEditable(true);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);


        button = new JButton("Connect to server");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(actionEvent -> {

            String clientName = login.getText();
            controller.getMainLayoutModel().setPlayer(clientName);

            new ClientListener(controller).start();


        });

        Dimension minSize0 = new Dimension(5, 50);
        Dimension prefSize0 = new Dimension(5, 50);
        Dimension maxSize0 = new Dimension(Short.MAX_VALUE, 50);


        Dimension minSize = new Dimension(5, 400);
        Dimension prefSize = new Dimension(5, 400);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 400);

        loginPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        loginPanel.add(login);

        loginPanel.add(new Box.Filler(minSize0, prefSize0, maxSize0));

        loginPanel.add(button);
        loginPanel.add(new Box.Filler(minSize, prefSize, maxSize));


        //  loginFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        //  loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  loginFrame.setVisible(true);

    }


}
/*

 */