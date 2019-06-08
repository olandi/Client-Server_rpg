package com.multiPlayer.connection.MessageObjects;

import com.multiPlayer.both.Hero.BodyParts;
import com.multiPlayer.both.Hero.Hero;

import java.io.Serializable;
import java.util.List;

public class HeroBattleAction implements Serializable {

    private Hero target;
    private List<BodyParts> attack;
    private List<BodyParts> defense;

    public HeroBattleAction(Hero target, List<BodyParts> attack, List<BodyParts> defense) {
        this.target = target;
        this.attack = attack;
        this.defense = defense;
    }

    public Hero getTarget() {
        return target;
    }

    public List<BodyParts> getAttack() {
        return attack;
    }

    public List<BodyParts> getDefense() {
        return defense;
    }

    @Override
    public String toString() {
        return "HeroBattleAction{" +
                "target=" + target +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}
