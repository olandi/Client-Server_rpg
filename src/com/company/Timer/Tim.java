package com.company.Timer;

import com.company.BattleView.Main1;
import com.company.Main;
import com.company.gameField.GameField;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Tim {
    private int initialValue;
    private int count;

    public Tim(int count) {
        this.count = count;
        this.initialValue = count;
    }

    public int dec(){

        if (count==0) reset(); else count--;
       // count>0? count--: reset();
        return count;
    }

    public int getCount() {
        return count;
    }

    public void reset(){

        while (ServerUtils.movementActions.size()>0){
            ServerUtils.movementActions.poll().perform();
        }

        ServerUtils.computeDamage();





        GameField.setAllSelectedFalse();
        GameField.setAllHeroMovable();
        count = initialValue;

        //System.out.println(GameField.getGameField());
        //System.out.println(GameFieldGUI.list);






        System.out.println(ServerUtils.attackAction);
        System.out.println(ServerUtils.blockAction);
        System.out.println(ServerUtils.map);
        ServerUtils.attackAction = new ArrayList<>();
        ServerUtils.blockAction = new ArrayList<>();
        ServerUtils.resetMap();




        Main.turnManager.setCurrentHero(null);

        Main1.frame.dispatchEvent(new WindowEvent(Main1.frame, WindowEvent.WINDOW_CLOSING));
        //TODO Костыль удалить: если окно боя не открывается, то battle = null и вылелает nullPointerException
       if (Main1.battle!=null) Main1.battle.resetBattleActions();

        Main.frame.repaint();
    }


}
