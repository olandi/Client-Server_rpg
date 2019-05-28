package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Sumilation {
    public static final int KNIGHT_HEALTH = 50;
    public static final int BERSERK_HEALTH = 50;
    public static final int KNIGHT_DAMAGE = 10;
    public static final int BERSERK_DAMAGE = 10;


    private String fightOneRound(Character firstCharacter, Character secondCharacter) {

        firstCharacter.Attack();
        firstCharacter.Defence();
        secondCharacter.Attack();
        secondCharacter.Defence();

        firstCharacter.hit(secondCharacter);
        secondCharacter.hit(firstCharacter);

        System.out.println(firstCharacter);
        System.out.println(secondCharacter);


        firstCharacter.resetAttackAndDef();
        secondCharacter.resetAttackAndDef();

        return "";
    }

    public Character runSimulation(Character firstCharacter, Character secondCharacter) {
        //пока есть хотя бы один живой

        System.out.println(firstCharacter);
        System.out.println(secondCharacter);
        do {
            fightOneRound(firstCharacter, secondCharacter);
        } while (firstCharacter.getHealth() > 0 && secondCharacter.getHealth() > 0);


        if (firstCharacter.getHealth() > 0) {
            System.out.println(firstCharacter.getClass().getSimpleName() + "WIN !!!!!");
            return firstCharacter;
        }
        if (secondCharacter.getHealth() > 0) {
            System.out.println(secondCharacter.getClass().getSimpleName() + "WIN !!!!!");
            return secondCharacter;
        }
        if (firstCharacter.getHealth() <= 0 && secondCharacter.getHealth() <= 0) {
            System.out.println("Both Died");
            return null;
        }

        return null;

    }


    public static void main(String[] args) {


        Sumilation sumilation = new Sumilation();
        Knight knight = new Knight(KNIGHT_HEALTH, KNIGHT_DAMAGE);
        Berserk berserk = new Berserk(BERSERK_HEALTH, BERSERK_DAMAGE);


        HashMap<String, Integer> map = new HashMap<>();

        int knightCount = 0;
        int bersCount = 0;

        String winner ="";
        for (int i = 0; i < 1000; i++) {
            Character character = sumilation.runSimulation(knight, berserk);


            if (character!=null) winner = character.getClass().getSimpleName();


           if ("Knight".equals(winner)) knightCount++;
            if ("Berserk".equals(winner)) bersCount++;

            knight.reset();
            berserk.reset();

        }

        System.out.println("knight "+knightCount);
        System.out.println("bers "+bersCount);

    }
}

abstract class Character {
    private int health;
    private int damage;

    protected List<Integer> attack = new ArrayList<>();
    protected List<Integer> defense = new ArrayList<>();


    public Character(int health, int damage) {
        this.health = health;
        this.damage = damage;
    }

    public void hit(Character character) {

        //для каждой атаки чара
        attack.forEach(i -> {
            //если враг не защищен от этой атаки
            if (!character.defense.contains(i))
                character.setHealth(character.getHealth() - this.getDamage());
        });

    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
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

    public Knight(int health, int damage) {
        super(health, damage);
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

    public Berserk(int health, int damage) {
        super(health, damage);
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

