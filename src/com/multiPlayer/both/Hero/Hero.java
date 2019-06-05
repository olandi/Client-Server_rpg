package com.multiPlayer.both.Hero;


import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

public class Hero  implements Serializable {
    private String name;
    private int health;
    private int damage;
    private TurnState turnState;
    private int speed;
    private int attackRange;

    private URL view;


    public int getSpeed() {
        return speed;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Hero setView(URL view) {
        this.view = view;
        return this;
    }

    public URL getView() {
        return view;
    }

    public Hero(String name) {
        this.name = name;
        this.turnState = TurnState.ReadyForTurn;
        this.health = 50;
        this.damage = 20;
        this.speed = 1;
        this.attackRange = 1;

    }


    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(TurnState turnState){
        this.turnState = turnState;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", damage=" + damage +
                ", turnState=" + turnState +
                ", view=" + view +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return Objects.equals(name, hero.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
