package com.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SumilationINT {
    public static final int KNIGHT_DAMAGE = 40;
    public static final int KNIGHT_HEALTH = 480;
    public static final int KNIGHT_EVASION = 0;

    public static final int BERSERK_DAMAGE = 50;
    public static final int BERSERK_HEALTH = 480;
    public static final int BERSERK_EVASION = 0;

    public static final int LOOPS = 10000;

    public static HashMap<Integer,Integer> turns = new HashMap<>();

    private String fightOneRound(Character firstCharacter, Character secondCharacter) {

        firstCharacter.Attack();
        firstCharacter.Defence();
        secondCharacter.Attack();
        secondCharacter.Defence();

        //System.out.println(firstCharacter);
       // System.out.println(secondCharacter);

        firstCharacter.hit(secondCharacter);
        secondCharacter.hit(firstCharacter);


        firstCharacter.resetAttackAndDef();
        secondCharacter.resetAttackAndDef();

        return "";
    }

    public Character runSimulation(Character firstCharacter, Character secondCharacter) {
        //пока есть хотя бы один живой

        int i = 0;
        do {
            fightOneRound(firstCharacter, secondCharacter);
            i++;
        } while (firstCharacter.getHealth() > 0 && secondCharacter.getHealth() > 0);


       // turns.put(i,turns.computeIfPresent())

        if (firstCharacter.getHealth() > 0 && secondCharacter.getHealth() <= 0) {
           // System.out.println(firstCharacter.getClass().getSimpleName() + "WIN !!!!!");

           /* turns.putIfAbsent(i, 0);
            turns.computeIfPresent(i,((key, value) -> value+1));*/


            return firstCharacter;
        } else if (secondCharacter.getHealth() > 0 && firstCharacter.getHealth() <= 0) {
          //  System.out.println(secondCharacter.getClass().getSimpleName() + "WIN !!!!!");

            turns.putIfAbsent(i, 0);
            turns.computeIfPresent(i,((key, value) -> value+1));

            return secondCharacter;
        } else
        {
            //urns.putIfAbsent(i, 0);
           // turns.computeIfPresent(i,((key, value) -> value+1));


            //  if (firstCharacter.getHealth() <= 0 && secondCharacter.getHealth() <= 0) { удалить
           // System.out.println("Both Died");
        return null;
           }


    }


    public static void main(String[] args) {


        SumilationINT sumilation = new SumilationINT();
        Knight knight = new Knight(KNIGHT_HEALTH, KNIGHT_DAMAGE, KNIGHT_EVASION);
        Berserk berserk = new Berserk(BERSERK_HEALTH, BERSERK_DAMAGE, BERSERK_EVASION);


        HashMap<String, Integer> map = new HashMap<>();

        int knightCount = 0;
        int bersCount = 0;
        int bothDeadCount = 0;

        String winner = "";
        for (int i = 0; i < LOOPS; i++) {
           // System.out.println(knight);
          //  System.out.println(berserk);
            Character character = sumilation.runSimulation(knight, berserk);

            if (character != null) {

                winner = character.getClass().getSimpleName();
                if ("Knight".equals(winner)) knightCount++;
                if ("Berserk".equals(winner)) bersCount++;

            } else bothDeadCount++;

            knight.reset();
            berserk.reset();

        }
        System.out.println();
        System.out.println("knight " + knightCount + ". Процент побед: " + knightCount * 1.0 / (knightCount + bersCount) * 100);
        System.out.println("bers " + bersCount + ". Процент побед: " + bersCount * 1.0 / (knightCount + bersCount) * 100);
        System.out.println("Разброс рандома: " + Math.abs(knightCount - bersCount));
        System.out.println("BothDead " + bothDeadCount);

        System.out.println(knightCount+bersCount+bothDeadCount+" total check: ");

        final int a = bersCount;
        final int b = bothDeadCount;
       // System.out.println(turns);
      //  turns.forEach((key,value)->System.out.print(key+" : "+(value*1.0 )+" --> "+value*1.0/a*100+"%\n"));

        turns.forEach((key,value)->System.out.print(key+" : "+(value*1.0 )+" --> "+value*1.0/(LOOPS-b)*100+"%\n"));
    }
}

abstract class Character {
    private int health;
    private int damage;
    int evasionChance;

    protected List<Integer> attack = new ArrayList<>();
    protected List<Integer> defense = new ArrayList<>();


    public Character(int health, int damage) {
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


    public abstract void reset();
}

class Knight extends Character {

    public Knight(int health, int damage, int evasion) {
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

    @Override
    public void reset() {
        defense = new ArrayList<>();
        attack = new ArrayList<>();
        setHealth(SumilationINT.KNIGHT_HEALTH);
    }


}

class Berserk extends Character {


    public Berserk(int health, int damage, int evasion) {
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

    @Override
    public void reset() {
        defense = new ArrayList<>();
        attack = new ArrayList<>();
        setHealth(SumilationINT.BERSERK_HEALTH);
    }


}

enum Parts {
    HEAD, BODY, LEG, R_ARM, L_ARM
}

