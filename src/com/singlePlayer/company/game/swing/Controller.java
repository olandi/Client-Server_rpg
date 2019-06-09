package com.singlePlayer.company.game.swing;

import com.singlePlayer.company.game.swing.model.HexagonItem;
import com.singlePlayer.company.game.swing.View.*;
import com.singlePlayer.company.game.Hero.Hero;
import com.singlePlayer.company.game.Hero.TurnState;
import com.singlePlayer.company.game.Hero.heroActions.HeroBattleAction;
import com.singlePlayer.company.game.Hero.heroActions.HeroMovementAction;
import com.singlePlayer.company.game.ServerUtils;


import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Controller {

    private MainModel model = new MainModel();

    private CombatLogPanel combatLogPanel = new CombatLogPanel(this);
    private BattleFieldPanel battleFieldPanel = new BattleFieldPanel(this);
    private HittingPanel hittingPanel = new HittingPanel(this);
    private TimerPanel timerPanel = new TimerPanel(this);
    private MainPanel mainGamePanel = new MainPanel(this);


    public ServerUtils getServerUtils(){
        return model.getServerUtils();
    }

    public List<HexagonItem> getBattleField(){
        return model.getServerUtils().getBattleField();
    }

    public Map<Hero,Integer> getHeroes(){
        return model.getServerUtils().getHeroes();
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


    public Hero getCurrentHero() {
        return model.getCurrentHero();
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

    public void sendMovementActionToServer(HeroMovementAction heroMovementAction) {

        model.getServerUtils().getMovementActions().put(getCurrentHero(), heroMovementAction);
        performHeroTurn(getCurrentHero());
        model.refresh();
    }


    public void sendBattleActionToServer(HeroBattleAction heroBattleAction) {

        model.getServerUtils().getHeroHeroBattleActions().put(getCurrentHero(), heroBattleAction);
        performHeroTurn(getCurrentHero());
        model.refresh();
    }

    private void performHeroTurn(Hero hero) {
        hero.setTurnState(TurnState.TurnIsFinished);
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

    private void specifyTimerValueOnGui(String string){
        timerPanel.getjLabel().setText(string);
    }






    private int round = 0;
    private Timer timer;
    private int roundDuration = /*10+1;*/
            ServerUtils.ROUND_DURATION+1;

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
    }


}
