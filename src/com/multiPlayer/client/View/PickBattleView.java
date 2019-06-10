package com.multiPlayer.client.View;

import com.multiPlayer.client.MainLayoutController;
import com.multiPlayer.connection.Message;
import com.multiPlayer.connection.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PickBattleView {
    private static final Logger LOGGER = LoggerFactory.getLogger(PickBattleView.class);
    private final MainLayoutController controller;

    //private JFrame frame = new JFrame("Клиент");

    private JPanel goBattlePanel;//= new JPanel(new BorderLayout());
    private JLabel playerLabel;//= new JLabel("", SwingConstants.CENTER);
    private JLabel serverLabel;//= new JLabel("", SwingConstants.CENTER);
    private JButton button;//= new JButton("check");
    private Message sendingMessage;


    public PickBattleView(MainLayoutController controller) {
        this.controller = controller;
        initView();

        setJoinButton();
    }

    public JPanel getGoBattlePanel() {
        return goBattlePanel;
    }

    public void updatePlayerLabel(String newLabel) {
        playerLabel.setText("Welcome, " + newLabel + " !");
    }

    public void updateServerLabel(String newLabel) {
        serverLabel.setText("Server: " + newLabel);
    }

    private void initView() {
        goBattlePanel = new JPanel(null);
        playerLabel = new JLabel();
        serverLabel = new JLabel();
        button = new JButton("Join to 1x1 battle");
        button.setFont(new Font("Arial", Font.PLAIN, 16));


        serverLabel.setBounds(270, 100, 400, 50);
        playerLabel.setBounds(300, 300, 300, 50);
        button.setBounds(300, 370, 200, 40);
        // frame.setSize(400,400);


        goBattlePanel.add(serverLabel/*, BorderLayout.NORTH*/);
        goBattlePanel.add(playerLabel/*, BorderLayout.CENTER*/);
        goBattlePanel.add(button/*, BorderLayout.SOUTH*/);

        playerLabel.setFont(new Font(playerLabel.getName(), Font.PLAIN, 20));
        serverLabel.setFont(new Font(serverLabel.getName(), Font.PLAIN, 20));

        button.addActionListener(actionEvent -> {
            try {
                controller.getConnection().send(sendingMessage);
                LOGGER.debug("send: {}", sendingMessage);

            } catch (IOException e) {
                LOGGER.error("cannot join/leave battle", e);
                e.printStackTrace();
            }
        });

    }

    public void setLeaveButton(){
        button.setText("Leave queue");
        sendingMessage = new Message(MessageType.LEAVE_BATTLE_REQUEST);
    }

    public void setJoinButton(){
        button.setText("Join to 1x1 battle");
        sendingMessage = new Message(MessageType.JOIN_TO_BATTLE_REQUEST);
    }
}
