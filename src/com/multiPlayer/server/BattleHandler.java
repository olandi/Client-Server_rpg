package com.multiPlayer.server;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.Hero.TurnState;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BattleHandler extends Thread {
    private Map<String, Connection> playerConnections;
    private String[] players;

    private Map<Connection, Hero> connectionHero = new ConcurrentHashMap<>();

    private List<Hero> heroes = new ArrayList<>();

    private static volatile Map<Hero, Integer> heroesForClient = new ConcurrentHashMap<>();

    private Map<Hero, HeroMovementAction> movementActions = new ConcurrentHashMap<>();
    private Map<Hero, HeroBattleAction> heroHeroBattleActions = new ConcurrentHashMap<>();


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
            while (!isOneHeroRemain()) {

                System.out.println("Есть живые");

                long start = System.currentTimeMillis();
                long finish = start + 30_700L;
                long current = 1;

                //пока таймер не дошел до 0
                while (current > 0 && isReadyToMoveHeroExist()) {

                    // System.out.println("time: " + current);

                    current = finish - System.currentTimeMillis();
                    System.out.println(current);

                    for (Connection c : playerConnections.values()) {
                        c.send(new Message(MessageType.TIMER, /*new TimerMessage(current)*/current));
                    }
                    sleep(500);


                } // end timer

                System.out.println("Round over");


                //считаем результаты боя : дамаг и передвижение
                performAllMovements();
                computeDamage();

                checkAliveHero();

                restartPlayersTurn();
                sendBroadcastData();
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

        performPlayersTurn(connection);
    }


    public Hero getHeroByPlayerName(String playerName) {
        for (Hero h : connectionHero.values()) {
            if (h.getName().equals(playerName)) return h;
        }
        return null;
    }

    public void performPlayersTurn(Connection connection) {

        Hero h = connectionHero.get(connection);
        h.setTurnState(TurnState.TurnIsFinished);
        heroesForClient.put(h, heroesForClient.get(connectionHero.get(connection)));

        sendBroadcastData();


    }


    public void sendBroadcastData() {
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

        //todo: без нью хеш мап - сервер отправляет одни данные, а клиент получает другие.
        for (Connection c : playerConnections.values()) {
            try {
                System.out.println("sending data: " + heroesForClient);

                c.resetObjectOutputStream1();
                //   c.resetObjectInputStream();

                c.send(new Message(MessageType.UPDATE_BATTLEFIELD, new UpdateBattleField(heroesForClient)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void performAllMovements() {
        movementActions.forEach((hero, value) -> moveHero(hero, value.getTileIndex()));
        movementActions.clear();
    }

    private void moveHero(Hero hero, int index) {
        //update hero index
        //heroesForClient.computeIfPresent(hero, (key, value) -> index);
        heroesForClient.put(hero, index);
    }


    private void restartPlayersTurn() {
        heroesForClient.keySet().forEach(i -> i.setTurnState(TurnState.ReadyForTurn));
    }

    private boolean isReadyToMoveHeroExist() {
        boolean result = false;

        for (Hero hero : heroesForClient.keySet()) {
            // System.out.println("Hero: " + hero + " " + hero.getTurnState());
            result |= hero.getTurnState().equals(TurnState.ReadyForTurn);
        }
        return result;
    }



    public void hitHero(Connection connection, HeroBattleAction data) {
        heroHeroBattleActions.put(connectionHero.get(connection), data);
        performPlayersTurn(connection);
    }


    public void computeDamage() {

        //для каждого героя
        heroesForClient.forEach((hero, index) -> {

            //для каждой атаки героя
            if (heroHeroBattleActions.get(hero) != null)
                heroHeroBattleActions.get(hero).getAttack().forEach(attack -> {

                            //если в списке защиты врага нет этой атаки

                            //исли цель не сделала ход, не поставила блоки
                            if (heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()) == null) {


                                heroesForClient.keySet().stream().filter(hero1 -> hero1.equals(heroHeroBattleActions.get(hero).getTarget()))
                                        .findFirst().get().takeDamage(hero.getDamage());
                                  /*
                                heroHeroBattleActions.get(hero).getTarget().setHealth(
                                        heroHeroBattleActions.get(hero).getTarget().getHealth() -
                                                hero.getDamage());
                                */

                                System.out.println("damage received_1");
                                //todo Controller.getCombatLogPanel().appendText("Hero: " + heroHeroBattleActions.get(hero).getToHero().getName() + " received 20 damage\n");


                            }

                            if (
                                    heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()) != null
                                            && !heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()).getDefense()
                                            .contains(attack)) {

                                //System.out.println("Hero " + hero + " hits");

                                heroesForClient.keySet().stream().filter(hero1 -> hero1.equals(heroHeroBattleActions.get(hero).getTarget()))
                                        .findFirst().get().takeDamage(hero.getDamage());
                                /*
                                heroHeroBattleActions.get(hero).getTarget().setHealth(
                                        heroHeroBattleActions.get(hero).getTarget().getHealth() -
                                                hero.getDamage());*/



                                System.out.println("damage received_2");
                                //todo   Controller.getCombatLogPanel().appendText("Hero: " + heroHeroBattleActions.get(hero).getToHero().getName() + " received 20 damage\n");

                            }
                        }
                );
        });

        heroHeroBattleActions.clear();
    }
    public boolean isOneHeroRemain() {
        return heroes.size() <= 1;
    }

    public void checkAliveHero() {
        Set<Hero> h = new HashSet<>(heroesForClient.keySet());

        for (Hero hero : h) {
            if (hero.getHealth() < 0) removeHero(hero);
        }
    }


    public void removeHero(Hero hero) {
        heroesForClient.remove(hero);
    }

    /*public Hero getHeroByIndex(int index) {
        for (Map.Entry<Hero, Integer> entry : heroesForClient.entrySet()) {
            if (entry.getValue() == index) return entry.getKey();
        }
        return null;
    }*/

}
