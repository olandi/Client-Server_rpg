package com.company.controllerUtils;

import com.company.BattleView.Battle;
import com.company.GameFieldGUI;

import java.awt.event.MouseListener;

public class ControllerUtils {

    private MouseListener battle ;
    private MouseListener gameFieldGUI;

    public ControllerUtils(Battle battle, GameFieldGUI gameFieldGUI) {
        this.battle = battle.getBattleMouseListener();
        this.gameFieldGUI = gameFieldGUI.getMouseListener();
    }

    public MouseListener getBattle() {
        return battle;
    }

    public MouseListener getGameFieldGUI() {
        return gameFieldGUI;
    }
}
