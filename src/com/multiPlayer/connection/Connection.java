package com.multiPlayer.connection;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

//класс соединения между клиентом и сервером
public class Connection implements Closeable {
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

/*
    public void resetObjectOutputStream1() throws IOException {
        out.reset();
    }
    public void resetObjectInputStream1() throws IOException {
        in.reset();
    }
*/
    public Socket getSocket() {
        return socket;
    }
}
