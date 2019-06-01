package com.multiPlayer.client.logic;

import com.singlePlayer.company.model.Hero.Hero;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class ClientUtils {
    private final static int SERVER_PORT = 5555;
    private final static String SERVER_ADDRESS = "localhost";

    private Socket socket;


    private void runClient() {

        try {


            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
         //   o.writeObject(hero);

        } catch (IOException e) {
            System.out.println("connect error");
            e.printStackTrace();
        }
    }
}
