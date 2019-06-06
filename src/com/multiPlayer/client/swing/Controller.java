package com.multiPlayer.client.swing;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.Hero.TurnState;
import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.other.MessageObjects.HeroBattleAction;
import com.multiPlayer.other.MessageObjects.HeroMovementAction;
import com.multiPlayer.client.MainLayoutController;
import com.multiPlayer.client.swing.View.*;
import com.multiPlayer.client.swing.model.HexagonItem;
import com.multiPlayer.connection.Message;


import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.multiPlayer.connection.MessageType.PLAYER_ACTION_EVENT;
import static com.multiPlayer.connection.MessageType.PLAYER_BATTLE_MESSAGE;
import static com.multiPlayer.connection.MessageType.PLAYER_MOVEMENT_MESSAGE;

public class Controller {
    private MainLayoutController controller;

    private MainModel model = new MainModel();

    private CombatLogPanel combatLogPanel = new CombatLogPanel(this);
    private BattleFieldPanel battleFieldPanel = new BattleFieldPanel(this);
    private HittingPanel hittingPanel = new HittingPanel(this);
    private TimerPanel timerPanel = new TimerPanel(this);

    private HeroInfoPanel heroInfoPanel = new HeroInfoPanel(this);

    private MainPanel mainGamePanel = new MainPanel(this);

    public Controller(MainLayoutController controller) {
        this.controller = controller;
    }

    public MainLayoutController getController() {
        return controller;
    }





    public MainModel getModel() {
        return model;
    }

    public void setController(MainLayoutController controller) {
        this.controller = controller;
    }

    public List<HexagonItem> getBattleField() {
        return model.getBattleField();
    }

    public Map<Hero, Integer> getHeroes() {
        return model.getHeroes();
    }

    public JPanel getMainGamePanel() {
        return mainGamePanel;
    }

    public CombatLogPanel getCombatLogPanel() {
        return combatLogPanel;
    }

    public BattleFieldPanel getBattleFieldPanel() {
        return battleFieldPanel;
    }

    public HittingPanel getHittingPanel() {
        return hittingPanel;
    }

    public void resetBattleMenu() {
        hittingPanel.resetBattleMenu();
    }

    public TimerPanel getTimerPanel() {
        return timerPanel;
    }


    public Hero getPlayerHero() {
        return model.getPlayersHero();
    }

    public Hero getEnemy() {
        return model.getEnemy();
    }

    public void setCurrentHero(Hero currentHero) {
        model.setCurrentHero(currentHero);
    }

    public void setEnemy(Hero enemy) {
        model.setEnemy(enemy);
    }

    public void sendMovementActionToServer(HeroMovementAction heroMovementAction) throws IOException {

        Message message = new Message(PLAYER_MOVEMENT_MESSAGE, heroMovementAction);
        System.out.println("send message to server: " + message);
        controller.getConnection().send(message);
        performHeroTurn(getPlayerHero());
        model.refresh();
    }


    public void sendBattleActionToServer(HeroBattleAction heroBattleAction) throws IOException {
        Message message = new Message(PLAYER_BATTLE_MESSAGE, heroBattleAction);
        System.out.println("send message to server: " + message);
        controller.getConnection().send(message);
        performHeroTurn(getPlayerHero());
        model.refresh();
    }

    private void performHeroTurn(Hero hero) {
        // hero.setTurnState(TurnState.TurnIsFinished);
        setCurrentHero(null);
    }

    public void repaintAllView() {
        getMainGamePanel().repaint();
    }


    public void setHittingPanelMouseListener() {
        removeAllMouseListeners();
        getMainGamePanel().addMouseListener(hittingPanel.getBattleMouseListener());
    }

    public void setBattleFieldPanelMouseListener() {
        removeAllMouseListeners();
        getMainGamePanel().addMouseListener(battleFieldPanel.getMouseListener());
    }


    private void removeAllMouseListeners() {
        Arrays.stream(getMainGamePanel().getMouseListeners())
                .forEach(mouseListener -> {
                    getMainGamePanel().removeMouseListener(mouseListener);
                });
    }


    public void closeHittingPanel() {
        model.setHittingPanelVisible(false);
    }

    public void openHittingPanel() {
        model.setHittingPanelVisible(true);
    }

    public boolean isVisibleBattleFrame() {
        return model.isHittingPanelVisible();
    }

    private void specifyTimerValueOnGui(String string) {
        timerPanel.getjLabel().setText(string);
    }

    public void initBattle(BattleField battleFieldArr, Map<Hero, Integer> heroes){
        model.initBattle(battleFieldArr,heroes);
        initPlayerHero();
        heroInfoPanel.initPlayerInfo();

        //heroInfoPanel = new HeroInfoPanel(this);
    }

    public void initPlayerHero() {
        model.initPlayerHero(
                model.getHeroByName(controller.getPlayer()));


        //model.getHeroByName(controller.getPlayer())

    }


    public void uptateBattleField(Map<Hero, Integer> data) {
        model.updateData(data);


        //update hero HP info
        heroInfoPanel.setHeroHP(model.getPlayersHero().getHealth());

        mainGamePanel.repaint();
    }

    public HeroInfoPanel getHeroInfoPanel() {
        return heroInfoPanel;
    }

/*
    private int round = 0;
    private Timer timer;
    //todo
    private int roundDuration = 41;


    public void runServerMainLoop() {

        class Tim {
            private int count = roundDuration;

            private void dec() {
                count--;
            }

            private int getCount() {
                return count;
            }

            private void reset() {
                count = roundDuration;
            }
        }

        Tim tim = new Tim();

        timer = new Timer(1000, i -> {
            tim.dec();
            specifyTimerValueOnGui(Integer.toString(tim.getCount()));

           // System.out.println(tim.count);

            if (!getServerUtils().isReadyToMoveHeroExist() || tim.count < 0) {
                timer.stop();
                specifyTimerValueOnGui("Идет анимация боя...");

                tim.reset();
                resetServerTurn();

                timer.start();
            }

            if (getServerUtils().isOneHeroRemain()) {
                timer.stop();
                specifyTimerValueOnGui("Game over");
            }

        });
        timer.start();
    }




    private void resetServerTurn() {
        round++;

        getServerUtils().performAllMovements();

        getServerUtils().computeDamage();

        getCombatLogPanel().appendText("Round " + round + "\n");

        for (Hero hero : getServerUtils().getHeroes().keySet()) {
            getCombatLogPanel().appendText("Hero: " + hero.getName() + " (" + hero.getHealth() + ") HP" + "\n");
        }

        getServerUtils().checkAliveHero();
        getServerUtils().setAllSelectedFalse();
        getServerUtils().setAllHeroMovable();
        setCurrentHero(null);
        //В этом методе идет перерисовка фреймов в том числе
        resetBattleMenu();
    }*/


}
