package com.multiPlayer.client.swing;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.connection.MessageObjects.HeroBattleAction;
import com.multiPlayer.connection.MessageObjects.HeroMovementAction;
import com.multiPlayer.client.MainLayoutController;
import com.multiPlayer.client.swing.View.*;
import com.multiPlayer.client.swing.model.HexagonItem;
import com.multiPlayer.connection.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.multiPlayer.connection.MessageType.PLAYER_BATTLE_MESSAGE;
import static com.multiPlayer.connection.MessageType.PLAYER_MOVEMENT_MESSAGE;

public class BattleFieldController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleFieldController.class);
    private MainLayoutController controller;

    private BattleFieldModel model = new BattleFieldModel();
    private CombatLogPanel combatLogPanel = new CombatLogPanel(this);
    private BattleFieldPanel battleFieldPanel = new BattleFieldPanel(this);
    private HittingPanel hittingPanel = new HittingPanel(this);
    private TimerPanel timerPanel = new TimerPanel(this);
    private HeroInfoPanel heroInfoPanel = new HeroInfoPanel(this);
    private MainPanel mainGamePanel = new MainPanel(this);


    public BattleFieldController(MainLayoutController controller) {
        this.controller = controller;
    }

    public MainLayoutController getController() {
        return controller;
    }

    public BattleFieldModel getModel() {
        return model;
    }

    public void setController(MainLayoutController controller) {
        this.controller = controller;
    }

    public List<HexagonItem> getBattleField() {
        return model.getBattleField();
    }

    public Map<String, Hero> getHeroes() {
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




    public void sendMovementActionToServer(HeroMovementAction heroMovementAction) throws IOException {
        Message message = new Message(PLAYER_MOVEMENT_MESSAGE, heroMovementAction);
        LOGGER.debug("send message to server: {}", message);
        controller.getConnection().send(message);
    }


    public void sendBattleActionToServer(HeroBattleAction heroBattleAction) throws IOException {
        Message message = new Message(PLAYER_BATTLE_MESSAGE, heroBattleAction);
        LOGGER.debug("send message to server: {}", message);
        controller.getConnection().send(message);
    }


    public void repaintAllView() {
        getMainGamePanel().repaint();
    }


    public void setHittingPanelMouseListener() {
        removeAllMouseListeners();
        mainGamePanel.addMouseListener(hittingPanel.getBattleMouseListener());
    }

    public void setBattleFieldPanelMouseListener() {
        removeAllMouseListeners();
        mainGamePanel.addMouseListener(battleFieldPanel.getMouseListener());
    }

    private void removeAllMouseListeners() {
        Arrays.stream(getMainGamePanel().getMouseListeners())
                .forEach(mouseListener ->
                        mainGamePanel.removeMouseListener(mouseListener));
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

    private void setTimerValueOnGui(String string) {
        timerPanel.getjLabel().setText(string);
    }

    public void initBattle(BattleField battleFieldArr, Map<String, Hero> heroes) {
        model.initBattle(battleFieldArr, heroes);
        model.initPlayerHero(controller.getPlayer());
        heroInfoPanel.initPlayerInfo();
    }


    public void updateBattleField(Map<String, Hero> data) {
        model.updateData(data);

        //update hero HP info
        if (model.isHeroPlayerHeroAlive()) {
            heroInfoPanel.setHeroHP(model.getPlayersHero().getHealth());
        } else {
            heroInfoPanel.setHeroHP(0);
        }

        resetBattleMenu();
        mainGamePanel.repaint();
    }

    public HeroInfoPanel getHeroInfoPanel() {
        return heroInfoPanel;
    }

    public void resetBattle() {
        model.resetAllData();
        heroInfoPanel.destroyPlayerInfo();
    }


    public Hero getEnemy(){
        return model.getEnemy();
    }

    public void setEnemy(Hero hero){
        model.setEnemy(hero);
    }
}
