package com.multiPlayer.other.MessageObjects;

import com.multiPlayer.both.Hero.Hero;

import java.io.Serializable;
import java.util.Map;

public class UpdateBattleField implements Serializable {
    private Map<Hero,Integer> heroes;

    public UpdateBattleField(Map<Hero, Integer> heroes) {
        this.heroes = heroes;
    }

    public Map<Hero, Integer> getHeroes() {
        return heroes;
    }

    @Override
    public String toString() {
        return "UpdateBattleField{" +
                "heroes=" + heroes +
                '}';
    }
}
