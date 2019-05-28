package com.company.model.damageTO;

import com.company.model.BodyParts;
import com.company.model.Hero.Hero;

import java.util.List;

public class DamageToForServer {

    private Hero fromHero;
    private Hero toHero;
    private List<BodyParts> attack;
    private List<BodyParts> defense;

    @Override
    public String toString() {
        return "DamageToForServer{" +
                "fromHero=" + fromHero +
                ", toHero=" + toHero +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }

    public DamageToForServer(Hero fromHero, Hero toHero) {
        this.fromHero = fromHero;
        this.toHero = toHero;
    }
/*
    public DamageToForServer(Hero fromHero, Hero toHero, List<BodyParts> attack, List<BodyParts> defense) {
        this.fromHero = fromHero;
        this.toHero = toHero;
        this.attack = attack;
        this.defense = defense;
    }*/

    public void addDamageAndBlockLists(List<BodyParts> attack, List<BodyParts> defense) {
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

    public void setDefense(List<BodyParts> defense) {
        this.defense = defense;
    }
}
