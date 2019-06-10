package com.multiPlayer.connection.MessageObjects;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.battleField.BattleField;

import java.io.Serializable;
import java.util.Map;

public class BattleFieldInstance implements Serializable {
    private BattleField battleField;
    private Map<String, Hero> heroes;
    private String battleLog;

    public BattleFieldInstance(BattleField battleField, Map<String, Hero> heroes, String battleLog) {
        this.battleField = battleField;
        this.heroes = heroes;
        this.battleLog = battleLog;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public Map<String, Hero> getHeroes() {
        return heroes;
    }

    public String getBattleLog() {
        return battleLog;
    }

    @Override
    public String toString() {
        return "BattleFieldInstance{" +
                "battleField=" + battleField +
                ", heroes=" + heroes +
                ", battleLog='" + battleLog + '\'' +
                '}';
    }
}
