package com.singlePlayer.company.game.Hero;


import java.io.Serializable;
import java.net.URL;

public class Hero  implements Serializable {
    private String name;
    private int health;
    private int damage;
    private TurnState turnState;

    //private String view = "d:/default.png";
    private URL view;

    public void setHealth(int health) {
        this.health = health;
    }

    public void setView(URL view) {
        this.view = view;
    }

    public URL getView() {
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

/*    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", damage=" + damage +
                ", turnState=" + turnState +
                ", view='" + view + '\'' +
                '}';
    }*/

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

}
