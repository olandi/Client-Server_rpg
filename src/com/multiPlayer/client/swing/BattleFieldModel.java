package com.multiPlayer.client.swing;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.Hero.TurnState;
import com.multiPlayer.both.ImageLoader;
import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.client.swing.model.HeroImages;
import com.multiPlayer.client.swing.model.HexagonItem;
import com.multiPlayer.client.swing.model.util.BattleFieldDrawer;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BattleFieldModel {
    private boolean isHittingPanelVisible = false;
    private HashMap<String, Image> imageMap;

    private Hero enemy;
    private Hero playersHero;
    private List<HexagonItem> battleField;
    private Map<String, Hero> heroes;
    private Set<Integer> heroRangeSet;
    private BattleField battleFieldArr;


    //таймер и лог боя
    public void initBattle(BattleField battleFieldArr, Map<String, Hero> heroes) {
        this.heroes = heroes;
        this.battleField = BattleFieldDrawer.createGuiBattlefield(50, 50, battleFieldArr, 40, 10);
        this.battleFieldArr = battleFieldArr;

        //инициализация картинок персонажей
        imageMap = new HashMap<>();
        heroes.values().forEach(hero -> {
            imageMap.put(hero.getViewId(), ImageLoader.loadImage(HeroImages.DATA_BASE.get(hero.getViewId())));
            imageMap.put(hero.getPortretId(), ImageLoader.loadImage(HeroImages.DATA_BASE.get(hero.getPortretId())));
        });
    }

    public void initPlayerHero(String playerHeroName) {
        this.playersHero = heroes.get(playerHeroName);
        initMovementRange();
    }


    private void initMovementRange() {
        heroRangeSet = battleFieldArr.getMovementArray(playersHero.getPosition(), playersHero.getSpeed());
        setHeroMovementRangeSelected(true);
    }

    public void setHeroMovementRangeSelected(boolean isSelected){
        heroRangeSet.forEach(i -> battleField.get(i).setSelected(isSelected));
    }


    public void updateData(Map<String, Hero> map) {
        this.heroes = map;
        map.values().stream().filter((hero) -> hero.equals(playersHero))
                .findFirst().ifPresent((this::updatePlayerHero));
    }

    private void updatePlayerHero(Hero hero) {
        this.playersHero = hero;
        if (playersHero.getTurnState().equals(TurnState.ReadyForTurn)) {
            battleField.forEach(i -> i.setSelected(false));
            initMovementRange();
        }
    }


    public boolean isHeroPlayerHeroAlive() {
        return heroes.get(playersHero.getName()).getHealth() > 0;
    }


    public HashMap<String, Image> getImageMap() {
        return imageMap;
    }

    public Set<Integer> getHeroRangeSet() {
        return heroRangeSet;
    }

    public Hero getHeroByIndex(int index) {
        return heroes.values().stream().filter(i -> i.getPosition() == index).findFirst().orElse(null);
    }

    public Hero getPlayersHero() {
        return playersHero;
    }

    public List<HexagonItem> getBattleField() {
        return battleField;
    }

    public Map<String, Hero> getHeroes() {
        return heroes;
    }

    public boolean isHittingPanelVisible() {
        return isHittingPanelVisible;
    }

    public void setHittingPanelVisible(boolean hittingPanelVisible) {
        isHittingPanelVisible = hittingPanelVisible;
    }

    public void resetAllData() {
        imageMap = null;
        isHittingPanelVisible = false;
        playersHero = null;
        battleField = null;
        heroes = null;
        heroRangeSet = null;
        battleFieldArr = null;
    }

    public Hero getEnemy() {
        return enemy;
    }

    public void setEnemy(Hero enemy) {
        this.enemy = enemy;
    }

}
