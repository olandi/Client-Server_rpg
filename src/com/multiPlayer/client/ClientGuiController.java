package com.multiPlayer.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGuiController {

    private ClientGuiView view = new ClientGuiView(this);
    private ClientGuiModel model = new ClientGuiModel();

    public static void main(String[] args) {
        ClientGuiController controller = new ClientGuiController();

        controller.view.updatePlayerLabel(controller.model.getClientName());


        controller.view.button.addActionListener(e -> controller.model.method());

    }

}
