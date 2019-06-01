package com.singlePlayer.company.server;

import com.singlePlayer.company.client.swing.Controller;
import com.singlePlayer.company.model.BodyParts;
import com.singlePlayer.company.model.Hero.Hero;
import com.singlePlayer.company.model.Hero.TurnState;
import com.singlePlayer.company.model.gameField.GameField;
import com.singlePlayer.company.model.heroActions.HeroAction;
import com.singlePlayer.company.model.damageTO.DamageToForServer;

import java.util.*;

public class ServerUtils {

    public static Deque<HeroAction> movementActions = new ArrayDeque<>();

    public static Map<Hero, DamageToForServer> map = new HashMap<>();

    public static List<Hero> heroes = new ArrayList<>();

    public final static int ROUND_DURATION = 40;



    public static boolean isReadyToMoveHeroExist() {
        getAndUpdateAllHeroes();
        boolean result = false;
        for (Hero hero : heroes) {

            // System.out.println("Hero: " + hero + " " + hero.getTurnState());
            result |= hero.getTurnState().equals(TurnState.ReadyForTurn);
        }
        return result;
    }

    public static void checkAliveHero() {
        for (Hero hero : heroes) {
            if (hero.getHealth() < 0) GameField.removeHero(hero);
        }
    }


    public static boolean isOneHeroRemain() {
        return heroes.size() <= 1;
    }


    private static List<BodyParts> getDefenseListFromHero(Hero hero) {
        if (map.get(hero) == null) return Collections.emptyList();
        return map.get(hero).getDefense();
    }


    private static List<BodyParts> improveDefenseOfHero(Hero hero) {
        List<BodyParts> result = new ArrayList<>(getDefenseListFromHero(hero));
        List<BodyParts> list = new ArrayList<>(getDefenseListFromHero(hero));

        for (BodyParts bodyParts : list) {
            switch (bodyParts) {
                case BodyL:
                    result.add(BodyParts.BodyR);
                    break;
                case BodyR:
                    result.add(BodyParts.BodyL);
                    break;
                case HeadL:
                    result.add(BodyParts.HeadR);
                    break;
                case HeadR:
                    result.add(BodyParts.HeadL);
                    break;
                case LegsL:
                    result.add(BodyParts.LegsR);
                    break;
                case LegsR:
                    result.add(BodyParts.LegsL);
                    break;
                case LeftArmL:
                    result.add(BodyParts.LeftArmR);
                    break;
                case LeftArmR:
                    result.add(BodyParts.LeftArmL);
                    break;
                case RightArmL:
                    result.add(BodyParts.RightArmR);
                    break;
                case RightArmR:
                    result.add(BodyParts.RightArmL);
                    break;
            }
        }


        return result;
    }


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

        //для каждого героя
        heroes.forEach(hero -> {

            //дополнительная защита
            if (map.get(hero)!=null && map.get(hero).getDefense()!=null)
              //  heroes.forEach(hero1 -> {map.get(hero1).setDefense(improveDefenseOfHero(hero1));});

            //для каждой атаки героя
            if (map.get(hero) != null) map.get(hero).getAttack().forEach(attack -> {

                        //если в списке защиты врага нет этой атаки

                        //исли цель не сделала ход, не поставила блоки
                        if (map.get(map.get(hero).getToHero()) == null) {
                            map.get(hero).getToHero().setHealth(
                                    map.get(hero).getToHero().getHealth() -
                                            hero.getDamage());

                           //todo Controller.getCombatLogPanel().appendText("Hero: " + map.get(hero).getToHero().getName() + " received 20 damage\n");


                        }

                        if (
                                map.get(map.get(hero).getToHero()) != null
                                        && !map.get(map.get(hero).getToHero()).getDefense()
                                        .contains(attack)) {

                            System.out.println("Hero " + hero + " hits");

                            map.get(hero).getToHero().setHealth(
                                    map.get(hero).getToHero().getHealth() -
                                            hero.getDamage());

                         //todo   Controller.getCombatLogPanel().appendText("Hero: " + map.get(hero).getToHero().getName() + " received 20 damage\n");

                        }
                    }


            );


        });


    }


}
