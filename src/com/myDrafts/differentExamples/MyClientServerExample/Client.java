package com.myDrafts.differentExamples.MyClientServerExample;

import com.company.model.Hero.Hero;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.concurrent.ThreadLocalRandom;

public class Client {
    private final static int SERVER_PORT = 5555;
    private final static String SERVER_ADDRESS = "localhost";

    private Socket socket;


    private void runClient() {

        try {
            String string = "Player" + ThreadLocalRandom.current().nextInt(1, 5000);
            Hero hero = new Hero(string);

            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
            o.writeObject(hero);

        } catch (IOException e) {
            System.out.println("connect error");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.runClient();

    }
}
