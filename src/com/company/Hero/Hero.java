package com.company.Hero;

public class Hero extends FieldItem{
    private String name;
    private int health;
    private int damage;
    private TurnState turnState;

    private String view = "d:/default.png";

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public Hero(String name) {
        this.name = name;
        this.turnState = TurnState.ReadyForTurn;
        this.health = 50;
        this.damage = 20;

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
                ", view='" + view + '\'' +
                '}';
    }

    @Override
    public String getContentType() {
        return "Hero";
    }
}
