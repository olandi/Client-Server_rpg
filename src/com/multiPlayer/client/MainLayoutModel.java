package com.multiPlayer.client;

import com.multiPlayer.client.p.Connection;

public class MainLayoutModel {
    private Connection connection;
    private String player;

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
