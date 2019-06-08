package com.multiPlayer.connection.MessageObjects;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.battleField.BattleField;

import java.io.Serializable;
import java.util.Map;

public class BattleFieldInstance implements Serializable {
    private BattleField battleField;
    private Map<String, Hero> heroes;

    public BattleFieldInstance(BattleField battleField, Map<String, Hero> heroes) {
        this.battleField = battleField;
        this.heroes = heroes;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public Map<String, Hero> getHeroes() {
        return heroes;
    }
}
