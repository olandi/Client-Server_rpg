package com.singlePlayer.company.client.swing;

import com.singlePlayer.company.client.model.HexagonItem;
import com.singlePlayer.company.client.swing.View.*;
import com.singlePlayer.company.model.Hero.Hero;
import com.singlePlayer.company.model.Hero.TurnState;
import com.singlePlayer.company.model.heroActions.HeroBattleAction;
import com.singlePlayer.company.model.heroActions.HeroMovementAction;
import com.singlePlayer.company.server.ServerUtils;
import com.singlePlayer.company.server.Tim;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Controller {
//TODO: Есть такая проблема, что некоторые панельные классы унаследованы от jpanel, а некоторые не унаследованы,
// но возвращают ссылку на сконструированный обьект jpanel. В результате получается путаница, об.
    // Исследовать необходимость наследоваться от jpanel и стандартизировать проеки

    private MainModel model = new MainModel();

    private CombatLogPanel combatLogPanel = new CombatLogPanel(this);
    private BattleFieldPanel battleFieldPanel = new BattleFieldPanel(this);
    private HittingPanel hittingPanel = new HittingPanel(this);
    private TimerPanel timerPanel = new TimerPanel(new Tim(this),this);


    //по идее должен быть последним
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
        return mainGamePanel.getMainPanel();
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
        //Todo возможны баги
       // hero.setSelected(false);
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
        // if (mainGamePanel!=null)
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


}
