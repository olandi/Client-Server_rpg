package com.company.client.utils;

import com.company.client.SwingView.BattleFieldPanel;
import com.company.model.Hero.Hero;
import com.company.model.Hero.TurnState;
import com.company.server.ServerUtils;
import com.company.model.damageTO.DamageForClient;
import com.company.model.damageTO.DamageToForServer;

public class ClientUtils {
    private static Hero hero;
    private static Hero enemy;

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        ClientUtils.hero = hero;
    }

    public Hero getEnemy() {
        return enemy;
    }

    public void setEnemy(Hero enemy) {
        ClientUtils.enemy = enemy;
    }

    private static void refresh() {
        ClientUtils.hero = null;
        ClientUtils.enemy = null;
    }

    public static void SendDamageToServer(DamageForClient damageForClient) {
        DamageToForServer damageToForServer = new DamageToForServer(hero, enemy);
        damageToForServer.addDamageAndBlockLists(damageForClient.getAttack(), damageForClient.getDefense());
        ServerUtils.map.put(hero, damageToForServer);

        performHeroTurn(hero);

        refresh();
    }

    private static void performHeroTurn(Hero hero) {


        hero.setTurnState(TurnState.TurnIsFinished);

        hero.setSelected(false);

        BattleFieldPanel.turnManager.setCurrentHero(null);
    }





}
