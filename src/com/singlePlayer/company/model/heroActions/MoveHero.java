package com.singlePlayer.company.model.heroActions;

import com.singlePlayer.company.model.Hero.Hero;
import com.singlePlayer.company.model.gameField.GameField;
import com.singlePlayer.company.model.gameField.HexagonItem;

public class MoveHero implements HeroAction {

    private Hero hero;
    private HexagonItem target;

    public MoveHero(Hero hero, HexagonItem target) {
        this.hero = hero;
        this.target = target;
    }

    @Override
    public void perform(/*HexagonItem hero, HexagonItem target*/) {
        GameField.moveHero(hero,target);
    }
}
