package com.company.Timer;


import com.company.GameFieldGUI;
import com.company.Hero.Hero;
import com.company.Main;
import com.company.clientUtils.ClientUtils;
import com.company.gameField.GameField;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Tim {
    private int initialValue;
    private int count;
    private int turn = 0;
    private JPanel battleFieldPanel;

    public Tim(int count, JPanel battleFieldPanel) {
        this.count = count;
        this.initialValue = count;
        this.battleFieldPanel = battleFieldPanel;
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






       // System.out.println(ServerUtils.attackAction);
     //   System.out.println(ServerUtils.blockAction);

       // System.out.println(ServerUtils.map);

     //   ServerUtils.attackAction = new ArrayList<>();
     //   ServerUtils.blockAction = new ArrayList<>();
        ServerUtils.resetMap();




        GameFieldGUI.turnManager.setCurrentHero(null);

       // Main1.battleFrame.dispatchEvent(new WindowEvent(Main1.battleFrame, WindowEvent.WINDOW_CLOSING));
        //TODO Костыль удалить: если окно боя не открывается, то battle = null и вылелает nullPointerException
     //  if (Main1.battle!=null) Main1.battle.resetBattleActions();

        ClientUtils.getBattle().resetBattleMenu();

        battleFieldPanel.repaint();
    }


}
