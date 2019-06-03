package com.multiPlayer.both.Hero.heroActions;


import com.multiPlayer.connection.MessageAction;

import java.io.Serializable;

public class HeroMovementAction implements Serializable {

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
