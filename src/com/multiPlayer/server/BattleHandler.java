package com.multiPlayer.server;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.client.swing.model.HeroImages;
import com.multiPlayer.connection.Connection;
import com.multiPlayer.connection.Message;
import com.multiPlayer.connection.MessageType;
import com.multiPlayer.other.MessageObjects.BattleFieldInstance;
import com.multiPlayer.other.MessageObjects.HeroBattleAction;
import com.multiPlayer.other.MessageObjects.HeroMovementAction;
import com.multiPlayer.other.MessageObjects.UpdateBattleField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleHandler extends Thread {
    private Map<String, Connection> playerConnections;
    private String[] players;

    private Map<Connection, Hero> connectionHero = new HashMap<>();
    private List<Hero> heroes = new ArrayList<>();

    private Map<Hero, Integer> heroesForClient = new HashMap<>();

    private Map<Hero, HeroMovementAction> movementActions = new HashMap<>();
    private Map<Hero, HeroBattleAction> heroHeroBattleActions = new HashMap<>();


    public BattleHandler(Map<String, Connection> playerConnections) {
        super();//todo изучить
        this.playerConnections = playerConnections;

        players = playerConnections.keySet().toArray(new String[0]);


    }

    @Override
    public void run() {
        System.out.println("battle handler start");
        //инициализация connectionHero
        playerConnections.forEach((playerName, connection) -> {
            Hero hero = new Hero(playerName);
            connectionHero.put(connection, hero);
            heroes.add(hero);
        });


        //инициализация heroesForClient
        //todo только для 2-x
        heroesForClient.put(heroes.get(0), 18);
        heroesForClient.put(heroes.get(1), 29);
        heroes.get(0).setView(HeroImages.KNIGHT_PATH);
        heroes.get(1).setView(HeroImages.PIRATE_PATH);

        //старт игры
        playerConnections.forEach((k, v) -> {
            try {
                v.send(new Message(MessageType.BATTLE_FIELD_INSTANCE,
                                new BattleFieldInstance(
                                        new BattleField(9, 6), heroesForClient)
                        )
                );
            } catch (IOException error) {
                error.printStackTrace();
            }
        });

        System.out.println(" " + currentThread());

        try {
            //пока есть живые персы
            while (true) {

                System.out.println("Есть живые");

                long start = System.currentTimeMillis();
                long finish = start + 10_700L;
                long current = 1;

                //пока таймер не дошел до 0
                while (current > 0) {

                    // System.out.println("time: " + current);

                    current = finish - System.currentTimeMillis();
                    System.out.println(current);

                    for (Connection c : playerConnections.values()) {
                        c.send(new Message(MessageType.TIMER, /*new TimerMessage(current)*/current));
                    }
                    sleep(500);


                } // end timer

                System.out.println("Round over");


                //считаем дамаг
                performAllMovements();

                System.out.println("sending data: " + heroesForClient);

                //todo: без нью хеш мап - отправлялись одни данные, а получались другие.
                for (Connection c : playerConnections.values()) {
                    c.send(new Message(MessageType.UPDATE_BATTLEFIELD, new UpdateBattleField(new HashMap<>(heroesForClient))));
                }

            }


        } catch (/*ClassNotFoundException |*/ InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
            interrupt();
        }

    }

    public void moveHero(Connection connection, HeroMovementAction action) {


        movementActions.put(connectionHero.get(connection), action);
    }

    public void moveHero(Hero hero, int index) {
        //update hero index
        //heroesForClient.computeIfPresent(hero, (key, value) -> index);
        heroesForClient.put(hero, index);

    }

    public void performAllMovements() {
        movementActions.forEach((hero, value) -> moveHero(hero, value.getTileIndex()));

        movementActions.clear();
    }


}
