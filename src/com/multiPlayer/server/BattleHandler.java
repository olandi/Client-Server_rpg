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
import java.util.stream.Stream;


public class BattleHandler extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleHandler.class);

    private Map<String, Connection> playerConnections;

    private Map<String, Hero> heroesForClient = new ConcurrentHashMap<>();
    private Map<Hero, HeroMovementAction> movementActions = new ConcurrentHashMap<>();
    private Map<Hero, HeroBattleAction> heroHeroBattleActions = new ConcurrentHashMap<>();
    private StringBuffer battleLog = new StringBuffer();
    private BattleField battleField = new BattleField(9, 6);


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
        playerConnections.keySet().forEach(i -> battleLog.append(i).append(" Вступает в бой!\n"));
        sendMessageToAllPlayers(
                new Message(MessageType.BATTLE_FIELD_INSTANCE,
                        new BattleFieldInstance(
                                battleField,
                                heroesForClient,
                                battleLog.toString())));
        battleLog.setLength(0);

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
                computeAllMovements();
                computeAllDamage();

                restartPlayersTurn();
                sendBroadcastData();

                battleLog.setLength(0);
            }
            //окончание боя

            //оповещение всех об окончании боя
            sendMessageToAllPlayers(new Message(MessageType.FINISH_BATTLE));

            //удаление игроков из текущих игр баттл менеджера
            playerConnections.forEach((k, v) -> BattleManager.getConnectionBattleHandlerMap().remove(v));


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
            interrupt();
        }

        LOGGER.debug("battle handler finish");
    }


    private void performPlayersTurn(String playerName) {
        Hero h = heroesForClient.get(playerName);
        h.setTurnState(TurnState.TurnIsFinished);
        heroesForClient.put(playerName, h);
        sendMessageToAllPlayers(new Message(MessageType.UPDATE_BATTLEFIELD, new UpdateBattleField(heroesForClient)));
    }

    private void sendMessageToAllPlayers(Message message) {
        playerConnections.forEach((name, connect) -> {
            try {
                LOGGER.debug("sending data to {} : {}", name, heroesForClient);
                connect.sendWithResetOut(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void sendBroadcastData() {
        playerConnections.forEach((name, connect) -> {
            try {
                LOGGER.debug("sending data to {} : {}", name, heroesForClient);

                connect.sendWithResetOut(
                        new Message(MessageType.UPDATE_BATTLEFIELD,
                                new UpdateBattleField(heroesForClient, battleLog.toString())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void computeAllMovements() {
        movementActions.forEach((hero, value) -> {
            battleLog.append(hero.getName()).append(" переходит в ").append(value.getTileIndex()).append(" хексагон\n");
            hero.moveTo(value.getTileIndex());
        });
        movementActions.clear();
    }


    private void restartPlayersTurn() {
        heroesForClient.values().forEach(i -> i.setTurnState(TurnState.ReadyForTurn));
    }

    private boolean isReadyToMoveHeroExist() {
        boolean result = false;
        for (Hero hero : heroesForClient.values()) {
            result |= hero.getTurnState().equals(TurnState.ReadyForTurn);
        }
        return result;
    }

    public void moveHero(String userName, HeroMovementAction action) {
        movementActions.put(heroesForClient.get(userName), action);
        performPlayersTurn(userName);

    }

    public void hitHero(String userName, Connection connection, HeroBattleAction data) {
        heroHeroBattleActions.put(heroesForClient.get(userName), data);
        performPlayersTurn(userName);
    }


    private void computeAllDamage() {

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

    private boolean isOneHeroRemain() {
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




    private Hero getHeroByPosition(int position) {
        for (Hero h : heroesForClient.values()) {
            if (h.getPosition() == position) return h;
        }
        return null;
    }

int i = 0;
    private void prepareMovement(){

        while (isTheSameIndexes()){
            System.out.println(++i);
            findmov();
        }
    }

    private boolean isTheSameIndexes(){
        HashSet<Integer> integers = new HashSet<>();
        movementActions.values().forEach(i->integers.add(i.getTileIndex()));
        return integers.size()
                != movementActions.values().size();
    }

    private void findmov() {
        Set<HeroMovementAction> lala = new HashSet<>(movementActions.values());
        lala.forEach(this::findCommonMovements);
        findCommonMovements2();
    }

    private void findCommonMovements2() {

        HashMap<Hero, HeroMovementAction> map = new HashMap<>(movementActions);

        map.forEach((hero1, action1) ->
        {
            Hero targetHero = getHeroByPosition(action1.getTileIndex());
            if (targetHero != null) {

                //определить вектор перемещения
                //может ли таргет двинуться по вектору перемещения, свободное ли место.
                //есть ли более сильный герой, который ходит в эту клетку

                //если может двинуться - двинуть

                int targetHeroNewPosition = battleField.mirrorDirection(hero1.getPosition(), action1.getTileIndex());

                    //если можно перем в новую ячейку, нет героя в новой ячейки
                if (targetHeroNewPosition != -1 && getHeroByPosition(targetHeroNewPosition) == null) {

                    HeroMovementAction action2 = new HeroMovementAction(targetHeroNewPosition);
                    movementActions.put(targetHero, action2);
                    findCommonMovements(action2);

                    //если action2 не остался, то удалить и action1
                    if (movementActions.get(targetHero)==null) movementActions.remove(hero1);


                }
            }
        });
    }


    private Map<Hero, HeroMovementAction> findCommonMovements(HeroMovementAction action) {

        Map<Hero, HeroMovementAction> map = new HashMap<>();
        //получить список героев, которые хотят переместиться в ячейку. Записать в map
        movementActions.forEach((h, a) -> {
            if (action.getTileIndex() == a.getTileIndex()) map.put(h, a);
        });

        //выбрать 1 самого сильного героя, либо не выбрать никого = null
        Hero theStrongestHero = findTheStrongestHero(map.keySet());

        if (theStrongestHero != null) {
            map.remove(theStrongestHero);
        }

        map.forEach((k, v) -> movementActions.remove(k));
        return map;
    }

    private Hero findTheStrongestHero(Set<Hero> heroes) {
        Set<Hero> heroes1 = new HashSet<>(heroes);
        Hero firstHero;
        Optional<Hero> hh = heroes.stream().max(Comparator.comparingInt(Hero::getDamage));
        if (hh.isPresent()) firstHero = hh.get(); else return null;
      //  Hero firstHero = heroes.stream().max(Comparator.comparingInt(Hero::getDamage)).get();
        //Optional<Hero> o = heroes.stream().max(Comparator.comparingInt(Hero::getDamage));
        heroes1.remove(firstHero);
        Optional<Hero> h = heroes1.stream().max(Comparator.comparingInt(Hero::getDamage));
        Hero secondHero;
        if (h.isPresent()) secondHero = h.get();
        else return firstHero;
        //Hero secondHero = heroes1.stream().max(Comparator.comparingInt(Hero::getDamage)).get();

        return firstHero.getDamage() == secondHero.getDamage() ? null : firstHero;
    }


    public static void main(String[] args) {


        HashMap<Hero, HeroMovementAction> map = new HashMap<>();

        Hero h1 = new Hero("1");
        h1.setDamage(21);
        h1.setPosition(2);

        Hero h2 = new Hero("2");
        h2.setDamage(20);
        h2.setPosition(11);

        Hero h3 = new Hero("3");
        h3.setDamage(21);
        h3.setPosition(13);

        Hero h4 = new Hero("4");
        h4.setDamage(20);
        h4.setPosition(18);

        Hero h5 = new Hero("5");
        h5.setDamage(21);
        h5.setPosition(19);


        map.put(h1, new HeroMovementAction(3));
        map.put(h2, new HeroMovementAction(12));
        map.put(h3, new HeroMovementAction(12));
        map.put(h4, new HeroMovementAction(11));
        map.put(h5, new HeroMovementAction(11));

        BattleHandler battleHandler = new BattleHandler(null);
        battleHandler.movementActions = map;
        battleHandler.heroesForClient = new HashMap<String, Hero>(){{put("1",h1);put("2",h2);put("3",h3);put("4",h4);put("5",h5);}};


        System.out.println(battleHandler.movementActions);
        battleHandler.prepareMovement();
        System.out.println(battleHandler.movementActions);



/*        System.out.println(findTheStrongestHero(heroes1)

        );*/
    }

}
