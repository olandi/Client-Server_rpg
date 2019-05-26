package com.company.Timer;

import com.company.BattleView.Main1;
import com.company.GameFieldGUI;
import com.company.Hero.Hero;
import com.company.Main;
import com.company.gameField.GameField;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Tim {
    private int initialValue;
    private int count;
    private int turn = 0;

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

    public void setCount(int count) {
        this.count = count;
    }

    public void reset(){
        count = initialValue;
        turn++;
        while (ServerUtils.movementActions.size()>0){
            ServerUtils.movementActions.poll().perform();
        }

        ServerUtils.computeDamage();

        GameFieldGUI.combatLog.appendText("Round "+turn+"\n");

        for(Hero hero: ServerUtils.heroes){
            GameFieldGUI.combatLog.appendText("Hero: "+hero.getName()+" ("+hero.getHealth()+") HP"+"\n");
        }

        ServerUtils.checkAliveHero();


        GameField.setAllSelectedFalse();
        GameField.setAllHeroMovable();


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
