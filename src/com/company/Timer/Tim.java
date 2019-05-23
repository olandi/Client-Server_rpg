package com.company.Timer;

import com.company.GameFieldGUI;
import com.company.Main;
import com.company.gameField.GameField;
import com.company.heroActions.HeroAction;

import java.util.Objects;

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

        while (Timer.actions.size()>0){
            Timer.actions.poll().perform();
        }

        GameField.setAllSelectedFalse();
        GameField.setAllHeroMovable();
        count = initialValue;

        //System.out.println(GameField.getGameField());
        //System.out.println(GameFieldGUI.list);

        Main.frame.repaint();
    }


}
