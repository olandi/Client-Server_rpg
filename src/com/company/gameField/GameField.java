package com.company.gameField;

import com.company.Hero.FieldItem;
import com.company.Hero.Hero;
import com.company.Hero.HeroImages;
import com.company.Hero.TurnState;
import com.company.ex2.Hexagon;

import java.util.ArrayList;
import java.util.List;

public class GameField {



    static private List<HexagonItem> list;

   /* public GameField() {
        buildGameField();
    }*/

    public static List<HexagonItem> getGameField() {
        return list;
    }

    /*private void buildGameField()*/ static {
        list = new ArrayList<>();


        for (int i = 0; i < 48; i++) {

            int x = i % 8;
            int y = i / 8;

            int xx = (y % 2 == 0) ? 40 + 80 * x : 80 + 80 * x;
            int yy = 40 + 70 * y;

            int fieldOffset = 30;


            list.add(new HexagonItem(new Hexagon(xx + fieldOffset, yy + fieldOffset, 40),i));
        }

        Hero pirate = new Hero("Pirate");
        pirate.setView(HeroImages.PIRATE_PATH);

        Hero knight = new Hero("Knight");
        knight.setView(HeroImages.KNIGHT_PATH);


        list.get(18).setCellContent(pirate);
        list.get(29).setCellContent(knight);


    }


    public static void setAllSelectedFalse(){
        list.forEach(i-> i.getCellContent().setSelected(false));
    }

    public static void setAllHeroMovable(){
        list.forEach(i->{
            if(i.getCellContent().getContentType().equals("Hero")){((Hero)i.getCellContent()).setTurnState(TurnState.ReadyForTurn);}
        });
    }


    public static void moveHero(HexagonItem hero, HexagonItem moveTo){

        FieldItem buffer =  moveTo.getCellContent();

        moveTo.setCellContent(hero.getCellContent());
        hero.setCellContent(buffer);

        //list.set(list.indexOf(moveTo),hero);//move hero to new place
       // list.set(list.indexOf(hero),moveTo);//remove hero from old place
       // System.out.println("done");

    }

}
