package com.multiPlayer.server;

import com.multiPlayer.connection.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BattleManager extends Thread {
    private static Map<String, Connection> waitingForBattle = new ConcurrentHashMap<>();

    private static Map<Connection, BattleHandler> connectionBattleHandlerMap = new ConcurrentHashMap<>();

    public static Map<String, Connection> getWaitingForBattle() {
        return waitingForBattle;
    }

    public static Map<Connection, BattleHandler> getConnectionBattleHandlerMap() {
        return connectionBattleHandlerMap;
    }

    public void addToBattleQueue(String userName, Connection connection) {
        waitingForBattle.put(userName, connection);
    }

    public void removeFromBattleQueue(String userName, Connection connection) {
        waitingForBattle.remove(userName);
    }


    public BattleHandler getBattleByConnection(Connection connection){
        return connectionBattleHandlerMap.get(connection);
    }


    @Override
    public void run() {
        System.out.println("battle manager start");
        try {
            while (true) {

                System.out.println("queue: " + waitingForBattle.size());


                if (waitingForBattle.size() == 2) {


                    BattleHandler battleHandler = new BattleHandler(new HashMap<>(waitingForBattle));

                    waitingForBattle.forEach((name,conn)->
                            connectionBattleHandlerMap.put(conn,battleHandler));

                    battleHandler.start();

                    waitingForBattle.clear();

                }
                sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
