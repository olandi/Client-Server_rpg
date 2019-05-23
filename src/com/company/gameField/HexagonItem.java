package com.company.gameField;

import com.company.Hero.EmptyCell;
import com.company.Hero.FieldItem;
import com.company.ex2.Hexagon;

public class HexagonItem {
    private Hexagon hexagon;
    private FieldItem cellContent;


    public HexagonItem(Hexagon hexagon,int cellIndex){
        this.hexagon = hexagon;
        this.cellContent = new EmptyCell(cellIndex);
    }

    public void setHexagon(Hexagon hexagon) {
        this.hexagon = hexagon;
    }

    public void setCellContent(FieldItem cellContent) {
        this.cellContent = cellContent;
    }

    public Hexagon getHexagon() {
        return hexagon;
    }

    public FieldItem getCellContent() {
        return cellContent;
    }
}
