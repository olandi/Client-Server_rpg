package com.company.model.damageTO;

import com.company.model.BodyParts;

import java.util.List;

public class DamageForClient {

    private List<BodyParts> attack;
    private List<BodyParts> defense;

    public DamageForClient(List<BodyParts> attack, List<BodyParts> defense) {
        this.attack = attack;
        this.defense = defense;
    }



    public List<BodyParts> getAttack() {
        return attack;
    }

    public List<BodyParts> getDefense() {
        return defense;
    }

    @Override
    public String toString() {
        return "DamageForClient{" +
                "attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}
