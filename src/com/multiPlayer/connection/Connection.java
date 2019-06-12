package com.multiPlayer.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

//класс соединения между клиентом и сервером
public class Connection implements Closeable {
    private static Logger LOGGER = LoggerFactory.getLogger(Connection.class);
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;


    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());

    }


    public void send(Message message) throws IOException {
        synchronized (out) {
            this.out.writeObject(message);
        }
    }


    /*
Lets come to the problem. for the first time when a client sends String to server, server prints it well,
add to it's arraylist, then broadcasts it to all clients and all clients can see that too. But next time
when client sends String message, server accepts it, adds to arraylist and broadcasts it, but this time all
clients gets old arraylist ( list with only one String which was added first ). I have printed arraylist
before broadcasting and it shows modified values, but at client side it shows list with one entry only.

----

This is normal behavior. If you send the same object (your ArrayList) several times to a given ObjectOutputStream,
the stream will send the full object the first time, and will only send a reference to this object the next times.
This is what allows sending a graph of objects without consuming too much bandwidth, and without going into infinite
loops because a references b which also references a.

To make sure the ArrayList is sent a second time, you need to call reset() on the ObjectOutputStream.
https://stackoverflow.com/questions/20543403/in-simple-chat-program-server-sending-arraylist-of-string-but-clients-receiving
 */
    public void sendWithResetOut(Message message) throws IOException {
        synchronized (out) {
            out.reset();
            this.out.writeObject(message);
        }
    }

    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (in) {
            return (Message) this.in.readObject();
        }
    }

    public SocketAddress getRemoteSocketAddress() {
        return this.socket.getRemoteSocketAddress();
    }

    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();
    }

    public Socket getSocket() {
        return socket;
    }
}
