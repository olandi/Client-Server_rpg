package com.company.heroActions;

import com.company.BattleView.BodyParts;
import com.company.Hero.Hero;
import com.company.Timer.ServerUtils;

import java.util.Arrays;
import java.util.List;

public class BlockAction implements HeroAction {

    private List<BodyParts> bodyParts;
    private Hero hero;

    public BlockAction(Hero hero, List<BodyParts> bodyParts) {
        this.hero = hero;
        this.bodyParts = bodyParts;
    }


    @Override
    public void perform() {
       // ServerUtils.blockAction.add(this);
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "BlockAction: "+getClass().getName() + "@" + Integer.toHexString(hashCode())+" {" +
                "bodyParts=" + bodyParts +
                ", hero=" + hero +
                '}';
    }
}
