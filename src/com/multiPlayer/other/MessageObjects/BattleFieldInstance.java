package com.multiPlayer.other.MessageObjects;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.client.swing.model.HexagonItem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BattleFieldInstance implements Serializable {
    private BattleField battleField;
    private Map<Hero, Integer> heroes;

    public BattleFieldInstance(BattleField battleField, Map<Hero, Integer> heroes) {
        this.battleField = battleField;
        this.heroes = heroes;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public Map<Hero, Integer> getHeroes() {
        return heroes;
    }
}
