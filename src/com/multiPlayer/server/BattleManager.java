package com.multiPlayer.server;

import com.multiPlayer.connection.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BattleManager extends Thread {
    private static final Logger  LOGGER = LoggerFactory.getLogger(BattleManager.class);

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
        LOGGER.debug("User {} wants to join to the battle. Current queue value is {}.", userName, waitingForBattle.size());
    }

    public void removeFromBattleQueue(String userName, Connection connection) {
        waitingForBattle.remove(userName);
        LOGGER.debug("User {} wants to leave queue. Current queue value is {}.", userName, waitingForBattle.size());
    }


    public BattleHandler getBattleByConnection(Connection connection) {
        return connectionBattleHandlerMap.get(connection);
    }


    @Override
    public void run() {
        LOGGER.debug("battle manager start");
        try {
            while (true) {

                if (waitingForBattle.size() == 2) {

                    BattleHandler battleHandler = new BattleHandler(new HashMap<>(waitingForBattle));

                    waitingForBattle.forEach((name, conn) ->
                            connectionBattleHandlerMap.put(conn, battleHandler));

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
