package com.multiPlayer.server;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.battleField.BattleField;
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

            System.out.println(" "+currentThread());

            try {
                //пока есть живые персы
                while (true) {
                    long start = System.currentTimeMillis();
                    long finish = start + 20_700L;
                    long current = 1;

                    //пока таймер не дошел до 0 и есть персы, готовые ходить
                    while (current > 0) {


                        //слушаем порты
                        Message message;
                        //todo тонкое место
                        if ((message = playerConnections.get(players[0]).receive()) != null) {

                            if (message.getType() == MessageType.PLAYER_MOVEMENT_MESSAGE) {
                                movementActions.put(
                                        heroes.stream().filter(i -> i.getName().equals(players[0])).findFirst().get()
                                        , (HeroMovementAction) message.getData()
                                );}
                        }


                        if ((message = playerConnections.get(players[1]).receive()) != null) {

                            if (message.getType() == MessageType.PLAYER_MOVEMENT_MESSAGE) {
                                movementActions.put(
                                        heroes.stream().filter(i -> i.getName().equals(players[1])).findFirst().get()
                                        , (HeroMovementAction) message.getData()
                                );}
                        }


                        current = finish - System.currentTimeMillis();
                        System.out.println(current);

                        for (Connection c : playerConnections.values()) {
                            c.send(new Message(MessageType.TIMER, /*new TimerMessage(current)*/current));
                        }
                        sleep(500);
                    }
                    System.out.println("Round over");


                    //считаем дамаг
                    performAllMovements();

                    for (Connection c : playerConnections.values()) {
                        c.send(new Message(MessageType.UPDATE_BATTLEFIELD, new UpdateBattleField(heroesForClient)));
                    }

                }


            } catch (ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
                interrupt();
            }








    }

    public void moveHero(Hero hero, int index) {
        //update hero index
        heroesForClient.computeIfPresent(hero, (key, value) -> index);
    }
    public void performAllMovements() {
        movementActions.forEach((hero, value) -> moveHero(hero, value.getTileIndex()));

        movementActions.clear();
    }



}
