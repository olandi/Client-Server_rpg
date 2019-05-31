/*
package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Sumilation {
    public static final double KNIGHT_HEALTH = 48;
    public static final double KNIGHT_DAMAGE = 8;
    public static final int KNIGHT_EVASION = 0;

    public static final double BERSERK_HEALTH = 48;
    public static final double BERSERK_DAMAGE = 5.5;
    public static final int BERSERK_EVASION = 10;


    //Sumilation.EVASION
    public static final int LOOPS = 10000;


    private String fightOneRound(Character firstCharacter, Character secondCharacter) {

        // System.out.println(firstCharacter);
        // System.out.println(secondCharacter);

        firstCharacter.Attack();
        firstCharacter.Defence();
        secondCharacter.Attack();
        secondCharacter.Defence();

        System.out.println(firstCharacter);
        System.out.println(secondCharacter);

        firstCharacter.hit(secondCharacter);
        secondCharacter.hit(firstCharacter);


        firstCharacter.resetAttackAndDef();
        secondCharacter.resetAttackAndDef();

        return "";
    }

    public Character runSimulation(Character firstCharacter, Character secondCharacter) {
        //пока есть хотя бы один живой


        do {
            fightOneRound(firstCharacter, secondCharacter);
        } while (firstCharacter.getHealth() > 0 && secondCharacter.getHealth() > 0);


        if (firstCharacter.getHealth() > 0 && secondCharacter.getHealth() <= 0) {
            System.out.println(firstCharacter.getClass().getSimpleName() + "WIN !!!!!");
            return firstCharacter;
        } else if (secondCharacter.getHealth() > 0 && firstCharacter.getHealth() <= 0) {
            System.out.println(secondCharacter.getClass().getSimpleName() + "WIN !!!!!");
            return secondCharacter;
        } else
            //  if (firstCharacter.getHealth() <= 0 && secondCharacter.getHealth() <= 0) {
            System.out.println("Both Died");
        return null;
        //   }


    }


    public static void main(String[] args) {


        Sumilation sumilation = new Sumilation();
        Knight knight = new Knight(KNIGHT_HEALTH, KNIGHT_DAMAGE, KNIGHT_EVASION);
        Berserk berserk = new Berserk(BERSERK_HEALTH, BERSERK_DAMAGE, BERSERK_EVASION);


        HashMap<String, Integer> map = new HashMap<>();

        int knightCount = 0;
        int bersCount = 0;
        int bothDeadCount = 0;

        String winner = "";
        for (int i = 0; i < LOOPS; i++) {
            System.out.println(knight);
            System.out.println(berserk);
            Character character = sumilation.runSimulation(knight, berserk);
            // System.out.println(knight);
            // System.out.println(berserk);

            if (character != null) {

                winner = character.getClass().getSimpleName();
                if ("Knight".equals(winner)) knightCount++;
                if ("Berserk".equals(winner)) bersCount++;

            } else bothDeadCount++;

            knight.reset();
            berserk.reset();

        }
        System.out.println();
        System.out.println("knight " + knightCount + ". Процент побед: "+ knightCount*1.0/(knightCount+bersCount)*100);
        System.out.println("bers " + bersCount + ". Процент побед: "+ bersCount*1.0/(knightCount+bersCount)*100);
        System.out.println("Разброс рандома: "+Math.abs(knightCount-bersCount));
        System.out.println("BothDead " + bersCount);
    }
}

abstract class Character {
    private double health;
    private double damage;
    int evasionChance;

    protected List<Integer> attack = new ArrayList<>();
    protected List<Integer> defense = new ArrayList<>();


    public Character(double health, double damage) {
        this.health = health;
        this.damage = damage;
    }

    public boolean isMissed(Character enemyCharacter) {

        int rand = ThreadLocalRandom.current().nextInt(1, 101);
        boolean isMissed = enemyCharacter.evasionChance != 0 && rand < enemyCharacter.evasionChance;
        if (isMissed) System.out.println(this.getClass().getSimpleName() + "missed");
        return isMissed;
    }

    public void hit(Character enemyCharacter) {

        //для каждой атаки чара
        attack.forEach(i -> {
            //если враг не защищен от этой атаки
            if (!enemyCharacter.defense.contains(i) && !this.isMissed(enemyCharacter))
                enemyCharacter.setHealth(enemyCharacter.getHealth() - this.getDamage());
        });

    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public List<Integer> getAttack() {
        return attack;
    }

    public List<Integer> getDefense() {
        return defense;
    }

    public abstract void Attack();

    public abstract void Defence();


    protected int getRandomPart() {
        return ThreadLocalRandom.current().nextInt(0, 5);

    }

    protected int getAnotherRandomPart(int old) {
        int n = ThreadLocalRandom.current().nextInt(0, 5);
        if (n == old) return getAnotherRandomPart(old);
        else return n;

    }

    public void resetAttackAndDef() {
        attack = new ArrayList<>();
        defense = new ArrayList<>();
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "health=" + health +
                ", damage=" + damage +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }


    public void reset() {
        defense = new ArrayList<>();
        attack = new ArrayList<>();
        setHealth(50);
    }
}

class Knight extends Character {

    public Knight(double health, double damage, int evasion) {
        super(health, damage);
        this.evasionChance = evasion;

    }

    @Override
    public void Attack() {
        int first = getRandomPart();
        attack.add(first);

    }

    @Override
    public void Defence() {
        int first = getRandomPart();
        int second = getAnotherRandomPart(first);
        defense.add(first);
        defense.add(second);
    }


}

class Berserk extends Character {


    public Berserk(double health, double damage, int evasion) {
        super(health, damage);
        this.evasionChance = evasion;
    }

    @Override
    public void Attack() {
        int first = getRandomPart();
        int second = getAnotherRandomPart(first);
        attack.add(first);
        attack.add(second);
    }

    @Override
    public void Defence() {

    }


}

enum Parts {
    HEAD, BODY, LEG, R_ARM, L_ARM
}

*/
