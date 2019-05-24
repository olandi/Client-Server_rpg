package com.company.serverToDamage;

import com.company.BattleView.BodyParts;
import com.company.Hero.Hero;

import java.util.List;

public class DamageTo {

    private Hero fromHero;
    private Hero toHero;
    private List<BodyParts> attack;
    private List<BodyParts> defense;

    @Override
    public String toString() {
        return "DamageTo{" +
                "fromHero=" + fromHero +
                ", toHero=" + toHero +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }

    public DamageTo(Hero fromHero, Hero toHero, List<BodyParts> attack, List<BodyParts> defense) {
        this.fromHero = fromHero;
        this.toHero = toHero;
        this.attack = attack;
        this.defense = defense;
    }

    public Hero getFromHero() {
        return fromHero;
    }

    public Hero getToHero() {
        return toHero;
    }

    public List<BodyParts> getAttack() {
        return attack;
    }

    public List<BodyParts> getDefense() {
        return defense;
    }
}
