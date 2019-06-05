package com.multiPlayer.server;


import com.multiPlayer.both.Hero.BodyParts;
import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.Hero.TurnState;
import com.multiPlayer.other.MessageObjects.HeroBattleAction;
import com.multiPlayer.other.MessageObjects.HeroMovementAction;
import com.multiPlayer.client.swing.model.HeroImages;
import com.multiPlayer.client.swing.model.Hexagon;
import com.multiPlayer.client.swing.model.HexagonItem;

import java.util.*;

public class ServerUtils {
    public static final int ROUND_DURATION = 40;

    private List<HexagonItem> battleField;
    private Map<Hero, Integer> heroes;

    private Map<Hero, HeroMovementAction> movementActions = new HashMap<>();
    private Map<Hero, HeroBattleAction> heroHeroBattleActions = new HashMap<>();

    private final int[] startPointsOfFirstTeam = new int[]{18};
    private final int[] startPointsOfSecondTeam = new int[]{29};
    private final int[][] getStartPoints = new int[][]{
            {18},
            {29}
    };


    public static Map<Hero, Integer> createTwoHeroesForBattle(String[] names) {

        /*heroes = new HashMap<>();
      //  Arrays.stream(names).forEach( name ->heroes.putIfAbsent(new Hero(name).setView(HeroImages.PIRATE_PATH),0));
        heroes.put(new Hero(names[0]).setView(HeroImages.PIRATE_PATH), 18);
        heroes.put(new Hero(names[1]).setView(HeroImages.KNIGHT_PATH), 29);*/

        return new HashMap<Hero, Integer>() {
            {
                put(new Hero(names[0]).setView(HeroImages.PIRATE_PATH), 18);
                put(new Hero(names[1]).setView(HeroImages.KNIGHT_PATH), 29);
            }
        };
    }

    public List<HexagonItem> getBattleField() {
        initbf();
        return battleField;
    }

    private void initbf() {
        battleField = new ArrayList<>();
        heroes = new HashMap<>();

        Hero pirate = new Hero("Pirate");
        pirate.setView(HeroImages.PIRATE_PATH);

        Hero knight = new Hero("Knight");
        knight.setView(HeroImages.KNIGHT_PATH);

        heroes.put(pirate, 18);
        heroes.put(knight, 29);


        for (int i = 0; i < 48 + 3; i++) {

         //   if (i == 8 || i == 25 || i == 42) continue;

            int x = i % 8;
            int y = i / 8;

            int xx = (y % 2 == 0) ? 40 + 80 * x : 80 + 80 * x;
            int yy = 40 + 70 * y;

            int fieldOffset = 30;

            battleField.add(new HexagonItem(new Hexagon(xx + fieldOffset, yy + fieldOffset, 40), i));
        }


        battleField.add(8, new HexagonItem(new Hexagon(710, 67, 40), 8));
        battleField.add(25, new HexagonItem(new Hexagon(710, 207, 40), 25));
        battleField.add(42, new HexagonItem(new Hexagon(710, 347, 40), 42));

    }

    //kill hero
    public void removeHero(Hero hero) {
        heroes.remove(hero);
    }

    public Hero getHeroByIndex(int index) {
        for (Map.Entry<Hero, Integer> entry : heroes.entrySet()) {
            if (entry.getValue() == index) return entry.getKey();
        }
        return null;
    }


    private HexagonItem getHexagonItemByHero(Hero hero) {
        return battleField.get(heroes.get(hero));
    }

    public void setAllSelectedFalse() {
        battleField.forEach(i -> i.setSelected(false));
    }

    public void setAllHeroMovable() {
        heroes.forEach((hero, index) -> hero.setTurnState(TurnState.ReadyForTurn));
    }


    public void moveHero(Hero hero, int index) {
        //update hero index
        heroes.computeIfPresent(hero, (key, value) -> index);
    }


    public Map<Hero, HeroMovementAction> getMovementActions() {
        return movementActions;
    }

    public Map<Hero, HeroBattleAction> getHeroHeroBattleActions() {
        return heroHeroBattleActions;
    }

    public void performAllMovements() {
        movementActions.forEach((hero, value) -> moveHero(hero, value.getTileIndex()));

        movementActions.clear();
    }

    public boolean isReadyToMoveHeroExist() {
        boolean result = false;

        for (Hero hero : heroes.keySet()) {
            // System.out.println("Hero: " + hero + " " + hero.getTurnState());
            result |= hero.getTurnState().equals(TurnState.ReadyForTurn);
        }
        return result;
    }

    public void checkAliveHero() {
        Set<Hero> h = new HashSet<>(heroes.keySet());

        for (Hero hero : h) {
            if (hero.getHealth() < 0) removeHero(hero);
        }
    }


    public boolean isOneHeroRemain() {
        return heroes.size() <= 1;
    }


    private List<BodyParts> getDefenseListFromHero(Hero hero) {
        if (heroHeroBattleActions.get(hero) == null) return Collections.emptyList();
        return heroHeroBattleActions.get(hero).getDefense();
    }


    public void computeDamage() {

        //для каждого героя
        heroes.forEach((hero, index) -> {

            //для каждой атаки героя
            if (heroHeroBattleActions.get(hero) != null)
                heroHeroBattleActions.get(hero).getAttack().forEach(attack -> {

                            //если в списке защиты врага нет этой атаки

                            //исли цель не сделала ход, не поставила блоки
                            if (heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()) == null) {
                                heroHeroBattleActions.get(hero).getTarget().setHealth(
                                        heroHeroBattleActions.get(hero).getTarget().getHealth() -
                                                hero.getDamage());

                                //todo Controller.getCombatLogPanel().appendText("Hero: " + heroHeroBattleActions.get(hero).getToHero().getName() + " received 20 damage\n");


                            }

                            if (
                                    heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()) != null
                                            && !heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()).getDefense()
                                            .contains(attack)) {

                                System.out.println("Hero " + hero + " hits");

                                heroHeroBattleActions.get(hero).getTarget().setHealth(
                                        heroHeroBattleActions.get(hero).getTarget().getHealth() -
                                                hero.getDamage());

                                //todo   Controller.getCombatLogPanel().appendText("Hero: " + heroHeroBattleActions.get(hero).getToHero().getName() + " received 20 damage\n");

                            }
                        }
                );
        });

        heroHeroBattleActions.clear();
    }

}
