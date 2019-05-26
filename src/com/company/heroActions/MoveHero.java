package com.company.heroActions;

import com.company.GameFieldGUI;
import com.company.Hero.Hero;
import com.company.gameField.GameField;
import com.company.gameField.HexagonItem;

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
