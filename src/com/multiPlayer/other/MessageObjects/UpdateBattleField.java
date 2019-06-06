package com.multiPlayer.other.MessageObjects;

import com.multiPlayer.both.Hero.Hero;

import java.io.Serializable;
import java.util.Map;

public class UpdateBattleField implements Serializable {
    private Map<Hero,Integer> heroes;
    private String battleLog;

    public UpdateBattleField(Map<Hero, Integer> heroes) {
        this.heroes = heroes;
    }

    public Map<Hero, Integer> getHeroes() {
        return heroes;
    }

    public void setHeroes(Map<Hero, Integer> heroes) {
        this.heroes = heroes;
    }

    public String getBattleLog() {
        return battleLog;
    }

    public void setBattleLog(String battleLog) {
        this.battleLog = battleLog;
    }

    @Override
    public String toString() {
        return "UpdateBattleField{" +
                "heroes=" + heroes +
                ", battleLog='" + battleLog + '\'' +
                '}';
    }
}
