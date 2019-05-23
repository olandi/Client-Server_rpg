package com.company.heroActions;

import com.company.GameFieldGUI;
import com.company.gameField.GameField;
import com.company.gameField.HexagonItem;

public class MoveHero implements HeroAction {

    private HexagonItem hero;
    private HexagonItem target;

    public MoveHero(HexagonItem hero, HexagonItem target) {
        this.hero = hero;
        this.target = target;
    }

    @Override
    public void perform(/*HexagonItem hero, HexagonItem target*/) {
        GameField.moveHero(hero,target);
    }
}
