package com.multiPlayer.client;

import com.multiPlayer.client.p.Connection;
import com.multiPlayer.client.p.Message;
import com.multiPlayer.client.p.MessageType;
import com.singlePlayer.company.model.Hero.Hero;

import java.io.IOException;
import java.net.Socket;

import static com.multiPlayer.ServerConstants.SERVER_ADDRESS;
import static com.multiPlayer.ServerConstants.SERVER_PORT;
import static com.multiPlayer.client.p.MessageType.PLAYER_NAME;

class ClientListener extends Thread {

    private MainLayoutController controller;

    public ClientListener(MainLayoutController controller) {

        this.controller = controller;
    }

    @Override
    public void run() {

        Socket socket;

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            //Todo: придумать как работать с коннекшенем, мб синглетон
            controller.getMainLayoutModel().setConnection(new Connection(socket));
            String clientName = controller.getMainLayoutModel().getPlayer();


            Hero hero = new Hero(clientName);
            Message message = new Message(PLAYER_NAME, hero.toString());


            controller.getMainLayoutModel().getConnection().send(message);
            controller.updatePlayerLabels();
            controller.getMainLayoutView().switchToBattle();

            clientMainLoop();

        } catch (IOException e) {
            System.out.println("connect error");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void clientMainLoop() throws IOException, ClassNotFoundException {
        while (true) {
            Message message = controller.getMainLayoutModel().getConnection().receive();

            if (message != null && message.getType() == MessageType.PLAYER_NAME_ACCEPTED) {

                System.out.println("Соединение с сервером установлено");
                    /*
                    JOptionPane.showMessageDialog(
                            loginFrame,
                            "Соединение с сервером установлено",
                            "Title",
                            JOptionPane.INFORMATION_MESSAGE);*/
            }
        }
    }


}