package com.myDrafts.differentExamples.MyClientServerExample;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
            String string = "Player №" + ThreadLocalRandom.current().nextInt(1, 5000);
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

         /* byte[] out = string.getBytes(Charset.forName("UTF-8"));
            socket.getOutputStream().write(out);
            socket.getOutputStream().flush();*/

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(string);



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
