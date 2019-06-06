package com.multiPlayer.client;

import com.multiPlayer.connection.Connection;

public class MainLayoutModel {
    private Connection connection;
    private String player;

    private boolean isInQueueToArena = false;

    public boolean isInQueueToArena() {
        return isInQueueToArena;
    }

    public void setInQueueToArena(boolean inQueueToArena) {
        isInQueueToArena = inQueueToArena;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
