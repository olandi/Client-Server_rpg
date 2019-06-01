package com.multiPlayer.client.View;

import com.multiPlayer.client.MainLayoutController;
import com.multiPlayer.connection.Message;
import com.multiPlayer.connection.MessageType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PickBattleView {

    private final MainLayoutController controller;

    //private JFrame frame = new JFrame("Клиент");

    private JPanel goBattlePanel = new JPanel(new BorderLayout());
    private JLabel playerLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel serverLabel = new JLabel("", SwingConstants.CENTER);
    private JButton button = new JButton("check");


    public PickBattleView(MainLayoutController controller) {
        this.controller = controller;
        initView();
    }

    public JPanel getGoBattlePanel() {
        return goBattlePanel;
    }

    public void updatePlayerLabel(String newLabel) {
        playerLabel.setText(newLabel);
    }

    public void updateServerLabel(String newLabel) {
        serverLabel.setText(newLabel);
    }

    public void showInfo() {

        JOptionPane.showMessageDialog(
                goBattlePanel,
                "Соединение с сервером установлено",
                "Title",
                JOptionPane.INFORMATION_MESSAGE);

    }

    private void initView() {

        // frame.setSize(400,400);

        goBattlePanel.add(serverLabel, BorderLayout.NORTH);
        goBattlePanel.add(playerLabel, BorderLayout.CENTER);
        goBattlePanel.add(button, BorderLayout.SOUTH);

        playerLabel.setFont(new Font(playerLabel.getName(), Font.PLAIN, 20));
        serverLabel.setFont(new Font(serverLabel.getName(), Font.PLAIN, 20));

        button.addActionListener(actionEvent ->{
            try {
                controller.getConnection().send(new Message(MessageType.JOIN_TO_BATTLE_REQUEST));
                System.out.println("send: JOIN_TO_BATTLE_REQUEST");
            }
            catch (IOException e){
                System.out.println("косяк при попытке отправить запрос на создание боя");
                e.printStackTrace();}
        });

    }

}
