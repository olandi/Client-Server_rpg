package com.multiPlayer.client.swing;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.client.swing.model.HexagonItem;
import com.multiPlayer.client.swing.model.util.BattleFildDrawer;
import com.multiPlayer.server.ServerUtils;


import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainModel {

    private Hero currentHero;
    private Hero enemy;
    private boolean isHittingPanelVisible = false;
    private Hero playersHero;

    private List<HexagonItem> battleField;
    private Map<Hero, Integer> heroes;

    private Set<Integer> heroRangeSet;
    private BattleField battleFieldArr;


    //таймер и лог боя
    public void initBattle(BattleField battleFieldArr, Map<Hero, Integer> heroes) {
        //todo принимать игровое поля для преобразования для gui
        this.heroes = heroes;
        // this.battleField = new ServerUtils().getBattleField();//todo переделать подмененный battleField
        this.battleField = BattleFildDrawer.createGuiBattlefield(50, 50, battleFieldArr, 40, 10);


        this.battleFieldArr = battleFieldArr;
    }

    public void initPlayerHero(Hero playersHero) {
        this.playersHero = playersHero;

        //update battleField
        battleField.get(heroes.get(playersHero)).setSelected(true);


        heroRangeSet = battleFieldArr.getMovementArray(heroes.get(playersHero), playersHero.getSpeed()); //todo перенести инициализацию moveRange


        heroRangeSet.forEach(i -> {
            battleField.get(i).setSelected(true);
        });
    }

    public void updateData(Map<Hero,Integer> map){
        this.heroes = map;


        battleField.forEach(i -> i.setSelected(false));
        battleField.get(heroes.get(playersHero)).setSelected(true);
        heroRangeSet = battleFieldArr.getMovementArray(heroes.get(playersHero), playersHero.getSpeed()); //todo перенести инициализацию moveRange
        heroRangeSet.forEach(i -> {
            battleField.get(i).setSelected(true);
        });




    }



    public Set<Integer> getHeroRangeSet() {
        return heroRangeSet;
    }

    public Hero getHeroByName(String name) {

        return heroes.keySet().stream().filter(hero -> name.equals(hero.getName())).findFirst().orElse(null);
    }


    public Hero getHeroByIndex(int index) {
        for (Map.Entry<Hero, Integer> entry : heroes.entrySet()) {
            if (entry.getValue() == index) return entry.getKey();
        }
        return null;
    }

    public Hero getPlayersHero() {
        return playersHero;
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

}
