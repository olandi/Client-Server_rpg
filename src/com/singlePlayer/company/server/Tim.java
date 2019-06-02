package com.singlePlayer.company.server;


import com.singlePlayer.company.client.swing.Controller;
import com.singlePlayer.company.model.Hero.Hero;

public class Tim {
    private int count= ServerUtils.ROUND_DURATION;
    private int round = 0;
    private Controller controller;

    public Tim(Controller controller) {
        this.controller = controller;
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
        count = ServerUtils.ROUND_DURATION;
        round++;

        controller.getServerUtils().performAllMovements();

/*        while (ServerUtils.movementActions.size() > 0) {
            ServerUtils.movementActions.poll().perform();
        }*/

        controller.getServerUtils().computeDamage();

        controller.getCombatLogPanel().appendText("Round " + round + "\n");

        for (Hero hero : controller.getServerUtils().getHeroes().keySet()) {
            controller.getCombatLogPanel().appendText("Hero: " + hero.getName() + " (" + hero.getHealth() + ") HP" + "\n");
        }

        controller.getServerUtils().checkAliveHero();

        controller.getServerUtils().setAllSelectedFalse();
        controller.getServerUtils().setAllHeroMovable();



        controller.setCurrentHero(null);

        //В этом методе идет перерисовка фреймов в том числе
        controller.resetBattleMenu();
    }
}
