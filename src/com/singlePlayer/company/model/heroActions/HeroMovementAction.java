package com.singlePlayer.company.model.heroActions;


public class HeroMovementAction {

    private int tileIndex;

    public HeroMovementAction(int tileIndex) {

        this.tileIndex = tileIndex;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    @Override
    public String toString() {
        return "HeroMovementAction{" +
                "tileIndex=" + tileIndex +
                '}';
    }
}
