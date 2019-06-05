package com.multiPlayer.server;

import com.multiPlayer.connection.Connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BattleManager extends Thread {
    private static Map<String, Connection> waitingForBattle = new ConcurrentHashMap<>();


    public void addToBattleQueue(String userName,Connection connection){
        waitingForBattle.put(userName, connection);
    }

    @Override
    public void run() {
        try {
            while (true){
                if (waitingForBattle.size()==2) {new BattleHandler(waitingForBattle);}
                sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        //super.run();

    }
}
