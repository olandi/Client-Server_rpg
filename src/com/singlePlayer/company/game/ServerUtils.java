package com.singlePlayer.company.game;

import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.client.swing.model.util.BattleFildDrawer;
import com.singlePlayer.company.game.swing.model.HeroImages;
import com.singlePlayer.company.game.swing.model.Hexagon;
import com.singlePlayer.company.game.swing.model.HexagonItem;
import com.singlePlayer.company.game.Hero.BodyParts;
import com.singlePlayer.company.game.Hero.Hero;
import com.singlePlayer.company.game.Hero.TurnState;
import com.singlePlayer.company.game.Hero.heroActions.HeroBattleAction;
import com.singlePlayer.company.game.Hero.heroActions.HeroMovementAction;

import java.util.*;

public class ServerUtils {
    public static final int ROUND_DURATION = 40;

    private List<HexagonItem> battleField;
    private Map<Hero, Integer> heroes;

    private Map<Hero, HeroMovementAction> movementActions = new HashMap<>();
    private Map<Hero, HeroBattleAction> heroHeroBattleActions = new HashMap<>();

    public Map<Hero, Integer> getHeroes() {
        return heroes;
    }

    public List<HexagonItem> getBattleField() {
        return battleField;
    }

    {
        battleField = new ArrayList<>();
        heroes = new HashMap<>();

        Hero pirate = new Hero("Pirate");
        pirate.setView(HeroImages.PIRATE_PATH);

        Hero knight = new Hero("Knight");
        knight.setView(HeroImages.KNIGHT_PATH);

        heroes.put(pirate, 18);
        heroes.put(knight, 29);


        for (int i = 0; i < 48; i++) {

            int x = i % 8;
            int y = i / 8;

            int xx = (y % 2 == 0) ? 40 + 80 * x : 80 + 80 * x;
            int yy = 40 + 70 * y;

            int fieldOffset = 30;

            battleField.add(new HexagonItem(new Hexagon(xx + fieldOffset, yy + fieldOffset, 40), i));
            //battleField.addAll( BattleFildDrawer.createGuiBattlefield(40,40,new BattleField(9,6),40,10));
        }


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

                                    //todo BattleFieldController.getCombatLogPanel().appendText("Hero: " + heroHeroBattleActions.get(hero).getToHero().getName() + " received 20 damage\n");


                                }

                                if (
                                        heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()) != null
                                                && !heroHeroBattleActions.get(heroHeroBattleActions.get(hero).getTarget()).getDefense()
                                                .contains(attack)) {

                                    System.out.println("Hero " + hero + " hits");

                                    heroHeroBattleActions.get(hero).getTarget().setHealth(
                                            heroHeroBattleActions.get(hero).getTarget().getHealth() -
                                                    hero.getDamage());

                                    //todo   BattleFieldController.getCombatLogPanel().appendText("Hero: " + heroHeroBattleActions.get(hero).getToHero().getName() + " received 20 damage\n");

                                }
                            }
                    );
        });

        heroHeroBattleActions.clear();
    }

}
