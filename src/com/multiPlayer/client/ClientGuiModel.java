package com.multiPlayer.client;

import com.singlePlayer.company.model.Hero.Hero;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class ClientGuiModel {

    private String clientName;

    public ClientGuiModel() {
        initGuiModel();
    }

    private void initGuiModel() {
        clientName = "Player" + ThreadLocalRandom.current().nextInt(1, 5000);
    }

    public String getClientName() {
        return clientName;
    }

    public void method() {
        int SERVER_PORT = 5555;
        String SERVER_ADDRESS = "localhost";
        Socket socket;

        try {

            Hero hero = new Hero(clientName);
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
            o.writeObject(hero);

        } catch (IOException e) {
            System.out.println("connect error");
            e.printStackTrace();
        }
    }
}


