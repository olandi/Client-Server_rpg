package com.company.model.gameField;

import com.company.client.model.HeroImages;
import com.company.model.Hero.*;
import com.company.client.model.Hexagon;

import java.util.ArrayList;
import java.util.List;

public class GameField {


    static private List<HexagonItem> list;

    public static List<HexagonItem> getGameField() {
        return list;
    }

    static {
        list = new ArrayList<>();


        for (int i = 0; i < 48; i++) {

            int x = i % 8;
            int y = i / 8;

            int xx = (y % 2 == 0) ? 40 + 80 * x : 80 + 80 * x;
            int yy = 40 + 70 * y;

            int fieldOffset = 30;


            list.add(new HexagonItem(new Hexagon(xx + fieldOffset, yy + fieldOffset, 40), i));
        }

        Hero pirate = new Hero("Pirate");
        pirate.setView(HeroImages.PIRATE_PATH);

        Hero knight = new Hero("Knight");
        knight.setView(HeroImages.KNIGHT_PATH);


        list.get(18).setCellContent(pirate);
        list.get(29).setCellContent(knight);


    }

    //kill hero
    public static void removeHero(Hero hero) {
        list.forEach(i -> {
            if (i.getCellContent() == hero){ // именно по ссылкам
                //TODO; сделать нормальный индекс элемента
                i.setCellContent(new EmptyCell(100));
            }
        });
    }


    private static HexagonItem getHexagonItemByHero(Hero hero){
        for (HexagonItem hexagonItem : list){
            if ("Hero".equals(hexagonItem.getCellContent().getContentType())&&((Hero) hexagonItem.getCellContent())==hero)
                return hexagonItem;
        }
        return null;
    }

    public static void setAllSelectedFalse() {
        list.forEach(i -> i.getCellContent().setSelected(false));
    }

    public static void setAllHeroMovable() {
        list.forEach(i -> {
            if (i.getCellContent().getContentType().equals("Hero")) {
                ((Hero) i.getCellContent()).setTurnState(TurnState.ReadyForTurn);
            }
        });
    }


    public static void moveHero(Hero hero, HexagonItem moveTo) {
        //remove hero from old place
        getHexagonItemByHero(hero).setCellContent(moveTo.getCellContent());
        //move hero to new place
        moveTo.setCellContent(hero);
    }

}
