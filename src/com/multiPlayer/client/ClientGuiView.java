package com.multiPlayer.client;

import javax.swing.*;
import java.awt.*;

public class ClientGuiView {

    private final ClientGuiController controller;

    private JFrame frame = new JFrame("Клиент");
    private JLabel playerLabel = new JLabel("",SwingConstants.CENTER);;
     JButton button = new JButton("go");


    public ClientGuiView(ClientGuiController controller) {
        this.controller = controller;
        initView();
    }

    public void updatePlayerLabel(String newLabel){
        playerLabel.setText(newLabel);
    }

    public void showInfo() {

        JOptionPane.showMessageDialog(
                frame,
                "Соединение с сервером установлено",
                "Title",
                JOptionPane.INFORMATION_MESSAGE);

    }

    private void initView() {

        frame.setSize(400,400);

        frame.add(playerLabel, BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);

        playerLabel.setFont(new Font(playerLabel.getName(), Font.PLAIN, 20));

        //frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
