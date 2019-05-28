package com.company.server;


import com.company.client.SwingView.BattleFieldPanel;
import com.company.controller.Controller;
import com.company.model.Hero.Hero;
import com.company.client.utils.ClientUtils;
import com.company.model.gameField.GameField;

public class Tim {
    private int initialValue;
    private int count;
    private int round = 0;

    public Tim(int count) {
        this.count = count;
        this.initialValue = count;
    }

    public void dec() {
        if (count != 0) count--;
        else reset();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void reset() {
        count = initialValue;
        round++;
        while (ServerUtils.movementActions.size() > 0) {
            ServerUtils.movementActions.poll().perform();
        }

        ServerUtils.computeDamage();

        Controller.getCombatLogPanel().appendText("Round " + round + "\n");

        for (Hero hero : ServerUtils.heroes) {
            Controller.getCombatLogPanel().appendText("Hero: " + hero.getName() + " (" + hero.getHealth() + ") HP" + "\n");
        }

        ServerUtils.checkAliveHero();

        GameField.setAllSelectedFalse();
        GameField.setAllHeroMovable();

        ServerUtils.resetMap();

        BattleFieldPanel.turnManager.setCurrentHero(null);

        //В этом методе идет перерисовка фреймов в том числе
        Controller.getHittingPanel().resetBattleMenu();
    }
}
