package com.singlePlayer.company.model;

import com.singlePlayer.company.model.Hero.Hero;

public class TurnManager {

    private Hero currentHero;

    public Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
    }

    @Override
    public String toString() {
        return "TurnManager{" +
                "currentHero=" + currentHero +
                '}';
    }
}
