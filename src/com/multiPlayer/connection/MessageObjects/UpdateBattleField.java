package com.multiPlayer.connection.MessageObjects;

import com.multiPlayer.both.Hero.Hero;

import java.io.Serializable;
import java.util.Map;

public class UpdateBattleField implements Serializable {
    private Map<String, Hero> heroes;
    private String battleLog;

    public UpdateBattleField(Map<String, Hero> heroes) {
        this.heroes = heroes;
    }
    public UpdateBattleField(Map<String, Hero> heroes,String battleLog) {
        this.heroes = heroes;
        this.battleLog = battleLog;
    }

    public Map<String, Hero> getHeroes() {
        return heroes;
    }

    public String getBattleLog() {
        return battleLog;
    }

    @Override
    public String toString() {
        return "UpdateBattleField{" +
                "heroes=" + heroes +
                ", battleLog='" + battleLog + '\'' +
                '}';
    }
}
