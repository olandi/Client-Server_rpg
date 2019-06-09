package com.multiPlayer.server;


import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.Hero.TurnState;
import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.connection.Connection;
import com.multiPlayer.connection.Message;
import com.multiPlayer.connection.MessageType;
import com.multiPlayer.connection.MessageObjects.BattleFieldInstance;
import com.multiPlayer.connection.MessageObjects.HeroBattleAction;
import com.multiPlayer.connection.MessageObjects.HeroMovementAction;
import com.multiPlayer.connection.MessageObjects.UpdateBattleField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class BattleHandler extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleHandler.class);

    private Map<String, Connection> playerConnections;

    private Map<String, Hero> heroesForClient = new ConcurrentHashMap<>();
    private Map<Hero, HeroMovementAction> movementActions = new ConcurrentHashMap<>();
    private Map<Hero, HeroBattleAction> heroHeroBattleActions = new ConcurrentHashMap<>();
    private StringBuffer battleLog = new StringBuffer();


    public BattleHandler(Map<String, Connection> playerConnections) {
        super("BattleHandler");
        this.playerConnections = playerConnections;
    }

    @Override
    public void run() {
        LOGGER.debug("battle handler start");

        //инициализация heroesForClient
        String[] playerNames = playerConnections.keySet().toArray(new String[0]);
        Hero knight = new Hero(playerNames[0]);
        knight.setPosition(18);
        knight.setViewId("KNIGHT");
        knight.setPortretId("KNIGHT_HEAD");

        Hero pirate = new Hero(playerNames[1]);
        pirate.setPosition(29);
        pirate.setViewId("PIRATE");
        pirate.setPortretId("PIRATE_HEAD");

        heroesForClient.put(playerNames[0], knight);
        heroesForClient.put(playerNames[1], pirate);
        ////////////////////////////////////////////////////////////////////////////


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


        playerConnections.keySet().forEach(i -> battleLog.append(i).append(" Вступает в бой!\n"));

        try {
            int round = 0;
            //пока не останется только один живой перс
            while (!isOneHeroRemain()) {

                long start = System.currentTimeMillis();
                long finish = start + 30_700L;
                long current = 1;

                //пока таймер не дошел до 0
                while (current > 0 && isReadyToMoveHeroExist()) {

                    current = finish - System.currentTimeMillis();

                    for (Connection c : playerConnections.values()) {
                        c.send(new Message(MessageType.TIMER, current));
                    }
                    sleep(500);


                } // end timer

                //костыль для отображения анимации у игроков
                animation();

                round++;
                battleLog.append("Round ").append(round).append("\n");

                //считаем результаты боя : дамаг и передвижение
                performAllMovements();
                computeDamage();

                restartPlayersTurn();
                sendBroadcastData();

                battleLog.setLength(0);
            }
            //окончание боя

            //оповещение всех об окончании боя
            sendMessageToAllPlayers(new Message(MessageType.FINISH_BATTLE));

            //удаление игроков из текущих игр баттле менеджера
            playerConnections.forEach((k, v) -> BattleManager.getConnectionBattleHandlerMap().remove(v));


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
            interrupt();
        }

        LOGGER.debug("battle handler finish");
    }


    public void moveHero(String userName, HeroMovementAction action) {
        movementActions.put(heroesForClient.get(userName), action);
        performPlayersTurn(userName);

    }


    public void performPlayersTurn(String playerName) {
        Hero h = heroesForClient.get(playerName);
        h.setTurnState(TurnState.TurnIsFinished);
        heroesForClient.put(playerName, h);

        // sendBroadcastData();
        sendMessageToAllPlayers(new Message(MessageType.UPDATE_BATTLEFIELD, new UpdateBattleField(heroesForClient)));

    }

    public void sendMessageToAllPlayers(Message message) {
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

        // без нью хеш мап - сервер отправляет одни данные, а клиент получает другие.
        for (Connection c : playerConnections.values()) {
            try {
                LOGGER.debug("sending data: {}", heroesForClient);
                c.resetObjectOutputStream1();
                c.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        //без нью хеш мап - сервер отправляет одни данные, а клиент получает другие.
        for (Connection c : playerConnections.values()) {
            try {
                LOGGER.debug("sending data: {}", heroesForClient);

                c.resetObjectOutputStream1();
                //   c.resetObjectInputStream();

                UpdateBattleField ubf = new UpdateBattleField(heroesForClient);
                ubf.setBattleLog(
                        battleLog.toString()
                );

                c.send(new Message(MessageType.UPDATE_BATTLEFIELD, ubf));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void performAllMovements() {


        movementActions.forEach((hero, value) -> {

            battleLog.append(hero.getName()).append(" переходит в ").append(value.getTileIndex()).append(" хексагон\n");

            moveHero(hero, value.getTileIndex());

        });
        movementActions.clear();
    }

    private void moveHero(Hero hero, int index) {
        hero.moveTo(index);
    }


    private void restartPlayersTurn() {
        heroesForClient.values().forEach(i -> i.setTurnState(TurnState.ReadyForTurn));
    }

    private boolean isReadyToMoveHeroExist() {
        boolean result = false;

        for (Hero hero : heroesForClient.values()) {
            // System.out.println("Hero: " + hero + " " + hero.getTurnState());
            result |= hero.getTurnState().equals(TurnState.ReadyForTurn);
        }
        return result;
    }


    public void hitHero(String userName, Connection connection, HeroBattleAction data) {
        heroHeroBattleActions.put(heroesForClient.get(userName), data);
        performPlayersTurn(userName);
    }


    public void computeDamage() {

        //для каждого героя
        heroesForClient.forEach((playerName, hero) -> {

            //для каждой атаки героя
            if (heroHeroBattleActions.get(hero) != null)
                heroHeroBattleActions.get(hero).getAttack().forEach(attack -> {


                            //исли цель не сделала ход, не поставила блоки
                            if (heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()) == null) {


                                heroesForClient.values().stream().filter(hero1 -> hero1.equals(heroHeroBattleActions.get(hero).getTarget()))
                                        .findFirst().get().takeDamage(hero.getDamage());

                                //System.out.println("damage received_1");

                            }
                            //если в списке защиты врага нет этой атаки
                            if (
                                    heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()) != null
                                            && !heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()).getDefense()
                                            .contains(attack)) {


                                heroesForClient.values().stream().filter(hero1 -> hero1.equals(heroHeroBattleActions.get(hero).getTarget()))
                                        .findFirst().get().takeDamage(hero.getDamage());


                                battleLog.append(hero.getName()).append(" нанес 20 дамага по ")
                                        .append(heroHeroBattleActions.get(hero).getTarget().getName()).append("\n");


                            }
                        }
                );
        });

        heroHeroBattleActions.clear();
    }

    public boolean isOneHeroRemain() {
        //todo: в идеале все игроки делятся на команды. проверять надо по командам
        for (Hero hero : heroesForClient.values()) {
            if (hero.getHealth() < 0) return true;
        }
        return false;
    }

    private void animation() {
        long start = System.currentTimeMillis();
        long finish = start + 2_000L;
        long current = 1;
        try {
            while (current > 0) {
                current = finish - System.currentTimeMillis();

                Message message = new Message(MessageType.ANIMATION);
                LOGGER.debug(MessageType.ANIMATION.toString());
                for (Connection c : playerConnections.values()) {
                    c.send(message);
                }
                sleep(700);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
