package com.company.clientUtils;

import com.company.BattleView.Battle;
import com.company.GameFieldGUI;
import com.company.Hero.Hero;
import com.company.Hero.TurnState;
import com.company.Main;
import com.company.Timer.ServerUtils;
import com.company.damageTO.DamageForClient;
import com.company.damageTO.DamageToForServer;
import com.company.gameField.GameField;

import java.awt.event.MouseListener;

public class ClientUtils {
    private static Hero hero;
    private static Hero enemy;
    private static MouseListener mouseListener;
    private static Battle battle;

    public static Battle getBattle() {
        return battle;
    }

    public static void setBattle(Battle battle) {
        ClientUtils.battle = battle;
    }

    public static MouseListener getMouseListener() {
        return mouseListener;
    }

    public static void setMouseListener(MouseListener mouseListener) {
        ClientUtils.mouseListener = mouseListener;
    }

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

    public static void refresh() {
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

    public static void performHeroTurn(Hero hero) {


        hero.setTurnState(TurnState.TurnIsFinished);

        hero.setSelected(false);

        GameFieldGUI.turnManager.setCurrentHero(null);
    }





}
