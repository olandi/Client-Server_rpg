package com.company.Timer;

import com.company.BattleView.BodyParts;
import com.company.Hero.Hero;
import com.company.gameField.GameField;
import com.company.heroActions.HeroAction;
import com.company.serverToDamage.DamageTo;

import java.util.*;

public class ServerUtils {

    public static Deque<HeroAction> movementActions = new ArrayDeque<>();
    public static List<HeroAction> blockAction = new ArrayList<>();
    public static List<HeroAction> attackAction = new ArrayList<>();

    public static Map<Hero, DamageTo> map = new HashMap<>();

    public static List<Hero> heroes = new ArrayList<>();

    public static void getAndUpdateAllHeroes() {
        heroes = new ArrayList<>();
        GameField.getGameField().forEach(i -> {
                    if ("Hero".equals(i.getCellContent().getContentType())) {
                        heroes.add((Hero) i.getCellContent());
                    }

                }
        );
    }

    public static void resetMap() {
        map.clear();
    }

    public static void computeDamage() {

        getAndUpdateAllHeroes();

       // heroes.forEach( hero-> map.put(hero,new DamageTo()));


        //для каждого героя
        heroes.forEach(hero->{

                //для каждой атаки героя
            if (map.get(hero)!=null) map.get(hero).getAttack().forEach( attack -> {

                    //если в списке защиты врага нет этой атаки

                //исли цель не сделала ход, не поставила блоки
               if(map.get(map.get(hero).getToHero())==null){
                   map.get(hero).getToHero().setHealth(
                           map.get(hero).getToHero().getHealth() -
                                   hero.getDamage());

                   System.out.println("Hero "+map.get(hero).getToHero()+" receives damage");


               }

                         if (
                                 map.get(map.get(hero).getToHero())!=null
                                 && !map.get(map.get(hero).getToHero()).getDefense()
                                 .contains(attack)) {

                             System.out.println("Hero "+hero+" hits");

                             map.get(hero).getToHero().setHealth(
                                     map.get(hero).getToHero().getHealth() -
                                     hero.getDamage());

                             System.out.println("Hero "+map.get(hero).getToHero()+" receives damage");


                         }
                     }


             );


        });


    }


}
