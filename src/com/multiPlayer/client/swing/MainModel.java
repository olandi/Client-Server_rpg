package com.multiPlayer.client.swing;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.client.swing.model.HexagonItem;
import com.multiPlayer.server.ServerUtils;


import java.util.List;
import java.util.Map;


public class MainModel {

    private Hero currentHero;
    private Hero enemy;
    private boolean isHittingPanelVisible = false;

    private List<HexagonItem> battleField;
    private Map<Hero, Integer> heroes;



    //таймер и лог боя
    public void initBattle(List<Integer> battleField, Map<Hero, Integer> heroes){
         //todo
        this.heroes = heroes;
        this.battleField = new ServerUtils().getBattleField();

    }

    public List<HexagonItem> getBattleField() {
        return battleField;
    }

    public Map<Hero, Integer> getHeroes() {
        return heroes;
    }

    public boolean isHittingPanelVisible() {
        return isHittingPanelVisible;
    }

    public void setHittingPanelVisible(boolean hittingPanelVisible) {
        isHittingPanelVisible = hittingPanelVisible;
    }

    public Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(Hero currentHero) {
       this.currentHero = currentHero;
    }

    public Hero getEnemy() {
        return enemy;
    }

    public void setEnemy(Hero enemy) {
        this.enemy = enemy;
    }

    public void refresh() {
        currentHero = null;
        enemy = null;
    }

    public Hero getHeroByIndex(int index) {
        for (Map.Entry<Hero, Integer> entry : heroes.entrySet()) {
            if (entry.getValue() == index) return entry.getKey();
        }
        return null;
    }
}
