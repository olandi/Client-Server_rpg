package com.company.model.heroActions;

import com.company.model.Hero.Hero;
import com.company.model.gameField.GameField;
import com.company.model.gameField.HexagonItem;

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
